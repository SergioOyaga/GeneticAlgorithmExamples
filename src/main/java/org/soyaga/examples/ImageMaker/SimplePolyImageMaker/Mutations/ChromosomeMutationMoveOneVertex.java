package org.soyaga.examples.ImageMaker.SimplePolyImageMaker.Mutations;

import lombok.AllArgsConstructor;
import org.soyaga.examples.ImageMaker.SimplePolyImageMaker.CustomChromosome;
import org.soyaga.ga.MutationPolicy.Mutations.Mutation;
import java.awt.*;
import java.util.ArrayList;
import java.util.random.RandomGenerator;

/**
 * Class that applies a mutation to a CustomChromosome (polygonColor, Polygon), by moving one vertex of the polygon.
 * We change one vertex position randomly, but moving it in a range close to the current position.
 */
@AllArgsConstructor
public class ChromosomeMutationMoveOneVertex implements Mutation {
    /**
     * Integer with the width of the image, delimits the movement in the x axe.
     */
    private final int width;
    /**
     * Integer with the height of the image, delimits the movement in the y axe.
     */
    private final int height;
    /**
     * This integer specifies the iteration at which to transition from a "significant" movement to a "minor" movement of vertices.
     * Typically, as the optimization nears completion, we aim to make smaller adjustments to vertices to attain subtle,
     * challenging-to-find improvements.
     */
    private final int smallMovementsIteration;
    /**
     * Integer with the number of pixels to allow the "small" displacement.
     */
    private Integer smallMove;
    /**
     * Integer with the number of pixels to allow the "big" displacement.
     */
    private Integer bigMove;

    /**
     * Function that applies the Mutations to the CustomChromosome Polygon.
     * We change one vertex position randomly, but moving it in a range close to the current position.
     *
     * @param gaPart ArrayList{@literal <Color,Polygon>}. In this case,the polygon is what we edit.
     * @param mutationArgs Undefined array of objects containing information needed to mutate the part. In this case,
     *                    an Integer with the iteration number.
     */
    @Override
    public void apply(Object gaPart, Object... mutationArgs) {
        Integer iteration = (Integer)mutationArgs[0];
        Integer pixels = iteration>this.smallMovementsIteration?this.smallMove:this.bigMove;
        Color oldColor = ((Color)((CustomChromosome) gaPart).getGeneticInformation().get(0));
        Polygon oldPolygon = ((Polygon)((CustomChromosome) gaPart).getGeneticInformation().get(1));
        Polygon newPolygon = new Polygon();
        for(int i=0;i<oldPolygon.npoints;i++)newPolygon.addPoint(oldPolygon.xpoints[i],oldPolygon.ypoints[i]);
        int index=RandomGenerator.getDefault().nextInt(0,oldPolygon.npoints);
        newPolygon.xpoints[index] = Math.max(0, Math.min(this.width,newPolygon.xpoints[index] +
                RandomGenerator.getDefault().nextInt(-pixels, pixels+1)));
        newPolygon.ypoints[index] = Math.max(0, Math.min(this.height,newPolygon.ypoints[index] +
                RandomGenerator.getDefault().nextInt(-pixels, pixels+1)));
        ((CustomChromosome) gaPart).setGeneticInformation(new ArrayList<>(){{add(oldColor);add(newPolygon);}});
    }
}
