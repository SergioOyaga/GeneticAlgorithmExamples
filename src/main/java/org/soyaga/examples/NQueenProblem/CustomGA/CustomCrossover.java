package org.soyaga.examples.NQueenProblem.CustomGA;

import org.soyaga.ga.CrossoverPolicy.ParentCross.Crossover;
import org.soyaga.ga.Individual;

import java.util.random.RandomGenerator;

/**
 * This class represents a HeuristicCrossover.
 */
public class CustomCrossover implements Crossover {
    /**
     * Crossover that although receives the generation number is just a copy of the HeuristicCrossover.
     * We could have varied the behavior of the crossover based on the context (iterations, convergence...).
     *
     * @param parent1   Individual with the first parent.
     * @param parent2   Individual with the second parent.
     * @param crossArgs Undefined Array of elements to perform the crossover procedure.
     * @return Individual containing the offspring information.
     */
    @Override
    public Individual computeChild(Individual parent1, Individual parent2, Object[] crossArgs) {
        Individual child = RandomGenerator.getDefault().nextBoolean()? parent1:parent2;
        return new Individual(child.getGenome().createCopy(), child.getFeasibilityFunction(), child.getObjectiveFunction(),parent1.getPenalization());
    }
}
