package org.soyaga.examples.NQueenProblem.CustomGA;

/**
 * Instantiates and optimize a CustomGeneticAlgorithm Object. Fills it with all the Custom GA classes needed to perform
 * the optimization. Optimizes given the previous configuration and prints the result in the console.
 */
public class RunNQueenOptimization {
    public static void main(String ... args){
        int populationSize = 100;
        int numberOfQueens = 8;
        assert numberOfQueens%2==0: "This only work with an even number of Queens";
        CustomGeneticAlgorithm ga = new CustomGeneticAlgorithm(
                "CustomGA",                                 // ID.
                populationSize,                                 // Size of the population.
                new CustomStoppingPolicy(                       // Custom Stopping criteria.
                        20000
                ),
                new CustomCrossoverPolicy(                      // Custom Crossover Policy.
                        populationSize*80/100,                      // Number of crossovers in even iterations.
                        populationSize*40/100,                      // Number of crossovers in odd iterations.
                        new CustomSelection(),                      // Custom Parent Selection.
                        new CustomCrossover()),                     // Custom Crossover type.
                new CustomMutationPolicy(                       // Custom Mutation Policy.
                        0.02,                                       // Custom mutation probability.
                        new CustomMutation(numberOfQueens)),        // Custom mutation to apply.
                new CustomElitismPolicy(                        // Custom Elitism Policy
                        populationSize*50/100,                      // Number of elitists in even iterations.
                        populationSize*10/100),                     // Number of elitists in odd iterations.
                new CustomNewbornPolicy(                        // Custom Newborn Policy
                        populationSize*10/100,                      // Number of newborns in even iterations.
                        populationSize*50/100),                     // Number of newborns in odd iterations.
                new ChessGAInitializer(                         // Initializer.
                        numberOfQueens,                             // Number of Queens.
                        new ChessFeasibilityFunction(numberOfQueens),   // Feasibility Function.
                        new ChessObjectiveFunction(numberOfQueens)),    // Objective Function.
                numberOfQueens                                  // Number of Queens.
        );
        ga.optimize();
        System.out.println(ga.getResult());

    }
}
