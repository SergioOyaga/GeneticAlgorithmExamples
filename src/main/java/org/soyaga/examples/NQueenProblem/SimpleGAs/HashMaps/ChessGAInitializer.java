package org.soyaga.examples.NQueenProblem.SimpleGAs.HashMaps;

import lombok.AllArgsConstructor;
import org.soyaga.Initializer.GAInitializer;
import org.soyaga.ga.Evaluable.FeasibilityFunction;
import org.soyaga.ga.Evaluable.ObjectiveFunction;
import org.soyaga.ga.GeneticInformationContainer.Chromosome.HashMapChromosome;
import org.soyaga.ga.GeneticInformationContainer.Gen.GenericGen;
import org.soyaga.ga.GeneticInformationContainer.Genome.HashMapGenome;
import org.soyaga.ga.Individual;

import java.util.AbstractMap;
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
     * Function that initializes a new individual from scratch. In this case each individual have one HashMspGenome,
     * (the key sof this map represents rows) composed of N HashMspChromosomes (the keys of this map represents columns).
     * And each Chromosome is composed of N GenericGen that stores random boolean representing the Queen.
     * @return Individual randomly initialized.
     */
    @Override
    public Individual initializeIndividual() {
        HashMapGenome<Integer, HashMapChromosome> genome = new HashMapGenome<>();
        for (int i=0;i<this.numberOfQueens;i++){
            HashMapChromosome<Integer, GenericGen> chromosome= new HashMapChromosome<>();
            for (int j=0;j<this.numberOfQueens;j++){
                GenericGen<Boolean> gen = new GenericGen( RandomGenerator.getDefault().nextBoolean());
                chromosome.add(new AbstractMap.SimpleEntry<Integer,GenericGen>(j,gen));
            }
            genome.add(new AbstractMap.SimpleEntry<Integer,HashMapChromosome>(i,chromosome));
        }
        return new Individual(genome,this.feasibilityFunction,this.objectiveFunction);

    }
}
