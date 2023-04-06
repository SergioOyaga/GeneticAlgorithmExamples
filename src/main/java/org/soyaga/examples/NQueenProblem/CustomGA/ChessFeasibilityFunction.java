package org.soyaga.examples.NQueenProblem.CustomGA;

import lombok.AllArgsConstructor;
import org.soyaga.ga.Evaluable.FeasibilityFunction;
import org.soyaga.ga.GeneticInformationContainer.Genome.Genome;

import java.util.HashSet;

/**
 * This class Is in charge of providing an evaluate method to compute the feasibility of a solution. This is the
 * feasibility of an individuals' genome.
 */
@AllArgsConstructor
public class ChessFeasibilityFunction extends FeasibilityFunction {
    /**
     * Number of Queens.
     */
    private final Integer nQueens;

    /**
     * Computes the feasibility of an individuals Genome. In this case  we consider that the solution is feasible
     * when all rows and columns are contemplated in the solution.
     * @param genome Genome object to evaluate.
     * @param objects VarArgs Object that allow to keep/retain information from the evaluation to be used in the
     *                 decision-making.
     * @return Double with the Feasibility value.
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
