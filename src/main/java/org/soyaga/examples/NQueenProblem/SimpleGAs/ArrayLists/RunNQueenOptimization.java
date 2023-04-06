package org.soyaga.examples.NQueenProblem.SimpleGAs.ArrayLists;

import org.soyaga.ga.CrossoverPolicy.FixedCrossoverPolicy;
import org.soyaga.ga.CrossoverPolicy.ParentCross.OnePointCrossover;
import org.soyaga.ga.CrossoverPolicy.ParentSelection.RandomSelection;
import org.soyaga.ga.ElitismPolicy.FixedElitismPolicy;
import org.soyaga.ga.MutationPolicy.Mutations.ChromosomeSwapMutation;
import org.soyaga.ga.MutationPolicy.OrderedSingleProbabilityMutPol;
import org.soyaga.ga.NewbornPolicy.FixedNewbornPolicy;
import org.soyaga.ga.StoppingPolicy.MaxIterationCriteriaPolicy;

import java.util.ArrayList;

/**
 * Instantiates and optimize a ChessGA Object. Fills it with all the GA classes needed to perform the optimization.
 * Optimizes given the previous configuration and prints the result in the console.
 */
public class RunNQueenOptimization {
    public static void main(String ... args){
        Integer populationSize = 100;
        Integer numberOfQueens = 8;
        ChessGA ga = new ChessGA("CompleteSimpleArrayListGA", populationSize,
                new MaxIterationCriteriaPolicy(1000),
                new FixedCrossoverPolicy(
                        populationSize*80/100,
                        new RandomSelection(),
                        new OnePointCrossover()),
                new OrderedSingleProbabilityMutPol(
                        new ArrayList<>(){{add(new ChromosomeSwapMutation(1));}},
                        new ArrayList<>(),
                        new ArrayList<>(),
                        0.01),
                new FixedElitismPolicy(populationSize*10/100),
                new FixedNewbornPolicy(populationSize*10/100),
                new ChessGAInitializer(numberOfQueens,
                        new ChessFeasibilityFunction(),
                        new ChessObjectiveFunction())
        );
        ga.optimize();
        System.out.println(ga.getResult());
    }
}
