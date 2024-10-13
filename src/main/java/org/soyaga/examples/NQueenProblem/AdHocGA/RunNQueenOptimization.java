package org.soyaga.examples.NQueenProblem.AdHocGA;

import org.soyaga.ga.CrossoverPolicy.FixedCrossoverPolicy;
import org.soyaga.ga.CrossoverPolicy.ParentCross.OnePointCrossover;
import org.soyaga.ga.CrossoverPolicy.ParentSelection.RandomSelection;
import org.soyaga.ga.ElitismPolicy.FixedElitismPolicy;
import org.soyaga.ga.MutationPolicy.ByLevelSingleProbMutPolicy;
import org.soyaga.ga.MutationPolicy.Mutations.GenericSwapMutation;
import org.soyaga.ga.NewbornPolicy.FixedNewbornPolicy;
import org.soyaga.ga.StatsRetrievalPolicy.NIterationsStatsRetrievalPolicy;
import org.soyaga.ga.StatsRetrievalPolicy.Stat.*;
import org.soyaga.ga.StoppingPolicy.MaxIterationCriteriaPolicy;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Instantiates and optimize a ChessGA Object. Fills it with all the GA classes needed to perform the optimization.
 * Optimizes given the previous configuration and prints the result in the console.
 */
public class RunNQueenOptimization {
    public static void main(String ... args) throws IOException {
        int populationSize = 100;
        int numberOfQueens = 8;
        String outputPath = "src/out/NQueen/AdHocGA";
        ChessGA ga = new ChessGA("CompleteSimpleLessClassesGA", // ID.
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
                                add(new GenericSwapMutation(1));}});    // Mutation to apply at Genome level.
                        }},
                        0.2,                                // Mutation probability.
                        false,                                          // Hash To shuffle levels.
                        false),                                         // Hash To shuffle Mutations.
                new FixedElitismPolicy(                             // Elitism Policy, fixed number.
                        populationSize*10/100                           // Number of elitists.
                ),
                new FixedNewbornPolicy(                             // Newborn Policy, fixed number.
                        populationSize*10/100                           // Number of newborns.
                ),
                new ChessGAInitializer(numberOfQueens,              // Initializer.
                        new ChessFeasibilityFunction(),                 // Feasibility Function.
                        new ChessObjectiveFunction()),                  // Objective Function.
                numberOfQueens,                                     // Number of Queens.
                new NIterationsStatsRetrievalPolicy(                // Stat Retrieval Policy, every N iterations.
                        1,                                              // Number of iterations.
                        new ArrayList<>(){{                         // Array of stats.
                            add(new CurrentMinFitnessStat(4));      // Min Fitness Stat.
                            add(new CurrentMaxFitnessStat(4));      // Max Fitness Stat.
                            add(new HistoricalMinFitnessStat(4));   // Hist Min Fitness Stat.
                            add(new HistoricalMaxFitnessStat(4));   // Hist Max Fitness Stat.
                            add(new MeanSdStat(4));                 // Fitness Mean and Standard Dev Stat.
                            add(new PercentileStat(4,               // Interpolated Percentile Fitness Stat.
                                    new ArrayList<>(){{                             // Array of percentiles.
                                        add(0);add(25);add(50);add(75);add(100);        // Percentiles values.
                            }}));
                            add(new StepGradientStat(4));           // Step Gradient Stat.
                            add(new TimeGradientStat(4));           // Time Gradient Stat.
                            add(new ElapsedTimeStat(4));            // Elapsed Time Stat.
                        }},
                        outputPath,                                     // Stat retrieval output path.
                        true,                                           // Print Stats in command line.
                        true                                            // Save Stats summary in a csv.
                )
        );
        ga.optimize();
        System.out.println(ga.getResult());
    }
}
