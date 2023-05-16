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
 * Class that applies a mutation to a CustomGenome (backgroundColor, ArrayList&lt;CustomChromosome&gt;),
 * by appending one CustomChromosome at the end of the genome.
 */
@AllArgsConstructor
public class GenomeMutationAddChromosome implements Mutation {
    /**
     * Integer with the width of the image, delimits the x in the shape vertexes.
     */
    private final int width;
    /**
     * Integer with the height of the image, delimits the y the shape vertexes.
     */
    private final int height;
    /**
     * Integer with the iteration when to stop looking for new Shapes. In the beginning of the optimization we allow
     * the addition of new shapes because we want to find new suitable  shapes to represent the image in some good
     * enough way. Typically, we remove this mutation before we remove its counterpart "GenomeMutationRemoveChromosome",
     * because we want to remove some "unnecessary" shapes (alpha=1.0, very small shapes, shapes hidden behind
     * others...) and avoid the addition of new ones.
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
     * Function that applies the mutations to the Genomes ArrayList&lt;customChromosome&gt;.
     * We just append one new CustomChromosome.
     * @param gaPart       Genome, Chromosome or Gen to mutate. In this case CustomGenome.
     * @param mutationArgs Undefined array of objects containing information needed to mutate the part. In this case,
     *                    an Integer with the iteration number.
     */
    @Override
    public void apply(Object gaPart, Object... mutationArgs) {
        Integer iteration = (Integer)mutationArgs[0];
        if(iteration>this.maxIterations) return;
        Color background =(Color) ((CustomGenome) gaPart).getGeneticInformation().get(0);
        ArrayList genome = (ArrayList) ((CustomGenome) gaPart).getGeneticInformation().get(1);
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
