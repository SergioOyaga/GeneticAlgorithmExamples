# ArrayLists
Solving the N-Queens puzzle using arrays as the base structure to store information.

## Contents
This folder contains five different classes that define the structures required for solving the problem. 
These classes implement their respective interfaces from OptimizationLib.ga.

1.  [ChessGA](#chessga): Extends `SimpleGeneticAlgorithm`.
2.  [ChessGAInitializer](#chessgainitializer): Extends `GAInitializer`.
3.  [ChessFeasibilityFunction](#chessfeasibilityfunction): Extends `FeasibilityFunction`.
4.  [ChessObjectiveFunction](#chessobjectivefunction): Extends `ObjectiveFunction`.
5.  [RunNQueenOptimization](#runnqueenoptimization): The main class for instantiation and optimization.

### [ChessGA](https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/java/org/soyaga/examples/NQueenProblem/SimpleGAs/ArrayLists/ChessGA.java)
This class extends `SimpleGeneticAlgorithm`, which by extension makes it an `Optimizer` instance. 
In other words, this class can be optimized, and the optimization results can be gathered.

````java
public void optimize(){...}
public Object getResult(){...}
````
The `optimize` method is inherited from the abstract class `SimpleGeneticAlgorithm`,
which defines a basic optimization procedure. 
The `getResults` method returns a string that will be [printed in the console](#result-example)
to display the best solution found.

### [ChessGAInitializer](https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/java/org/soyaga/examples/NQueenProblem/SimpleGAs/ArrayLists/ChessGAInitializer.java)
This class initializes a new individual from scratch. 
It uses `ArrayListGenome`, `ArrayListChromosome`, 
and `GenericGen` to store the information of a randomly initialized individual.

````java
public Individual initializeIndividual()
````
In this case, the row position is implicitly stored based on the position of the chromosome in the `Genome` (which is an `ArrayList<Chromosome>`). 

Therefore, only N `Chromosome`s are created, each containing a single `Gen`. Each `Gen` contains an integer representing the column position of the queen on that row. 


````mermaid
flowchart LR
  subgraph  ide1 [Genome]
    direction LR
    subgraph ide2 [Chromosomes]
        direction LR
        subgraph ide3[Chromosome1]
            direction LR
            Gen1
        end
        subgraph ide4[ChromosomeX]
            direction LR
            GenX
        end
        subgraph ide5[ChromosomeN]
            direction LR
            GenN
        end
        Gen1-->GenX-->GenN
    end
    style ide1 fill:#0405
    style ide2 fill:#0045
    style ide3 fill:#4005
    style ide4 fill:#4005
    style ide5 fill:#4005
  end
````

### [ChessFeasibilityFunction](https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/java/org/soyaga/examples/NQueenProblem/SimpleGAs/ArrayLists/ChessFeasibilityFunction.java)
This function evaluates the feasibility of a solution. 
For the N-Queens problem, it ensures that each queen is placed in a different column and row from other queens. 
The function returns a double indicating the number of collisions (missing rows or columns where no queen is placed).

### [ChessObjectiveFunction](https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/java/org/soyaga/examples/NQueenProblem/SimpleGAs/ArrayLists/ChessObjectiveFunction.java)
This function evaluates the objective function of a solution. 
For the N-Queens problem in this array design, 
it counts the number of collisions (confronted queens) by looking at the diagonals. 
The function returns a double indicating the number of diagonal collisions between queens.

### [RunNQueenOptimization](https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/java/org/soyaga/examples/NQueenProblem/SimpleGAs/ArrayLists/RunNQueenOptimization.java)
This is the main class where the program execution begins. 
It involves instantiating the `ChessGA` object (previously defined), optimizing it, and retrieving the results.

The specific components for `ChessGA` include:

-   `MaxIterationCriteriaPolicy`: A `StoppingCriteria` based on a maximum number of iterations.
-   `FixedCrossoverPolicy`: A `CrossoverPolicy` that applies a fixed number of crossovers each iteration.
    -   `RandomSelection`: A selection procedure in which each parent is chosen randomly.
    -   `OnePointCrossover`: A crossover procedure in which the genome of the parents is combined by breaking it in two pieces 
    and joining one part from one parent and the other part from the other parent in the child.
-   `OrderedSingleProbabilityMutPol`: A `MutationPolicy` that applies mutations stored in arrays sequentially and filters the
    probability of applying the mutation with a fixed probability.
    -   `ChromosomeSwapMutation`: A mutation that consists of interchanging `Chromosome`s stored in an `ArrayList`.
-   `FixedElitismPolicy`: An `ElitismPolicy` that carries over a fixed number of elite individuals each iteration.
-   `FixedNewbornPolicy`: A `NewbornPolicy` that creates a fixed number of newborn individuals each iteration.
-   `ChessGAInitializer`: The `Initializer` previously defined.
    -   `ChessFeasibilityFunction`: The `FeasibilityFunction` previously defined.
    -   `ChessObjectiveFunction`: The `ObjectiveFunction` previously defined.

## Result Example
For:

-   `numberOfQueens = 8`
-   `populationSize = 100`
-   `iterations = 1000`
-   `Pmut = 0.01`
-   `crossed = 80`
-   `elitist = 10`
-   `newborns = 10`

````
/
|Feasibility= 0.0000
|ObjectiveFunction= 0.0000
|Fitness= 0.0000
\
_  _  _  Q  _  _  _  _
_  _  _  _  _  _  _  Q
_  _  _  _  Q  _  _  _
_  _  Q  _  _  _  _  _
Q  _  _  _  _  _  _  _
_  _  _  _  _  _  Q  _
_  Q  _  _  _  _  _  _
_  _  _  _  _  Q  _  _ 
````
