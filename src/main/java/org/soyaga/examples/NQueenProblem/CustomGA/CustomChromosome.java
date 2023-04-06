package org.soyaga.examples.NQueenProblem.CustomGA;

import lombok.AllArgsConstructor;
import org.soyaga.ga.GeneticInformationContainer.Chromosome.Chromosome;

import java.util.Collection;
import java.util.HashSet;

/**
 * CustomChromosome that contains 2 CustomGen objects that represents 2 queens in the board.
 */
@AllArgsConstructor
public class CustomChromosome implements Chromosome {
    /**
     * HashSet&lt;CustomGen&gt; with just 2 Genes.
     */
    private HashSet<CustomGen> genList;

    /**
     * Constructor that initializes an empty Chromosome
     */
    public CustomChromosome() {
        this.genList = new HashSet<>();
    }

    /**
     * Function that gets the chromosome value.
     */
    @Override
    public Collection<CustomGen>  getGeneticInformation() {
        return this.genList;
    }

    /**
     * Function that sets the chromosome value. Although it is not mandatory, it should assert the chromosome type.
     *
     * @param chromosome Object containing the Chromosome structure.
     */
    @Override
    public void setGeneticInformation(Object chromosome) {
        this.genList = (HashSet<CustomGen>)chromosome;
    }

    /**
     * Constructor that creates a deep copy of the Chromosome.
     *
     * @return Chromosome containing the deep copy.
     */
    @Override
    public CustomChromosome createCopy() {
        HashSet<CustomGen> listCopy = new HashSet<>();
        for(CustomGen gen:this.genList)listCopy.add(gen.createCopy());
        return new CustomChromosome(listCopy);
    }
}
