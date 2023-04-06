package org.soyaga.examples.NQueenProblem.CustomGA;

import lombok.AllArgsConstructor;
import org.soyaga.ga.GeneticInformationContainer.GeneticInformationContainer;

/**
 * Class that contains the information of an Integer. This class will be interpreted by the CustomGenome as a row or
 * column number.
 */
@AllArgsConstructor
public class CustomBase implements GeneticInformationContainer {
    Integer baseVal;
    /**
     * Function that gathers the genetic information.
     * @return Object containing the genetic information. Typically, Genome, Chromosome or Gen.
     */
    @Override
    public Integer getGeneticInformation() {
        return this.baseVal;
    }

    /**
     * Function that sets the genetic information.
     * @param geneticInformation Object containing the genetic information. Typically, Genome, Chromosome or Gen.
     */
    @Override
    public void setGeneticInformation(Object geneticInformation) {
        this.baseVal=(Integer) geneticInformation;
    }

    /**
     * Constructor that creates a deep copy of the GeneticInformationContainer.
     * @return GeneticInformationContainer containing the deep copy.
     */
    @Override
    public CustomBase createCopy() {
        return new CustomBase(this.baseVal);
    }

    /**
     * Function to verbose the optimization process.
     * @return a string containing the Gen string representation.
     */
    @Override
    public String toString() {
        return this.baseVal.toString();
    }
}
