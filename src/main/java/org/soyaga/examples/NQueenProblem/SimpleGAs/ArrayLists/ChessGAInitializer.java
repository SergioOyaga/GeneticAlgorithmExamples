package org.soyaga.examples.NQueenProblem.SimpleGAs.ArrayLists;

import lombok.AllArgsConstructor;
import org.soyaga.Initialaizer.GAInitializer;
import org.soyaga.ga.Evaluable.FeasibilityFunction;
import org.soyaga.ga.Evaluable.ObjectiveFunction;
import org.soyaga.ga.GeneticInformationContainer.Chromosome.ArrayListChromosome;
import org.soyaga.ga.GeneticInformationContainer.Gen.GenericGen;
import org.soyaga.ga.GeneticInformationContainer.Genome.ArrayListGenome;
import org.soyaga.ga.Individual;

import java.util.ArrayList;
import java.util.Collections;

/**
 * This class allow the initialization of new Individuals from scratch.
 */
@AllArgsConstructor
public class ChessGAInitializer extends GAInitializer {
    private final Integer numberOfQueens;
    private final FeasibilityFunction feasibilityFunction;
    private final ObjectiveFunction objectiveFunction;

    /**
     * Function that initializes a new individual from scratch. In this case each individual have one ArrayListGenome
     * (Positions in the Array represents rows), composed of N ArrayListChromosomes (Positions in the Array represents
     * cols). And each Chromosome is composed of n GenericGen that stores random boolean representing the Queen.
     * @return Individual randomly initialized.
     */
    @Override
    public Individual initializeIndividual() {
        ArrayListGenome<ArrayListChromosome> genome = new ArrayListGenome<>();
        ArrayList<Integer> positions = new ArrayList<>();
        for (int i=0;i<this.numberOfQueens;i++)positions.add(i);
        Collections.shuffle(positions);
        for (int i=0;i<this.numberOfQueens;i++){
            ArrayListChromosome <GenericGen>chromosome= new ArrayListChromosome<>();
            GenericGen<Integer> gen = new GenericGen<>(positions.get(i));
            chromosome.add(gen);
            genome.add(chromosome);
        }
        return new Individual(genome,this.feasibilityFunction,this.objectiveFunction);

    }
}
