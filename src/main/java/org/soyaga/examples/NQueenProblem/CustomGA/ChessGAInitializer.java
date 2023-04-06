package org.soyaga.examples.NQueenProblem.CustomGA;

import lombok.AllArgsConstructor;
import org.soyaga.Initialaizer.GAInitializer;
import org.soyaga.ga.Evaluable.FeasibilityFunction;
import org.soyaga.ga.Evaluable.ObjectiveFunction;
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
     * Function that initializes a new individual from scratch. In this case each individual have one CustomGenome,
     * composed of N/2 CustomChromosomes. And each Chromosome is composed of 2 ChessGenes that stores the position
     * (row, col) using a CustomBase object.
     * @return Individual randomly initialized.
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
        return new Individual(genome,this.feasibilityFunction,this.objectiveFunction);

    }
}
