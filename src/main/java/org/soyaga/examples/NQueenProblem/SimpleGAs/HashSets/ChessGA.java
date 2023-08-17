package org.soyaga.examples.NQueenProblem.SimpleGAs.HashSets;


import org.soyaga.Initializer.GAInitializer;
import org.soyaga.ga.CrossoverPolicy.CrossoverPolicy;
import org.soyaga.ga.ElitismPolicy.ElitismPolicy;
import org.soyaga.ga.GeneticAlgorithm.SimpleGeneticAlgorithm;
import org.soyaga.ga.GeneticInformationContainer.Chromosome.HashSetChromosome;
import org.soyaga.ga.GeneticInformationContainer.Genome.HashSetGenome;
import org.soyaga.ga.MutationPolicy.MutationPolicy;
import org.soyaga.ga.NewbornPolicy.NewbornPolicy;
import org.soyaga.ga.StoppingPolicy.StoppingCriteriaPolicy;

import java.util.Arrays;

/**
 * Extends SimpleGeneticAlgorithm and defines how we gather the results.
 */
public class ChessGA extends SimpleGeneticAlgorithm {

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
     */
    public ChessGA(String ID, Integer initialPopulationSize, StoppingCriteriaPolicy stoppingCriteriaPolicy,
                   CrossoverPolicy crossoverPolicy, MutationPolicy mutationPolicy, ElitismPolicy elitismPolicy,
                   NewbornPolicy newbornPolicy, GAInitializer gaInitializer) {
        super(ID, initialPopulationSize, stoppingCriteriaPolicy, crossoverPolicy, mutationPolicy, elitismPolicy,
                newbornPolicy, gaInitializer);
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
        HashSetGenome<HashSetChromosome<ChessGen>> genomeObject =
                (HashSetGenome<HashSetChromosome<ChessGen>>) this.population.getBestIndividual().getGenome();
        int nQueens = genomeObject.getGeneticInformation().size();
        String [][] matrix = new String[nQueens][nQueens];
        for(String[] arr:matrix)Arrays.fill(arr," _ ");
        for(HashSetChromosome<ChessGen> chromosome:genomeObject.getGeneticInformation()){
            for(ChessGen gen:chromosome.getGeneticInformation()){
                matrix[gen.getColumn()][gen.getRow()] = gen.getValue()?" Q ":matrix[gen.getColumn()][gen.getRow()];
            }
        }
        for(int i=0;i<nQueens;i++){
            for(int j=0;j<nQueens;j++){
                solution.append(matrix[i][j]);
            }
            solution.append('\n');
        }
        return indInfo+solution;
    }
}
