package org.soyaga.examples.NQueenProblem.SimpleGAs.HashSets;

import org.soyaga.ga.CrossoverPolicy.FixedCrossoverPolicy;
import org.soyaga.ga.CrossoverPolicy.ParentCross.HeuristicCrossover;
import org.soyaga.ga.CrossoverPolicy.ParentSelection.RandomSelection;
import org.soyaga.ga.ElitismPolicy.FixedElitismPolicy;
import org.soyaga.ga.MutationPolicy.Mutations.BoolGenDenialMutation;
import org.soyaga.ga.MutationPolicy.UnorderedSingleProbabilityMutPol;
import org.soyaga.ga.NewbornPolicy.FixedNewbornPolicy;
import org.soyaga.ga.StoppingPolicy.MaxIterationCriteriaPolicy;

import java.util.HashSet;

/**
 * Instantiates and optimize a ChessGA Object. Fills it with all the GA classes needed to perform the optimization.
 * Optimizes given the previous configuration and prints the result in the console.
 */
public class RunNQueenOptimization {
    public static void main(String ... args){
        Integer populationSize = 3;
        Integer numberOfQueens = 8;
        ChessGA ga = new ChessGA("CompleteSimpleHashSetGA", populationSize,
                new MaxIterationCriteriaPolicy(20000),
                new FixedCrossoverPolicy(
                        1,
                        new RandomSelection(),
                        new HeuristicCrossover()),
                new UnorderedSingleProbabilityMutPol(
                        new HashSet<>(),
                        new HashSet<>(),
                        new HashSet<>(){{add(new BoolGenDenialMutation());}},
                        0.01),
                new FixedElitismPolicy(1),
                new FixedNewbornPolicy(1),
                new ChessGAInitializer(numberOfQueens,
                        new ChessFeasibilityFunction(),
                        new ChessObjectiveFunction())
        );
        ga.optimize();
        System.out.println(ga.getResult());
    }
}
