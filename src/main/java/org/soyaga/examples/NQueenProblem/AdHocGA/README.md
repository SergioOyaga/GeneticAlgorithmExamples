# LessClasses
Solution of the NQueen optimization problem cutting the genetic information at the Genome level. For a problem as simple
as this one, it is not necessary to encapsulate the info in deeper levels than the genome itself. This example 
uses an ArrayList &lt;Integer&gt; as genetic information container.  

## In this folder:
We find 5 different classes that defines the problem dependent structures that we have to create (Implementing their 
corresponding OptimizationLib.ga interfaces).
1. [ChessGA](#chessga): Extends SimpleGeneticAlgorithm.
2. [ChessGAInitializer](#chessgainitializer): Extends GAInitializer.
3. [ChessFeasibilityFunction](#chessfeasibilityfunction):Extends FeasibilityFunction.
4. [ChessObjectiveFunction](#chessobjectivefunction): Extends ObjectiveFunction.
5. [RunNQueenOptimization](#runnqueenoptimization): This is the main class. Here we instantiate our ChessGA Object with all his 
components.

### [ChessGA](https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/java/org/soyaga/examples/NQueenProblem/AdHocGA/ChessGA.java):
This class extends SimpleGeneticAlgorithm, which by extension makes it an Optimizer instance. In other words this class 
can be optimized and its results can be gathered.
````code
public void optimize(){...}
public Object getResult(){...}
````
The <i>optimize</i> function is already implemented by the abstract class SimpleGeneticAlgorithm, which defines a basic 
optimization procedure. The <i>getResults</i> function returns a String that will be [printed in the console](#result-example)
with the best solution found.

### [ChessGAInitializer](https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/java/org/soyaga/examples/NQueenProblem/AdHocGA/ChessGAInitializer.java):
Initializes a new individual from zero. In our case, uses HashMapGenome, ArrayList &lt;Integer&gt; to 
store the information of a randomly initialized individual.
````code
public Individual initializeIndividual()
````
In this case, the information is stored using a methodology equivalent as the used in the Arrays example. The row number
is implicitly stored in the ArrayList position (from 0 to N-1) and the column number is the Integer stored in the ArrayList
(also from 0 to N-1).

````mermaid
flowchart LR
    subgraph  ide0 [Genome]
        subgraph ide1 [ArrayList &ltInteger&gt]
            Integer1 o--o Integer2 o--o Integer3 o--o IntegerX o--o IntegerN
        end
    end
````


### [ChessFeasibilityFunction](https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/java/org/soyaga/examples/NQueenProblem/AdHocGA/ChessFeasibilityFunction.java):
This function evaluates the feasibility of a solution. In the case of the NQueen problem it may or may not have sense
to talk about feasibility. We decided to give it the mission of ensuring that each queen is placed in a
different column and row than the other queens.

This function return a Double containing the number of collisions (missing rows or columns where no queen is placed).


### [ChessObjectiveFunction](https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/java/org/soyaga/examples/NQueenProblem/AdHocGA/ChessObjectiveFunction.java):
This function evaluates the objective function of a solution. In the case of the NQueen problem in this design,
we only have to count the number of collisions (confronted Queens) looking at the diagonals.

This function return a Double containing the number of diagonal collisions between queens.


### [RunNQueenOptimization](https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/java/org/soyaga/examples/NQueenProblem/AdHocGA/RunNQueenOptimization.java):
This is the main class. Is where the run starts. As simple as instantiate the ChessGA object (previously defined) filled
with its components, optimize it, and retrieve the results.

The specific components for the ChessGA are:
- MaxIterationCriteriaPolicy: A StoppingCriteria based on a maximum number of iteration.
- FixedCrossoverPolicy: A CrossoverPolicy that applies each iteration a fixed number of crossovers.
  - RandomSelection: A Selection procedure in which each parent is chosen randomly.
  - OnePointCrossover: A Crossover procedure in which the genome of the parents is combined by braking it in two pieces
    and joining one part from one parent and the other part from the other parent in the child.
- OrderedSingleProbabilityMutPol: A MutationPolicy that applies mutations stored in arrays sequentially and filters the
  probability of applying the mutation with a fixed probability.
  - ChromosomeSwapMutation: A mutation that consist on interchanging Chromosomes stored in an ArrayList.
- FixedElitismPolicy: An ElitismPolicy that applies each iteration a fixed number of elitist individuals.
- FixedNewbornPolicy: A newbornPolicy that applies each iteration a fixed number of newborn individuals.
- ChessGAInitializer: The Initializer previously defined.
  - ChessFeasibilityFunction: The FeasibilityFunction previously defined.
  - ChessObjectiveFunction: The ObjectiveFunction previously defined.


## Result example:
For:
- numberOfQueens = 8
- populationSize = 100
- iterations = 1000
- Pmut = 0.02
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
 Q  _  _  _  _  _  _  _ 
 _  _  Q  _  _  _  _  _ 
 _  _  _  _  _  _  _  Q 
 _  _  _  _  _  Q  _  _ 
 _  _  _  Q  _  _  _  _ 
 _  Q  _  _  _  _  _  _ 
 _  _  _  _  Q  _  _  _ 
````
