# HashSets
Solution of the NQueen optimization problem using Sets as base structure to store information.

## In this folder:
We find 6 different classes that defines the problem dependent structures that we have to create (Implementing their 
corresponding OptimizationLib.ga interfaces).
1. [ChessGA](#chessga): Extends SimpleGeneticAlgorithm.
2. [ChessGAInitializer](#chessgainitializer): Extends GAInitializer.
3. [ChessFeasibilityFunction](#chessfeasibilityfunction):Extends FeasibilityFunction.
4. [ChessObjectiveFunction](#chessobjectivefunction): Extends ObjectiveFunction.
5. [ChessGen](#chessgen): Extends Gen.
6. [RunNQueenOptimization](#runnqueenoptimization): This is the main class. Here we instantiate our ChessGA Object with all his 
components.

### [ChessGA](https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/java/org/soyaga/examples/NQueenProblem/SimpleGAs/HashSets/ChessGA.java):
This class extends SimpleGeneticAlgorithm, which by extension makes it an Optimizer instance. In other words this class 
can be optimized and its results can be gathered.
````code
public void optimize(){...}
public Object getResult(){...}
````
The <i>optimize</i> function is already implemented by the abstract class SimpleGeneticAlgorithm, which defines a basic 
optimization procedure. The <i>getResults</i> function returns a String that will be [printed in the console](#result-example)
with the best solution found.

### [ChessGAInitializer](https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/java/org/soyaga/examples/NQueenProblem/SimpleGAs/HashSets/ChessGAInitializer.java):
Initializes a new individual from zero. In our case, uses HashSetGenome, HashSetChromosome and ChessGen to 
store the information of a randomly initialized individual.
````code
public Individual initializeIndividual()
````
In this case, all information is stored in the ChessGen. NxN Genes are created, one for each chess square. The gen 
contains the information of the row, column and a random boolean that represent the queen.  

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
This function evaluates the feasibility of a solution. In the case of the NQueen problem it may or may not have sense 
to talk about feasibility. We decided to give it the mission of ensuring that only N queens are placed in the solution.

This function return a Double containing the number of collisions (deficit or excess of Queens in the solution).


### [ChessObjectiveFunction](https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/java/org/soyaga/examples/NQueenProblem/SimpleGAs/HashSets/ChessObjectiveFunction.java):
This function evaluates the objective function of a solution. In the case of the NQueen problem in this set design, 
we have to count the number of collisions (confronted Queens) looking in all directions (row, column and diagonals).

This function return a Double containing the number collisions between queens.


### [ChessGen](https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/java/org/soyaga/examples/NQueenProblem/SimpleGAs/HashSets/ChessGen.java):
This class defines a Gen that contains three variables, Integers with the row and column numbers (both immutables) and a
Boolean representing if the there is a Queen in that square `true = queen, false = noQueen`. The mutations will only 
apply to the boolean parameter, changing its value.

### [RunNQueenOptimization](https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/java/org/soyaga/examples/NQueenProblem/SimpleGAs/HashSets/RunNQueenOptimization.java):
This is the main class. Is where the run starts. As simple as instantiate the ChessGA object (previously defined) filled
with its components, optimize it, and retrieve the results.

The specific components for the ChessGA are:
- MaxIterationCriteriaPolicy: A StoppingCriteria based on a maximum number of iteration.
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
Although the solution is almost a good one, as you can see, the lack of Crossover makes difficult to achieve the best 
result. Ths is an example of how important is to define a good strategy to solve your problem. We could have implemented
a more complex crossover procedure that mixes the genomes in a fancy way, but we wanted to keep this example as simple 
as possible.
