package org.soyaga.examples.NQueenProblem.SimpleGAs.ArrayLists;

import org.soyaga.ga.CrossoverPolicy.FixedCrossoverPolicy;
import org.soyaga.ga.CrossoverPolicy.ParentCross.OnePointCrossover;
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
        int numberOfQueens = 8;
        ChessGA ga = new ChessGA("CompleteSimpleArrayListGA",   // ID.
                populationSize,                                     // Size of the population.
                new MaxIterationCriteriaPolicy(                     // Stopping criteria, max iterations.
                        1000                                            // Maximum number of iterations.
                ),
                new FixedCrossoverPolicy(                           // Crossover Policy, fixed number.
                        populationSize*80/100,                          // Number of crossovers.
                        new RandomSelection(),                          // Parent Selection, random selection.
                        new OnePointCrossover()),                       // Crossover type, one-point crossover.
                new ByLevelSingleProbMutPolicy(                     // Mutation Policy, single prob.
                        new ArrayList<>(){{                             // Mutation Levels. Only one level (Genome).
                            add(new ArrayList<>(){{                         // Array of mutation at Genome level.
                                add(new GenericSwapMutation(1));    // Mutation to apply at Genome level.
                            }});
                        }},
                        0.01,                                           // Mutation probability.
                        false,                                          // Hash To shuffle levels.
                        false),                                         // Hash To shuffle mutations.
                new FixedElitismPolicy(                             // Elitism Policy, fixed number.
                        populationSize*10/100                           // Number of elitists.
                ),
                new FixedNewbornPolicy(                             // Newborn Policy, fixed number.
                        populationSize*10/100                           // Number of newborns.
                ),
                new ChessGAInitializer(                             // Initializer.
                        numberOfQueens,                                 // Number of Queens.
                        new ChessFeasibilityFunction(),                 // Feasibility Function.
                        new ChessObjectiveFunction())                   // Objective Function.
        );
        ga.optimize();
        System.out.println(ga.getResult());
    }
}
