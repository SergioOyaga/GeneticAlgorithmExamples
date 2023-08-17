package org.soyaga.examples.ImageMaker.ShapeImageMaker;

import org.soyaga.ga.Evaluable.Objective.ObjectiveFunction;
import org.soyaga.ga.GeneticInformationContainer.Genome.Genome;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


/**
 * This class evaluates the objective function of an individual's genome.
 * It measures how well adapted an individual's genome is to its environment,
 * bringing it closer to the optimal solution.
 */
public class ShapeImageObjectiveFunction implements ObjectiveFunction {
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
     * Integer indicating the iteration at which to begin penalizing the number of shapes in the genome.
     * Initially, during optimization, we allow the addition of new shapes to discover suitable shapes that can
     * adequately represent the image. Hence, in the iterations preceding the specified threshold, no penalization occurs.
     * However, beyond this iteration threshold, two identical images composed of varying shape counts will exhibit
     * distinct fitness levels, with a higher shape count resulting in penalization.
     * This approach maintains controlled execution time and prevents an excessive proliferation of shapes in the genome.
     * However, it also introduces a compromise in the final result's precision. This compromise stems from smaller details
     * that could be captured by smaller shapes remaining uncovered. This is due to the larger penalization associated
     * with introducing a new shape compared to the benefit gained by placing it. Thus, a tradeoff exists between these
     * two concepts.
     */
    private final int threshold;

    /**
     * Constructor. Returns a new ShapeImageObjectiveFunction.
     *
     * @param image BufferedImage with the image we want to use as reference.
     * @param threshold Iteration when we start to penalize long genomes.
     */
    public ShapeImageObjectiveFunction(BufferedImage image, Integer threshold) {
        this.width = image.getWidth();
        this.height = image.getHeight();
        this.imageArray = image.getRGB(0,0,this.width,this.height,null,0,this.width);
        this.threshold = threshold;
    }

    /**
     * This function calculates the quality of an individual's genome by comparing pixel by pixel the real image
     * and the reconstructed one.
     *
     * @param genome The Genome object to be evaluated.
     * @param objects VarArgs Object that retains information from the evaluation for use in decision-making.
     * @return A Double containing the value of the objective function for this individual.In this case,
     *      an Integer with the iteration number.
     */
    @Override
    public Double evaluate(Genome<?> genome, Object... objects) {
        Integer iteration = (Integer) objects[0];
        double longGenomePenalization =iteration>=this.threshold?0.05:0;
        CustomGenome genomeObject = (CustomGenome) genome;
        ArrayList<Object> genome_list = genomeObject.getGeneticInformation();

        BufferedImage image = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics2D = image.createGraphics();
        AffineTransform original = graphics2D.getTransform();
        Color background = (Color) genome_list.get(0);
        ArrayList<CustomChromosome> chromosome_set = (ArrayList<CustomChromosome>) genome_list.get(1);
        graphics2D.setBackground(background);
        graphics2D.clearRect(0, 0, image.getWidth(),image.getHeight());
        int alphaCounter = 0; // Counter to penalize having transparent shapes
        for(CustomChromosome chromosome:chromosome_set) {
            Color shapeColor = (Color) chromosome.getGeneticInformation().get(0);
            Double rotation = (Double) chromosome.getGeneticInformation().get(1);
            Shape shape = (Shape) chromosome.getGeneticInformation().get(2);
            alphaCounter+=shapeColor.getAlpha()==255?1:0;
            graphics2D.setColor(shapeColor);
            AffineTransform at = AffineTransform.getRotateInstance(rotation);
            graphics2D.setTransform(at);
            graphics2D.fill(shape);
            graphics2D.setTransform(original);
        }
        graphics2D.dispose();
        int[] polyImageArray = image.getRGB(0,0,this.width,this.height,null,0,this.width);
        return computeColorDistance(polyImageArray) + 1000*alphaCounter + longGenomePenalization*chromosome_set.size();
    }

    /**
     * Function that computes the color distance of an entire image pixel-wise.
     *
     * @param polyImageArray int[] with the image to compare to.
     * @return Double with the distance (averaged per pixel).
     */
    private Double computeColorDistance(int[] polyImageArray) {
        double distance=0.0;
        for(int i =0;i<this.imageArray.length;i++){
            distance += colorDiffAlpha(this.imageArray[i],polyImageArray[i])/this.imageArray.length;
        }
        return distance;
    }

    /**
     * Function that computes the Euclidean distance (l2-norm) for two colors stored in the default format,
     * TYPE_INT_ARGB, that represents Color as an int (4 bytes) with alpha channel in bits 24-31,
     * red channels in 16-23, green in 8-15 and blue in 0-7.
     *
     * @param color1 Integer with the color1 in TYPE_INT_ARGB format.
     * @param color2 Integer with the color2 in TYPE_INT_ARGB format.
     * @return a double with the l2-norm distance between the two colors.
     */
    private double colorDiffAlpha(int color1, int color2) {
        int color1b = color1 & 0xff;
        int color1g = (color1 & 0xff00) >> 8;
        int color1r = (color1 & 0xff0000) >> 16;
        int color1a = (color1 & 0xff000000) >> 24;
        int color2b = color2 & 0xff;
        int color2g = (color2 & 0xff00) >> 8;
        int color2r = (color2 & 0xff0000) >> 16;
        int color2a = (color2 & 0xff000000) >> 24;

        int dr = color1r - color2r;
        int dg = color1g - color2g;
        int db = color1b - color2b;
        int da = color1a - color2a;

        return Math.sqrt(dr * dr + dg * dg + db * db + da * da);
    }

}
