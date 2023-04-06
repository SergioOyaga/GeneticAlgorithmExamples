package org.soyaga.examples.NQueenProblem.SimpleGAs.HashMaps;


import org.soyaga.Initialaizer.GAInitializer;
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
    private final Integer  nQueens;
    public ChessGA(String ID, Integer initialPopulationSize, StoppingCriteriaPolicy stoppingCriteriaPolicy,
                   CrossoverPolicy crossoverPolicy, MutationPolicy mutationPolicy, ElitismPolicy elitismPolicy,
                   NewbornPolicy newbornPolicy, GAInitializer gaInitializer, Integer nQueens) {
        super(ID, initialPopulationSize, stoppingCriteriaPolicy, crossoverPolicy, mutationPolicy, elitismPolicy,
                newbornPolicy, gaInitializer);
        this.nQueens = nQueens;
    }

    /**
     * Transform the genome of the best individual to a String representing its value.
     * @return String.
     */
    @Override
    public Object getResult() {
        String indInfo = this.population.getBestIndividual().toString();
        StringBuilder solution = new StringBuilder("\n");
        HashMapGenome<Integer, HashMapChromosome> genomeObject = (HashMapGenome) this.population.getBestIndividual().getGenome();
        String [][] matrix = new String[this.nQueens][this.nQueens];

        for(Map.Entry<Integer,HashMapChromosome> chromosome:genomeObject.getGeneticInformation()){
            Integer row= chromosome.getKey();
            HashMapChromosome<Integer,GenericGen> genes = chromosome.getValue();
            for(Map.Entry<Integer,GenericGen> gen:genes.getGeneticInformation()){
                Integer col = gen.getKey();
                GenericGen<Boolean> genVal = gen.getValue();
                matrix[row][col] =genVal.getGeneticInformation()? " Q ":" _ ";
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
