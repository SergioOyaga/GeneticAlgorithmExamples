package org.soyaga.examples.PolyImageMaker;

import org.soyaga.ga.Evaluable.ObjectiveFunction;
import org.soyaga.ga.GeneticInformationContainer.Genome.Genome;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * This class Evaluates the objective function to an individuals Genome. This is how
 * well adapted is an individuals' genome to its environment (closer to the optimal solution).
 */
public class PolyImageObjectiveFunction extends ObjectiveFunction {
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
     * Constructor. Returns a new PolyImageObjectiveFunction.
     * @param image BufferedImage with the image we want to use as reference.
     * @param threshold Iteration when we start to penalize long genomes.
     */
    public PolyImageObjectiveFunction(BufferedImage image, Integer threshold) {
        this.width = image.getWidth();
        this.height = image.getHeight();
        this.imageArray = image.getRGB(0,0,this.width,this.height,null,0,this.width);
        this.threshold = threshold;
    }

    /**
     * Function that computes how good is this individuals' Genome.
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
        return computeColorDistance(polyImageArray) + 1000*alphaCounter + longGenomePenalization*chromosome_set.size();
    }

    /**
     * Function that computes the colour distance of an entire image pixel-wise.
     * @param polyImageArray int[] with the image to compare to.
     * @return Double with the distance (averaged per pixel).
     */
    private Double computeColorDistance(int[] polyImageArray) {
        Double distance=0.0;
        for(int i =0;i<this.imageArray.length;i++){
            // We normalize inside here because for big images, the distance overflows the precision. (bad for performance)
            // we could take this computation to the GPU and the multi-thread will do its magic, improving drastically
            // the performance.
            distance += colorDiffAlpha(this.imageArray[i],polyImageArray[i])/this.imageArray.length;
        }
        return distance;
    }

    /**
     * Function that computes the Euclidean distance (l2-norm) for two colours stored in the default format,
     * TYPE_INT_ARGB, that represents Color as an int (4 bytes) with alpha channel in bits 24-31,
     * red channels in 16-23, green in 8-15 and blue in 0-7.
     * @param color1 Integer with the color1 in TYPE_INT_ARGB format.
     * @param color2 Integer with the color2 in TYPE_INT_ARGB format.
     * @return a double with the l2-norm distance between the two colours.
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
