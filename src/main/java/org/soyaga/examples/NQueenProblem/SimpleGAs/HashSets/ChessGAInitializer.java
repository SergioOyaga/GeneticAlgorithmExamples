package org.soyaga.examples.NQueenProblem.SimpleGAs.HashSets;

import lombok.AllArgsConstructor;
import org.soyaga.Initialaizer.GAInitializer;
import org.soyaga.ga.Evaluable.FeasibilityFunction;
import org.soyaga.ga.Evaluable.ObjectiveFunction;
import org.soyaga.ga.GeneticInformationContainer.Chromosome.HashSetChromosome;
import org.soyaga.ga.GeneticInformationContainer.Genome.HashSetGenome;
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
     * Function that initializes a new individual from scratch. In this case each individual have one HashSetGenome,
     * composed of N HashSetChromosomes. And each Chromosome is composed of N ChessGenes that stores the position
     * (row, col) and a random boolean representing the Queen.
     * @return Individual randomly initialized.
     */
    @Override
    public Individual initializeIndividual() {
        HashSetGenome<HashSetChromosome> genome = new HashSetGenome<>();
        for (int i=0;i<this.numberOfQueens;i++){
            HashSetChromosome<ChessGen> chromosome= new HashSetChromosome<>();
            for (int j=0;j<this.numberOfQueens;j++){
                ChessGen gen = new ChessGen(i, j, RandomGenerator.getDefault().nextBoolean());
                chromosome.add(gen);
            }
            genome.add(chromosome);
        }
        return new Individual(genome,this.feasibilityFunction,this.objectiveFunction);

    }
}
