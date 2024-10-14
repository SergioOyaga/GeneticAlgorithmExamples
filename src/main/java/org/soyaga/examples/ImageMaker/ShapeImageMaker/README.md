# ShapeImageMaker
The ShapeImageMaker addresses the task of discovering a collection of colored shapes that accurately represents an image. Historically, this challenge has been commonly exemplified through the recreation of iconic artworks like the Mona Lisa painting. Nevertheless, it's crucial to recognize that this technique can be applied to virtually any image, as images can be deconstructed into an arrangement of overlapping shapes.

<table>
  <tr>
    <th colspan="2"> <b>Ellipses </b></th>
  </tr>
  <tr>
    <td> <b>Ellipses evolution </b></td>
    <td> <b>Reference</b> </td>
  </tr>
  <tr>
    <td> <img src="https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/out/ImageMaker/ellipses.gif"  title="Solution for the ellipses" alt="Solution for the ellipses" width="300" height="300" /></td>
    <td> <img src="https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/resources/ImageMaker/ellipses.jpg"  title="Reference ellipses" alt="Reference ellipses" width="300" height="300" /></td>
  </tr>
  <tr>
    <th colspan="2"> <b>DNA</b> </th>
  </tr>
  <tr>
    <td> <b>DNA evolution </b></td>
    <td> <b>Reference</b> </td>
  </tr>
  <tr>
    <td> <img src="https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/out/ImageMaker/dna.gif"  title="Solution for the DNA" alt="Solution for the DNA" width="300" height="300" /></td>
    <td> <img src="https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/resources/ImageMaker/dna.png"  title="Reference DNA" alt="Reference DNA" width="300" height="300" /></td>
  </tr>
</table>

## In this folder:
We can find the descriptions of the classes in the [SimplePolyImageMaker](https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/java/org/soyaga/examples/ImageMaker/SimplePolyImageMaker) directory. The classes that have undergone changes are specified as follows:

1. [CustomGeneticAlgorithm](#customgeneticalgorithm): This class implements the GeneticAlgorithm interface.
2. [ShapeImageInitializer](#shapeimageinitializer): This class extends the GAInitializer abstract class.
3. [ShapeImageObjectiveFunction](#shapeimageobjectivefunction): This class extends the ObjectiveFunction interface.
4. [CustomChromosome](#customchromosome): This class implements the Chromosome interface.
5. [Mutations](#mutations): This folder contains various mutation classes.
    - [ChromosomeMutationChangeShapeType](#chromosomemutationchangeshapetype): This class implements the Mutation interface.
    - [ChromosomeMutationColor](#chromosomemutationcolor): This class implements the Mutation interface.
    - [ChromosomeMutationMoveShape](#chromosomemutationmoveshape): This class implements the Mutation interface.
    - [ChromosomeMutationRotateShape](#chromosomemutationrotateshape): This class implements the Mutation interface.
    - [GenomeMutationAddChromosome](#genomemutationaddchromosome): This class implements the Mutation interface.
6. [RunShapeImageOptimization](#runshapeimageoptimization): This is the main class where we instantiate our CustomGeneticAlgorithm object along with all its components.


### [CustomGeneticAlgorithm](https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/java/org/soyaga/examples/ImageMaker/ShapeImageMaker/CustomGeneticAlgorithm.java):
This class implements GeneticAlgorithm, which by extension makes it an Optimizer instance. In other words, this class
can be optimized and its results can be gathered.
````code
public void optimize(){...}
public Object getResult(){...}
````
The <i>optimize</i> function is implemented in a way that the generation number is passed as VarArg to most of the actions
executed by the CustomGeneticAlgorithm parts (CrossoverPolicy, MutationPolicy...). The <i>getResults</i> function
returns an Image that will be stored or used to build the gif.

### [ShapeImageInitializer](https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/java/org/soyaga/examples/ImageMaker/ShapeImageMaker/ShapeImageInitializer.java):
This class initializes a new individual from scratch.
It uses CustomGenome, CustomChromosome, Color, Double and Polygon to store the information of a randomly
initialized individual.
````code
public Individual initializeIndividual()
````
In this scenario, the GeneticInformationContainers contain relatively sparse information. Specifically:
- The Genome holds the background color and an array of chromosomes.
- Each Chromosome encapsulates a Shape along with the corresponding fill Color and shape rotation.

````mermaid
flowchart TB
    subgraph  ide1 [Unordered Structures ]
      direction TB    
      CustomGenome o--o ColorBackground
      CustomGenome o--o ChromosomeList
    end
  subgraph  ide2 [Ordered Structure]
    direction TB
    ChromosomeList o--o CustomGenome1
    ChromosomeList o--o CustomGenomeX
    ChromosomeList o--o CustomGenomeY
    CustomGenome1 o--o Color1
    CustomGenome1 o--o Rotation1
    CustomGenome1 o--o Shape1
    CustomGenomeX o--o ColorX
    CustomGenomeX o--o RotationX
    CustomGenomeX o--o ShapeX
    CustomGenomeY o--o ColorY
    CustomGenomeY o--o RotationY
    CustomGenomeY o--o ShapeY
  end
    
````

### [ShapeImageObjectiveFunction](https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/java/org/soyaga/examples/ImageMaker/ShapeImageMaker/ShapeImageObjectiveFunction.java):
This function is responsible for evaluating the objective function of a solution. In this context, the L~2~ norm is computed for the RGBA colors in the image generated by the genome, contrasting them with the colors in the actual image.

The outcome of this function is a Double value, representing the ratio of Euclidean color deviation per pixel across the entire generated image when compared to the reference image.

### [CustomChromosome](https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/java/org/soyaga/examples/ImageMaker/ShapeImageMaker/CustomChromosome.java):
A Chromosome that contains the genetic information:
  * color (Color): Contains the Shape filling color.
  * rotation (Double): Contains the angle in radians that the affine transform has to apply to this shape.
  * shape (Shape): Shape that we want to incorporate into the image.

### [Mutations](https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/java/org/soyaga/examples/ImageMaker/ShapeImageMaker/Mutations):
Folder that contains all the mutations that are applied to the genomes.
1. #### [ChromosomeMutationChangeShapeType](https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/java/org/soyaga/examples/ImageMaker/ShapeImageMaker/Mutations/ChromosomeMutationChangeShapeType.java)
    Class that mutates a Chromosome. It changes the Shape type by randomly initializing the new one inside the bounding box of the old one.
2. #### [ChromosomeMutationColor](https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/java/org/soyaga/examples/ImageMaker/ShapeImageMaker/Mutations/ChromosomeMutationColor.java):
    Class that mutates a Chromosome. It slightly changes the color that fills the shape randomly.
3. #### [ChromosomeMutationMoveShape](https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/java/org/soyaga/examples/ImageMaker/ShapeImageMaker/Mutations/ChromosomeMutationMoveShape.java):
   Class that mutates a Chromosome. It slightly moves the shape. The behavior differs based on the shape type:
    <ul>
        <li><b>Polygon:</b> Move one vertex.</li>
        <li><b>Ellipse2D:</b> Move the position of the entire shape or alter its width and height.</li>
        <li><b>Arc2D:</b> Move the position of the entire shape, change width and height, modify angle start and extent, or adjust the arch type.</li>
        <li><b>CubicCurve2D:</b> Adjust X1 and Y1, X1ctrl and Y1ctrl, X2ctrl and Y2ctrl, or X2 and Y2.</li>
        <li><b>QuadCurve2D:</b> Adjust X1 and Y1, X1ctrl and Y1ctrl, or X2 and Y2.</li>
        <li><b>RoundRectangle2D:</b> Move the position of the entire shape, modify width and height, or adjust arch width and height.</li>
    </ul>
4. #### [ChromosomeMutationRotateShape](https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/java/org/soyaga/examples/ImageMaker/ShapeImageMaker/Mutations/ChromosomeMutationRotateShape.java):
   Class that mutates the Chromosome. It slightly rotates the shape.
5. #### [GenomeMutationAddChromosome](https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/java/org/soyaga/examples/ImageMaker/ShapeImageMaker/Mutations/GenomeMutationAddChromosome.java):
   Class that mutates the Genome. Randomly creates and adds a Chromosome to the genome (add one new shape).


### [RunShapeImageOptimization](https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/java/org/soyaga/examples/ImageMaker/ShapeImageMaker/RunShapeImageOptimization.java):
This is the main class where the program execution begins.
It involves instantiating the ChessGA object (previously defined), optimizing it, and retrieving the results.

The specific components for CustomGeneticAlgorithm include:
- MaxIterationCriteriaPolicy: A StoppingCriteria based on a maximum number of iterations.
- FixedCrossoverPolicy: A CrossoverPolicy that applies each iteration a fixed number of crossovers.
  - TournamentSelection: A Selection procedure in which each parent is chosen by the tournament method.
  - CustomCrossover: The Crossover  previously defined.
- CustomMutationPolicy: The MutationPolicy previously defined.
  - ChromosomeMutationChangeShapeType: The Mutation previously defined.
  - ChromosomeMutationColor: The Mutation previously defined.
  - ChromosomeMutationMoveShape: The Mutation previously defined.
  - ChromosomeMutationRotateShape: The Mutation previously defined.
  - GenomeMutationAddChromosome: The Mutation previously defined.
  - GenomeMutationBackground: The Mutation previously defined.
  - GenomeMutationShapeOrder: The Mutation previously defined.
  - GenomeMutationRemoveChromosome: The Mutation previously defined.
- FixedElitismPolicy: An ElitismPolicy that applies each iteration a fixed number of elitist individuals.
- FixedNewbornPolicy: A newbornPolicy that applies each iteration a fixed number of newborn individuals.
- ShapeImageInitializer: The Initializer previously defined.
  - ShapeImageObjectiveFunction: The ObjectiveFunction previously defined.

## Comment:
This is a more general approach compared to the one used in the [SimplePolyImageMaker](https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/java/org/soyaga/examples/ImageMaker/SimplePolyImageMaker).
However, general approaches are not always the most suitable. While attempting to recreate images with subtle curves,
ellipses, quadratic, or cubic curves may provide greater accuracy than using simple polygons. This implementation serves
as an example of the flexibility achievable when implementing the OptimizationLib. Functions and classes can be committed
to abstract classes or interfaces (such as "Shape"), allowing the "chain of responsibility" to be assigned to higher or
lower classes for implementing the behavior of concrete classes (e.g., Polygon, Ellipse2D.Double, ...). We encourage
readers to create their own Shape implementations (e.g., ScreamingCatShape) and explore the optimization of images using
them. :scream_cat:

