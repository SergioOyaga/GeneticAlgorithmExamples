package org.soyaga.examples.NQueenProblem.SimpleGAs.HashMaps;


import org.soyaga.Initializer.GAInitializer;
import org.soyaga.ga.CrossoverPolicy.CrossoverPolicy;
import org.soyaga.ga.ElitismPolicy.ElitismPolicy;
import org.soyaga.ga.GeneticAlgorithm.SimpleGeneticAlgorithm;
import org.soyaga.ga.GeneticInformationContainer.Chromosome.HashMapChromosome;
import org.soyaga.ga.GeneticInformationContainer.Gen.GenericGen;
import org.soyaga.ga.GeneticInformationContainer.Genome.HashMapGenome;
import org.soyaga.ga.MutationPolicy.MutationPolicy;
import org.soyaga.ga.NewbornPolicy.NewbornPolicy;
import org.soyaga.ga.StoppingPolicy.StoppingCriteriaPolicy;

import java.util.Map;

/**
 * Extends SimpleGeneticAlgorithm and defines how we gather the results.
 */
public class ChessGA extends SimpleGeneticAlgorithm {
    /**
     * Integer with the number of queens.
     */
    private final Integer  nQueens;

    /**
     * Constructor that matches its super constructor.
     *
     * @param ID String with the GA description.
     * @param initialPopulationSize Integer with the initial population size.
     * @param stoppingCriteriaPolicy StoppingCriteriaPolicy.
     * @param crossoverPolicy CrossoverPolicy.
     * @param mutationPolicy MutationPolicy.
     * @param elitismPolicy ElitismPolicy.
     * @param newbornPolicy NewbornPolicy.
     * @param gaInitializer GAInitializer.
     * @param nQueens Integer with the number of queens.
     */
    public ChessGA(String ID, Integer initialPopulationSize, StoppingCriteriaPolicy stoppingCriteriaPolicy,
                   CrossoverPolicy crossoverPolicy, MutationPolicy mutationPolicy, ElitismPolicy elitismPolicy,
                   NewbornPolicy newbornPolicy, GAInitializer gaInitializer, Integer nQueens) {
        super(ID, initialPopulationSize, stoppingCriteriaPolicy, crossoverPolicy, mutationPolicy, elitismPolicy,
                newbornPolicy, gaInitializer);
        this.nQueens = nQueens;
    }

    /**
     * Transform the genome of the best individual into a String representing its value.
     *
     * @return String.
     */
    @Override
    public Object getResult(Object... resultArgs) {
        String indInfo = this.population.getBestIndividual().toString();
        StringBuilder solution = new StringBuilder("\n");
        HashMapGenome<Integer, HashMapChromosome<Integer,GenericGen<Boolean>>> genomeObject =
                (HashMapGenome<Integer, HashMapChromosome<Integer,GenericGen<Boolean>>>) this.population.getBestIndividual().getGenome();
        String [][] matrix = new String[this.nQueens][this.nQueens];

        for(Map.Entry<Integer,HashMapChromosome<Integer,GenericGen<Boolean>>> chromosome:genomeObject.getGeneticInformation()){
            Integer row= chromosome.getKey();
            for(Map.Entry<Integer,GenericGen<Boolean>> gen: chromosome.getValue().getGeneticInformation()){
                Integer col = gen.getKey();
                matrix[row][col] =gen.getValue().getGeneticInformation()? " Q ":" _ ";
            }
        }
        for(int i=0;i<this.nQueens;i++){
            for(int j=0;j<this.nQueens;j++){
                solution.append(matrix[i][j]);
            }
            solution.append('\n');
        }
        return indInfo+solution;
    }
}
