package org.soyaga.examples.ImageMaker.CudaPolyImageMaker.Mutations;

import lombok.AllArgsConstructor;
import org.soyaga.examples.ImageMaker.CudaPolyImageMaker.CustomChromosome;
import org.soyaga.examples.ImageMaker.CudaPolyImageMaker.CustomGenome;
import org.soyaga.ga.MutationPolicy.Mutations.Mutation;

import java.awt.*;
import java.util.ArrayList;
import java.util.random.RandomGenerator;


/**
 * Class that applies a mutation to a CustomGenome (backgroundColor, ArrayList{@literal <CustomChromosome>}),
 * by changing the background color. We change the RGB values of the background randomly, but only in a range close to
 * the current values.
 */
@AllArgsConstructor
public class GenomeMutationBackground implements Mutation {
    /**
     * This integer specifies the iteration at which to halt the search for new colors. Generally, the optimization process
     * initially attains the background color, making it advisable to disable this mutation at the outset.
     */
    private final int maxIterations;
    /**
     * This integer specifies the range to be explored each time the color is mutated. Typically, this value lies
     * between 0 and 255, and is relatively small. For example: 10.
     */
    private final int move;

    /**
     * Function that applies the mutations to the Genomes ArrayList{@literal <CustomChromosome>};
     * We change the RGB values of the background randomly, but only in a range close to
     * the current values.
     *
     * @param gaPart ArrayList{@literal <Color,ArrayList<CustomChromosome>>}. In this case,the color is what we edit.
     * @param mutationArgs Undefined array of objects containing information needed to mutate the part. In this case,
     *                    an Integer with the iteration number.
     */
    @Override
    public void apply(Object gaPart, Object... mutationArgs) {
        Integer iteration = (Integer)mutationArgs[0];
        if(iteration>this.maxIterations) return;
        Color oldBackground = ((Color)((CustomGenome) gaPart).getGeneticInformation().get(0));
        ArrayList<CustomChromosome> oldGenome = (ArrayList<CustomChromosome>) ((CustomGenome) gaPart).getGeneticInformation().get(1);
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
