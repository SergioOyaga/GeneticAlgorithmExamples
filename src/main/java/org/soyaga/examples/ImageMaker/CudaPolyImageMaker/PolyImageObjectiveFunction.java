package org.soyaga.examples.ImageMaker.CudaPolyImageMaker;

import jcuda.Pointer;
import jcuda.Sizeof;
import jcuda.driver.CUdeviceptr;
import jcuda.jcublas.JCublas2;
import jcuda.jcublas.cublasHandle;
import org.soyaga.examples.ImageMaker.CudaPolyImageMaker.Kernel.ColorDifKernel;
import org.soyaga.ga.Evaluable.Objective.ObjectiveFunction;
import org.soyaga.ga.GeneticInformationContainer.Genome.Genome;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import static java.lang.Math.min;
import static jcuda.driver.JCudaDriver.cuMemAlloc;
import static jcuda.driver.JCudaDriver.cuMemcpyHtoD;
import static jcuda.jcublas.JCublas2.cublasCreate;

/**
 * This class Evaluates the objective function to an individuals Genome. This is how
 * well adapted is an individuals' genome to its environment (closer to the optimal solution).
 */
public class PolyImageObjectiveFunction implements ObjectiveFunction {
    /**
     * Array with the color of each pixel in the image in sRGB color space.
     */
    private final int[] imageArray;
    /**
     * Integer with the width of the image, used to create teh images from the genomes.
     */
    private final int width;
    /**
     * Integer with the height of the image, used to create teh images from the genomes.
     */
    private final int height;
    /**
     * Integer with the iteration when to start penalizing the number of polygons in the genome.
     * In the beginning of the optimization we allow the addition of new polygons because we want to find new suitable
     * polygons to represent the image in some good enough way. So in the iterations before the threshold there is no
     * penalization, but after that iteration, two exact same images  composed with different number of polygons will
     * have different fitnesses and the one with more polygons will be penalized.
     * This approach keeps the execution time controlled and the number of polygons on the genome will not explode.
     * On the other hand, we are compromising the definition of the result, because small details that could be covered
     * by small polygons will never be covered (because the penalization of adding this new polygon is bigger than the
     * reward of placing it). So, there is a tradeoff between these two concepts.
     */
    private final int threshold;

    /**
     * ColorDifKernel class tht contains the structures used to perform a computation in the GPU Cuda kernel
     */
    private final ColorDifKernel colorDifKernel;

    /**
     * cublasHandle class that is used in by the cuBLAS (CUDA Basic Linear Algebra Subroutine).
     */
    cublasHandle handle;
    /**
     * Pointer that references the device memory-chunk with the original image.
     */
    CUdeviceptr deviceReferenceImage;
    /**
     * Pointer that references the device memory-chunk with the vector that contains the distance for each pixel.
     */
    CUdeviceptr deviceInternalDifferenceVector;
    /**
     * Pointer that references the device memory-chunk with the poly-image.
     */
    CUdeviceptr devicePolyImage;


    /**
     * Constructor. Returns a new ShapeImageObjectiveFunction.
     * @param image BufferedImage with the image we want to use as reference.
     * @param threshold Iteration when we start to penalize long genomes.
     */
    public PolyImageObjectiveFunction(BufferedImage image, Integer threshold, String cudaFileNamePath) throws IOException {
        this.width = image.getWidth();
        this.height = image.getHeight();
        this.imageArray = image.getRGB(0,0,this.width,this.height,null,0,this.width);
        this.threshold = threshold;

        this.colorDifKernel = new ColorDifKernel(cudaFileNamePath);

        this.handle = new cublasHandle();
        cublasCreate(handle);

        this.deviceReferenceImage = new CUdeviceptr();
        cuMemAlloc(this.deviceReferenceImage, (long) this.width *this.height * Sizeof.INT);
        cuMemcpyHtoD(this.deviceReferenceImage, Pointer.to(this.imageArray),
                (long) this.width *this.height * Sizeof.INT);

        this.deviceInternalDifferenceVector = new CUdeviceptr();
        cuMemAlloc(this.deviceInternalDifferenceVector, (long) this.width *this.height * Sizeof.INT);
        cuMemcpyHtoD(this.deviceInternalDifferenceVector, Pointer.to(this.imageArray),
                (long) this.width *this.height * Sizeof.INT);

        this.devicePolyImage = new CUdeviceptr();
        cuMemAlloc(this.devicePolyImage, (long) this.width *this.height * Sizeof.INT);
        cuMemcpyHtoD(this.devicePolyImage, Pointer.to(this.imageArray),
                (long) this.width *this.height * Sizeof.INT);

        fillKernel();
    }

    /**
     * Function that computes the kernel sizes (blockSizeX and gridSizeX) and fills the actual kernel class with them.
     */
    private void fillKernel() {
        // Compute the grids/blocks sizes for the problem.
        int blockSizeX = min(this.width*this.height,1024); // number of threads in the x block direction
        int gridSizeX = (this.width*this.height+blockSizeX-1)/blockSizeX; // number of blocks in the x grid direction
        // Fill the kernel class with all the information
        this.colorDifKernel.fillKernel(gridSizeX,1,1,blockSizeX,1,1,0,
                null,null);
    }

    /**
     * Function that computes how good is this individuals' Genome. It computes the objective value by deflecting
     * the computation to the GPU.
     * @param genome Genome object to evaluate.
     * @param objects VarArgs Object that allow to keep/retain information from the evaluation to be used in the
     *                 decision-making.
     * @return a Double containing the value of the objective function to this individual. In this case,
     *                     an Integer with the iteration number.
     */
    @Override
    public Double evaluate(Genome genome, Object... objects) {
        Integer iteration = (Integer) objects[0];
        double longGenomePenalization =iteration>=this.threshold?0.1:0;
        CustomGenome genomeObject = (CustomGenome) genome;
        ArrayList<Object> genome_list = genomeObject.getGeneticInformation();

        BufferedImage image = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics2D = image.createGraphics();
        Color background = (Color) genome_list.get(0);
        ArrayList<CustomChromosome> chromosome_set = (ArrayList<CustomChromosome>) genome_list.get(1);
        graphics2D.setBackground(background);
        graphics2D.clearRect(0, 0, image.getWidth(),image.getHeight());
        Integer alphaCounter = 0; // Counter to penalize having transparent polygons
        for(CustomChromosome chromosome:chromosome_set) {
            Color polygonColor = (Color) chromosome.getGeneticInformation().get(0);
            Polygon polygon = (Polygon) chromosome.getGeneticInformation().get(1);
            alphaCounter+=polygonColor.getAlpha()==255?1:0;
            graphics2D.setColor(polygonColor);
            graphics2D.fillPolygon(polygon);
        }
        graphics2D.dispose();
        int[] polyImageArray = image.getRGB(0,0,this.width,this.height,null,0,this.width);
        // Computation in the CUDA kernel
        cuMemcpyHtoD(this.devicePolyImage, Pointer.to(polyImageArray),
                (long) this.width *this.height * Sizeof.INT);
        this.colorDifKernel.runKernel( polyImageArray.length,this.deviceReferenceImage, this.devicePolyImage,
                this.deviceInternalDifferenceVector);
        double[] result = new double[1];
        JCublas2.cublasDasum(this.handle,this.width *this.height,this.deviceInternalDifferenceVector,1,
                Pointer.to(result));

        return result[0] + 1000*alphaCounter + longGenomePenalization*chromosome_set.size();
    }
}
