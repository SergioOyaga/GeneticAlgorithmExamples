package org.soyaga.examples.ImageMaker.ShapeImageMaker;

import lombok.AllArgsConstructor;
import org.soyaga.ga.GeneticInformationContainer.Genome.Genome;

import java.awt.*;
import java.util.ArrayList;

/**
 * Class that contains a Color and an ArrayList&lt;CustomChromosome&gt;. Those are the parts compose our image.
 */
@AllArgsConstructor
public class CustomGenome implements Genome {
    /**
     * Color representing the image background color.
     */
    private Color backgroundColor;
    /**
     * ArrayList&lt;CustomChromosome&gt with the polygons and colors that paints our image.
     */
    private ArrayList<CustomChromosome> chromosomeList;


    /**
     * Function that gathers the genetic information. In this case ArrayList&lt;Color,ArrayList&lt;CustomChromosome&gt;&gt;.
     * @return Object containing the genetic information. Typically, Genome, Chromosome or Gen.
     */
    @Override
    public ArrayList<Object> getGeneticInformation() {
        Color color_final=this.backgroundColor;
        ArrayList<CustomChromosome> chromosomeList_final = this.chromosomeList;
        return new ArrayList<>(){{add(0,color_final);add(1,chromosomeList_final);}};
    }

    /**
     * Function that sets the genetic information.
     * @param chromosomes Object containing the genetic information. Typically, Genome, Chromosome or Gen.
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
