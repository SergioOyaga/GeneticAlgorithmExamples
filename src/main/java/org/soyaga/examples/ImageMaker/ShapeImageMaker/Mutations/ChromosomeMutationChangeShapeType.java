package org.soyaga.examples.ImageMaker.ShapeImageMaker.Mutations;

import lombok.AllArgsConstructor;
import org.soyaga.examples.ImageMaker.ShapeImageMaker.CustomChromosome;
import org.soyaga.ga.MutationPolicy.Mutations.Mutation;

import java.awt.*;
import java.awt.geom.*;
import java.util.ArrayList;
import java.util.random.RandomGenerator;

/**
 * Class that applies a mutation to a CustomChromosome (color, rotation, shape), by randomly changing the shape type.
 * The new shape is going to be placed close to the old shape.
 */
@AllArgsConstructor
public class ChromosomeMutationChangeShapeType implements Mutation {
    /**
     * This integer specifies the iteration at which to halt the search for new shapes. Initially, during optimization,
     * we permit the addition of new shapes to discover suitable representations for the image. However, as the optimization
     * progresses, this mutation is generally removed in the later stages. In these final iterations, the focus shifts to the
     * removal and minor adjustment of existing shapes.
     */
    private final int maxIterations;
    /**
     * Arraylist with the possible shapes to change to. Currently, the shapes implemented are:
     * <ul>
     *     <li>Polygon.</li>
     *     <li>Ellipse2D.</li>
     *     <li>Arc2D.</li>
     *     <li>CubicCurve2D.</li>
     *     <li>QuadCurve2D.</li>
     *     <li>RoundRectangle2D.</li>
     * </ul>
     */
    private final ArrayList<Shape>  availableShapes;
    /**
     * Arraylist with the constraints of each shape if any. Contains the editable params used to build the objects.
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
    ArrayList<Object[]> limitations;

    /**
     * Function that applies the mutations to the CustomChromosome Shape.
     * We change one shape type to another. We use the bounding box of the current shape to randomly create a new shape
     * that fits inside this bounding box.
     *
     * @param gaPart ArrayList{@literal <Color,Double, Shape>}. In this case, the Shape is what we edit.
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
        int shapeIndex = RandomGenerator.getDefault().nextInt(availableShapes.size());
        Shape newShape = availableShapes.get(shapeIndex);
        Rectangle2D boundingBox = oldShape.getBounds2D();
        if (newShape instanceof Polygon) {
            Polygon tempShape = new Polygon();
            int numberOfVertexes = RandomGenerator.getDefault().nextInt(
                    3,(Integer)this.limitations.get(shapeIndex)[0]+1);
            for (int i = 0; i < numberOfVertexes; i++){
                tempShape.addPoint(
                        (int) (boundingBox.getX()+RandomGenerator.getDefault().nextInt(Math.max(1,(int) boundingBox.getWidth()))),
                        (int) (boundingBox.getY()+RandomGenerator.getDefault().nextInt(Math.max(1,(int) boundingBox.getHeight()))));
            }
            newShape =tempShape;
        }
        else if (newShape instanceof Ellipse2D) {
            newShape = new Ellipse2D.Double(
                    boundingBox.getX(),
                    boundingBox.getY(),
                    Math.max(1,(int) boundingBox.getWidth()),
                    Math.max(1,(int) boundingBox.getHeight())
                );
        }
        else if (newShape instanceof Arc2D) {
            newShape = new Arc2D.Double(
                    boundingBox.getX(),
                    boundingBox.getY(),
                    Math.max(1,(int) boundingBox.getWidth()),
                    Math.max(1,(int) boundingBox.getHeight()),
                    RandomGenerator.getDefault().nextDouble(360),
                    RandomGenerator.getDefault().nextDouble(360),
                    RandomGenerator.getDefault().nextInt(3)
                );
        }
        else if (newShape instanceof CubicCurve2D) {
            newShape = new CubicCurve2D.Double(
                    boundingBox.getX() + RandomGenerator.getDefault().nextInt(Math.max(1,(int) boundingBox.getWidth())),
                    boundingBox.getY() + RandomGenerator.getDefault().nextInt(Math.max(1,(int) boundingBox.getHeight())),
                    boundingBox.getX() + RandomGenerator.getDefault().nextInt(Math.max(1,(int) boundingBox.getWidth())),
                    boundingBox.getY() + RandomGenerator.getDefault().nextInt(Math.max(1,(int) boundingBox.getHeight())),
                    boundingBox.getX() + RandomGenerator.getDefault().nextInt(Math.max(1,(int) boundingBox.getWidth())),
                    boundingBox.getY() + RandomGenerator.getDefault().nextInt(Math.max(1,(int) boundingBox.getHeight())),
                    boundingBox.getX() + RandomGenerator.getDefault().nextInt(Math.max(1,(int) boundingBox.getWidth())),
                    boundingBox.getY() + RandomGenerator.getDefault().nextInt(Math.max(1,(int) boundingBox.getHeight()))
                );
        }
        else if (newShape instanceof QuadCurve2D) {
            newShape = new QuadCurve2D.Double(
                    boundingBox.getX() + RandomGenerator.getDefault().nextInt(Math.max(1,(int) boundingBox.getWidth())),
                    boundingBox.getY() + RandomGenerator.getDefault().nextInt(Math.max(1,(int) boundingBox.getHeight())),
                    boundingBox.getX() + RandomGenerator.getDefault().nextInt(Math.max(1,(int) boundingBox.getWidth())),
                    boundingBox.getY() + RandomGenerator.getDefault().nextInt(Math.max(1,(int) boundingBox.getHeight())),
                    boundingBox.getX() + RandomGenerator.getDefault().nextInt(Math.max(1,(int) boundingBox.getWidth())),
                    boundingBox.getY() + RandomGenerator.getDefault().nextInt(Math.max(1,(int) boundingBox.getHeight()))
                );
        }
        else if (newShape instanceof RoundRectangle2D) {
            newShape = new RoundRectangle2D.Double(
                    boundingBox.getX(),
                    boundingBox.getY(),
                    boundingBox.getWidth(),
                    boundingBox.getHeight(),
                    RandomGenerator.getDefault().nextDouble(Math.max(1,(int) boundingBox.getWidth())),
                    RandomGenerator.getDefault().nextDouble(Math.max(1,(int) boundingBox.getHeight()))
                );
        }
        else {
            System.out.println("Shape: "+oldShape.toString()+" not supported.");
            newShape=null;
        }
        Shape finalShape = newShape;
        ((CustomChromosome) gaPart).setGeneticInformation(new ArrayList<>() {{
            add(oldColor);
            add(oldRotation);
            add(finalShape);
        }});
    }
}
