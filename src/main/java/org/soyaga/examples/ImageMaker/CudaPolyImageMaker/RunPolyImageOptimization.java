package org.soyaga.examples.ImageMaker.CudaPolyImageMaker;

import org.soyaga.examples.ImageMaker.CudaPolyImageMaker.Mutations.*;
import org.soyaga.ga.CrossoverPolicy.FixedCrossoverPolicy;
import org.soyaga.ga.CrossoverPolicy.ParentSelection.TournamentSelection;
import org.soyaga.ga.ElitismPolicy.FixedElitismPolicy;
import org.soyaga.ga.NewbornPolicy.FixedNewbornPolicy;
import org.soyaga.ga.StoppingPolicy.MaxIterationCriteriaPolicy;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Instantiates and optimize a CustomGeneticAlgorithm Object. Fills it with all the Custom GA classes needed to perform
 * the optimization. Optimizes given the previous configuration and prints the result in the console.
 */
public class RunPolyImageOptimization {
    public static void main(String ... args) throws IOException {
        //Integer with the initial population size. The optimization-time population size is NºOfCrossed+NºOfElitist+NºOfNewborns
        int initialPopulationSize = 50;
        //Integer with the initial number of polygons in the new individuals.
        Integer initialNumberOfPolygons = 1;
        //Integer with the number of vertexes on the polygons.
        Integer numberOfVertexes = 3;
        //Integer with the number of iterations.
        int maxGenerations = 4000;
        //String with the path to the image we want to recreate.
        String imageToRecreate = "ImageMaker/big_landscape.png";
        //String with the path to the image we want to recreate, without the .png/.jpg, it is added by default.
        String outputPath = "src/out/ImageMaker/image";
        //String with the path to the image we want to recreate.
        String cudaFileNamePath = "src/main/java/org/soyaga/examples/ImageMaker/CudaPolyImageMaker/Kernel/JCudaColorDistance.cu";

        InputStream stream = RunPolyImageOptimization.class.getClassLoader().getResourceAsStream(imageToRecreate);
        assert stream != null;
        BufferedImage image = ImageIO.read(stream);

        // Instantiate the GeneticAlgorithm.
        // You can see where we put the threshold for each mutation (iteration when they stop to apply), and the actual
        // probabilities of each mutation. You can also see the iteration when the length of the genome starts to
        // penalize the objective function.
        CustomGeneticAlgorithm ga = new CustomGeneticAlgorithm(
                "CustomPolyImageMaker",                     // ID.
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
                                    maxGenerations*50/100,                  // Max number of iterations that this mutation applies.
                                    10));                           // Number of units to change the RGB value.
                            add(new GenomeMutationPolygonOrder(         // Mutation, swap polygon order.
                                    maxGenerations*60/100));                // Max number of iterations that this mutation applies.
                            add(new GenomeMutationAddChromosome(        // Mutation, add new polygon.
                                    image.getWidth(), image.getHeight(),    // Size of the image.
                                    maxGenerations*70/100,                  // Max number of iterations that this mutation applies.
                                    numberOfVertexes));                     // Number of vertexes for the polygon.
                            add(new GenomeMutationRemoveChromosome(     // Mutation, removes a polygon.
                                    maxGenerations*80/100));                // Max number of iterations that this mutation applies.
                        }},
                        new ArrayList<>(){{                         // Array of Mutations to the chromosome.
                            add(new ChromosomeMutationColor(            // Mutation, change polygon color.
                                    maxGenerations*90/100,                  // Max number of iterations that this mutation applies.
                                    10));                                   // Number of units to change the RGB value.
                            add(new ChromosomeMutationMoveOneVertex(    // Mutation, move one vertex of the polygon.
                                    image.getWidth(), image.getHeight(),    // Size of the image.
                                    maxGenerations*80/100,                  // Iterations when the move decreases in size.
                                    10,                                     // Number of pixels to move the vertex in the small phase.
                                    20));                                   // Number of pixels to move the vertex in the big phase.
                        }},
                        new double[]{0.02,0.05,0.08,0.05},          // Probabilities for the Genome Mutations.
                        new double[]{0.01,0.02}),                   // Probabilities for the Genome Mutations.
                new FixedElitismPolicy(                         // Elitism policy, fixed number.
                        initialPopulationSize*4/100),               // Number of elitists.
                new FixedNewbornPolicy(                         // Newborn policy, fixed number.
                        initialPopulationSize*10/100),              // Number of newborns.
                new PolyImageInitializer(                       // GAInitializer.
                        initialNumberOfPolygons,                    // Initial number of polygons.
                        numberOfVertexes,                           // Number of vertexes for each polygon.
                        image.getHeight(), image.getWidth(),        // Size of the image
                        new PolyImageObjectiveFunction(             // Objective function.
                                image,                                  // Image to compare with.
                                maxGenerations*95/100,                  // Limit when the number of polygons is minimized.
                                cudaFileNamePath)),                     // String where to look for the Cuda C code.
                image.getWidth(), image.getHeight(),            // Size of the image
                2                                                // Number of iterations to Stores a GIF image.
        );
        //Optimize the GeneticAlgorithm
        ga.optimize();
        //Plot the results
        GifCreator.createGif(ga.getBestImages(),outputPath+".gif",5);
        ImageIO.write((BufferedImage)ga.getResult(maxGenerations), "png",  new File(outputPath+".png"));

    }
}
