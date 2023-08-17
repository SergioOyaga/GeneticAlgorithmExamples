package org.soyaga.examples.ImageMaker.ShapeImageMaker;

import lombok.AllArgsConstructor;
import org.soyaga.ga.GeneticInformationContainer.Chromosome.Chromosome;

import java.awt.*;
import java.awt.geom.*;
import java.util.ArrayList;

/**
 * Class that contains a Shape, rotation of the shape and a Color. Those are the fundamental parts of our image.
 */
@AllArgsConstructor
public class CustomChromosome implements Chromosome<Object> {
    /**
     * Color of the Shape.
     */
    private Color color;
    /**
     * Rotation of the Shape.
     */
    private Double rotation;
    /**
     * Shape.
     */
    private Shape shape;

    /**
     * Constructor that creates a deep copy of the Chromosome. Color and Rotation are effectively finals,
     * and the Shape can be deep copied using the constructor of the not abstract class. Currently, the shapes
     * implemented are:
     * <ul>
     *     <li>Polygon.</li>
     *     <li>Ellipse2D.</li>
     *     <li>Arc2D.</li>
     *     <li>CubicCurve2D.</li>
     *     <li>QuadCurve2D.</li>
     *     <li>RoundRectangle2D.</li>
     * </ul>
     * @return Chromosome containing the deep copy.
     */
    @Override
    public CustomChromosome createCopy() {
        Shape shape;
        if(this.shape instanceof Polygon){
            shape = new Polygon(
                    ((Polygon)this.shape).xpoints,
                    ((Polygon)this.shape).ypoints,
                    ((Polygon)this.shape).npoints);
        }
        else if (this.shape instanceof Ellipse2D) {
            shape = new Ellipse2D.Double(
                    ((Ellipse2D) this.shape).getX(),
                    ((Ellipse2D) this.shape).getY(),
                    ((Ellipse2D) this.shape).getWidth(),
                    ((Ellipse2D) this.shape).getHeight()
            );
        }
        else if (this.shape instanceof Arc2D) {
            shape = new Arc2D.Double(
                    ((Arc2D) this.shape).getX(),
                    ((Arc2D) this.shape).getY(),
                    ((Arc2D) this.shape).getWidth(),
                    ((Arc2D) this.shape).getHeight(),
                    ((Arc2D) this.shape).getAngleStart(),
                    ((Arc2D) this.shape).getAngleExtent(),
                    ((Arc2D) this.shape).getArcType()
            );
        }
        else if (this.shape instanceof CubicCurve2D) {
            shape = new CubicCurve2D.Double(
                    ((CubicCurve2D) this.shape).getX1(),
                    ((CubicCurve2D) this.shape).getY1(),
                    ((CubicCurve2D) this.shape).getCtrlX1(),
                    ((CubicCurve2D) this.shape).getCtrlY1(),
                    ((CubicCurve2D) this.shape).getCtrlX2(),
                    ((CubicCurve2D) this.shape).getCtrlY2(),
                    ((CubicCurve2D) this.shape).getX2(),
                    ((CubicCurve2D) this.shape).getY2()
            );
        }
        else if (this.shape instanceof QuadCurve2D) {
            shape = new QuadCurve2D.Double(
                    ((QuadCurve2D) this.shape).getX1(),
                    ((QuadCurve2D) this.shape).getY1(),
                    ((QuadCurve2D) this.shape).getCtrlX(),
                    ((QuadCurve2D) this.shape).getCtrlY(),
                    ((QuadCurve2D) this.shape).getX2(),
                    ((QuadCurve2D) this.shape).getY2()
            );
        }
        else if (this.shape instanceof RoundRectangle2D) {
            shape = new RoundRectangle2D.Double(
                    ((RoundRectangle2D) this.shape).getX(),
                    ((RoundRectangle2D) this.shape).getY(),
                    ((RoundRectangle2D) this.shape).getWidth(),
                    ((RoundRectangle2D) this.shape).getHeight(),
                    ((RoundRectangle2D) this.shape).getArcWidth(),
                    ((RoundRectangle2D) this.shape).getArcHeight()
            );
        }
        else {
            System.out.println("Shape: "+this.shape.toString()+" not supported.");
            shape=null;
        }
        return new CustomChromosome(this.color,this.rotation, shape);
    }

    /**
     * Function that gathers the genetic information. In this case ArrayList&lt;Color, Double, Shape&gt;.
     *
     * @return Object containing the genetic information.
     */
    @Override
    public ArrayList<Object> getGeneticInformation() {
        Color color_final= this.color;
        Double rotation_final= this.rotation;
        Shape shape_final= this.shape;
        return new ArrayList<>() {{add(color_final);add(rotation_final);add(shape_final);}};
    }

    /**
     * Function that sets the genetic information.
     *
     * @param geneticInformation Object containing the genetic information.
     *                           In this case ArrayList&lt;Color,Shape&gt;.
     */
    @Override
    public void setGeneticInformation(Object geneticInformation) {
        this.color = (Color)(((ArrayList<?>)geneticInformation).get(0));
        this.rotation = (Double) (((ArrayList<?>)geneticInformation).get(1));
        this.shape =  (Shape)(((ArrayList<?>)geneticInformation).get(2));
    }

    /**
     * Function to verbose the optimization process.
     *
     * @return a string containing the Gen string representation.
     */
    @Override
    public String toString() {
        return "Shape = " + this.shape.toString() + ", col = " + this.color.toString() + ", rot = " + this.rotation;
    }

}
