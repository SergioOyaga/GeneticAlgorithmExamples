package org.soyaga.examples.NQueenProblem.CustomGA;

import lombok.AllArgsConstructor;
import org.soyaga.ga.GeneticInformationContainer.Gen.Gen;

import java.util.ArrayList;

/**
 * CustomGen containing CustomBase objects for the row and column numbers where the queen is placed.
 */
@AllArgsConstructor
public class CustomGen implements Gen {
    /**
     * CustomBase representing the row number where this queen is placed.
     */
    private CustomBase row;
    /**
     * CustomBase representing the column number where this queen is placed.
     */
    private CustomBase col;


    /**
     * Constructor that creates a deep copy of the Chromosome.
     * @return Chromosome containing the deep copy.
     */
    @Override
    public CustomGen createCopy() {
        return new CustomGen(this.row.createCopy(),this.col.createCopy());
    }

    /**
     * Function that gathers the genetic information.
     * @return Object containing the genetic information. Typically, Genome, Chromosome or Gen.
     */
    @Override
    public ArrayList<CustomBase> getGeneticInformation() {
        CustomBase b1= this.row;
        CustomBase b2= this.col;
        return new ArrayList<CustomBase>() {{add(b1);add(b2);}};
    }

    /**
     * Function that sets the genetic information.
     * @param geneticInformation Object containing the genetic information. Typically, Genome, Chromosome or Gen.
     */
    @Override
    public void setGeneticInformation(Object geneticInformation) {
        this.row.setGeneticInformation (((ArrayList)geneticInformation).get(0));
        this.col.setGeneticInformation (((ArrayList)geneticInformation).get(1));
    }

    /**
     * Function to verbose the optimization process.
     * @return a string containing the Gen string representation.
     */
    @Override
    public String toString() {
        return "row = " + this.row.toString() + ", col = " + this.col.toString();
    }
}
