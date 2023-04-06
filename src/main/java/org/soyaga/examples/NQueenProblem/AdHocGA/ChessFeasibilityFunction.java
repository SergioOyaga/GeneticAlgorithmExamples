package org.soyaga.examples.NQueenProblem.AdHocGA;

import org.soyaga.ga.Evaluable.FeasibilityFunction;
import org.soyaga.ga.GeneticInformationContainer.Genome.Genome;

import java.util.HashSet;

/**
 * This class Is in charge of providing an evaluate method to compute the feasibility of a solution. This is the
 * feasibility of an individuals' genome.
 */
public class ChessFeasibilityFunction extends FeasibilityFunction {
    /**
     * Computes the feasibility of an individuals Genome. In this case  we consider that the solution is feasible if the
     * genome's positions contains all the numbers between 0 and N-1. This means all Queens are placed in different
     * Columns (Integer stored in the genome) and different rows (satisfy by construction,Integer position in the genome).
     * @param genome Genome object to evaluate.
     * @param objects VarArgs Object that allow to keep/retain information from the evaluation to be used in the
     *                 decision-making.
     * @return Double with the Feasibility value.
     */
    @Override
    public Double evaluate(Genome genome, Object... objects) {
        HashSet rows = new HashSet();
        HashSet columns = new HashSet();
        int column = 0;
        for (Object chromosome : genome.getGeneticInformation()) {
            Integer row = (Integer)chromosome;
            columns.add(column);
            rows.add(row);
            column++;
        }
        HashSet rowsAux= new HashSet<>(rows);
        HashSet columnsAux = new HashSet<>(columns);
        columns.removeAll(rowsAux);
        rows.removeAll(columnsAux);
        return (columns.size()+ rows.size())*1.;
    }
}
