package org.soyaga.examples.ImageMaker.ShapeImageMaker.Mutations;

import lombok.AllArgsConstructor;
import org.soyaga.examples.ImageMaker.ShapeImageMaker.CustomChromosome;
import org.soyaga.ga.MutationPolicy.Mutations.Mutation;

import java.awt.*;
import java.awt.geom.*;
import java.util.ArrayList;
import java.util.random.RandomGenerator;

/**
 * Class that applies a mutation to a CustomChromosome (color, rotation, shape), by moving  the shape, somewhere
 * close to the current position.
 */
@AllArgsConstructor
public class ChromosomeMutationMoveShape implements Mutation {
    /**
     * Integer with the width of the image, delimits the movement in the x axe.
     */
    private final int width;
    /**
     * Integer with the height of the image, delimits the movement in the y axe.
     */
    private final int height;
    /**
     * Integer with the iteration when to change from una "big" movement to one "small". Typically, when we are finishing our
     * optimization we want to move less the vertexes in order to achieve little hard to find improvements.
     */
    private final int smallMovementsIteration;
    /**
     * Integer with the number of pixels/degrees to allow the "small" displacement.
     */
    private Integer smallMove;
    /**
     * Integer with the number of pixels/degrees to allow the "big" displacement.
     */
    private Integer bigMove;

    /**
     * Function that applies the mutations to the Chromosomes Shape.
     * We just move a little the shape.
     * <ul>
     *     <li><b>Polygon:</b> Move one vertex.</li>
     *     <li><b>Ellipse2D:</b> Move the position of the whole shape or change  with and height.</li>
     *     <li><b>Arc2D:</b> Move the position of the whole shape, or, change  with and height, or, change angle start
     *     and extent, or, change arch type.</li>
     *     <li><b>CubicCurve2D:</b> Move X1 and Y1, or, X1ctrl and Y1ctrl, or, X2ctrl and Y2ctrl, or, X2 and Y2.</li>
     *     <li><b>QuadCurve2D:</b> Move X1 and Y1, or, X1ctrl and Y1ctrl, or, X2 and Y2.</li>
     *     <li><b>RoundRectangle2D:</b> Move the position of the whole shape, or, change with and height, or, change
     *     arch with and height.</li>
     * </ul>
     * @param gaPart       Genome, Chromosome or Gen to mutate. In this case CustomChromosome.
     * @param mutationArgs Undefined array of objects containing information needed to mutate the part. In this case,
     *                    an Integer with the iteration number.
     */
    @Override
    public void apply(Object gaPart, Object... mutationArgs) {
        Integer iteration = (Integer)mutationArgs[0];
        Integer pixels = iteration>this.smallMovementsIteration?this.smallMove:this.bigMove;
        Color oldColor = ((Color)((CustomChromosome) gaPart).getGeneticInformation().get(0));
        Double oldRotation = ((Double)((CustomChromosome) gaPart).getGeneticInformation().get(1));
        Shape oldShape = ((Shape)((CustomChromosome) gaPart).getGeneticInformation().get(2));
        Shape newShape;
        if (oldShape instanceof Polygon oldPolygon) {
            Polygon tempShape = new Polygon();
            for (int i = 0; i < oldPolygon.npoints; i++)
                tempShape.addPoint(oldPolygon.xpoints[i], oldPolygon.ypoints[i]);
            int index = RandomGenerator.getDefault().nextInt(0, oldPolygon.npoints);
            tempShape.xpoints[index] = Math.max(0, Math.min(this.width, tempShape.xpoints[index] +
                    RandomGenerator.getDefault().nextInt(-pixels, pixels + 1)));
            tempShape.ypoints[index] = Math.max(0, Math.min(this.height, tempShape.ypoints[index] +
                    RandomGenerator.getDefault().nextInt(-pixels, pixels + 1)));
            newShape =tempShape;
        }
        else if (oldShape instanceof Ellipse2D oldEllipse) {
            if(RandomGenerator.getDefault().nextBoolean()){
                newShape = new Ellipse2D.Double(
                        Math.max(0, Math.min(this.width, oldEllipse.getX() +
                                RandomGenerator.getDefault().nextInt(-pixels, pixels + 1))),
                        Math.max(0, Math.min(this.height, oldEllipse.getY() +
                                RandomGenerator.getDefault().nextInt(-pixels, pixels + 1))),
                        oldEllipse.getWidth(),
                        oldEllipse.getHeight()
                );
            }
            else {
                newShape = new Ellipse2D.Double(
                        oldEllipse.getX(),
                        oldEllipse.getY(),
                        Math.max(1, oldEllipse.getWidth() +
                                RandomGenerator.getDefault().nextInt(-pixels, pixels + 1)),
                        Math.max(1, oldEllipse.getHeight() +
                                RandomGenerator.getDefault().nextInt(-pixels, pixels + 1))
                );
            }
        }
        else if (oldShape instanceof Arc2D oldArch) {
            int type = RandomGenerator.getDefault().nextInt(4);
            if(type == 0){
                newShape = new Arc2D.Double(
                        Math.max(0, Math.min(this.width, oldArch.getX() +
                                RandomGenerator.getDefault().nextInt(-pixels, pixels + 1))),
                        Math.max(0, Math.min(this.height, oldArch.getY() +
                                RandomGenerator.getDefault().nextInt(-pixels, pixels + 1))),
                        oldArch.getWidth(),
                        oldArch.getHeight(),
                        oldArch.getAngleStart(),
                        oldArch.getAngleExtent(),
                        oldArch.getArcType()
                );
            }
            else if(type == 1) {
                newShape = new Arc2D.Double(
                        oldArch.getX(),
                        oldArch.getY(),
                        Math.max(1, oldArch.getWidth() +
                                RandomGenerator.getDefault().nextInt(-pixels, pixels + 1)),
                        Math.max(1, oldArch.getHeight() +
                                RandomGenerator.getDefault().nextInt(-pixels, pixels + 1)),
                        oldArch.getAngleStart(),
                        oldArch.getAngleExtent(),
                        oldArch.getArcType()
                );
            }
            else if(type == 2) {
                newShape = new Arc2D.Double(
                        oldArch.getX(),
                        oldArch.getY(),
                        oldArch.getWidth(),
                        oldArch.getHeight(),
                        oldArch.getAngleStart()+
                                RandomGenerator.getDefault().nextInt(-pixels, pixels + 1),
                        Math.max(1,oldArch.getAngleExtent()+
                                RandomGenerator.getDefault().nextInt(-pixels, pixels + 1)),
                        oldArch.getArcType()
                );
            }
            else if(type == 3) {
                newShape = new Arc2D.Double(
                        oldArch.getX(),
                        oldArch.getY(),
                        oldArch.getWidth(),
                        oldArch.getHeight(),
                        oldArch.getAngleStart(),
                        oldArch.getAngleExtent(),
                        RandomGenerator.getDefault().nextInt(3)
                );
            }
            else{
                System.out.println("Arch Type only provide [0->3] degrees of mobility in ChromosomeMutationMoveShape, " +
                        "try to access to Nº:"+type+ ". Shape was not moved!!");
                newShape=oldShape;
            }
        }
        else if (oldShape instanceof CubicCurve2D oldCubic) {
            int type = RandomGenerator.getDefault().nextInt(4);
            if(type == 0){
                newShape = new CubicCurve2D.Double(
                        Math.max(0, Math.min(this.width, oldCubic.getX1() +
                                RandomGenerator.getDefault().nextInt(-pixels, pixels + 1))),
                        Math.max(0, Math.min(this.height, oldCubic.getY1() +
                                RandomGenerator.getDefault().nextInt(-pixels, pixels + 1))),
                        oldCubic.getCtrlX1(),
                        oldCubic.getCtrlY1(),
                        oldCubic.getCtrlX2(),
                        oldCubic.getCtrlY2(),
                        oldCubic.getX2(),
                        oldCubic.getY2()
                );
            }
            else if(type == 1) {
                newShape = new CubicCurve2D.Double(
                        oldCubic.getX1(),
                        oldCubic.getY1(),
                        Math.max(0, Math.min(this.width, oldCubic.getCtrlX1() +
                                RandomGenerator.getDefault().nextInt(-pixels, pixels + 1))),
                        Math.max(0, Math.min(this.height, oldCubic.getCtrlY1() +
                                RandomGenerator.getDefault().nextInt(-pixels, pixels + 1))),
                        oldCubic.getCtrlX2(),
                        oldCubic.getCtrlY2(),
                        oldCubic.getX2(),
                        oldCubic.getY2()
                );
            }
            else if(type == 2) {
                newShape = new CubicCurve2D.Double(
                        oldCubic.getX1(),
                        oldCubic.getY1(),
                        oldCubic.getCtrlX1(),
                        oldCubic.getCtrlY1(),
                        Math.max(0, Math.min(this.width, oldCubic.getCtrlX2() +
                                RandomGenerator.getDefault().nextInt(-pixels, pixels + 1))),
                        Math.max(0, Math.min(this.height, oldCubic.getCtrlY2() +
                                RandomGenerator.getDefault().nextInt(-pixels, pixels + 1))),
                        oldCubic.getX2(),
                        oldCubic.getY2()
                );
            }
            else if(type == 3) {
                newShape = new CubicCurve2D.Double(
                        oldCubic.getX1(),
                        oldCubic.getY1(),
                        oldCubic.getCtrlX1(),
                        oldCubic.getCtrlY1(),
                        oldCubic.getCtrlX2(),
                        oldCubic.getCtrlY2(),
                        Math.max(0, Math.min(this.width, oldCubic.getX2() +
                                RandomGenerator.getDefault().nextInt(-pixels, pixels + 1))),
                        Math.max(0, Math.min(this.height, oldCubic.getY2() +
                                RandomGenerator.getDefault().nextInt(-pixels, pixels + 1)))
                );
            }
            else{
                System.out.println("CubicCurve2D Type only provide [0->3] degrees of mobility in ChromosomeMutationMoveShape, " +
                        "try to access to Nº:"+type+ ". Shape was not moved!!");
                newShape=oldShape;
            }
        }
        else if (oldShape instanceof QuadCurve2D oldQuad) {
            int type = RandomGenerator.getDefault().nextInt(3);
            if(type == 0){
                newShape = new QuadCurve2D.Double(
                        Math.max(0, Math.min(this.width, oldQuad.getX1() +
                                RandomGenerator.getDefault().nextInt(-pixels, pixels + 1))),
                        Math.max(0, Math.min(this.height, oldQuad.getY1() +
                                RandomGenerator.getDefault().nextInt(-pixels, pixels + 1))),
                        oldQuad.getCtrlX(),
                        oldQuad.getCtrlY(),
                        oldQuad.getX2(),
                        oldQuad.getY2()
                );
            }
            else if(type == 1) {
                newShape = new QuadCurve2D.Double(
                        oldQuad.getX1(),
                        oldQuad.getY1(),
                        Math.max(1, Math.min(this.width, oldQuad.getCtrlX() +
                                RandomGenerator.getDefault().nextInt(-pixels, pixels + 1))),
                        Math.max(1, Math.min(this.height, oldQuad.getCtrlY() +
                                RandomGenerator.getDefault().nextInt(-pixels, pixels + 1))),
                        oldQuad.getX2(),
                        oldQuad.getY2()
                );
            }
            else if(type == 2) {
                newShape = new QuadCurve2D.Double(
                        oldQuad.getX1(),
                        oldQuad.getY1(),
                        oldQuad.getCtrlX(),
                        oldQuad.getCtrlY(),
                        Math.max(0, Math.min(this.width, oldQuad.getX2() +
                                RandomGenerator.getDefault().nextInt(-pixels, pixels + 1))),
                        Math.max(0, Math.min(this.height, oldQuad.getY2() +
                                RandomGenerator.getDefault().nextInt(-pixels, pixels + 1)))
                );
            }
            else{
                System.out.println("QuadCurve2D Type only provide [0->2] degrees of mobility in ChromosomeMutationMoveShape, " +
                        "try to access to Nº:"+type+ ". Shape was not moved!!");
                newShape=oldShape;
            }
        }
        else if (oldShape instanceof RoundRectangle2D oldRec) {
            int type = RandomGenerator.getDefault().nextInt(3);
            if(type == 0){
                newShape = new RoundRectangle2D.Double(
                        Math.max(0, Math.min(this.width, oldRec.getX() +
                                RandomGenerator.getDefault().nextInt(-pixels, pixels + 1))),
                        Math.max(0, Math.min(this.height, oldRec.getY() +
                                RandomGenerator.getDefault().nextInt(-pixels, pixels + 1))),
                        oldRec.getWidth(),
                        oldRec.getHeight(),
                        oldRec.getArcWidth(),
                        oldRec.getArcHeight()
                );
            }
            else if(type == 1) {
                newShape = new RoundRectangle2D.Double(
                        oldRec.getX(),
                        oldRec.getY(),
                        Math.max(0, Math.min(this.width, oldRec.getWidth() +
                                RandomGenerator.getDefault().nextInt(-pixels, pixels + 1))),
                        Math.max(0, Math.min(this.height, oldRec.getHeight() +
                                RandomGenerator.getDefault().nextInt(-pixels, pixels + 1))),
                        oldRec.getArcWidth(),
                        oldRec.getArcHeight()
                );
            }
            else if(type == 2) {
                newShape = new RoundRectangle2D.Double(
                        oldRec.getX(),
                        oldRec.getY(),
                        oldRec.getWidth(),
                        oldRec.getHeight(),
                        Math.max(0, Math.min(this.width, oldRec.getArcWidth() +
                                RandomGenerator.getDefault().nextInt(-pixels, pixels + 1))),
                        Math.max(0, Math.min(this.height, oldRec.getArcHeight() +
                                RandomGenerator.getDefault().nextInt(-pixels, pixels + 1)))
                );
            }
            else{
                System.out.println("RoundRectangle2D Type only provide [0->2] degrees of mobility in ChromosomeMutationMoveShape, " +
                        "try to access to Nº:"+type+ ". Shape was not moved!!");
                newShape=oldShape;
            }
        }
        else {
            System.out.println("Shape: "+oldShape.toString()+" not supported.");
            newShape=null;
        }
            ((CustomChromosome) gaPart).setGeneticInformation(new ArrayList<>() {{
                add(oldColor);
                add(oldRotation);
                add(newShape);
            }});
    }
}
