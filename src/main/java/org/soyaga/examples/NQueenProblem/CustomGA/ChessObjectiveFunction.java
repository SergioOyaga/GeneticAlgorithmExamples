package org.soyaga.examples.NQueenProblem.CustomGA;

import lombok.AllArgsConstructor;
import org.soyaga.ga.Evaluable.Objective.ObjectiveFunction;
import org.soyaga.ga.GeneticInformationContainer.Genome.Genome;

/**
 * This class evaluates the objective function of an individual's genome.
 * It measures how well adapted an individual's genome is to its environment,
 * bringing it closer to the optimal solution.
 */
@AllArgsConstructor
public class ChessObjectiveFunction implements ObjectiveFunction {
    /**
     * Integer with the number of queens.
     */
    private final Integer nQueens;

    /**
     * This function calculates the quality of an individual's genome by checking for collisions.
     *
     * @param genome The Genome object to be evaluated.
     * @param objects VarArgs Object that retains information from the evaluation for use in decision-making.
     * @return A Double containing the value of the objective function for this individual.
     */
    @Override
    public Double evaluate(Genome<?> genome, Object... objects) {
        double objective=0.;
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
     * Auxiliary function that computes the collisions (only diagonals)
     * of a Queen in a certain position on a given board.
     *
     * @param board A Boolean[][]. It has 'true' where the queens are placed and 'false' elsewhere.
     * @param row Row number of the Queen to be checked.
     * @param col Column number of the Queen to be checked.
     * @return An int representing the number of collisions.
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
