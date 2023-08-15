# HashSets
Solving the N-Queen optimization problem using sets as the base structure to store information.

## In this folder:
This folder contains 6 different classes that define the structures required for solving the problem.
These classes implement their respective interfaces from OptimizationLib.ga.
1. [ChessGA](#chessga): Extends SimpleGeneticAlgorithm.
2. [ChessGAInitializer](#chessgainitializer): Extends GAInitializer.
3. [ChessFeasibilityFunction](#chessfeasibilityfunction):Extends FeasibilityFunction.
4. [ChessObjectiveFunction](#chessobjectivefunction): Extends ObjectiveFunction.
5. [ChessGen](#chessgen): Extends Gen.
6. [RunNQueenOptimization](#runnqueenoptimization): The main class for instantiation and optimization.

### [ChessGA](https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/java/org/soyaga/examples/NQueenProblem/SimpleGAs/HashSets/ChessGA.java):
This class extends SimpleGeneticAlgorithm, which by extension makes it an Optimizer instance.
In other words, this class can be optimized and its results can be gathered.
````code
public void optimize(){...}
public Object getResult(){...}
````
The <i>optimize</i> method is inherited from the abstract class SimpleGeneticAlgorithm,
which defines a basic optimization procedure.
The <i>getResults</i> method returns a string that will be [printed in the console](#result-example)
to display the best solution found.

### [ChessGAInitializer](https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/java/org/soyaga/examples/NQueenProblem/SimpleGAs/HashSets/ChessGAInitializer.java):
This class initializes a new individual from scratch.
It uses HashSetGenome, HashSetChromosome,
and ChessGen to store the information of a randomly initialized individual.
````code
public Individual initializeIndividual()
````
In this scenario, all information is stored within the ChessGen instances. A total of NxN genes are created, 
with one gene corresponding to each square on the chessboard. Each gene carries the information of the row, column, 
and a randomly generated boolean representing the presence of a queen.

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
This function evaluates the feasibility of a solution.
For the N-Queen problem, it ensures that exactly N queens are placed on the board.
The function returns a double indicating the number of missing or exceeding queens on the board.


### [ChessObjectiveFunction](https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/java/org/soyaga/examples/NQueenProblem/SimpleGAs/HashSets/ChessObjectiveFunction.java):
This function evaluates the objective function of a solution.
For the N-Queen problem in this set design, it counts the number of collisions (confronted Queens).
The function returns a double indicating the number of collisions between queens.


### [ChessGen](https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/java/org/soyaga/examples/NQueenProblem/SimpleGAs/HashSets/ChessGen.java):
This class defines a Gen containing three variables: two immutables as Integers for the row and column numbers, 
and a Boolean indicating the presence of a Queen in that square (`true` for queen, `false` for no queen). 
Mutations will exclusively affect the boolean parameter, altering its value.


### [RunNQueenOptimization](https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/java/org/soyaga/examples/NQueenProblem/SimpleGAs/HashSets/RunNQueenOptimization.java):
This is the main class where the program execution begins.
It involves instantiating the ChessGA object (previously defined), optimizing it, and retrieving the results.

The specific components for ChessGA include:
- MaxIterationCriteriaPolicy: A StoppingCriteria based on a maximum number of iterations.
- FixedCrossoverPolicy: A CrossoverPolicy that applies each iteration a fixed number of crossovers.
  - RandomSelection: A Selection procedure in which each parent is chosen randomly.
  - HeuristicCrossover: A crossover that copies the whole genome of only one parent into the child. This makes the 
  semi-heuristic a more heuristic process by removing the cross between individuals.
- UnorderedSingleProbabilityMutPol: A MutationPolicy that applies mutations stored in HasSets and filters the
probability of applying the mutation with a fixed probability.
  - BoolGenDenialMutation: A mutation that consist on negating a Gen with its information in a boolean type.
- FixedElitismPolicy: An ElitismPolicy that applies each iteration a fixed number of elitist individuals.
- FixedNewbornPolicy: A newbornPolicy that applies each iteration a fixed number of newborn individuals.
- ChessGAInitializer: The Initializer previously defined.
  - ChessFeasibilityFunction: The FeasibilityFunction previously defined.
  - ChessObjectiveFunction: The ObjectiveFunction previously defined.


## Result example:
For:
- numberOfQueens = 8
- populationSize = 3
- iterations = 20000
- Pmut = 0.02
- crossed = 1
- elitist = 1
- newborns = 1
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
While the solution is nearly satisfactory, as evident, 
the absence of crossover makes it challenging to attain the optimal outcome. 
This exemplifies the significance of devising a robust strategy to address your problem. 
We could have employed a more intricate crossover procedure that elegantly blends the genomes, 
but for the sake of simplicity, we chose to keep this example as straightforward as possible.
