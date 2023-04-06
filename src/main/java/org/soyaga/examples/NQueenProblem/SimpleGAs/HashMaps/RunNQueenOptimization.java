package org.soyaga.examples.NQueenProblem.SimpleGAs.HashMaps;

import org.soyaga.ga.CrossoverPolicy.FixedCrossoverPolicy;
import org.soyaga.ga.CrossoverPolicy.ParentSelection.RandomSelection;
import org.soyaga.ga.ElitismPolicy.FixedElitismPolicy;
import org.soyaga.ga.MutationPolicy.Mutations.GeneticMapSwapMutation;
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
        Integer populationSize = 100;
        Integer numberOfQueens = 8;
        GeneticMapSwapMutation mut = new GeneticMapSwapMutation(1);
        ChessGA ga = new ChessGA("CompleteSimpleHashMapGA", populationSize,
                new MaxIterationCriteriaPolicy(2000),
                new FixedCrossoverPolicy(
                        populationSize*80/100,
                        new RandomSelection(),
                        new MapCrossover()),
                new UnorderedSingleProbabilityMutPol(
                        new HashSet<>(){{add(mut);}},
                        new HashSet<>(){{add(mut);}},
                        new HashSet<>(),
                        0.8),
                new FixedElitismPolicy(populationSize*10/100),
                new FixedNewbornPolicy(populationSize*10/100),
                new ChessGAInitializer(numberOfQueens,
                        new ChessFeasibilityFunction(numberOfQueens),
                        new ChessObjectiveFunction(numberOfQueens)),
                numberOfQueens
        );
        ga.optimize();
        System.out.println(ga.getResult());

    }
}
