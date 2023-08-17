# SimplePolyImageMaker
The SimplePolyImageMaker addresses the task of discovering a collection of colored polygons that accurately represents 
an image. Historically, this challenge has been commonly exemplified through the recreation of iconic artworks like the
Mona Lisa painting. Nevertheless, it's crucial to recognize that this technique can be applied to virtually any image, 
as images can be deconstructed into an arrangement of overlapping polygons.

<table>
  <tr>
    <th colspan="2"> <b>Triangle </b></th>
  </tr>
  <tr>
    <td> <b>Triangle evolution </b></td>
    <td> <b>Reference</b> </td>
  </tr>
  <tr>
    <td> <img src="https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/out/ImageMaker/triangle.gif"  title="Solution for a triangle" alt="Solution for a triangle" width="300" height="300" /></td>
    <td> <img src="https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/resources/ImageMaker/triangle.png"  title="Reference triangle" alt="Reference triangle" width="300" height="300" /></td>
  </tr>
  <tr>
    <th colspan="2"> <b>Geometric Shapes</b> </th>
  </tr>
  <tr>
    <td> <b>Geometric Shapes evolution </b></td>
    <td> <b>Reference</b> </td>
  </tr>
  <tr>
    <td> <img src="https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/out/ImageMaker/geom.gif"  title="Solution for geometric shapes" alt="Solution for geometric shapes" width="300" height="300" /></td>
    <td> <img src="https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/resources/ImageMaker/geom.png"  title="Reference geometric shapes" alt="Reference geometric shapes" width="300" height="300" /></td>
  </tr>
  <tr>
    <th colspan="2"> <b>Monalisa </b></th>
  </tr>
  <tr>
    <td> <b>Monalisa evolution </b></td>
    <td> <b>Reference</b> </td>
  </tr>
  <tr>
    <td> <img src="https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/out/ImageMaker/monalisa.gif"  title="Solution for the monalisa" alt="Solution for the monalisa" width="200" height="300" /></td>
    <td> <img src="https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/resources/ImageMaker/monalisa.png"  title="Reference monalisa" alt="Reference monalisa" width="200" height="300" /></td>
  </tr>
    
</table>

Replicating curves can be challenging when using triangles as building blocks.
<table>
  <tr>
    <th colspan="2"> <b>Circle </b></th>
  </tr>
  <tr>
    <td> <b>Circle evolution </b></td>
    <td> <b>Reference</b> </td>
  </tr>
  <tr>
    <td> <img src="https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/out/ImageMaker/circle.gif"  title="Solution for a circle" alt="Solution for a circle" width="300" height="300" /></td>
    <td> <img src="https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/resources/ImageMaker/circle.png"  title="Reference circle" alt="Reference circle" width="300" height="300" /></td>
  </tr>
  <tr>
    <th colspan="2"> <b>Ring</b> </th>
  </tr>
  <tr>
    <td> <b>Ring evolution </b></td>
    <td> <b>Reference</b> </td>
  </tr>
  <tr>
    <td> <img src="https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/out/ImageMaker/ring.gif"  title="Solution for a ring" alt="Solution for a ring" width="300" height="300" /></td>
    <td> <img src="https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/resources/ImageMaker/ring.png"  title="Reference geometric ring" alt="Reference ring" width="300" height="300" /></td>
  </tr>
</table>

## In this folder:
We have created 15 distinct classes that define various problem structures, each of which implements the corresponding 
interfaces from OptimizationLib.ga:

1. [CustomGeneticAlgorithm](#customgeneticalgorithm): Implements the GeneticAlgorithm interface.
2. [PolyImageInitializer](#polyimageinitializer): Extends the GAInitializer interface.
3. [PolyImageObjectiveFunction](#polyimageobjectivefunction): Implements the ObjectiveFunction interface.
4. [CustomGenome](#customgenome): Implements the Genome interface.
5. [CustomChromosome](#customchromosome): Implements the Chromosome interface.
6. [CustomMutationPolicy](#custommutationpolicy): Implements the MutationPolicy interface.
7. [CustomCrossover](#customcrossover): Implements the Crossover interface.
8. [GifCreator](#gifcreator): Utility for creating GIFs.
9. [Mutations](#mutations): Folder containing mutation classes.
    - [ChromosomeMutationColor](#chromosomemutationcolor): Implements the Mutation interface.
    - [ChromosomeMutationMoveOneVertex](#chromosomemutationmoveonevertex): Implements the Mutation interface.
    - [GenomeMutationAddChromosome](#genomemutationaddchromosome): Implements the Mutation interface.
    - [GenomeMutationBackground](#genomemutationbackground): Implements the Mutation interface.
    - [GenomeMutationPolygonOrder](#genomemutationpolygonorder): Implements the Mutation interface.
    - [GenomeMutationRemoveChromosome](#genomemutationremovechromosome): Implements the Mutation interface.
10. [RunPolyImageOptimization](#runpolyimageoptimization): The main class where we instantiate the CustomGeneticAlgorithm 
object with all its components.

### [CustomGeneticAlgorithm](https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/java/org/soyaga/examples/ImageMaker/SimplePolyImageMaker/CustomGeneticAlgorithm.java):
This class implements GeneticAlgorithm, which by extension makes it an Optimizer instance. In other words, this class
can be optimized and its results can be gathered.
````code
public void optimize(){...}
public Object getResult(){...}
````
The <i>optimize</i> function is implemented in a way that the generation number is passed as VarArg to most of the actions
executed by the CustomGeneticAlgorithm parts (CrossoverPolicy, MutationPolicy...). The <i>getResults</i> function 
returns an Image that will be stored or used to build the gif.

### [PolyImageInitializer](https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/java/org/soyaga/examples/ImageMaker/SimplePolyImageMaker/PolyImageInitializer.java):
This class initializes a new individual from scratch.
It uses CustomGenome, CustomChromosome, Color and Polygon to store the information of a randomly
initialized individual.
````code
public Individual initializeIndividual()
````
In this scenario, the GeneticInformationContainers contain relatively sparse information. Specifically:
- The Genome holds the background color and an array of chromosomes.
- Each Chromosome encapsulates a Polygon along with the corresponding fill Color.

As a result of this structure, the data required for evaluating an individual is distributed sparsely within both the 
Genome and its constituent Chromosomes.

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

### [PolyImageObjectiveFunction](https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/java/org/soyaga/examples/ImageMaker/SimplePolyImageMaker/PolyImageObjectiveFunction.java):
This function is responsible for evaluating the objective function of a solution. In this context, the L~2~ norm is 
computed for the RGBA colors in the image generated by the genome, contrasting them with the colors in the actual image.

The outcome of this function is a Double value, representing the ratio of Euclidean color deviation per pixel across 
the entire generated image when compared to the reference image.

### [CustomGenome](https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/java/org/soyaga/examples/ImageMaker/SimplePolyImageMaker/CustomGenome.java):
A Genome that contains the genetic information:
  * backgroundColor (Color): Contains the image background color.
  * chromosomeList (ArrayList&lt;CustomChromosome&gt;): Ordered List, but dynamic in size.

### [CustomChromosome](https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/java/org/soyaga/examples/ImageMaker/SimplePolyImageMaker/CustomChromosome.java):
A Chromosome that contains the genetic information:
  * color (Color): Contains the Polygon filling color.
  * polygon (Polygon): Shape that we want to incorporate into the image.

### [CustomMutationPolicy](https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/java/org/soyaga/examples/ImageMaker/SimplePolyImageMaker/CustomMutationPolicy.java):
A MutationPolicy is responsible for implementing mutations on CustomGenome and CustomChromosome objects. Each mutation 
class is characterized by a particular mutation probability rate and a predefined maximum number of iterations during 
which it can be applied.

### [CustomCrossover](https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/java/org/soyaga/examples/ImageMaker/SimplePolyImageMaker/CustomCrossover.java):
A Crossover operation combines the backgrounds of the two parent genomes and executes a two-point crossover on the 
ArrayLists of CustomChromosomes. These ArrayLists function as dynamic-sized Ordered Lists.

### [GifCreator](https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/java/org/soyaga/examples/ImageMaker/SimplePolyImageMaker/GifCreator.java):
An auxiliary class that contains a single static method designed for constructing a GIF from an 
ArrayList&lt;BufferedImage&gt;.

### [Mutations](https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/java/org/soyaga/examples/ImageMaker/SimplePolyImageMaker/Mutations):
This folder contains all the mutations that are applied to the genomes.

1. #### [ChromosomeMutationColor](https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/java/org/soyaga/examples/ImageMaker/SimplePolyImageMaker/Mutations/ChromosomeMutationColor.java):
   This class mutates a Chromosome. It slightly changes the color that fills the polygon randomly.
2. #### [ChromosomeMutationMoveOneVertex](https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/java/org/soyaga/examples/ImageMaker/SimplePolyImageMaker/Mutations/ChromosomeMutationMoveOneVertex.java):
   This class mutates a Chromosome. It slightly moves one random vertex of the polygon.
3. #### [GenomeMutationAddChromosome](https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/java/org/soyaga/examples/ImageMaker/SimplePolyImageMaker/Mutations/GenomeMutationAddChromosome.java):
   This class mutates the Genome. It randomly creates and adds a Chromosome to the genome (adding one new polygon).
4. #### [GenomeMutationBackground](https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/java/org/soyaga/examples/ImageMaker/SimplePolyImageMaker/Mutations/GenomeMutationBackground.java):
   This class mutates the Genome. It slightly changes the color that fills the image background.
5. #### [GenomeMutationPolygonOrder](https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/java/org/soyaga/examples/ImageMaker/SimplePolyImageMaker/Mutations/GenomeMutationPolygonOrder.java):
   This class mutates the Genome. It swaps the position of two random Chromosomes.
6. #### [GenomeMutationRemoveChromosome](https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/java/org/soyaga/examples/ImageMaker/SimplePolyImageMaker/Mutations/GenomeMutationRemoveChromosome.java):
   This class mutates the Genome. It randomly removes one Chromosome from the genome.

### [RunPolyImageOptimization](https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/java/org/soyaga/examples/ImageMaker/SimplePolyImageMaker/RunPolyImageOptimization.java):
This is the main class where the program execution begins.
It involves instantiating the ChessGA object (previously defined), optimizing it, and retrieving the results.

The specific components for CustomGeneticAlgorithm include:
- MaxIterationCriteriaPolicy: A StoppingCriteria based on a maximum number of iterations.
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
-  NIterationsStatsRetrievalPolicy: A StatsRetrievalPolicy that applies the evaluation of the Stats every N iterations.
  - CurrentMinFitnessStat: A Stat that computes the current minimal fitness value.
  - CurrentMaxFitnessStat: A Stat that computes the current maximal fitness value.
  - HistoricalMinFitnessStat: A Stat that computes the historical minimal fitness value.
  - HistoricalMaxFitnessStat: A Stat that computes the historical maximal fitness value.
  - MeanSdStat: A Stat that computes the population's mean fitness along with the standard deviation.
  - PercentileStat: A Stat that computes the fitness values for specific percentiles.
  - StepGradientStat: A Stat that computes the step-gradient of the mean fitness.
  - TimeGradientStat: A Stat that computes the time-gradient of the mean fitness.
  - ElapsedTimeStat: A Stat that computes the elapsed time.

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
    <td> <img src="https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/out/ImageMaker/monalisa_cubes.gif"  title="Evolution monalisa" alt="Evolution monalisa" width="200" height="300" /></td>
    <td> <img src="https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/out/ImageMaker/monalisa_cubes.png"  title="Solution monalisa" alt="Solution monalisa" width="200" height="300" /></td>
    <td> <img src="https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/out/ImageMaker/graph.png"  title="Convergence graph" alt="Convergence graph" width="500" height="300" /></td>
  </tr>
</table>

## Comment:
It's important to note that this example represents a more challenging approach to image recreation. Many existing 
methods in the literature tend to solve the problem by disregarding the actual shapes that compose the images. 
In these approaches, images are often treated as genomes, and crossovers blend images in various ways. Mutations may 
involve simple additions of new polygons or the introduction of randomly colored pixels. The primary objective in these 
cases is to replicate the image itself.

In contrast, our approach aims not only to replicate the image but also to preserve the identity of the individual 
polygons that collectively form the image. Thus, our optimization process seeks to determine the optimal number of 
unique polygons and colors that faithfully represent the image. This task is considerably more challenging and nuanced.
:relaxed:
