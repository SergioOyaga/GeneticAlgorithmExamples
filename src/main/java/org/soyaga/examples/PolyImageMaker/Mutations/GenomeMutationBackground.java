package org.soyaga.examples.PolyImageMaker.Mutations;

import lombok.AllArgsConstructor;
import org.soyaga.examples.PolyImageMaker.CustomChromosome;
import org.soyaga.examples.PolyImageMaker.CustomGenome;
import org.soyaga.ga.MutationPolicy.Mutations.Mutation;
import java.awt.*;
import java.util.ArrayList;
import java.util.random.RandomGenerator;

/**
 * Class that applies a mutation to a CustomGenome (backgroundColor, ArrayList&lt;CustomChromosome&gt;),
 * by randomly changing the background color, somewhere close to its current color.
 */
@AllArgsConstructor
public class GenomeMutationBackground implements Mutation {
    /**
     * Integer with the iteration when to stop looking for new colors. Typically, the background color is the first
     * thing the optimization achieves, so we suggest to suppress this mutation the first one.
     */
    private final int maxIterations;
    /**
     * Integer with the range we want to explore in each time we mutate the color. Something between 0 and 255, usually
     * something relatively small. Ej.: 10
     */
    private final int move;
    /**
     * Function that applies the mutations to the Genomes Background.
     * We just change the color randomly by moving a little one of the RGB values.
     * @param gaPart       Genome, Chromosome or Gen to mutate. In this case CustomGenome.
     * @param mutationArgs Undefined array of objects containing information needed to mutate the part. In this case,
     *                    an Integer with the iteration number.
     */
    @Override
    public void apply(Object gaPart, Object... mutationArgs) {
        Integer iteration = (Integer)mutationArgs[0];
        if(iteration>this.maxIterations) return;
        Color oldBackground = ((Color)((CustomGenome) gaPart).getGeneticInformation().get(0));
        ArrayList oldGenome = (ArrayList) ((CustomGenome) gaPart).getGeneticInformation().get(1);
        int [] rgb= new int[]{oldBackground.getRed(),oldBackground.getGreen(),oldBackground.getBlue()};
        int index=RandomGenerator.getDefault().nextInt(0,rgb.length);
        rgb[index]+= RandomGenerator.getDefault().nextInt(-this.move, this.move+1);
        Color newBackground = new Color(
                Math.max(0,Math.min(rgb[0],255)),
                Math.max(0,Math.min(rgb[1],255)),
                Math.max(0,Math.min(rgb[2],255))
        );
        ((CustomGenome) gaPart).setGeneticInformation(new ArrayList<>(){{add(newBackground);add(oldGenome);}});
    }
}
