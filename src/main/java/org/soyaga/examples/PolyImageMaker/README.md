# PolyImageMaker
The PolyImageMaker is the problem of finding a set of colored polygons that represents an image.
Classically this problem has been proposed for solving the Monalisa painting, however any image can be represented 
as an overlap of polygons.

<table>
  <tr>
    <th colspan="2"> <b>Triangle </b></th>
    <th colspan="2"> <b>Geometric Shapes</b> </th>
    <th colspan="2"> <b>Monalisa </b></th>
  </tr>
  <tr>
    <td> <b>Triangle evolution </b></td>
    <td> <b>Reference</b> </td>
    <td> <b>Geometric Shapes evolution </b></td>
    <td> <b>Reference</b> </td>
    <td> <b>Monalisa evolution </b></td>
    <td> <b>Reference</b> </td>
  </tr>
  <tr>
    <td> <img src="https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/out/PolyImageMaker/triangle.gif"  title="Solution for a triangle" alt="Solution for a triangle" width="300" height="300" /></td>
    <td> <img src="https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/resources/PolyImageMaker/triangle.png"  title="Reference triangle" alt="Reference triangle" width="300" height="300" /></td>
    <td> <img src="https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/out/PolyImageMaker/geom.gif"  title="Solution for geometric shapes" alt="Solution for geometric shapes" width="300" height="300" /></td>
    <td> <img src="https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/resources/PolyImageMaker/geom.png"  title="Reference geometric shapes" alt="Reference geometric shapes" width="300" height="300" /></td>
    <td> <img src="https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/out/PolyImageMaker/monalisa.gif"  title="Solution for the monalisa" alt="Solution for the monalisa" width="200" height="300" /></td>
    <td> <img src="https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/resources/PolyImageMaker/monalisa.png"  title="Reference monalisa" alt="Reference monalisa" width="200" height="300" /></td>
  </tr>
</table>

Some hard shapes to replicate with triangles are curves.
<table>
  <tr>
    <th colspan="2"> <b>Circle </b></th>
    <th colspan="2"> <b>Ring</b> </th>
  </tr>
  <tr>
    <td> <b>Circle evolution </b></td>
    <td> <b>Reference</b> </td>
    <td> <b>Ring evolution </b></td>
    <td> <b>Reference</b> </td>
  </tr>
  <tr>
    <td> <img src="https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/out/PolyImageMaker/circle.gif"  title="Solution for a circle" alt="Solution for a circle" width="500" height="500" /></td>
    <td> <img src="https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/resources/PolyImageMaker/circle.png"  title="Reference circle" alt="Reference circle" width="500" height="500" /></td>
    <td> <img src="https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/out/PolyImageMaker/ring.gif"  title="Solution for a ring" alt="Solution for a ring" width="500" height="500" /></td>
    <td> <img src="https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/resources/PolyImageMaker/ring.png"  title="Reference geometric ring" alt="Reference ring" width="500" height="500" /></td>
  </tr>
</table>

## In this folder:
We find 15 different classes that defines the problem structures that we decided to create (Implementing their 
corresponding OptimizationLib.ga interfaces).
1. [CustomGeneticAlgorithm](#customgeneticalgorithm): Implements GeneticAlgorithm.
2. [PolyImageInitializer](#polyimageinitializer): Extends GAInitializer.
3. [PolyImageObjectiveFunction](#polyimageobjectivefunction): Extends ObjectiveFunction.
4. [CustomGenome](#customgenome): Implements Genome.
5. [CustomChromosome](#customchromosome): Implements Chromosome.
6. [CustomMutationPolicy](#custommutationpolicy): Implements MutationPolicy.
7. [CustomCrossover](#customcrossover): Implements Crossover.
8. [GifCreator](#gifcreator): Creates a gif.
9. [Mutations](#mutations): Folder with the mutations.
   1. [ChromosomeMutationColor](#chromosomemutationmolor):Implements Mutation.
   2. [ChromosomeMutationMoveOneVertex](#chromosomemutationmoveonevertex): Implements Mutation.
   3. [GenomeMutationAddChromosome](#genomemutationaddchromosome): Implements Mutation.
   4. [GenomeMutationBackground](#genomemutationbackground): Implements Mutation.
   5. [GenomeMutationPolygonOrder](#genomemutationpolygonorder): Implements Mutation.
   6. [GenomeMutationRemoveChromosome](#genomemutationremovechromosome): Implements Mutation.
9. [RunPolyImageOptimization](#runpolyimageoptimization): This is the main class. Here we instantiate our ChessGA Object with all his 
components.

### [CustomGeneticAlgorithm](https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/java/org/soyaga/examples/PolyImageMaker/CustomGeneticAlgorithm.java):
This class implements GeneticAlgorithm, which by extension makes it an Optimizer instance. In other words this class 
can be optimized and its results can be gathered.
````code
public void optimize(){...}
public Object getResult(){...}
````
The <i>optimize</i> function is implemented in a way that the generation number is passed as VarArg to most of the actions
executed by the CustomGeneticAlgorithm parts (CrossoverPolicy, MutationPolicy...). The <i>getResults</i> function 
returns an Image that will be stored or used to build the gif.

### [PolyImageInitializer](https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/java/org/soyaga/examples/PolyImageMaker/PolyImageInitializer.java):
Initializes a new individual from zero. In our case, uses CustomGenome, CustomChromosome, Color and Polygon to 
store the information of a randomly initialized individual.
````code
public Individual initializeIndividual()
````
In this case, the information is sparse in the GeneticInformationContainers.Genome and Chromosome contains information 
needed to evaluate an individual.

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
    CustomGenome1 o--o Polygon1
    CustomGenomeX o--o ColorX
    CustomGenomeX o--o PolygonX
    CustomGenomeY o--o ColorY
    CustomGenomeY o--o PolygonY
  end
    
````

### [PolyImageObjectiveFunction](https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/java/org/soyaga/examples/PolyImageMaker/PolyImageObjectiveFunction.java):
This function evaluates the objective function of a solution. In this case we compute the l~2~ norm for the RGBA colors 
from the image generated by the genome against the colors in the real image.

This function return a Double containing the ratio of Euclidean color deviation per pixel of the whole image compared 
with the reference one.

### [CustomGenome](https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/java/org/soyaga/examples/PolyImageMaker/CustomGenome.java):
A Genome that contains the genetic information:
  * backgroundColor (Color): Contains the image background color.
  * chromosomeList (ArrayList&lt;CustomChromosome&gt;): Ordered List, but dynamic in size.

### [CustomChromosome](https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/java/org/soyaga/examples/PolyImageMaker/CustomChromosome.java):
A Chromosome that contains the genetic information:
  * color (Color): Contains the Polygon filling color.
  * polygon (Polygon): Shape that we want to fray in the image.

### [CustomMutationPolicy](https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/java/org/soyaga/examples/PolyImageMaker/CustomMutationPolicy.java):
A MutationPolicy that applies mutations to the CustomGenome and CustomChromosome Objects. Each mutation class have a 
specific mutation probability rate. 

### [CustomCrossover](https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/java/org/soyaga/examples/PolyImageMaker/CustomCrossover.java):
A Crossover that mixes the background of the two parents genomes and performs a two point crossover for the 
CustomChromosome ArrayLists (Ordered Lists but with dynamic size).

### [GifCreator](https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/java/org/soyaga/examples/PolyImageMaker/GifCreator.java):
An auxiliary class that has only one static method that allow the construction of a gif from an 
ArrayList&lt;BufferedImage&gt;.

### [Mutations](https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/java/org/soyaga/examples/PolyImageMaker/Mutations):
Folder that contains all the mutations that are applied to the genomes.

1. #### [ChromosomeMutationColor](https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/java/org/soyaga/examples/PolyImageMaker/Mutations/ChromosomeMutationColor.java):
   Class that mutates a Chromosome. It slightly changes the color of that fills the polygon randomly.
2. #### [ChromosomeMutationMoveOneVertex](https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/java/org/soyaga/examples/PolyImageMaker/Mutations/ChromosomeMutationMoveOneVertex.java):
   Class that mutates a Chromosome. It slightly moves one random vertex of the polygon.
3. #### [GenomeMutationAddChromosome](https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/java/org/soyaga/examples/PolyImageMaker/Mutations/GenomeMutationAddChromosome.java):
   Class that mutates the Genome. Randomly creates and adds a Chromosome tho the genome (add one new polygon).
4. #### [GenomeMutationBackground](https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/java/org/soyaga/examples/PolyImageMaker/Mutations/GenomeMutationBackground.java):
   Class that mutates the Genome. It slightly changes the color of that fills the image background.
5. #### [GenomeMutationPolygonOrder](https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/java/org/soyaga/examples/PolyImageMaker/Mutations/GenomeMutationPolygonOrder.java):
   Class that mutates the Genome. It swaps the position of two random Chromosomes.
6. #### [GenomeMutationRemoveChromosome](https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/java/org/soyaga/examples/PolyImageMaker/Mutations/GenomeMutationRemoveChromosome.java):
   Class that mutates the Genome. Randomly removes one Chromosome from the genome.


### [RunNQueenOptimization](https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/java/org/soyaga/examples/NQueenProblem/CustomGA/RunNQueenOptimization.java):
This is the main class. Is where the run starts. As simple as instantiate the CustomGeneticAlgorithm object (previously defined) filled
with its components, optimize it, and retrieve the results.

The specific components for the CustomGeneticAlgorithm are:
- MaxIterationCriteriaPolicy: A StoppingCriteria based on a maximum number of iteration.
- FixedCrossoverPolicy: A CrossoverPolicy that applies each iteration a fixed number of crossovers.
  - TournamentSelection: A Selection procedure in which each parent is chosen by the tournament method.
  - CustomCrossover: The Crossover  previously defined.
- CustomMutationPolicy: The MutationPolicy previously defined.
  - ChromosomeMutationColor: The Mutation previously defined.
  - ChromosomeMutationMoveOneVertex: The Mutation previously defined.
  - GenomeMutationAddChromosome: The Mutation previously defined.
  - GenomeMutationBackground: The Mutation previously defined.
  - GenomeMutationPolygonOrder: The Mutation previously defined.
  - GenomeMutationRemoveChromosome: The Mutation previously defined.
- FixedElitismPolicy: An ElitismPolicy that applies each iteration a fixed number of elitist individuals.
- FixedNewbornPolicy: A newbornPolicy that applies each iteration a fixed number of newborn individuals.
- PolyImageInitializer: The Initializer previously defined.
  - PolyImageObjectiveFunction: The ObjectiveFunction previously defined.


## Result example:
For:
```java
//Integer with the initial population size. 
// During the optimization-time, the population size is NºOfCrossed + NºOfElitist + NºOfNewborns
int initialPopulationSize = 50;
//Integer with the initial number of polygons in the new individuals.
Integer initialNumberOfPolygons = 1;
//Integer with the number of vertexes on the polygons.
Integer numberOfVertexes = 4;
//Integer with the number of iterations.
int maxGenerations = 20000;

CustomGeneticAlgorithm ga = new CustomGeneticAlgorithm("CustomPolyImageMaker", initialPopulationSize,
        new MaxIterationCriteriaPolicy(maxGenerations),
        new FixedCrossoverPolicy(initialPopulationSize*90/100, // NºOfCrossed
        new TournamentSelection(),
        new CustomCrossover()),
        new CustomMutationPolicy(
            new ArrayList<>(){{
                add(new GenomeMutationBackground(maxGenerations*50/100, 10)); // Background color change stop
                add(new GenomeMutationPolygonOrder(maxGenerations*60/100)); // Polygon order swap stop
                add(new GenomeMutationAddChromosome(image.getWidth(), image.getHeight(), 
                            maxGenerations*70/100,numberOfVertexes)); // Add polygons stop
                add(new GenomeMutationRemoveChromosome(maxGenerations*80/100)); // Remove polygons stop
                }},
            new ArrayList<>(){{
                add(new ChromosomeMutationColor(maxGenerations*90/100, 10)); // Polygon color change stop
                add(new ChromosomeMutationMoveOneVertex(image.getWidth(), image.getHeight(),
                            maxGenerations*80/100, 10, 20)); // Move Polygon vertex from large to small
                }},
            new double[]{0.02,0.05,0.08,0.05}, // Genome mutations probabilities
            new double[]{0.01,0.02}), // Chromosome mutations probabilities
        new FixedElitismPolicy(initialPopulationSize*4/100), // NºOfElitist
        new FixedNewbornPolicy(initialPopulationSize*10/100), // NºOfNewborns
        new PolyImageInitializer(initialNumberOfPolygons, numberOfVertexes, image.getHeight(), image.getWidth(),
        new PolyImageObjectiveFunction(image,maxGenerations*95/100)), // Start to penalize genome length
        image.getWidth(), image.getHeight(), 10 // step to store intermediate images
        );
```
We have:
<table>
  <tr>
    <th colspan="2"> <b>Monalisa </b></th>
    <th colspan="1"> <b>Convergence</b> </th>
  </tr>
  <tr>
    <td> <b>Monalisa evolution </b></td>
    <td> <b>Monalisa final</b> </td>
    <td> <b>Convergence Graph </b></td>
  </tr>
  <tr>
    <td> <img src="https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/out/PolyImageMaker/monalisa_cubes.gif"  title="Evolution monalisa" alt="Evolution monalisa" width="200" height="300" /></td>
    <td> <img src="https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/out/PolyImageMaker/monalisa_cubes.png"  title="Solution monalisa" alt="Solution monalisa" width="200" height="300" /></td>
    <td> <img src="https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/out/PolyImageMaker/graph.png"  title="Convergence graph" alt="Convergence graph" width="300" height="300" /></td>
  </tr>
</table>

## Comment:
Notice that this example is a hard approach to image recreation. Most of the examples that we found in the literature
solve the problem losing track of the actual shapes that form the images. Those approaches tend to use images as 
genomes and the Crossovers mixe the images in different ways. Mutations perform simple additions of new polygons, or add
random colored pixels. The objective in that case is simply to reproduce the image. In our case we want to reproduce the
image, but we want to be able to identify the polygons that constitute it. So our optimization has to find the right 
number of unique polygons and colors that best represent the image (a way harder task :relaxed: ).

