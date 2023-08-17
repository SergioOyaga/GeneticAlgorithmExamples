package org.soyaga.examples.ImageMaker.ShapeImageMaker;

import lombok.AllArgsConstructor;
import org.soyaga.ga.GeneticInformationContainer.Genome.Genome;
import java.awt.*;
import java.util.ArrayList;

/**
 * CustomGenome that consist of a Color for the image background and an ArrayList of CustomChromosomes.
 */
@AllArgsConstructor
public class CustomGenome implements Genome<Object> {
    /**
     * Color representing the image background color.
     */
    private Color backgroundColor;
    /**
     * ArrayList&lt;CustomChromosome&gt with the shapes, rotations and colors.
     */
    private ArrayList<CustomChromosome> chromosomeList;


    /**
     * Function that gathers the genetic information. In this case ArrayList&lt;Color,ArrayList&lt;CustomChromosome&gt;&gt;.
     *
     * @return Object containing the genetic information.
     */
    @Override
    public ArrayList<Object> getGeneticInformation() {
        Color color_final=this.backgroundColor;
        ArrayList<CustomChromosome> chromosomeList_final = this.chromosomeList;
        return new ArrayList<>(){{add(0,color_final);add(1,chromosomeList_final);}};
    }

    /**
     * Function that sets the genetic information.
     *
     * @param chromosomes Object containing the genetic information.
     *                           In this case ArrayList&lt;Color,ArrayList&lt;CustomChromosome&gt;&gt;.
     */
    @Override
    public void setGeneticInformation(Object chromosomes) {
        this.backgroundColor = (Color)((ArrayList<Object>)chromosomes).get(0);
        this.chromosomeList = (ArrayList<CustomChromosome>)((ArrayList<Object>)chromosomes).get(1);
    }

    /**
     * Constructor that creates a deep copy of the Genome. Color is effectively final, and each CustomChromosome
     * implements its own createCopy() method.
     *
     * @return Chromosome containing the deep copy.
     */
    @Override
    public CustomGenome createCopy() {
        ArrayList<CustomChromosome> listCopy = new ArrayList<>();
        for(CustomChromosome chromosome:this.chromosomeList)listCopy.add(chromosome.createCopy());
        return new CustomGenome(this.backgroundColor, listCopy) {
        };
    }
}
