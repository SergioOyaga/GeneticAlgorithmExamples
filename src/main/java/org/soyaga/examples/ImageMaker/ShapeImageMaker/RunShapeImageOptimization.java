package org.soyaga.examples.ImageMaker.ShapeImageMaker;

import org.soyaga.examples.ImageMaker.ShapeImageMaker.Mutations.*;
import org.soyaga.ga.CrossoverPolicy.FixedCrossoverPolicy;
import org.soyaga.ga.CrossoverPolicy.ParentSelection.TournamentSelection;
import org.soyaga.ga.ElitismPolicy.FixedElitismPolicy;
import org.soyaga.ga.NewbornPolicy.FixedNewbornPolicy;
import org.soyaga.ga.StoppingPolicy.MaxIterationCriteriaPolicy;

import java.awt.*;
import javax.imageio.ImageIO;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Instantiates and optimize a CustomGeneticAlgorithm Object. Fills it with all the Custom GA classes needed to perform
 * the optimization. Optimizes given the previous configuration and prints the result in the console.
 * In this case the optimization performs an image recreation using different shapes. The shapes implemented are:
 * <ul>
 *     <li>Polygon.</li>
 *     <li>Ellipse2D.</li>
 *     <li>Arc2D.</li>
 *     <li>CubicCurve2D.</li>
 *     <li>QuadCurve2D.</li>
 *     <li>RoundRectangle2D.</li>
 * </ul>
 */
public class RunShapeImageOptimization {
    public static void main(String ... args) throws IOException {
        //Integer with the initial population size. The optimization-time population size is NºOfCrossed+NºOfElitist+NºOfNewborns
        int initialPopulationSize = 100;
        //Integer with the initial number of shapes in the new individuals.
        Integer initialNumberOfShapes = 1;
        //Integer with the number of iterations.
        int maxGenerations = 20000;
        //ArrayList with an instance of the shape we want to include in the recreation.
        ArrayList<Shape> availableShapes = new ArrayList<>(){{
            add(new Polygon());
            add(new Ellipse2D.Double());
            add(new Arc2D.Double());
            add(new CubicCurve2D.Double());
            add(new QuadCurve2D.Double());
            add(new RoundRectangle2D.Double());
        }};
        //ArrayList of objects ordered in the same way as the objects in the availableShapes ArrayList. Contains the
        // editable params used to build the objects
        ArrayList<Object[]> limitations = new ArrayList<>(){{
            add(new Object[]{3});   // params for the Polygon maxVertex
            add(new Object[]{50});  // params for the ellipse maxSizeRatio
            add(new Object[]{50});  // params for the Arch2D maxSizeRatio
            add(new Object[]{});    // params for the CubicCurve2D
            add(new Object[]{});    // params for the QuadCurve2D
            add(new Object[]{50});  // params for the RoundRectangle2D maxSizeRatio
        }};
        //String with the path to the image we want to recreate.
        String imageToRecreate = "ImageMaker/dna.png";
        //String with the path to the image we want to recreate, without the .png/.jpg, it is added by default.
        String outputPath = "src/out/ImageMaker/image";


        InputStream stream = RunShapeImageOptimization.class.getClassLoader().getResourceAsStream(imageToRecreate);
        assert stream != null;
        BufferedImage image = ImageIO.read(stream);

        ///////////////////////////////////////////////////////////
        /////// Uncomment if you want to rescale the image. ///////
        /////// Big images can lead to huge execution times.///////
        ///////////////////////////////////////////////////////////
        /*
        BufferedImage image_original = ImageIO.read(stream);
        Image image_scaled = image_original.getScaledInstance(600,600, Image.SCALE_SMOOTH);
        BufferedImage image= new BufferedImage(image_scaled.getWidth(null), image_scaled.getHeight(null),BufferedImage.TYPE_INT_ARGB);
        Graphics2D bGr = image.createGraphics();
        bGr.drawImage(image_scaled, 0, 0, null);
        bGr.dispose();
        */
        ///////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////

        // Instantiate the GeneticAlgorithm.
        // You can see where we put the threshold for each mutation (iteration when they stop to apply), and the actual
        // probabilities of each mutation. You can also see the iteration when the length of the genome starts to
        // penalize the objective function.
        CustomGeneticAlgorithm ga = new CustomGeneticAlgorithm("CustomShapeImageMaker", initialPopulationSize,
                new MaxIterationCriteriaPolicy(maxGenerations),
                new FixedCrossoverPolicy(initialPopulationSize*90/100,
                        new TournamentSelection(),
                        new CustomCrossover()),
                new CustomMutationPolicy(
                        new ArrayList<>(){{
                            add(new GenomeMutationBackground(maxGenerations*30/100, 10));
                            add(new GenomeMutationShapeOrder(maxGenerations*50/100));
                            add(new GenomeMutationAddChromosome(image.getWidth(), image.getHeight(),
                                    maxGenerations*90/100,availableShapes, limitations));
                            add(new GenomeMutationRemoveChromosome(maxGenerations));
                        }},
                        new ArrayList<>(){{
                            add(new ChromosomeMutationColor(maxGenerations*90/100, 10));
                            add(new ChromosomeMutationMoveShape(image.getWidth(), image.getHeight(),
                                    maxGenerations*80/100, 10, 20));
                            add(new ChromosomeMutationRotateShape(maxGenerations,0.18));
                            add(new ChromosomeMutationChangeShapeType(maxGenerations*80/100,availableShapes,limitations));
                        }},
                        new double[]{0.02,0.05,0.08,0.05},new double[]{0.001,0.002,0.002, 0.002}),
                new FixedElitismPolicy(initialPopulationSize*4/100),
                new FixedNewbornPolicy(initialPopulationSize*10/100),
                new ShapeImageInitializer(initialNumberOfShapes, image.getHeight(), image.getWidth(),
                        availableShapes, limitations,
                        new ShapeImageObjectiveFunction(image,maxGenerations*50/100)),
                image.getWidth(), image.getHeight(),10
        );
        //Optimize the GeneticAlgorithm
        ga.optimize();
        //Plot the results
        GifCreator.createGif(ga.getBestImages(),outputPath+".gif",10);
        ImageIO.write((BufferedImage)ga.getResult(), "png",  new File(outputPath+".png"));

    }
}
