package org.soyaga.examples.NQueenProblem.CustomGA;

/**
 * Instantiates and optimize a CustomGeneticAlgorithm Object. Fills it with all the Custom GA classes needed to perform
 * the optimization. Optimizes given the previous configuration and prints the result in the console.
 */
public class RunNQueenOptimization {
    public static void main(String ... args){
        Integer populationSize = 100;
        Integer numberOfQueens = 8;
        assert numberOfQueens%2==0: "This only work with an even number of Queens";
        CustomGeneticAlgorithm ga = new CustomGeneticAlgorithm("CustomGA", populationSize,
                new CustomStoppingPolicy(20000),
                new CustomCrossoverPolicy(populationSize*80/100,populationSize*40/100,
                        new CustomSelection(),
                        new CustomCrossover()),
                new CustomMutationPolicy(0.02,new CustomMutation(numberOfQueens)),
                new CustomElitismPolicy(populationSize*50/100,populationSize*10/100),
                new CustomNewbornPolicy(populationSize*10/100,populationSize*50/100),
                new ChessGAInitializer(numberOfQueens,
                        new ChessFeasibilityFunction(numberOfQueens),
                        new ChessObjectiveFunction(numberOfQueens)),
                numberOfQueens
        );
        ga.optimize();
        System.out.println(ga.getResult());

    }
}
