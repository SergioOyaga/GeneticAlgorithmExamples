package org.soyaga.examples.ImageMaker.ShapeImageMaker.Mutations;

import lombok.AllArgsConstructor;
import org.soyaga.examples.ImageMaker.ShapeImageMaker.CustomChromosome;
import org.soyaga.ga.MutationPolicy.Mutations.Mutation;
import java.awt.*;
import java.util.ArrayList;
import java.util.random.RandomGenerator;

/**
 * Class that applies a mutation to a CustomChromosome (color, rotation, shape), by changing the color.
 * We change the color randomly, the RGB values are changed in a range close to the current position.
 */
@AllArgsConstructor
public class ChromosomeMutationColor implements Mutation {
    /**
     * This integer specifies the iteration at which to cease the search for new colors. Generally, as the optimization reaches
     * its conclusion, we intend to halt color adjustments for shapes that are already well-represented. The emphasis shifts
     * to other tasks, such as making minor adjustments to shape placements.
     */
    private final int maxIterations;
    /**
     * This integer specifies the range to be explored each time the color is mutated. Typically, this value lies between 0 and 255,
     * and is relatively small. For example: 10.
     */
    private final int move;

    /**
     * Function that applies the mutations to the CustomChromosome color.
     * We change the color randomly, the RGB values are changed in a range close to the current position.
     *
     * @param gaPart ArrayList{@literal <Color,Double, Shape>}. In this case, the color is what we edit.
     * @param mutationArgs Undefined array of objects containing information needed to mutate the part. In this case,
     *                    an Integer with the iteration number.
     */
    @Override
    public void apply(Object gaPart, Object... mutationArgs) {
        Integer iteration = (Integer)mutationArgs[0];
        if(iteration>this.maxIterations) return;
        Color oldColor = ((Color)((CustomChromosome) gaPart).getGeneticInformation().get(0));
        Double oldRotation = ((Double)((CustomChromosome) gaPart).getGeneticInformation().get(1));
        Shape oldShape = ((Shape)((CustomChromosome) gaPart).getGeneticInformation().get(2));
        int [] rgb= new int[]{oldColor.getRed(),oldColor.getGreen(),oldColor.getBlue(),oldColor.getAlpha()};
        int index=RandomGenerator.getDefault().nextInt(0,rgb.length);
        rgb[index]+= RandomGenerator.getDefault().nextInt(-this.move, this.move+1);
        Color newcolor = new Color(
                Math.max(0,Math.min(rgb[0],255)),
                Math.max(0,Math.min(rgb[1],255)),
                Math.max(0,Math.min(rgb[2],255)),
                Math.max(0,Math.min(rgb[3],255))
        );
        ((CustomChromosome) gaPart).setGeneticInformation(new ArrayList<>(){{add(newcolor);add(oldRotation);add(oldShape);}});
    }
}
