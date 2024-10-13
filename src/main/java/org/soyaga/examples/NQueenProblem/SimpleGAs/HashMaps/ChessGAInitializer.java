package org.soyaga.examples.NQueenProblem.SimpleGAs.HashMaps;

import lombok.AllArgsConstructor;
import org.soyaga.Initializer.GAInitializer;
import org.soyaga.ga.Evaluable.Feasibility.FeasibilityFunction;
import org.soyaga.ga.Evaluable.Objective.ObjectiveFunction;
import org.soyaga.ga.GeneticInformationContainer.Chromosome.HashMapChromosome;
import org.soyaga.ga.GeneticInformationContainer.Gen.GenericGen;
import org.soyaga.ga.GeneticInformationContainer.Genome.HashMapGenome;
import org.soyaga.ga.Individual;

import java.util.AbstractMap;
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
     * In this case, each individual has one HashMapGenome (where the keys of this map represent rows),
     * composed of N HashMapChromosomes (where the keys of this map represent columns).
     * Each Chromosome is further composed of N GenericGen instances that Stores random booleans representing the
     * presence of a Queen.
     *
     * @return A randomly initialized Individual.
     */
    @Override
    public Individual initializeIndividual() {
        HashMapGenome<Integer, HashMapChromosome<Integer,GenericGen<Boolean>>> genome = new HashMapGenome<>();
        for (int i=0;i<this.numberOfQueens;i++){
            HashMapChromosome<Integer, GenericGen<Boolean>> chromosome= new HashMapChromosome<>();
            for (int j=0;j<this.numberOfQueens;j++){
                GenericGen<Boolean> gen = new GenericGen<>( RandomGenerator.getDefault().nextBoolean());
                chromosome.add(new AbstractMap.SimpleEntry<>(j,gen));
            }
            genome.add(new AbstractMap.SimpleEntry<>(i,chromosome));
        }
        return new Individual(genome,this.feasibilityFunction,this.objectiveFunction, 100.);

    }
}
