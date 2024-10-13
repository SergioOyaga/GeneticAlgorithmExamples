package org.soyaga.examples.NQueenProblem.SimpleGAs.HashSets;

import org.soyaga.ga.CrossoverPolicy.FixedCrossoverPolicy;
import org.soyaga.ga.CrossoverPolicy.ParentCross.HeuristicCrossover;
import org.soyaga.ga.CrossoverPolicy.ParentSelection.RandomSelection;
import org.soyaga.ga.ElitismPolicy.FixedElitismPolicy;
import org.soyaga.ga.MutationPolicy.ByLevelSingleProbMutPolicy;
import org.soyaga.ga.MutationPolicy.Mutations.BoolGenDenialMutation;
import org.soyaga.ga.NewbornPolicy.FixedNewbornPolicy;
import org.soyaga.ga.StoppingPolicy.MaxIterationCriteriaPolicy;

import java.util.ArrayList;

/**
 * Instantiates and optimize a ChessGA Object. Fills it with all the GA classes needed to perform the optimization.
 * Optimizes given the previous configuration and prints the result in the console.
 */
public class RunNQueenOptimization {
    public static void main(String ... args){
        Integer populationSize = 3;
        Integer numberOfQueens = 8;
        ChessGA ga = new ChessGA("CompleteSimpleHashSetGA", // ID.
                populationSize,                                 // Size of the population.
                new MaxIterationCriteriaPolicy(                 // Stopping criteria, max iterations.
                        20000                                       // Maximum number of iterations.
                ),
                new FixedCrossoverPolicy(                       // Crossover Policy, fixed number.
                        1,                                          // Number of crossovers.
                        new RandomSelection(),                      // Parent Selection, random selection.
                        new HeuristicCrossover()),                  // Crossover type, heuristic crossover.
                new ByLevelSingleProbMutPolicy(                 // Mutation Policy, single prob.
                        new ArrayList<>(){{                         // Mutation Levels. Effective only on Gen level.
                            add(new ArrayList<>());                     // Array of mutation at Genome level.
                            add(new ArrayList<>());                     // Array of mutation at Chromosome level.
                            add(new ArrayList<>(){{                     // Array of mutation at Gen level.
                                add(new BoolGenDenialMutation());           // Mutation to apply at Gen level.
                            }});
                        }},
                        0.01,                           // Mutation probability.
                        false,                                      // Hash To shuffle levels.
                        false),                                     // Hash To shuffle Mutations.
                new FixedElitismPolicy(                         // Elitism Policy, fixed number.
                        1                                           // Number of elitists.
                ),
                new FixedNewbornPolicy(                         // Newborn Policy, fixed number.
                        1                                           // Number of newborns.
                ),
                new ChessGAInitializer(                         // Initializer

                        numberOfQueens,                             // Number of Queens.
                        new ChessFeasibilityFunction(),             // Feasibility Function.
                        new ChessObjectiveFunction())               // Objective Function.
        );
        ga.optimize();
        System.out.println(ga.getResult());
    }
}
