package org.soyaga.examples.NQueenProblem.SimpleGAs.HashMaps;

import lombok.AllArgsConstructor;
import org.soyaga.ga.Evaluable.ObjectiveFunction;
import org.soyaga.ga.GeneticInformationContainer.Chromosome.HashMapChromosome;
import org.soyaga.ga.GeneticInformationContainer.Gen.GenericGen;
import org.soyaga.ga.GeneticInformationContainer.Genome.Genome;
import org.soyaga.ga.GeneticInformationContainer.Genome.HashMapGenome;

import java.util.Map;

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
        HashMapGenome<Integer, HashMapChromosome> genomeObject = (HashMapGenome) genome;
        boolean [][] matrix = new boolean[this.nQueens][this.nQueens];
        for(Map.Entry<Integer,HashMapChromosome> chromosome:genomeObject.getGeneticInformation()){
            Integer row= chromosome.getKey();
            HashMapChromosome<Integer,GenericGen> genes = chromosome.getValue();
            for(Map.Entry<Integer,GenericGen> gen:genes.getGeneticInformation()){
                Integer col = gen.getKey();
                GenericGen<Boolean> genVal = gen.getValue();
                matrix[row][col] =genVal.getGeneticInformation();
            }
        }
        //check collisions
        for(int i =0; i<this.nQueens;i++){
            for(int j=0;j<this.nQueens;j++){
                if(matrix[i][j]) {
                    objective+= computeCollisions(matrix, i, j);
                }
            }
        }
        return objective;
    }

    /**
     * Aux function that that computes the collisions (row, cols and diags) of a Queen in a certain position given a board.
     * @param board Boolean[][]. with true where the queens are faced and false elsewhere.
     * @param row row number of the Queen we want to check.
     * @param col column number of the Queen we want to check.
     * @return an int with the number of collisions.
     */
    private int computeCollisions(boolean[][] board, int row, int col)
    {
        int i, j, collisions = 0;

        /* Check this col on lower side */
        if (row< board.length)
            for (i = row+1; i < board.length; i++)
                if (board[i][col])
                    collisions++;

        /* Check this row on right side */
        if (col< board.length)
            for (i = col+1; i <  board.length; i++)
                if (board[row][i])
                    collisions++;

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
