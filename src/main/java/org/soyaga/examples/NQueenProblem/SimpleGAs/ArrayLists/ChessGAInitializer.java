package org.soyaga.examples.NQueenProblem.SimpleGAs.ArrayLists;

import lombok.AllArgsConstructor;
import org.soyaga.Initializer.GAInitializer;
import org.soyaga.ga.Evaluable.Feasibility.FeasibilityFunction;
import org.soyaga.ga.Evaluable.Objective.ObjectiveFunction;
import org.soyaga.ga.GeneticInformationContainer.Chromosome.ArrayListChromosome;
import org.soyaga.ga.GeneticInformationContainer.Gen.GenericGen;
import org.soyaga.ga.GeneticInformationContainer.Genome.ArrayListGenome;
import org.soyaga.ga.Individual;

import java.util.ArrayList;
import java.util.Collections;

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
     * In this case, each individual has one ArrayListGenome, composed of N ArrayListChromosomes
     * (where positions in the array represent rows).
     * Each Chromosome is further composed of a GenericGen instance that stores an integer representing a Queens column.
     *
     * @return A randomly initialized Individual.
     */
    @Override
    public Individual initializeIndividual() {
        ArrayListGenome<ArrayListChromosome<GenericGen<Integer>>> genome = new ArrayListGenome<>();
        ArrayList<Integer> positions = new ArrayList<>();
        for (int i=0;i<this.numberOfQueens;i++)positions.add(i);
        Collections.shuffle(positions);
        for (int i=0;i<this.numberOfQueens;i++){
            ArrayListChromosome<GenericGen<Integer>> chromosome = new ArrayListChromosome<>();
            GenericGen<Integer> gen = new GenericGen<>(positions.get(i));
            chromosome.add(gen);
            genome.add(chromosome);
        }
        return new Individual(genome,this.feasibilityFunction,this.objectiveFunction, 100.);

    }
}
