package org.soyaga.examples.NQueenProblem.AdHocGA;

import lombok.AllArgsConstructor;
import org.soyaga.Initializer.GAInitializer;
import org.soyaga.ga.Evaluable.FeasibilityFunction;
import org.soyaga.ga.Evaluable.ObjectiveFunction;
import org.soyaga.ga.GeneticInformationContainer.Genome.ArrayListGenome;
import org.soyaga.ga.Individual;

import java.util.random.RandomGenerator;

/**
 * This class allow the initialization of new Individuals from scratch.
 */
@AllArgsConstructor
public class ChessGAInitializer extends GAInitializer {
    private final Integer numberOfQueens;
    private final FeasibilityFunction feasibilityFunction;
    private final ObjectiveFunction objectiveFunction;

    /**
     * Function that initializes a new individual from scratch. In this case each individual have one ArrayListGenome,
     * composed of an ArrayList&lt;Integers&gt; where each position in the array represents a row, and the value stores
     * the column.
     * @return Individual randomly initialized.
     */
    @Override
    public Individual initializeIndividual() {
        ArrayListGenome<Integer> genome = new ArrayListGenome<>();
        for (int i=0;i<this.numberOfQueens;i++){
            genome.add(RandomGenerator.getDefault().nextInt(numberOfQueens));
        }
        return new Individual(genome,this.feasibilityFunction,this.objectiveFunction);

    }
}
