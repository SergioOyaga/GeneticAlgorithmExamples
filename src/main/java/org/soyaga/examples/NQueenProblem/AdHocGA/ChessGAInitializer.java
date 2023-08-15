package org.soyaga.examples.NQueenProblem.AdHocGA;

import lombok.AllArgsConstructor;
import org.soyaga.Initializer.GAInitializer;
import org.soyaga.ga.Evaluable.Feasibility.FeasibilityFunction;
import org.soyaga.ga.Evaluable.Objective.ObjectiveFunction;
import org.soyaga.ga.GeneticInformationContainer.Genome.ArrayListGenome;
import org.soyaga.ga.Individual;

import java.util.random.RandomGenerator;

/**
 * This class enables the initialization of new individuals from scratch.
 */
@AllArgsConstructor
public class ChessGAInitializer extends GAInitializer {
    private final Integer numberOfQueens;
    private final FeasibilityFunction feasibilityFunction;
    private final ObjectiveFunction objectiveFunction;

    /**
     * This function initializes a new individual from scratch.
     * In this case, each individual has one ArrayListGenome, composed of N Integers
     * (where positions in the array represent rows and the integer value the column).
     *
     * @return A randomly initialized Individual.
     */
    @Override
    public Individual initializeIndividual() {
        ArrayListGenome<Integer> genome = new ArrayListGenome<>();
        for (int i=0;i<this.numberOfQueens;i++){
            genome.add(RandomGenerator.getDefault().nextInt(numberOfQueens));
        }
        return new Individual(genome,this.feasibilityFunction,this.objectiveFunction, 100.);

    }
}
