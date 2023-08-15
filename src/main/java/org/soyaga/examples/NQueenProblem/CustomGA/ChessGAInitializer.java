package org.soyaga.examples.NQueenProblem.CustomGA;

import lombok.AllArgsConstructor;
import org.soyaga.Initializer.GAInitializer;
import org.soyaga.ga.Evaluable.Feasibility.FeasibilityFunction;
import org.soyaga.ga.Evaluable.Objective.ObjectiveFunction;
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
     * In this case, each individual has one CustomGenome, composed of N/2 CustomChromosomes.
     * Each Chromosome is further composed of 2 ChessGenes instances that store the position
     * (row, column) each in a CustomBase. The combination of customBases in a CustomGen defines a queen.
     *
     * @return A randomly initialized Individual.
     */
    @Override
    public Individual initializeIndividual() {
        CustomGenome genome = new CustomGenome();
        for (int i=0;i<this.numberOfQueens/2;i++){
            CustomChromosome chromosome= new CustomChromosome();
            CustomGen gen = new CustomGen(
                    new CustomBase(RandomGenerator.getDefault().nextInt(this.numberOfQueens)),
                    new CustomBase(RandomGenerator.getDefault().nextInt(this.numberOfQueens))
            );
            chromosome.add(gen);
            gen = new CustomGen(
                    new CustomBase(RandomGenerator.getDefault().nextInt(this.numberOfQueens)),
                    new CustomBase(RandomGenerator.getDefault().nextInt(this.numberOfQueens))
            );
            chromosome.add(gen);
            genome.add(chromosome);
        }
        return new Individual(genome,this.feasibilityFunction,this.objectiveFunction,100.);

    }
}
