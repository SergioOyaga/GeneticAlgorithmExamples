package org.soyaga.examples.ImageMaker.SimplePolyImageMaker;

import lombok.Getter;
import org.soyaga.Initialaizer.GAInitializer;
import org.soyaga.ga.CrossoverPolicy.CrossoverPolicy;
import org.soyaga.ga.ElitismPolicy.ElitismPolicy;
import org.soyaga.ga.GeneticAlgorithm.GeneticAlgorithm;
import org.soyaga.ga.MutationPolicy.MutationPolicy;
import org.soyaga.ga.NewbornPolicy.NewbornPolicy;
import org.soyaga.ga.Population;
import org.soyaga.ga.StoppingPolicy.StoppingCriteriaPolicy;

import java.awt.*;
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
    private final Integer initialPopulationSize;
    /**
     * Population object of the GA.
     */
    private Population population;
    /**
     * StoppingCriteriaPolicy object of the GA.
     */
    private StoppingCriteriaPolicy stoppingCriteriaPolicy;
    /**
     * CrossoverPolicy object of the GA.
     */
    private CrossoverPolicy crossoverPolicy;
    /**
     * MutationPolicy object of the GA.
     */
    private MutationPolicy mutationPolicy;
    /**
     * ElitismPolicy object of the GA.
     */
    private ElitismPolicy elitismPolicy;
    /**
     * NewbornPolicy object of the GA.
     */
    private NewbornPolicy newbornPolicy;
    /**
     * GAInitializer Object used to initialize new individuals in each optimization
     * iteration.
     */
    private GAInitializer gaInitializer;
    /**
     * Integer with the width of the image.
     */
    private final Integer width;
    /**
     * Integers with the height of the image.
     */
    private final Integer height;
    /**
     * Integer with the current iteration number. Needed as class parameter because in the getResults method is used to
     * plot some useful information.
     */
    private int generation=0;
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
     * VarArgs and some steps of the evolution are stored to create a GIF.
     */
    @Override
    public void optimize() throws IOException {
        this.gaInitializer.initialize(this);
        this.population.evaluate(this.generation);
        long start = System.currentTimeMillis();
        this.population.evaluate(this.generation);
        long finish = System.currentTimeMillis();
        long timeElapsed = finish - start;
        System.out.println("Generation = "+this.generation +
                ", Fitness = "+this.population.getBestIndividual().getFitnessValue()+
                ", EvaluationTime = "+ timeElapsed);
        this.bestImages.add((BufferedImage) this.getResult());
        while (this.stoppingCriteriaPolicy.hasToContinue(this.generation)){
            Population newPopulation = this.crossoverPolicy.apply(this.population, this.generation);
            this.mutationPolicy.apply(newPopulation, this.generation);
            newPopulation.add(this.elitismPolicy.apply(this.population, this.generation));
            newPopulation.add(this.newbornPolicy.apply(this.gaInitializer, this.generation));
            this.population = newPopulation;
            start = System.currentTimeMillis();
            this.population.evaluate(this.generation);
            finish = System.currentTimeMillis();
            timeElapsed = finish - start;
            this.generation++;
            System.out.println("Generation = "+this.generation +
                    ", Fitness = "+this.population.getBestIndividual().getFitnessValue()+
                    ", EvaluationTime = "+ timeElapsed);
            if(this.generation%this.gifStep==0)this.bestImages.add((BufferedImage) this.getResult());
        }
    }

    /**
     * Transform the genome of the best individual to a BufferedImage.
     * @return BufferedImage.
     */
    @Override
    public Object getResult() {
        CustomGenome genomeObject = (CustomGenome) this.population.getBestIndividual().getGenome();
        BufferedImage image = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics2D = image.createGraphics();
        ArrayList<Object> genome_list = genomeObject.getGeneticInformation();
        Color background = (Color) genome_list.get(0);
        ArrayList<CustomChromosome> chromosome_set = (ArrayList<CustomChromosome>) genome_list.get(1);
        graphics2D.setBackground(background);
        graphics2D.clearRect(0, 0, this.width,this.height);
        for(CustomChromosome chromosome:chromosome_set){
            Color polygonColor = (Color) chromosome.getGeneticInformation().get(0);
            Polygon polygon = (Polygon) chromosome.getGeneticInformation().get(1);
            graphics2D.setColor(polygonColor);
            graphics2D.fillPolygon(polygon);
            graphics2D.setColor(Color.GREEN);
            graphics2D.drawPolygon(polygon);
        }
        graphics2D.drawString("N of polygons= "+chromosome_set.size() + ", Generation= "+ this.generation,10,10);
        graphics2D.drawString("Fitness= "+ this.population.getBestIndividual().getFitnessValue(),
                10,10+graphics2D.getFontMetrics().getHeight());
        graphics2D.dispose();
        return image;
    }

    /**
     * Population getter.
     * @return current Population
     */
    @Override
    public Population getPopulation() {
        return this.population;
    }

    /**
     * Initial population size getter.
     * @return Integer with the initial population size.
     */
    @Override
    public Integer getInitialPopulationSize() {
        return this.initialPopulationSize;
    }
}
