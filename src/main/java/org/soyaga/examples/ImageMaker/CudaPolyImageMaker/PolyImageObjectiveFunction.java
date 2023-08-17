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
 * This class evaluates the objective function of an individual's genome.
 * It measures how well adapted an individual's genome is to its environment,
 * bringing it closer to the optimal solution.
 */
public class PolyImageObjectiveFunction implements ObjectiveFunction {
    /**
     * Array with the color of each pixel in the image in sRGB color space.
     */
    private final int[] imageArray;
    /**
     * Integer with the width of the image, used to create the images from the genomes.
     */
    private final int width;
    /**
     * Integer with the height of the image, used to create the images from the genomes.
     */
    private final int height;
    /**
     * Integer indicating the iteration at which to begin penalizing the number of polygons in the genome.
     * Initially, during optimization, we allow the addition of new polygons to discover suitable shapes that can
     * adequately represent the image. Hence, in the iterations preceding the specified threshold, no penalization occurs.
     * However, beyond this iteration threshold, two identical images composed of varying polygon counts will exhibit
     * distinct fitness levels, with a higher polygon count resulting in penalization.
     * This approach maintains controlled execution time and prevents an excessive proliferation of polygons in the genome.
     * However, it also introduces a compromise in the final result's precision. This compromise stems from smaller details
     * that could be captured by smaller polygons remaining uncovered. This is due to the larger penalization associated
     * with introducing a new polygon compared to the benefit gained by placing it. Thus, a tradeoff exists between these
     * two concepts.
     */
    private final int threshold;
    /**
     * ColorDifKernel class that contains the structures used to perform a computation in the GPU Cuda kernel.
     */
    private final ColorDifKernel colorDifKernel;
    /**
     * cublasHandle class that is used by the cuBLAS (CUDA Basic Linear Algebra Subroutine).
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
     * Constructor. Returns a new PolyImageObjectiveFunction.
     *
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
     * This function calculates the quality of an individual's genome by comparing pixel by pixel the real image
     * and the reconstructed one. It computes the objective value by deflecting
     * the computation to the GPU.
     *
     * @param genome The Genome object to be evaluated.
     * @param objects VarArgs Object that retains information from the evaluation for use in decision-making.
     * @return A Double containing the value of the objective function for this individual. In this case,
     *      an Integer with the iteration number.
     */
    @Override
    public Double evaluate(Genome<?> genome, Object... objects) {
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
        int alphaCounter = 0; // Counter to penalize having transparent polygons
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
