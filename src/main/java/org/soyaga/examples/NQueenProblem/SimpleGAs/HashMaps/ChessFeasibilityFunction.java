package org.soyaga.examples.NQueenProblem.SimpleGAs.HashMaps;

import lombok.AllArgsConstructor;
import org.soyaga.ga.Evaluable.Feasibility.FeasibilityFunction;
import org.soyaga.ga.GeneticInformationContainer.Chromosome.HashMapChromosome;
import org.soyaga.ga.GeneticInformationContainer.Gen.GenericGen;
import org.soyaga.ga.GeneticInformationContainer.Genome.Genome;
import org.soyaga.ga.GeneticInformationContainer.Genome.HashMapGenome;

import java.util.Map;

/**
 * This class is responsible for providing an 'evaluate' method to compute the feasibility of a solution.
 * Specifically, it evaluates the feasibility of an individual's genome.
 */
@AllArgsConstructor
public class ChessFeasibilityFunction implements FeasibilityFunction {
    /**
     * Number of Queens.
     */
    private final Integer nQueens;


    /**
     * Computes the feasibility of an individual's genome.
     * In this case, we consider the solution feasible if the genome contains exactly N Queens.
     * Each Queen is represented by a 'true' value in the genome's genes.
     *
     * @param genome The Genome object to be evaluated.
     * @param objects VarArgs object that allows retaining information from the evaluation for use in decision-making.
     * @return A Double representing the feasibility value.
     */
    @Override
    public Double evaluate(Genome<?> genome, Object... objects) {
        int count = 0;
        HashMapGenome<Integer, HashMapChromosome<Integer,GenericGen<Boolean>>> genomeObject =
                (HashMapGenome<Integer, HashMapChromosome<Integer,GenericGen<Boolean>>>) genome;
        for(Map.Entry<Integer,HashMapChromosome<Integer,GenericGen<Boolean>>> chromosome:genomeObject.getGeneticInformation()){
            for(Map.Entry<Integer,GenericGen<Boolean>> gen:chromosome.getValue().getGeneticInformation()){
                if(gen.getValue().getGeneticInformation()){
                    count+=1;
                }
            }
        }
        return Math.abs(count-this.nQueens)*1.;
    }
}