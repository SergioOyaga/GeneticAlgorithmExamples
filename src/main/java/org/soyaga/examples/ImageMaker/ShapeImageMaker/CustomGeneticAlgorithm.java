package org.soyaga.examples.ImageMaker.ShapeImageMaker;

import lombok.Getter;
import org.soyaga.Initializer.GAInitializer;
import org.soyaga.ga.CrossoverPolicy.CrossoverPolicy;
import org.soyaga.ga.ElitismPolicy.ElitismPolicy;
import org.soyaga.ga.GeneticAlgorithm.GeneticAlgorithm;
import org.soyaga.ga.MutationPolicy.MutationPolicy;
import org.soyaga.ga.NewbornPolicy.NewbornPolicy;
import org.soyaga.ga.Population;
import org.soyaga.ga.StoppingPolicy.StoppingCriteriaPolicy;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Extends GeneticAlgorithm and defines how we perform the optimization cycles and how we gather the results.
 */
public class CustomGeneticAlgorithm implements GeneticAlgorithm {
    /**
     * String with the name of the GA.
     */
    private final String ID;
    /**
     * Integer with the initial populationSize.
     */
    @Getter
    private final Integer initialPopulationSize;
    /**
     * Population object of the GA.
     */
    @Getter
    private Population population;
    /**
     * StoppingCriteriaPolicy object of the GA.
     */
    private final StoppingCriteriaPolicy stoppingCriteriaPolicy;
    /**
     * CrossoverPolicy object of the GA.
     */
    private final CrossoverPolicy crossoverPolicy;
    /**
     * MutationPolicy object of the GA.
     */
    private final MutationPolicy mutationPolicy;
    /**
     * ElitismPolicy object of the GA.
     */
    private final ElitismPolicy elitismPolicy;
    /**
     * NewbornPolicy object of the GA.
     */
    private final NewbornPolicy newbornPolicy;
    /**
     * GAInitializer Object used to initialize new individuals in each optimization
     * iteration.
     */
    private final GAInitializer gaInitializer;
    /**
     * Integer with the width of the image.
     */
    private final Integer width;
    /**
     * Integer with the height of the image.
     */
    private final Integer height;
    /**
     * ArrayList&lt;BufferedImage&gt; containing the optimization intermediate steps.
     */
    @Getter
    private ArrayList<BufferedImage> bestImages= new ArrayList<>();
    /**
     * Integer with the number of steps between each frame of the GIF.
     */
    private final int gifStep;


    /**
     * It receives all parameters needed to create an object of this class.
     * @param ID String with the name of the GA.
     * @param initialPopulationSize    Integer with the initial number of individuals of the
     *                          population.
     * @param stoppingCriteriaPolicy  StoppingCriteriaPolicy object with the criteria already defined.
     * @param crossoverPolicy CrossoverPolicy object with the crossoverPolicy already defined.
     * @param mutationPolicy MutationPolicy object with the mutation defined.
     * @param elitismPolicy ElitismPolicy object with the elitism defined.
     * @param newbornPolicy NewbornPolicy object with the newborns defined.
     * @param gaInitializer GAInitializer object with the initialization information.
     * @param width Integer with the width of the image.
     * @param height Integer with the height of the image.
     * @param gifStep Integer with the number of steps to store the gif image.
     */
    public CustomGeneticAlgorithm(String ID, Integer initialPopulationSize, StoppingCriteriaPolicy stoppingCriteriaPolicy,
                                  CrossoverPolicy crossoverPolicy, MutationPolicy mutationPolicy, ElitismPolicy elitismPolicy,
                                  NewbornPolicy newbornPolicy, GAInitializer gaInitializer, Integer width, Integer height,
                                  Integer gifStep
                                  ) {
        super();
        this.ID = ID;
        this.initialPopulationSize = initialPopulationSize;
        this.population = new Population();
        this.stoppingCriteriaPolicy = stoppingCriteriaPolicy;
        this.crossoverPolicy = crossoverPolicy;
        this.mutationPolicy = mutationPolicy;
        this.elitismPolicy= elitismPolicy;
        this.newbornPolicy = newbornPolicy;
        this.gaInitializer = gaInitializer;
        this.width = width;
        this.height = height;
        this.gifStep = gifStep;
    }


    /**
     * Optimization procedure, similar to the SimpleGeneticAlgorithm procedure, but the iteration is passed along the
     * VarArgs and some steps of the evolution are stored to create a GIF. In addition some minor manual stats are
     * reported in the execution.
     */
    @Override
    public void optimize() throws IOException {
        Integer generation=0;
        this.gaInitializer.initialize(this);
        this.population.evaluate(generation);
        long start = System.currentTimeMillis();
        this.population.evaluate(generation);
        long finish = System.currentTimeMillis();
        long timeElapsed = finish - start;
        System.out.println("Generation = "+generation +
                ", Fitness = "+this.population.getBestIndividual().getFitnessValue()+
                ", EvaluationTime = "+ timeElapsed);
        this.bestImages.add((BufferedImage) this.getResult(generation));
        while (this.stoppingCriteriaPolicy.hasToContinue(generation)){
            Population newPopulation = this.crossoverPolicy.apply(this.population, generation);
            this.mutationPolicy.apply(newPopulation, generation);
            newPopulation.add(this.elitismPolicy.apply(this.population, generation));
            newPopulation.add(this.newbornPolicy.apply(this.gaInitializer, generation));
            this.population = newPopulation;
            start = System.currentTimeMillis();
            this.population.evaluate(generation);
            finish = System.currentTimeMillis();
            timeElapsed = finish - start;
            generation++;
            System.out.println("Generation = "+generation +
                    ", Fitness = "+this.population.getBestIndividual().getFitnessValue()+
                    ", EvaluationTime = "+ timeElapsed);
            if(generation%this.gifStep==0)this.bestImages.add((BufferedImage) this.getResult(generation));
        }
    }

    /**
     * Transform the genome of the best individual to a BufferedImage.
     *
     * @return BufferedImage.
     */
    @Override
    public Object getResult(Object... resultArgs) {
        Integer generation = (Integer) resultArgs[0];
        CustomGenome genomeObject = (CustomGenome) this.population.getBestIndividual().getGenome();
        BufferedImage image = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics2D = image.createGraphics();
        AffineTransform original = graphics2D.getTransform();
        ArrayList<Object> genome_list = genomeObject.getGeneticInformation();
        Color background = (Color) genome_list.get(0);
        ArrayList<CustomChromosome> chromosome_set = (ArrayList<CustomChromosome>) genome_list.get(1);
        graphics2D.setBackground(background);
        graphics2D.clearRect(0, 0, this.width,this.height);
        for(CustomChromosome chromosome:chromosome_set){
            Color shapeColor = (Color) chromosome.getGeneticInformation().get(0);
            Double rotation = (Double) chromosome.getGeneticInformation().get(1);
            Shape shape = (Shape) chromosome.getGeneticInformation().get(2);
            graphics2D.setColor(shapeColor);
            AffineTransform at = AffineTransform.getRotateInstance(rotation);
            graphics2D.setTransform(at);
            graphics2D.fill(shape);
            graphics2D.setColor(Color.GREEN);
            //graphics2D.draw(shape);
            graphics2D.setTransform(original);
        }
        graphics2D.drawString("N of shapes= "+chromosome_set.size() + ", Generation= "+ generation,10,10);
        graphics2D.drawString("Fitness= "+ this.population.getBestIndividual().getFitnessValue(),
                10,10+graphics2D.getFontMetrics().getHeight());
        graphics2D.dispose();
        return image;
    }

}
