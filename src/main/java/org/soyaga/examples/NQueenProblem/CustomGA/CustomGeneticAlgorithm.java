package org.soyaga.examples.NQueenProblem.CustomGA;

import org.soyaga.Initializer.GAInitializer;
import org.soyaga.ga.CrossoverPolicy.CrossoverPolicy;
import org.soyaga.ga.ElitismPolicy.ElitismPolicy;
import org.soyaga.ga.GeneticAlgorithm.GeneticAlgorithm;
import org.soyaga.ga.MutationPolicy.MutationPolicy;
import org.soyaga.ga.NewbornPolicy.NewbornPolicy;
import org.soyaga.ga.Population;
import org.soyaga.ga.StoppingPolicy.StoppingCriteriaPolicy;

import java.util.ArrayList;
import java.util.Arrays;

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
     * ArrayList of Strings with the best Individual.toString() history.
     */
    private final ArrayList<String> history;
    /**
     * Number of queens.
     */
    private final Integer nQueens;

    /**
     * Constructor.
     *
     * @param ID String with the name of the GA.
     * @param initialPopulationSize    Integer with the initial number of individuals in the population.
     * @param stoppingCriteriaPolicy  StoppingCriteriaPolicy object with the criteria already defined.
     * @param crossoverPolicy CrossoverPolicy object with the crossoverPolicy already defined.
     * @param mutationPolicy MutationPolicy object with the mutation defined.
     * @param elitismPolicy ElitismPolicy object with the elitism defined.
     * @param newbornPolicy NewbornPolicy object with the newborns defined.
     * @param gaInitializer GAInitializer.
     * @param nQueens Integer with the number of queens.
     */
    public CustomGeneticAlgorithm(String ID, Integer initialPopulationSize, StoppingCriteriaPolicy stoppingCriteriaPolicy,
                                  CrossoverPolicy crossoverPolicy, MutationPolicy mutationPolicy, ElitismPolicy elitismPolicy,
                                  NewbornPolicy newbornPolicy, GAInitializer gaInitializer, Integer nQueens) {
        super();
        this.ID = ID;
        this.initialPopulationSize = initialPopulationSize;
        this.population=new Population();
        this.stoppingCriteriaPolicy = stoppingCriteriaPolicy;
        this.crossoverPolicy = crossoverPolicy;
        this.mutationPolicy = mutationPolicy;
        this.elitismPolicy= elitismPolicy;
        this.newbornPolicy = newbornPolicy;
        this.gaInitializer = gaInitializer;
        this.history = new ArrayList<>();
        this.nQueens = nQueens;
    }


    /**
     * Optimization procedure, similar to the SimpleGeneticAlgorithm procedure, but the iteration is passed along the
     * VarArgs and the best individual fitness to the stopping criteria.
     */
    @Override
    public void optimize() {
        Integer generation=0;
        this.gaInitializer.initialize(this);
        this.population.evaluate();
        this.history.add("Generation = "+generation + "\n"+this.population.getBestIndividual().toString());
        while (this.stoppingCriteriaPolicy.hasToContinue(generation,this.population.getBestIndividual().getFitnessValue())){
            Population newPopulation=this.crossoverPolicy.apply(this.population,generation);
            this.mutationPolicy.apply(newPopulation,generation);
            newPopulation.add(this.elitismPolicy.apply(this.population,generation));
            newPopulation.add(this.newbornPolicy.apply(this.gaInitializer,generation));
            this.population = newPopulation;
            this.population.evaluate();
            generation++;
            this.history.add("\n\nGeneration = "+generation + "\n"+this.population.getBestIndividual().toString());
        }

    }

    /**
     * Transform the genome of the best individual to a String representing its value.
     *
     * @return String.
     */
    @Override
    public Object getResult(Object... resultArgs) {
        StringBuilder solution = new StringBuilder("\n");
        CustomGenome genomeObject = (CustomGenome) this.population.getBestIndividual().getGenome();
        String [][] matrix = new String[nQueens][nQueens];
        for(String[] arr:matrix){
            Arrays.fill(arr," _ ");
        }
        for(CustomChromosome chromosome:genomeObject.getGeneticInformation()){
            for(CustomGen gen:chromosome.getGeneticInformation()){
                Integer row = gen.getGeneticInformation().get(0).getGeneticInformation();
                Integer col = gen.getGeneticInformation().get(1).getGeneticInformation();
                matrix[row][col] = " Q ";
            }
        }
        for(int i=0;i<nQueens;i++){
            for(int j=0;j<nQueens;j++){
                solution.append(matrix[i][j]);
            }
            solution.append('\n');
        }
        return String.join("", this.history)+solution;

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
