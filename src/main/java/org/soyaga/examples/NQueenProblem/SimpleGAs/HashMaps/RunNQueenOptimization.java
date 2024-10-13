package org.soyaga.examples.NQueenProblem.SimpleGAs.HashMaps;

import org.soyaga.ga.CrossoverPolicy.FixedCrossoverPolicy;
import org.soyaga.ga.CrossoverPolicy.ParentSelection.RandomSelection;
import org.soyaga.ga.ElitismPolicy.FixedElitismPolicy;
import org.soyaga.ga.MutationPolicy.ByLevelSingleProbMutPolicy;
import org.soyaga.ga.MutationPolicy.Mutations.GenericSwapMutation;
import org.soyaga.ga.NewbornPolicy.FixedNewbornPolicy;
import org.soyaga.ga.StoppingPolicy.MaxIterationCriteriaPolicy;

import java.util.ArrayList;

/**
 * Instantiates and optimize a ChessGA Object. Fills it with all the GA classes needed to perform the optimization.
 * Optimizes given the previous configuration and prints the result in the console.
 */
public class RunNQueenOptimization {
    public static void main(String ... args){
        int populationSize = 100;
        Integer numberOfQueens = 8;
        GenericSwapMutation mut = new GenericSwapMutation(1);   // Mutation to apply at Genome and Chromosome level.
        ChessGA ga = new ChessGA("CompleteSimpleHashMapGA", // ID.
                populationSize,                                 // Size of the population.
                new MaxIterationCriteriaPolicy(                 // Stopping criteria, max iterations.
                        2000                                        // Maximum number of iterations.
                ),
                new FixedCrossoverPolicy(                       // Crossover Policy, fixed number.
                        populationSize*80/100,                      // Number of crossovers.
                        new RandomSelection(),                      // Parent Selection, random selection.
                        new MapCrossover()),                        // Crossover type, map crossover.
                new ByLevelSingleProbMutPolicy(                 // Mutation Policy, single prob.
                        new ArrayList<>(){{                         // Mutation Levels.
                            add(new ArrayList<>(){{add(mut);}});        // Array of mutation at Genome level.
                            add(new ArrayList<>(){{add(mut);}});        // Array of mutation at Chromosome level.
                        }},
                        0.8,                            // Mutation probability.
                        false,                                      // Hash To shuffle levels.
                        false),                                     // Hash To shuffle Mutations.
                new FixedElitismPolicy(                         // Elitism Policy, fixed number.
                            populationSize*10/100                   // Number of elitists.
                ),
                new FixedNewbornPolicy(                         // Newborn Policy, fixed number.
                        populationSize*10/100                       // Number of newborns.
                ),
                new ChessGAInitializer(                         // Initializer
                        numberOfQueens,                             // Number of Queens.
                        new ChessFeasibilityFunction(numberOfQueens),// Feasibility Function.
                        new ChessObjectiveFunction(numberOfQueens)), // Objective Function.
                numberOfQueens                                  // Integer, number of queens.
        );
        ga.optimize();
        System.out.println(ga.getResult());

    }
}
