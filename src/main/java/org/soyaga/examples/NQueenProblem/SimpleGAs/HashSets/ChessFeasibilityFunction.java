package org.soyaga.examples.NQueenProblem.SimpleGAs.HashSets;

import org.soyaga.ga.Evaluable.Feasibility.FeasibilityFunction;
import org.soyaga.ga.GeneticInformationContainer.Chromosome.HashSetChromosome;
import org.soyaga.ga.GeneticInformationContainer.Genome.Genome;
import org.soyaga.ga.GeneticInformationContainer.Genome.HashSetGenome;

/**
 * This class is responsible for providing an 'evaluate' method to compute the feasibility of a solution.
 * Specifically, it evaluates the feasibility of an individual's genome.
 */
public class ChessFeasibilityFunction implements FeasibilityFunction {
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
        HashSetGenome<HashSetChromosome<ChessGen>> genomeObject = (HashSetGenome<HashSetChromosome<ChessGen>>) genome;
        int nQueens = genomeObject.getGeneticInformation().size();
        for(HashSetChromosome<ChessGen> chromosome:genomeObject.getGeneticInformation()){
            for(ChessGen gen:chromosome.getGeneticInformation()){
                if(gen.getGeneticInformation()){
                    count+=1;
                }
            }
        }
        return Math.abs(count-nQueens)*1.;
    }
}
