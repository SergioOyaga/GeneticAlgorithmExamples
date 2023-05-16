package org.soyaga.examples.ImageMaker.ShapeImageMaker.Mutations;

import lombok.AllArgsConstructor;
import org.soyaga.examples.ImageMaker.ShapeImageMaker.CustomChromosome;
import org.soyaga.ga.MutationPolicy.Mutations.Mutation;

import java.awt.*;
import java.util.ArrayList;
import java.util.random.RandomGenerator;

/**
 * Class that applies a mutation to a CustomChromosome (color,rotation, shape), by rotating the shape, something
 * close to the current rotation.
 */
@AllArgsConstructor
public class ChromosomeMutationRotateShape implements Mutation {
    /**
     * Integer with the iteration when to stop looking for new colors. Typically, when we are finishing our
     * optimization we want stop changing color of the already good shapes, and we want to focus on other tasks like
     * moving a little the shape vertexes to try to improve the placement.
     */
    private final int maxIterations;
    /**
     * Double with the angle in radians that we want to explore in each time we rotate the shape.
     * Something between 0.0 and 2 Math.Pi.
     * something relatively small. Ej.: 0.18
     */
    private final Double arch;
    /**
     * Function that applies the mutations to the Chromosome rotation.
     * We just rotate the shape randomly a little.
     * @param gaPart       Genome, Chromosome or Gen to mutate. In this case CustomChromosome.
     * @param mutationArgs Undefined array of objects containing information needed to mutate the part. In this case,
     *                   an Integer with the iteration number.
     */
    @Override
    public void apply(Object gaPart, Object... mutationArgs) {
        Integer iteration = (Integer)mutationArgs[0];
        if(iteration>this.maxIterations) return;
        Color oldColor = ((Color)((CustomChromosome) gaPart).getGeneticInformation().get(0));
        Double oldRotation = ((Double)((CustomChromosome) gaPart).getGeneticInformation().get(1));
        Shape oldShape = ((Shape)((CustomChromosome) gaPart).getGeneticInformation().get(2));
        Double newRotation = oldRotation + RandomGenerator.getDefault().nextDouble(-this.arch,this.arch);
        ((CustomChromosome) gaPart).setGeneticInformation(new ArrayList<>(){{add(oldColor);add(newRotation);add(oldShape);}});
    }
}
