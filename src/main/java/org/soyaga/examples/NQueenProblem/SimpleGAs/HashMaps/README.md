# HashMaps Implementation

This implementation solves the N-Queens optimization problem using maps (`HashMaps`) as the fundamental data structure for storing information.

## Class Structure

This folder contains six classes that define the structures required for solving the problem. Each class implements its respective interface from `OptimizationLib.ga`:

1. **[ChessGA](#chessga):** Extends `SimpleGeneticAlgorithm`.
2. **[ChessGAInitializer](#chessgainitializer):** Extends `GAInitializer`.
3. **[ChessFeasibilityFunction](#chessfeasibilityfunction):** Extends `FeasibilityFunction`.
4. **[ChessObjectiveFunction](#chessobjectivefunction):** Extends `ObjectiveFunction`.
5. **[MapCrossover](#mapcrossover):** Extends `Crossover`.
6. **[RunNQueenOptimization](#runnqueenoptimization):** The main class for instantiation and optimization.

### ChessGA

This class extends `SimpleGeneticAlgorithm`, making it an `Optimizer` instance. This means the class can be optimized, and its results can be retrieved. 

````java
public void optimize(){...}
public Object getResult(){...}
````

The `optimize` method is inherited from the abstract class `SimpleGeneticAlgorithm`, 
which defines a basic optimization procedure. 
The `getResults` method returns a string that will be [printed in the console](#result-example) 
to display the best solution found.

### [ChessGAInitializer](https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/java/org/soyaga/examples/NQueenProblem/SimpleGAs/HashMaps/ChessGAInitializer.java):

This class initializes a new individual from scratch. 
It uses `HashMapGenome`, `HashMapChromosome`, 
and `GenericGen` to store the information of a randomly initialized individual. 

````java
public Individual initializeIndividual()
````
In this scenario, all information is stored in the map keys and the gene value.

An NxN grid of genes is created, representing the chessboard (one gene per square).

Each gene contains a random boolean value, representing the presence or absence of a queen on that square.

The row information is stored in the Genome Map key, and the column information is stored in the Chromosome Map key. The combination of the Genome and Chromosome keys uniquely identifies each square on the chessboard.

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


### [ChessObjectiveFunction](https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/java/org/soyaga/examples/NQueenProblem/SimpleGAs/HashMaps/ChessObjectiveFunction.java):

This function evaluates the objective function of a solution. For the N-Queen problem in this map design, it counts the number of collisions (queens attacking each other). The function returns a double indicating the number of collisions between queens.

### [MapCrossover](https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/java/org/soyaga/examples/NQueenProblem/SimpleGAs/HashMaps/MapCrossover.java):

Crossover that interchanges random chromosomes between parents. In this case, it interchanges values associated with the same key in the Entry Set of pairs `<Key, Val>` of the Genome HashMap. The same methodology is applied to the Genes inside a Chromosome.

### [RunNQueenOptimization](https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/java/org/soyaga/examples/NQueenProblem/SimpleGAs/HashMaps/RunNQueenOptimization.java):

This is the main class where the program execution begins. It involves instantiating the ChessGA object (previously defined), optimizing it, and retrieving the results.

The specific components for ChessGA include:

-   MaxIterationCriteriaPolicy: A StoppingCriteria based on a maximum number of iterations.
-   FixedCrossoverPolicy: A CrossoverPolicy that applies a fixed number of crossovers each iteration.
    -   RandomSelection: A Selection procedure in which each parent is chosen randomly.
    -   MapCrossover: The Crossover previously defined.
-   UnorderedSingleProbabilityMutPol: A MutationPolicy that applies mutation stored in HashSets and filters the probability of applying the mutation with a fixed probability.
    -   GeneticMapSwapMutation: A mutation that consists of swapping values associated with a key of a different HashMap.
-   FixedElitismPolicy: An ElitismPolicy that applies a fixed number of elitist individuals each iteration.
-   FixedNewbornPolicy: A NewbornPolicy that applies a fixed number of newborn individuals each iteration.
-   ChessGAInitializer: The Initializer previously defined.
    -   ChessFeasibilityFunction: The FeasibilityFunction previously defined.
    -   ChessObjectiveFunction: The ObjectiveFunction previously defined.

## Result Example:

For:

-   numberOfQueens = 8
-   populationSize = 100
-   iterations = 2000
-   Pmut = 0.8
-   crossed = 80
-   elitist = 10
-   newborns = 10

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
