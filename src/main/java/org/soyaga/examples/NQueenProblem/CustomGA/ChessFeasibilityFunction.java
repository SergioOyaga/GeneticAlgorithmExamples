package org.soyaga.examples.NQueenProblem.CustomGA;

import lombok.AllArgsConstructor;
import org.soyaga.ga.Evaluable.Feasibility.FeasibilityFunction;
import org.soyaga.ga.GeneticInformationContainer.Genome.Genome;

import java.util.HashSet;

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
     * In this case, we consider the solution feasible if the genome contains all columns and rows of the board.
     * Each Queen is represented by the combinations of CustomBases in each CustomGen.
     *
     * @param genome The Genome object to be evaluated.
     * @param objects VarArgs object that allows retaining information from the evaluation for use in decision-making.
     * @return A Double representing the feasibility value.
     */
    @Override
    public Double evaluate(Genome genome, Object... objects) {
        HashSet<Integer> rows = new HashSet<>();
        HashSet<Integer> cols = new HashSet<>();
        CustomGenome genomeObject = (CustomGenome) genome;
        for(CustomChromosome chromosome:genomeObject.getGeneticInformation()){
            for(CustomGen gen:chromosome.getGeneticInformation()){
                rows.add(gen.getGeneticInformation().get(0).getGeneticInformation());
                cols.add(gen.getGeneticInformation().get(1).getGeneticInformation());
            }
        }
        return Math.abs(rows.size()+cols.size()-2*nQueens)*1.;
    }
}
