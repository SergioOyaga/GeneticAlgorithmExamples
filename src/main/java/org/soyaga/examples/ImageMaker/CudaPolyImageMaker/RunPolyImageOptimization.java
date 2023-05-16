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
        CustomGeneticAlgorithm ga = new CustomGeneticAlgorithm("CustomPolyImageMaker", initialPopulationSize,
                new MaxIterationCriteriaPolicy(maxGenerations),
                new FixedCrossoverPolicy(initialPopulationSize*90/100,
                        new TournamentSelection(),
                        new CustomCrossover()),
                new CustomMutationPolicy(
                        new ArrayList<>(){{
                            add(new GenomeMutationBackground(maxGenerations*50/100, 10));
                            add(new GenomeMutationPolygonOrder(maxGenerations*60/100));
                            add(new GenomeMutationAddChromosome(image.getWidth(), image.getHeight(), maxGenerations*70/100,numberOfVertexes));
                            add(new GenomeMutationRemoveChromosome(maxGenerations*80/100));
                        }},
                        new ArrayList<>(){{
                            add(new ChromosomeMutationColor(maxGenerations*90/100, 10));
                            add(new ChromosomeMutationMoveOneVertex(image.getWidth(), image.getHeight(),maxGenerations*80/100, 10, 20));
                        }},
                        new double[]{0.02,0.05,0.08,0.05},new double[]{0.01,0.02}),
                new FixedElitismPolicy(initialPopulationSize*4/100),
                new FixedNewbornPolicy(initialPopulationSize*10/100),
                new PolyImageInitializer(initialNumberOfPolygons, numberOfVertexes, image.getHeight(), image.getWidth(),
                        new PolyImageObjectiveFunction(image,maxGenerations*95/100,cudaFileNamePath)),
                image.getWidth(), image.getHeight(),2
        );
        //Optimize the GeneticAlgorithm
        ga.optimize();
        //Plot the results
        GifCreator.createGif(ga.getBestImages(),outputPath+".gif",5);
        ImageIO.write((BufferedImage)ga.getResult(), "png",  new File(outputPath+".png"));

    }
}
