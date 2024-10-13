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
 * In this case, the optimization performs an image recreation using different shapes. The shapes implemented are:
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
        CustomGeneticAlgorithm ga = new CustomGeneticAlgorithm(
                "CustomShapeImageMaker",                    // ID.
                initialPopulationSize,                          // Initial population size.
                new MaxIterationCriteriaPolicy(                 // Stopping policy, max iterations.
                        maxGenerations                              // Max iterations.
                ),
                new FixedCrossoverPolicy(                       // Crossover policy, fixed number.
                        initialPopulationSize*90/100,               // Number of crossed individuals.
                        new TournamentSelection(),                  // Selection, tournament.
                        new CustomCrossover()),                     // Custom Crossover.
                new CustomMutationPolicy(                       // Custom Mutation policy.
                        new ArrayList<>(){{                         // Array of Mutations to the genome.
                            add(new GenomeMutationBackground(           // Mutation, change background color.
                                    maxGenerations*30/100,                  // Max number of iterations that this mutation applies.
                                    10));                                   // Number of units to change the RGB value.
                            add(new GenomeMutationShapeOrder(           // Mutation, swap shape order.
                                    maxGenerations*50/100));                // Max number of iterations that this mutation applies.
                            add(new GenomeMutationAddChromosome(        // Mutation, add new shape.
                                    image.getWidth(), image.getHeight(),    // Size of the image.
                                    maxGenerations*90/100,                  // Max number of iterations that this mutation applies.
                                    availableShapes,                        // Available shapes.
                                    limitations));                          // Limitations of the shapes.
                            add(new GenomeMutationRemoveChromosome(     // Mutation, removes a shape.
                                    maxGenerations));                       // Max number of iterations that this mutation applies.
                        }},
                        new ArrayList<>(){{                             // Array of Mutations to the chromosome.
                            add(new ChromosomeMutationColor(                // Mutation, change shape color.
                                    maxGenerations*90/100,                      // Max number of iterations that this mutation applies.
                                    10));                                       // Number of units to change the RGB value.
                            add(new ChromosomeMutationMoveShape(            // Mutation, move the shape.
                                    image.getWidth(), image.getHeight(),        // Size of the image.
                                    maxGenerations*80/100,                      // Iterations when the move decreases in size.
                                    10,                                         // Number of pixels to move the shape in the small phase.
                                    20));                                       // Number of pixels to move the shape in the big phase.
                            add(new ChromosomeMutationRotateShape(          // Mutation, rotate the shape.
                                    maxGenerations,                             // Max number of iterations that this mutation applies.
                                    0.18));                                     // Max arch radians that the shape can rotate.
                            add(new ChromosomeMutationChangeShapeType(      // Mutation, change the shape type.
                                    maxGenerations*80/100,                      // Max number of iterations that this mutation applies.
                                    availableShapes,                            // Available shapes.
                                    limitations));                              // Limitations of the shapes.
                        }},
                        new double[]{0.02,0.05,0.08,0.05},              // Probabilities for the Genome Mutations.
                        new double[]{0.001,0.002,0.002, 0.002}),        // Probabilities for the Genome Mutations.
                new FixedElitismPolicy(                             // Elitism policy, fixed number.
                        initialPopulationSize*4/100),                   // Number of elitists.
                new FixedNewbornPolicy(                             // Newborn policy, fixed number.
                        initialPopulationSize*10/100),                  // Number of newborns.
                new ShapeImageInitializer(                          // GAInitializer.
                        initialNumberOfShapes,                          // Initial number of shapes.
                        image.getHeight(), image.getWidth(),            // Size of the image
                        availableShapes,                                // Available shapes.
                        limitations,                                    // Limitations of the shapes.
                        new ShapeImageObjectiveFunction(                // Objective function.
                                image,                                      // Image to compare with.
                                maxGenerations*50/100)),                    // Limit when the number of shapes is minimized.
                image.getWidth(), image.getHeight(),                // Size of the image.
                10                                                  // Number of iterations to Stores a GIF image.
        );
        //Optimize the GeneticAlgorithm
        ga.optimize();
        //Plot the results
        GifCreator.createGif(ga.getBestImages(),outputPath+".gif",10);
        ImageIO.write((BufferedImage)ga.getResult(maxGenerations), "png",  new File(outputPath+".png"));

    }
}
