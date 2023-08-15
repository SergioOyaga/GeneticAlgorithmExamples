package org.soyaga.examples.NQueenProblem.SimpleGAs.ArrayLists;

import org.soyaga.ga.Evaluable.Feasibility.FeasibilityFunction;
import org.soyaga.ga.GeneticInformationContainer.Chromosome.ArrayListChromosome;
import org.soyaga.ga.GeneticInformationContainer.Gen.GenericGen;
import org.soyaga.ga.GeneticInformationContainer.Genome.Genome;

import java.util.HashSet;

/**
 * This class is responsible for providing an 'evaluate' method to compute the feasibility of a solution.
 * Specifically, it evaluates the feasibility of an individual's genome.
 */
public class ChessFeasibilityFunction implements FeasibilityFunction {
    /**
     * Computes the feasibility of an individual's genome.
     * In this case, we consider the solution feasible if the genome's chromosomes contain all numbers between 0
     * and N-1.
     * This ensures that all Queens are placed in different
     * columns (chromosome values) and different rows (determined by chromosome position in the genome).
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
        for (Object chromosomes : genome.getGeneticInformation()) {
            Integer row =  ((ArrayListChromosome<GenericGen<Integer>>) chromosomes)
                                    .getGeneticInformation().get(0).getGeneticInformation();
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
