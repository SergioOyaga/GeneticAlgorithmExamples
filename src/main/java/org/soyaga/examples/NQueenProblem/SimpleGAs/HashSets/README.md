# HashSets Implementation

This implementation solves the N-Queens optimization problem using sets (`HashSets`) as the fundamental data structure for storing information.

## Class Structure

This folder contains six classes that define the structures required for solving the problem. Each class implements its respective interface from `OptimizationLib.ga`:

1. **[ChessGA](#chessga):** Extends `SimpleGeneticAlgorithm`.
2. **[ChessGAInitializer](#chessgainitializer):** Extends `GAInitializer`.
3. **[ChessFeasibilityFunction](#chessfeasibilityfunction):** Extends `FeasibilityFunction`.
4. **[ChessObjectiveFunction](#chessobjectivefunction):** Extends `ObjectiveFunction`.
5. **[ChessGen](#chessgen):** Extends `Gen`.
6. **[RunNQueenOptimization](#runnqueenoptimization):** The main class for instantiation and optimization.

### ChessGA

This class extends `SimpleGeneticAlgorithm`, making it an `Optimizer` instance. This means the class can be optimized, and its results can be retrieved.

```java
public void optimize(){...}
public Object getResult(){...}
````
The `optimize()` method, inherited from `SimpleGeneticAlgorithm`, defines a basic optimization procedure. The `getResults()` method returns a string representing the best solution found, which is then [printed to the console](#result-example).

### [ChessGAInitializer](https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/java/org/soyaga/examples/NQueenProblem/SimpleGAs/HashSets/ChessGAInitializer.java):
This class initializes a new individual from scratch. It utilizes `HashSetGenome`, `HashSetChromosome`, and `ChessGen` to store the information of a randomly initialized individual.

````java
public Individual initializeIndividual()
````
In this implementation, all information is stored within `ChessGen` instances. A total of *N×N* genes are created, one for each square on the chessboard. Each gene holds the row and column information for its corresponding square and a randomly generated boolean value indicating the presence or absence of a queen.

````mermaid
flowchart TB
    subgraph  ide1 [Unordered Structures]
    direction TB    
    Genome o--o Chromosome1
    Genome o--o ChromosomeX
    Genome o--o ChromosomeN
    Chromosome1 o--o GenY1
    Chromosome1 o--o GenYZ
    Chromosome1 o--o GenYN
    ChromosomeX o--o GenN1
    ChromosomeX o--o GenNZ
    ChromosomeX o--o GenNN
    ChromosomeN o--o Gen11
    ChromosomeN o--o Gen1Z
    ChromosomeN o--o Gen1N
    end
    
    
````


### [ChessFeasibilityFunction](https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/java/org/soyaga/examples/NQueenProblem/SimpleGAs/HashSets/ChessFeasibilityFunction.java):
This function evaluates the feasibility of a solution. For the N-Queens problem, it ensures that exactly *N* queens are placed on the board. The function returns a double value indicating the number of missing or exceeding queens.

### [ChessObjectiveFunction](https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/java/org/soyaga/examples/NQueenProblem/SimpleGAs/HashSets/ChessObjectiveFunction.java):
This function evaluates the objective function of a solution. In this set-based design for the N-Queens problem, it counts the number of collisions between queens. The function returns a double value representing the total number of collisions.

### [ChessGen](https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/java/org/soyaga/examples/NQueenProblem/SimpleGAs/HashSets/ChessGen.java):
This class defines a `Gen` containing three variables:

- Two immutable integers representing the row and column numbers.
- A boolean value indicating the presence (`true`) or absence (`false`) of a queen on that square.

Mutations will exclusively affect the boolean parameter, toggling its value.

### [RunNQueenOptimization](https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/java/org/soyaga/examples/NQueenProblem/SimpleGAs/HashSets/RunNQueenOptimization.java):
This is the main class where program execution begins. It involves instantiating the `ChessGA` object, optimizing it, and retrieving the results.

The specific components for `ChessGA` include:

- **MaxIterationCriteriaPolicy:** A `StoppingCriteria` based on a maximum number of iterations.
- **FixedCrossoverPolicy:** A `CrossoverPolicy` that applies a fixed number of crossovers per iteration.
  - **RandomSelection:** A selection procedure where each parent is chosen randomly.
  - **HeuristicCrossover:** A crossover that copies the entire genome of only one parent into the child. This makes the semi-heuristic approach more heuristic by eliminating the exchange of genetic material between individuals.
- **UnorderedSingleProbabilityMutPol:** A `MutationPolicy` that applies mutations stored in `HashSets` and filters the probability of applying each mutation with a fixed probability.
  - **BoolGenDenialMutation:** A mutation that negates the boolean value stored within a `Gen`.
- **FixedElitismPolicy:** An `ElitismPolicy` that selects a fixed number of elite individuals per iteration.
- **FixedNewbornPolicy:** A `NewbornPolicy` that introduces a fixed number of new individuals per iteration.
- **ChessGAInitializer:** The `Initializer` previously defined.
  - **ChessFeasibilityFunction:** The `FeasibilityFunction` previously defined.
  - **ChessObjectiveFunction:** The `ObjectiveFunction` previously defined.

## Result example:
Using the following parameters:

- `numberOfQueens = 8`
- `populationSize = 3`
- `iterations = 20000`
- `Pmut = 0.02`
- `crossed = 1`
- `elitist = 1`
- `newborns = 1`
````
/
|Feasibility= 0.0000
|ObjectiveFunction= 2.0000
|Fitness= 2.0000
\
 _  _  _  _  _  _  Q  _ 
 Q  _  _  _  _  _  _  _ 
 _  _  Q  _  _  _  _  _ 
 _  _  _  _  Q  _  _  _ 
 _  Q  _  _  _  _  _  _ 
 _  _  _  _  _  _  _  Q 
 _  _  _  _  _  Q  _  _ 
 _  Q  _  _  _  _  _  _ 
````
The solution obtained might be nearly satisfactory. However, the absence of a proper crossover mechanism can make it challenging to achieve the optimal solution. This highlights the importance of designing a robust strategy tailored to the specific problem. While a more sophisticated crossover procedure could have been employed to effectively combine genomes, this example prioritizes simplicity and clarity. 

