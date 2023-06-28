package org.soyaga.examples.ImageMaker.ShapeImageMaker;

import lombok.AllArgsConstructor;
import org.soyaga.Initializer.GAInitializer;
import org.soyaga.ga.Evaluable.ObjectiveFunction;
import org.soyaga.ga.Individual;

import java.awt.*;
import java.awt.geom.*;
import java.util.ArrayList;
import java.util.random.RandomGenerator;

/**
 * This class allow the initialization of new Individuals from scratch.We initialize individuals by selecting a random
 * background color, and an ArrayList of CustomChromosomes of length initialNumberOfShapes. Each CustomChromosome
 * is composed of a Color, a rotation angle (Double in radians) and a Shape. Currently, the shapes implemented are:
 * <ul>
 *     <li>Polygon.</li>
 *     <li>Ellipse2D.</li>
 *     <li>Arc2D.</li>
 *     <li>CubicCurve2D.</li>
 *     <li>QuadCurve2D.</li>
 *     <li>RoundRectangle2D.</li>
 * </ul>
 */
@AllArgsConstructor
public class ShapeImageInitializer extends GAInitializer {
    /**
     * Integer with initial number of shapes to include in the image.
     */
    private final Integer initialNumberOfShapes;
    /**
     * Integer with the height of the image.
     */
    private final Integer height;
    /**
     * Integer with the width of the image.
     */
    private final Integer width;
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
     * ObjectiveFunction with the objective function that defines the problem objective.
     */
    private final ObjectiveFunction objectiveFunction;

    /**
     * Function that initializes a new individual from scratch. In this case each individual have one CustomGenome,
     * composed of a background Color and a list of CustomChromosomes (Color, ArrayList&lt;CustomChromosomes&gt;).
     * At the same time, each CustomChromosome is composed of one Color, the rotating angle (radians) and one Shape
     * (Color,Double, Shape). The shapes to initialize are selected randomly. Currently, the shapes implemented are:
     * <ul>
     *     <li>Polygon.</li>
     *     <li>Ellipse2D.</li>
     *     <li>Arc2D.</li>
     *     <li>CubicCurve2D.</li>
     *     <li>QuadCurve2D.</li>
     *     <li>RoundRectangle2D.</li>
     * </ul>
     *
     * @return Individual randomly initialized.
     */
    @Override
    public Individual initializeIndividual() {
        ArrayList<CustomChromosome> chromosomes=new ArrayList<>();
        for (int i = 0; i<this.initialNumberOfShapes; i++){
            int shapeIndex = RandomGenerator.getDefault().nextInt(availableShapes.size());
            Shape shapeType = availableShapes.get(shapeIndex);
            Shape shape;
            if(shapeType instanceof Polygon){
                //Assumes Object[] = [maxNumberOfVertexes] with maxNumberOfVertexes>2
                int numberOfVertexes = RandomGenerator.getDefault().nextInt(
                        3,(Integer)this.limitations.get(shapeIndex)[0]+1);
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

            CustomChromosome chromosome= new CustomChromosome(
                    new Color(
                        RandomGenerator.getDefault().nextFloat(),
                        RandomGenerator.getDefault().nextFloat(),
                        RandomGenerator.getDefault().nextFloat(),
                        RandomGenerator.getDefault().nextFloat()),
                    RandomGenerator.getDefault().nextDouble(2*Math.PI),
                    shape);
            chromosomes.add(chromosome);
        }
        CustomGenome genome = new CustomGenome(new Color(
                    RandomGenerator.getDefault().nextFloat(),
                    RandomGenerator.getDefault().nextFloat(),
                    RandomGenerator.getDefault().nextFloat()),
                chromosomes);
        return new Individual(genome,null, this.objectiveFunction);

    }
}
