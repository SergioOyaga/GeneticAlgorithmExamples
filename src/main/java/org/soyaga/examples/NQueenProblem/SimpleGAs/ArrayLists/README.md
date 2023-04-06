# ArrayLists
Solution of the NQueen optimization problem using Arrays as base structure to store information.

## In this folder:
We find 5 different classes that defines the problem dependent structures that we have to create (Implementing their 
corresponding OptimizationLib.ga interfaces).
1. [ChessGA](#chessga): Extends SimpleGeneticAlgorithm.
2. [ChessGAInitializer](#chessgainitializer): Extends GAInitializer.
3. [ChessFeasibilityFunction](#chessfeasibilityfunction):Extends FeasibilityFunction.
4. [ChessObjectiveFunction](#chessobjectivefunction): Extends ObjectiveFunction.
5. [RunNQueenOptimization](#runnqueenoptimization): This is the main class. Here we instantiate our ChessGA Object with all his 
components.

### [ChessGA](TODO:addLink):
This class extends SimpleGeneticAlgorithm, which by extension makes it an Optimizer instance. In other words this class 
can be optimized and its results can be gathered.
````code
public void optimize(){...}
public Object getResult(){...}
````
The <i>optimize</i> function is already implemented by the abstract class SimpleGeneticAlgorithm, which defines a basic 
optimization procedure. The <i>getResults</i> function returns a String that will be [printed in the console](#result-example)
with the best solution found.

### [ChessGAInitializer](TODO:addLink):
Initializes a new individual from zero. In our case, uses ArrayListGenome, ArrayListChromosome and GenericGen to 
store the information of a randomly initialized individual.
````code
public Individual initializeIndividual()
````
In this case, we already store implicitly tha row position by knowing the position of the chromosome in the 
ArrayList &lt Chromosome &gt. As consequence, only N Chromosomes with one Gen each one are created. Each Gen contains an
integer with the column position.

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


### [ChessFeasibilityFunction](TODO:addLink):
This function evaluates the feasibility of a solution. In the case of the NQueen problem it may or may not have sense 
to talk about feasibility. We decided to give it the mission of ensuring that each queen is placed in a 
different column and row than the other queens.

This function return a Double containing the number of collisions (missing rows or columns where no queen is placed).


### [ChessObjectiveFunction](TODO:addLink):
This function evaluates the objective function of a solution. In the case of the NQueen problem in this array design, 
we only have to count the number of collisions (confronted Queens) looking at the diagonals.

This function return a Double containing the number of diagonal collisions between queens.


### [RunNQueenOptimization](TODO:addLink):
This is the main class. Is where the run starts. As simple as instantiate the ChessGA object (previously defined) filled
with its components, optimize it, and retrieve the results.

The specific components for the ChessGA are:
- MaxIterationCriteriaPolicy: A StoppingCriteria based on a maximum number of iteration.
- FixedCrossoverPolicy: A CrossoverPolicy that applies each iteration a fixed number of crossovers.
  - RandomSelection: A Selection procedure in which each parent is chosen randomly.
  - OnePointCrossover: A Crossover procedure in which the genome of the parents is combined by braking it in two pieces 
  and joining one part from one parent and the other part from the other parent in the child.
- OrderedSingleProbabilityMutPol: A MutationPolicy that applies mutation stored in arrays sequentially and filters the
probability of applying the mutation with a fixed probability.
  - ChromosomeSwapMutation: A mutation that consist on interchanging Chromosomes stored in an ArrayList.
- FixedElitismPolicy: An ElitismPolicy that applies each iteration a fixed number of elitist individuals.
- FixedNewbornPolicy: A newbornPolicy that applies each iteration a fixed number o newborn individuals.
- ChessGAInitializer: The Initializer previously defined.
  - ChessFeasibilityFunction: The FeasibilityFunction previously defined.
  - ChessObjectiveFunction: The ObjectiveFunction previously defined.

## Result example:
For:
- numberOfQueens = 8
- populationSize = 100
- iterations = 1000
- Pmut = 0.01
- crossed = 80
- elitist = 10
- newborns = 10
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
