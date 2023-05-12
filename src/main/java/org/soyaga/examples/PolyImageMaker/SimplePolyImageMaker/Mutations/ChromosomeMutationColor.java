package org.soyaga.examples.PolyImageMaker.SimplePolyImageMaker.Mutations;

import lombok.AllArgsConstructor;
import org.soyaga.examples.PolyImageMaker.SimplePolyImageMaker.CustomChromosome;
import org.soyaga.ga.MutationPolicy.Mutations.Mutation;
import java.awt.*;
import java.util.ArrayList;
import java.util.random.RandomGenerator;

/**
 * Class that applies a mutation to a CustomChromosome (color, polygon), by randomly changing the polygon color,
 * somewhere close to the current color.
 */
@AllArgsConstructor
public class ChromosomeMutationColor implements Mutation {
    /**
     * Integer with the iteration when to stop looking for new colors. Typically, when we are finishing our
     * optimization we want stop changing color of the already good polygons, and we want to focus on other tasks like
     * moving a little the polygon vertexes to try to improve the placement.
     */
    private final int maxIterations;
    /**
     * Integer with the range we want to explore in each time we mutate the color. Something between 0 and 255, usually
     * something relatively small. Ej.: 10
     */
    private final int move;
    /**
     * Function that applies the mutations to the Chromosome color.
     * We just change the color randomly by moving a little one of the RGBA values.
     * @param gaPart       Genome, Chromosome or Gen to mutate. In this case CustomChromosome.
     * @param mutationArgs Undefined array of objects containing information needed to mutate the part. In this case,
     *                   an Integer with the iteration number.
     */
    @Override
    public void apply(Object gaPart, Object... mutationArgs) {
        Integer iteration = (Integer)mutationArgs[0];
        if(iteration>this.maxIterations) return;
        Color oldColor = ((Color)((CustomChromosome) gaPart).getGeneticInformation().get(0));
        Polygon oldPolygon = ((Polygon)((CustomChromosome) gaPart).getGeneticInformation().get(1));
        int [] rgb= new int[]{oldColor.getRed(),oldColor.getGreen(),oldColor.getBlue(),oldColor.getAlpha()};
        int index=RandomGenerator.getDefault().nextInt(0,rgb.length);
        rgb[index]+= RandomGenerator.getDefault().nextInt(-this.move, this.move+1);
        Color newcolor = new Color(
                Math.max(0,Math.min(rgb[0],255)),
                Math.max(0,Math.min(rgb[1],255)),
                Math.max(0,Math.min(rgb[2],255)),
                Math.max(0,Math.min(rgb[3],255))
        );
        ((CustomChromosome) gaPart).setGeneticInformation(new ArrayList<>(){{add(newcolor);add(oldPolygon);}});
    }
}
