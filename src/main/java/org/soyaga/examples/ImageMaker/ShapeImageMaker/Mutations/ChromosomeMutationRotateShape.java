package org.soyaga.examples.ImageMaker.ShapeImageMaker.Mutations;

import lombok.AllArgsConstructor;
import org.soyaga.examples.ImageMaker.ShapeImageMaker.CustomChromosome;
import org.soyaga.ga.MutationPolicy.Mutations.Mutation;

import java.awt.*;
import java.util.ArrayList;
import java.util.random.RandomGenerator;

/**
 * Class that applies a mutation to a CustomChromosome (color, rotation, shape), by rotating the shape.
 * We change the rotation randomly, the value is changed in a range close to the current value.
 */
@AllArgsConstructor
public class ChromosomeMutationRotateShape implements Mutation {
    /**
     * This integer specifies the iteration at which to cease the search for new rotations. Generally, as the optimization reaches
     * its conclusion, we intend to halt rotation adjustments for the shapes that are already well-represented. The emphasis shifts
     * to other tasks, such as making minor adjustments to shape placement.
     */
    private final int maxIterations;
    /**
     * Double with the angle in radians that we want to explore in each time we rotate the shape.
     * Something between 0.0 and 2 Math.Pi.
     * Something relatively small. Ej.: 0.18
     */
    private final Double arch;

    /**
     * Function that applies the mutations to the Chromosome rotation.
     * We change the rotation randomly, the value is changed in a range close to the current value.
     *
     * @param gaPart ArrayList{@literal <Color,Double, Shape>}. In this case, the rotation is what we edit.
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
