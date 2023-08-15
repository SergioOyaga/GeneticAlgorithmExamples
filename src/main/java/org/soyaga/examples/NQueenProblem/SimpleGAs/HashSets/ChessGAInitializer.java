package org.soyaga.examples.NQueenProblem.SimpleGAs.HashSets;

import lombok.AllArgsConstructor;
import org.soyaga.Initializer.GAInitializer;
import org.soyaga.ga.Evaluable.Feasibility.FeasibilityFunction;
import org.soyaga.ga.Evaluable.Objective.ObjectiveFunction;
import org.soyaga.ga.GeneticInformationContainer.Chromosome.HashSetChromosome;
import org.soyaga.ga.GeneticInformationContainer.Genome.HashSetGenome;
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
     * In this case, each individual has one HashSetGenome, composed of N HashSetChromosomes.
     * Each Chromosome is further composed of N ChessGenes instances that store the position
     * (row, column) and a boolean representing a Queen.
     *
     * @return A randomly initialized Individual.
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
        return new Individual(genome,this.feasibilityFunction,this.objectiveFunction, 100.);

    }
}
