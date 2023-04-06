package org.soyaga.examples.NQueenProblem.CustomGA;

import lombok.AllArgsConstructor;
import org.soyaga.ga.GeneticInformationContainer.Genome.Genome;

import java.util.Collection;
import java.util.HashSet;

/**
 * CustomGenome that consist in a HashSet of CustomChromosomes.
 */
@AllArgsConstructor
public class CustomGenome implements Genome {
    /**
     * HashSet<CustomChromosome> representing the Genome.
     */
    HashSet<CustomChromosome> chromosomeList;

    /**
     * Constructor that initializes an empty genome.
     */
    public CustomGenome() {
        this.chromosomeList=new HashSet<>();
    }

    /**
     * Function that Gets the Chromosome structure value.
     * @return Collection<CustomChromosome> containing the chromosomeList.
     */
    @Override
    public Collection<CustomChromosome> getGeneticInformation() {
        return this.chromosomeList;
    }

    /**
     * Function that Sets the Chromosome structure value.
     * @param chromosomes HashSet<CustomChromosome> to set.
     */
    @Override
    public void setGeneticInformation(Object chromosomes) {
        this.chromosomeList = (HashSet<CustomChromosome>)chromosomes;
    }

    /**
     * Constructor that creates a deep copy of the Genome
     * @return Genome containing the deep copy.
     */
    @Override
    public CustomGenome createCopy() {
        HashSet<CustomChromosome> listCopy = new HashSet<>();
        for(CustomChromosome chromosome:this.chromosomeList)listCopy.add(chromosome.createCopy());
        return new CustomGenome(listCopy) {
        };
    }
}
