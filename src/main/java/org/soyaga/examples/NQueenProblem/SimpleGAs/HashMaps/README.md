# HashMaps
Solution of the NQueen optimization problem using Maps as base structure to store information.

## In this folder:
We find 6 different classes that defines the problem dependent structures that we have to create (Implementing their 
corresponding OptimizationLib.ga interfaces).
1. [ChessGA](#chessga): Extends SimpleGeneticAlgorithm.
2. [ChessGAInitializer](#chessgainitializer): Extends GAInitializer.
3. [ChessFeasibilityFunction](#chessfeasibilityfunction):Extends FeasibilityFunction.
4. [ChessObjectiveFunction](#chessobjectivefunction): Extends ObjectiveFunction.
5. [MapCrossover](#mapcrossover): Extends Crossover.
6. [RunNQueenOptimization](#runnqueenoptimization): This is the main class. Here we instantiate our ChessGA Object with all his 
components.

### [ChessGA](https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/java/org/soyaga/examples/NQueenProblem/SimpleGAs/HashMaps/ChessGA.java):
This class extends SimpleGeneticAlgorithm, which by extension makes it an Optimizer instance. In other words this class 
can be optimized and its results can be gathered.
````code
public void optimize(){...}
public Object getResult(){...}
````
The <i>optimize</i> function is already implemented by the abstract class SimpleGeneticAlgorithm, which defines a basic 
optimization procedure. The <i>getResults</i> function returns a String that will be [printed in the console](#result-example)
with the best solution found.

### [ChessGAInitializer](https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/java/org/soyaga/examples/NQueenProblem/SimpleGAs/HashMaps/ChessGAInitializer.java):
Initializes a new individual from zero. In our case, uses HashMapGenome, HashMapChromosome and GenericGen to 
store the information of a randomly initialized individual.
````code
public Individual initializeIndividual()
````
In this case, all information is stored in the map keys and the Gen value. NxN Genes are created, one for each chess 
square. The gen contains a random boolean that represent the queen. The row information is in the Genome Map key. The 
column information in the Chromosome Map key.

````mermaid
flowchart TB
    subgraph  ide0 [Structure]
    Genome
    end
    subgraph  ide1 [ Key =1]
    direction TB    
    Genome -...- Chromosome1
    end
    subgraph  ide2 [Key =X]
    Genome -...- ChromosomeX
    end
    subgraph  ide3 [Key =N]
    Genome -...- ChromosomeN
    end
    subgraph  ide4 [Key =1]
    Chromosome1 -...- Gen11
    end
    subgraph  ide5 [Key =Z]
    Chromosome1 -...- Gen1Z
    end
    subgraph  ide6 [Key =N]
    Chromosome1 -...- Gen1N
    end
    subgraph  ide7 [Key =1]
    ChromosomeX -...- GenX1
    end
    subgraph  ide8 [Key =Z]
    ChromosomeX -...- GenXZ
    end
    subgraph  ide9 [Key =N]
    ChromosomeX -...- GenXN
    end
    subgraph  ide10 [Key =1]
    ChromosomeN -...- GenN1
    end
    subgraph  ide11 [Key =Z]
    ChromosomeN -...- GenNZ
    end
    subgraph  ide12 [Key =N]
    ChromosomeN -...- GenNN
    end

    
````


### [ChessFeasibilityFunction](https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/java/org/soyaga/examples/NQueenProblem/SimpleGAs/HashMaps/ChessFeasibilityFunction.java):
This function evaluates the feasibility of a solution. In the case of the NQueen problem it may or may not have sense 
to talk about feasibility. We decided to give it the mission of ensuring that only N queens are placed in the solution.

This function return a Double containing the number of collisions (deficit or excess of Queens in the solution).


### [ChessObjectiveFunction](https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/java/org/soyaga/examples/NQueenProblem/SimpleGAs/HashMaps/ChessObjectiveFunction.java):
This function evaluates the objective function of a solution. In the case of the NQueen problem in this map design, 
we have to count the number of collisions (confronted Queens) looking in all directions (row, column and diagonals).

This function return a Double containing the number collisions between queens.


### [MapCrossover](https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/java/org/soyaga/examples/NQueenProblem/SimpleGAs/HashMaps/MapCrossover.java):
Crossover that interchanges random chromosomes between parents. In this case, it interchanges values associated to 
the same key in the Entry Set of pairs &lt; Key, Val &gt; of the Genome Hashmap.
The same methodology is applied to the Genes inside a Chromosome.

### [RunNQueenOptimization](https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/java/org/soyaga/examples/NQueenProblem/SimpleGAs/HashMaps/RunNQueenOptimization.java):
This is the main class. Is where the run starts. As simple as instantiate the ChessGA object (previously defined) filled
with its components, optimize it, and retrieve the results.

The specific components for the ChessGA are:
- MaxIterationCriteriaPolicy: A StoppingCriteria based on a maximum number of iteration.
- FixedCrossoverPolicy: A CrossoverPolicy that applies each iteration a fixed number of crossovers.
  - RandomSelection: A Selection procedure in which each parent is chosen randomly.
  - MapCrossover: The Crossover previously defined.
- UnorderedSingleProbabilityMutPol: A MutationPolicy that applies mutation stored in HasSets and filters the
probability of applying the mutation with a fixed probability.
  - GeneticMapSwapMutation: A mutation that consist on swapping values associated with a key of different HasMap.
- FixedElitismPolicy: An ElitismPolicy that applies each iteration a fixed number of elitist individuals.
- FixedNewbornPolicy: A newbornPolicy that applies each iteration a fixed number o newborn individuals.
- ChessGAInitializer: The Initializer previously defined.
  - ChessFeasibilityFunction: The FeasibilityFunction previously defined.
  - ChessObjectiveFunction: The ObjectiveFunction previously defined.


## Result example:
For:
- numberOfQueens = 8
- populationSize = 100
- iterations = 2000
- Pmut = 0.8
- crossed = 80
- elitist = 10
- newborns = 10
````
/
|Feasibility= 0.0000
|ObjectiveFunction= 0.0000
|Fitness= 0.0000
\
 _  _  _  _  _  _  Q  _ 
 _  _  _  Q  _  _  _  _ 
 _  Q  _  _  _  _  _  _ 
 _  _  _  _  _  _  _  Q 
 _  _  _  _  _  Q  _  _ 
 Q  _  _  _  _  _  _  _ 
 _  _  Q  _  _  _  _  _ 
 _  _  _  _  Q  _  _  _ 
````
