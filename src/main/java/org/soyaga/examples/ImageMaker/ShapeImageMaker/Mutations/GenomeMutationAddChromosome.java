package org.soyaga.examples.ImageMaker.ShapeImageMaker.Mutations;

import lombok.AllArgsConstructor;
import org.soyaga.examples.ImageMaker.ShapeImageMaker.CustomChromosome;
import org.soyaga.examples.ImageMaker.ShapeImageMaker.CustomGenome;
import org.soyaga.ga.MutationPolicy.Mutations.Mutation;

import java.awt.*;
import java.awt.geom.*;
import java.util.ArrayList;
import java.util.random.RandomGenerator;

/**
 * Class that applies a mutation to a CustomGenome (backgroundColor, ArrayList{@literal <CustomChromosome>}),
 * by adding one new CustomChromosome at the end of the genome.
 */
@AllArgsConstructor
public class GenomeMutationAddChromosome implements Mutation {
    /**
     * Integer with the width of the image, delimits the x in the shape vertexes.
     */
    private final int width;
    /**
     * Integer with the height of the image, delimits the y in the shape vertexes.
     */
    private final int height;
    /**
     * This integer specifies the iteration at which the search for new shapes should cease. Initially, during optimization,
     * the addition of new shapes is allowed to identify suitable shapes for adequately representing the image. This approach
     * often results in the addition of numerous similar shapes, contributing to the proximity of shapes (due to the crossover
     * operation). Typically, this mutation is disabled before its counterpart, "GenomeMutationRemoveChromosome," is removed.
     * The objective is to eliminate some "unnecessary" shapes (e.g., those with alpha=1.0, very small shapes, or shapes
     * concealed behind others) and prevent the introduction of new ones.
     */
    private final int maxIterations;
    /**
     * ArrayList with an instance of the shape we want to include in the recreation. Currently, the shapes implemented are:
     * <ul>
     *     <li>Polygon.</li>
     *     <li>Ellipse2D.</li>
     *     <li>Arc2D.</li>
     *     <li>CubicCurve2D.</li>
     *     <li>QuadCurve2D.</li>
     *     <li>RoundRectangle2D.</li>
     * </ul>
     */
    private final ArrayList<Shape> availableShapes;
    /**
     * ArrayList of objects ordered in the same way as the objects in the availableShapes ArrayList. Contains the
     * editable params used to build the objects.
     * <ul>
     *     <li><b>params for the Polygon [maxVertex]</b>: Maximum number of vertex that a Polygon can have. The minimum is 3.</li>
     *     <li><b>params for the ellipse [maxSizeRatio]</b>: Integer that divides the max width and height (image width and height)
     *     that the shape can be initialized with.</li>
     *     <li><b>params for the Arch2D [maxSizeRatio]</b>: Integer that divides the max width and height (image width and height)
     *     that the shape can be initialized with.</li>
     *     <li><b>params for the CubicCurve2D [].</b></li>
     *     <li><b>params for the QuadCurve2D [].</b></li>
     *     <li><b>params for the RoundRectangle2D [maxSizeRatio]</b>: Integer that divides the max width and height (image width and height)
     *     that the shape can be initialized with.</li>
     * </ul>
     */
    private final ArrayList<Object[]> limitations;

    /**
     * Function that applies the Mutations to the Genomes ArrayList{@literal <CustomChromosome>};
     * We just add one new random CustomChromosome at the end of the list.
     *
     * @param gaPart ArrayList{@literal <Color,ArrayList<CustomChromosome>>}. In this case,the array is what we edit.
     * @param mutationArgs Undefined array of objects containing information needed to mutate the part. In this case,
     *                    an Integer with the iteration number.
     */
    @Override
    public void apply(Object gaPart, Object... mutationArgs) {
        Integer iteration = (Integer)mutationArgs[0];
        if(iteration>this.maxIterations) return;
        Color background =(Color) ((CustomGenome) gaPart).getGeneticInformation().get(0);
        ArrayList<CustomChromosome> genome = (ArrayList<CustomChromosome>) ((CustomGenome) gaPart).getGeneticInformation().get(1);
        int shapeIndex = RandomGenerator.getDefault().nextInt(availableShapes.size());
        Shape shapeType = availableShapes.get(shapeIndex);
        Shape shape;
        if(shapeType instanceof Polygon){
            //Assumes Object[] = [maxNumberOfVertexes] with maxNumberOfVertexes>2
            int numberOfVertexes = RandomGenerator.getDefault().nextInt(
                    3,(Integer)limitations.get(shapeIndex)[0]+1);
            shape = new Polygon();
            for(int j=0;j<numberOfVertexes;j++){
                ((Polygon)shape).addPoint(RandomGenerator.getDefault().nextInt(width),
                        RandomGenerator.getDefault().nextInt(height));
            }
        }
        else if (shapeType instanceof Ellipse2D) {
            //Assumes Object[] = [maxSizeRatio]
            int ratio  = (Integer)this.limitations.get(shapeIndex)[0];
            shape = new Ellipse2D.Double(
                    RandomGenerator.getDefault().nextDouble(this.width),
                    RandomGenerator.getDefault().nextDouble(this.height),
                    Math.max(1,RandomGenerator.getDefault().nextDouble((double) this.width /ratio)),
                    Math.max(1,RandomGenerator.getDefault().nextDouble((double) this.height /ratio))
            );
        }
        else if (shapeType instanceof Arc2D) {
            //Assumes Object[] = [maxSizeRatio]
            int ratio  = (Integer)this.limitations.get(shapeIndex)[0];
            shape = new Arc2D.Double(
                    RandomGenerator.getDefault().nextDouble(this.width),
                    RandomGenerator.getDefault().nextDouble(this.height),
                    Math.max(1,RandomGenerator.getDefault().nextDouble((double) this.width /ratio)),
                    Math.max(1,RandomGenerator.getDefault().nextDouble((double) this.height /ratio)),
                    RandomGenerator.getDefault().nextDouble(360),
                    RandomGenerator.getDefault().nextDouble(360),
                    RandomGenerator.getDefault().nextInt(3)
            );
        }
        else if (shapeType instanceof CubicCurve2D) {
            //Assumes Object[] = []
            shape = new CubicCurve2D.Double(
                    RandomGenerator.getDefault().nextDouble(this.width),
                    RandomGenerator.getDefault().nextDouble(this.height),
                    RandomGenerator.getDefault().nextDouble(this.width),
                    RandomGenerator.getDefault().nextDouble(this.height),
                    RandomGenerator.getDefault().nextDouble(this.width),
                    RandomGenerator.getDefault().nextDouble(this.height),
                    RandomGenerator.getDefault().nextDouble(this.width),
                    RandomGenerator.getDefault().nextDouble(this.height)
            );
        }
        else if (shapeType instanceof QuadCurve2D) {
            //Assumes Object[] = []
            shape = new QuadCurve2D.Double(
                    RandomGenerator.getDefault().nextDouble(this.width),
                    RandomGenerator.getDefault().nextDouble(this.height),
                    RandomGenerator.getDefault().nextDouble(this.width),
                    RandomGenerator.getDefault().nextDouble(this.height),
                    RandomGenerator.getDefault().nextDouble(this.width),
                    RandomGenerator.getDefault().nextDouble(this.height)
            );
        }
        else if (shapeType instanceof RoundRectangle2D) {
            //Assumes Object[] = [maxSizeRatio]
            int ratio  = (Integer)this.limitations.get(shapeIndex)[0];
            shape = new RoundRectangle2D.Double(
                    RandomGenerator.getDefault().nextDouble(this.width),
                    RandomGenerator.getDefault().nextDouble(this.height),
                    Math.max(1,RandomGenerator.getDefault().nextDouble((double) this.width /ratio)),
                    Math.max(1,RandomGenerator.getDefault().nextDouble((double) this.height /ratio)),
                    RandomGenerator.getDefault().nextDouble(this.width),
                    RandomGenerator.getDefault().nextDouble(this.height)
            );
        }
        else {
            System.out.println("Shape: "+shapeType.toString()+" not supported.");
            shape=null;
        }
        CustomChromosome newChromosome = new CustomChromosome(new Color(
                RandomGenerator.getDefault().nextFloat(),
                RandomGenerator.getDefault().nextFloat(),
                RandomGenerator.getDefault().nextFloat(),
                RandomGenerator.getDefault().nextFloat()
        ), RandomGenerator.getDefault().nextDouble(2*Math.PI),shape);
        genome.add(newChromosome);
        ((CustomGenome) gaPart).setGeneticInformation(new ArrayList<>(){{add(background);add(genome);}});
    }
}
