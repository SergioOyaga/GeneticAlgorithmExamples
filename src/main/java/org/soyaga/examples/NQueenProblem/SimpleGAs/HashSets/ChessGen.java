package org.soyaga.examples.NQueenProblem.SimpleGAs.HashSets;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.soyaga.ga.GeneticInformationContainer.Gen.Gen;

/**
 * ChessGen containing the row and column numbers as Integers and indicating the presence of a queen with a Boolean.
 */
@AllArgsConstructor
@Getter
public class ChessGen implements Gen<Boolean> {
    /**
     * Row number immutable.
     */
    private final Integer row;
    /**
     * Column number immutable.
     */
    private final Integer column;
    /**
     * Boolean true == Queen, false== empty.
     */
    private Boolean value;

    /**
     * Constructor that creates a deep copy of the Chromosome.
     *
     * @return Chromosome containing the deep copy.
     */
    @Override
    public Gen<Boolean> createCopy() {
        return new ChessGen(this.row, this.column, this.value);
    }

    /**
     * Function that gathers the genetic information.
     *
     * @return Object containing the genetic information. Typically, Genome, Chromosome or Gen.
     */
    @Override
    public Boolean getGeneticInformation() {
        return this.value;
    }

    /**
     * Function that sets the genetic information.
     *
     * @param geneticInformation Object containing the genetic information. Typically, Genome, Chromosome or Gen.
     */
    @Override
    public void setGeneticInformation(Object geneticInformation) {
        this.value = (Boolean) geneticInformation;
    }
}
