package org.soyaga.examples.NQueenProblem.CustomGA;

import lombok.AllArgsConstructor;
import org.soyaga.ga.Evaluable.ObjectiveFunction;
import org.soyaga.ga.GeneticInformationContainer.Genome.Genome;

/**
 * This class Evaluates the objective function to an individuals Genome. This is the
 * well adapted is an individuals' genome to its environment (closer to the optimal solution).
 */
@AllArgsConstructor
public class ChessObjectiveFunction extends ObjectiveFunction {
    /**
     * Integer with the number of queens.
     */
    private final Integer nQueens;

    /**
     * Function that computes how good is this individuals' Genome.
     * @param genome Genome object to evaluate.
     * @param objects VarArgs Object that allow to keep/retain information from the evaluation to be used in the
     *                 decision-making.
     * @return a Double containing the value of the objective function to this individual.
     */
    @Override
    public Double evaluate(Genome genome, Object... objects) {
        Double objective=0.;
        CustomGenome genomeObject = (CustomGenome) genome;
        boolean [][] matrix = new boolean[nQueens][nQueens];
       for(CustomChromosome chromosome:genomeObject.getGeneticInformation()){
            for(CustomGen gen:chromosome.getGeneticInformation()){
                Integer row = gen.getGeneticInformation().get(0).getGeneticInformation();
                Integer col = gen.getGeneticInformation().get(1).getGeneticInformation();
                matrix[row][col] = true;
            }
        }
        for(int i =0; i<nQueens;i++){
            for(int j=0;j<nQueens;j++){
                if(matrix[i][j]) {
                    objective+= computeCollisions(matrix, i, j);
                }
            }
        }
        return objective;
    }

    /**
     * Aux function that that computes the collisions (only diags) of a Queen in a certain position given a board.
     * @param board Boolean[][]. with true where the queens are faced and false elsewhere.
     * @param row row number of the Queen we want to check.
     * @param col column number of the Queen we want to check.
     * @return an int with the number of collisions.
     */
    private int computeCollisions(boolean[][] board, int row, int col)
    {
        int i, j, collisions = 0;

        /* Check lower diagonal on right side */
        if (row < board.length && col< board.length)
            for (i = row+1, j = col+1; i <  board.length && j <  board.length; i++, j++)
                if (board[i][j])
                    collisions++;

        /* Check upper diagonal on right side */
        if(row>0)
            for (i = row-1, j = col+1; i>=0 && j< board.length; i--, j++)
                if (board[i][j])
                    collisions++;

        return collisions;
    }
}
