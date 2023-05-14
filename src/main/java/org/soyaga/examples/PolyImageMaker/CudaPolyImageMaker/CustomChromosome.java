package org.soyaga.examples.PolyImageMaker.CudaPolyImageMaker;

import lombok.AllArgsConstructor;
import org.soyaga.ga.GeneticInformationContainer.Chromosome.Chromosome;

import java.awt.*;
import java.util.ArrayList;

/**
 * Class that contains a Polygon and a Color. Those are the fundamental parts of our image.
 */
@AllArgsConstructor
public class CustomChromosome implements Chromosome {
    /**
     * Color of the Polygon.
     */
    private Color color;
    /**
     * Polygon.
     */
    private Polygon polygon;


    /**
     * Constructor that creates a deep copy of the Chromosome. Color is effectively final, and the polygon can be deep
     * copied using its constructor.
     * @return Chromosome containing the deep copy.
     */
    @Override
    public CustomChromosome createCopy() {
        return new CustomChromosome(this.color,
                new Polygon(this.polygon.xpoints,this.polygon.ypoints,this.polygon.npoints));
    }

    /**
     * Function that gathers the genetic information. In this case ArrayList&lt;Color,Polygon&gt;.
     * @return Object containing the genetic information. Typically, Genome, Chromosome or Gen.
     */
    @Override
    public ArrayList<Object> getGeneticInformation() {
        Color color_final= this.color;
        Polygon polygon_final= this.polygon;
        return new ArrayList<>() {{add(color_final);add(polygon_final);}};
    }

    /**
     * Function that sets the genetic information.
     * @param geneticInformation Object containing the genetic information. Typically, Genome, Chromosome or Gen.
     *                           In this case ArrayList&lt;Color,Polygon&gt;.
     */
    @Override
    public void setGeneticInformation(Object geneticInformation) {
        this.color = (Color)(((ArrayList)geneticInformation).get(0));
        this.polygon =  (Polygon)(((ArrayList)geneticInformation).get(1));
    }

    /**
     * Function to verbose the optimization process.
     * @return a string containing the Gen string representation.
     */
    @Override
    public String toString() {
        return "Polygon = " + this.polygon.toString() + ", col = " + this.color.toString();
    }

}
