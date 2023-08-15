package org.soyaga.examples.NQueenProblem.AdHocGA;

import org.soyaga.ga.Evaluable.Feasibility.FeasibilityFunction;
import org.soyaga.ga.GeneticInformationContainer.Genome.Genome;

import java.util.HashSet;

/**
 * This class is responsible for providing an 'evaluate' method to compute the feasibility of a solution.
 * Specifically, it evaluates the feasibility of an individual's genome.
 */
public class ChessFeasibilityFunction implements FeasibilityFunction {
    /**
     * Computes the feasibility of an individual's genome.
     * In this case, we consider the solution feasible if the genome's positions contain all numbers between 0
     * and N-1.
     * This ensures that all Queens are placed in different
     * columns (Genome values) and different rows (Genome's values positions).
     *
     * @param genome The Genome object to be evaluated.
     * @param objects VarArgs object that allows retention of information from the evaluation for use in decision-making.
     * @return A Double representing the feasibility value.
     */
    @Override
    public Double evaluate(Genome<?> genome, Object... objects) {
        HashSet<Integer> rows = new HashSet<>();
        HashSet<Integer> columns = new HashSet<>();
        int column = 0;
        for (Object chromosome : genome.getGeneticInformation()) {
            Integer row = (Integer)chromosome;
            columns.add(column);
            rows.add(row);
            column++;
        }
        HashSet<Integer> rowsAux= new HashSet<>(rows);
        HashSet<Integer> columnsAux = new HashSet<>(columns);
        columns.removeAll(rowsAux);
        rows.removeAll(columnsAux);
        return (columns.size()+ rows.size())*1.;
    }
}
