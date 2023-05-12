package org.soyaga.examples.PolyImageMaker.SimplePolyImageMaker;

import org.soyaga.examples.PolyImageMaker.SimplePolyImageMaker.Mutations.*;
import org.soyaga.ga.CrossoverPolicy.FixedCrossoverPolicy;
import org.soyaga.ga.CrossoverPolicy.ParentSelection.TournamentSelection;
import org.soyaga.ga.ElitismPolicy.FixedElitismPolicy;
import org.soyaga.ga.NewbornPolicy.FixedNewbornPolicy;
import org.soyaga.ga.StoppingPolicy.MaxIterationCriteriaPolicy;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
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
        Integer numberOfVertexes = 7;
        //Integer with the number of iterations.
        int maxGenerations = 20000;
        //String with the path to the image we want to recreate.
        String imageToRecreate = "PolyImageMaker/monalisa.png";
        //String with the path to the image we want to recreate, without the .png/.jpg, it is added by default.
        String outputPath = "src/out/PolyImageMaker/image";


        InputStream stream = RunPolyImageOptimization.class.getClassLoader().getResourceAsStream(imageToRecreate);
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
                        new PolyImageObjectiveFunction(image,maxGenerations*95/100)),
                image.getWidth(), image.getHeight(),10
        );
        //Optimize the GeneticAlgorithm
        ga.optimize();
        //Plot the results
        GifCreator.createGif(ga.getBestImages(),outputPath+".gif",5);
        ImageIO.write((BufferedImage)ga.getResult(), "png",  new File(outputPath+".png"));

    }
}
