# AdHocGA
This solution to the N-Queens puzzle cuts the genetic information at the `Genome` level. For a problem as simple
as this one, it is not necessary to encapsulate the information in deeper levels than the genome itself. This example 
uses an `ArrayList<Integer>` as a genetic information container.

## In this folder
We find five different classes that define the problem-dependent structures that we have to create (implementing their 
corresponding OptimizationLib.ga interfaces).

1.  [ChessGA](#chessga): Extends `StatsGeneticAlgorithm`.
2.  [ChessGAInitializer](#chessgainitializer): Extends `GAInitializer`.
3.  [ChessFeasibilityFunction](#chessfeasibilityfunction): Extends `FeasibilityFunction`.
4.  [ChessObjectiveFunction](#chessobjectivefunction): Extends `ObjectiveFunction`.
5.  [RunNQueenOptimization](#runnqueenoptimization): The main class for instantiation and optimization.

### [ChessGA](https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/java/org/soyaga/examples/NQueenProblem/AdHocGA/ChessGA.java)
This class extends `StatsGeneticAlgorithm`, which by extension makes it an `Optimizer` instance. In other words, this class 
can be optimized, and its results can be gathered.
````java
public void optimize(){...}
public Object getResult(){...}
````
The `optimize` function is already implemented by the abstract class `StatsGeneticAlgorithm`, which defines a basic 
optimization procedure. The `getResults` function returns a string that will be [printed in the console](#result-example)
with the best solution found. The optimization statistics will be both printed to the console and saved in a CSV file with a name format: "summary__YYYY-MM-DD__hh-mm.csv".

### [ChessGAInitializer](https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/java/org/soyaga/examples/NQueenProblem/AdHocGA/ChessGAInitializer.java)
This class initializes a new individual from scratch.
It uses `ArrayListGenome` and integers to store the information of a randomly initialized individual.
````java
public Individual initializeIndividual()
````
In this case, the row position is implicitly stored based on the position of the integer in the `Genome` 
(`ArrayList<Integer>`). Each integer represents the column position of the queen in that row.

````mermaid
flowchart LR
    subgraph  ide0 [Genome]
        subgraph ide1 [ArrayList &ltInteger&gt]
            Integer1 o--o Integer2 o--o Integer3 o--o IntegerX o--o IntegerN
        end
    end
````

### [ChessFeasibilityFunction](https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/java/org/soyaga/examples/NQueenProblem/AdHocGA/ChessFeasibilityFunction.java)
This function evaluates the feasibility of a solution.
For the N-Queens problem, it ensures that each queen is placed in a different column and row from other queens.
The function returns a double indicating the number of collisions (missing rows or columns where no queen is placed).

### [ChessObjectiveFunction](https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/java/org/soyaga/examples/NQueenProblem/AdHocGA/ChessObjectiveFunction.java)
This function evaluates the objective function of a solution.
For the N-Queens problem in this array design,
it counts the number of collisions (queens that can attack each other diagonally) by looking at the diagonals.
The function returns a double indicating the number of diagonal collisions between queens.

### [RunNQueenOptimization](https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/java/org/soyaga/examples/NQueenProblem/AdHocGA/RunNQueenOptimization.java)
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
-   `NIterationsStatsRetrievalPolicy`: A `StatsRetrievalPolicy` that applies the evaluation of the stats every N iterations.
    -   `CurrentMinFitnessStat`: A `Stat` that computes the current minimal fitness value.
    -   `CurrentMaxFitnessStat`: A `Stat` that computes the current maximal fitness value.
    -   `HistoricalMinFitnessStat`: A `Stat` that computes the historical minimal fitness value.
    -   `HistoricalMaxFitnessStat`: A `Stat` that computes the historical maximal fitness value.
    -   `MeanSdStat`: A `Stat` that computes the population's mean fitness along with the standard deviation.
    -   `PercentileStat`: A `Stat` that computes the fitness values for specific percentiles.
    -   `StepGradientStat`: A `Stat` that computes the step-gradient of the mean fitness.
    -   `TimeGradientStat`: A `Stat` that computes the time-gradient of the mean fitness.
    -   `ElapsedTimeStat`: A `Stat` that computes the elapsed time.

## Result Example
For:

-   `numberOfQueens = 8`
-   `populationSize = 100`
-   `iterations = 1000`
-   `Pmut = 0.02`
-   `crossed = 80`
-   `elitist = 10`
-   `newborns = 10`

The CSV file is stored with a name with the format: "summary__YYYY-MM-DD__hh-mm.csv".

````
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
| Iteration | CurrentMin | CurrentMax | HistoricalMin | HistoricalMax | MeanFitness | StandardDev | P0 | P25 | P50 | P75 | P100 | StepGradient(u/iter) | TimeGradient(u/s) | ElapsedTime(s) |
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
| 0         | 102.0000   | 504.0000   | 102.0000      | 504.0000      | 281.9200    | 89.0058     | 10 | 204 | 303 | 305 | 504. |                      |                   | 0.0374         |
| 1         | 102.0000   | 506.0000   | 102.0000      | 506.0000      | 273.8900    | 99.4956     | 10 | 204 | 303 | 305 | 506. | 8.0300               | 49469.5728        | 0.0162         |
| 2         | 4.0000     | 506.0000   | 4.0000        | 506.0000      | 245.8200    | 104.3784    | 4. | 202 | 206 | 304 | 506. | 28.0700              | 467755.3741       | 0.0060         |
| 3         | 4.0000     | 405.0000   | 4.0000        | 506.0000      | 239.7500    | 98.6912     | 4. | 202 | 205 | 305 | 405. | 6.0700               | 151443.3273       | 0.0040         |
| 4         | 4.0000     | 407.0000   | 4.0000        | 506.0000      | 242.7500    | 101.2326    | 4. | 203 | 205 | 305 | 407. | -3.0000              | -74981.2547       | 0.0040         |
| 5         | 4.0000     | 507.0000   | 4.0000        | 507.0000      | 252.6500    | 99.8444     | 4. | 203 | 206 | 304 | 507. | -9.9000              | -247642.3944      | 0.0040         |
| 6         | 4.0000     | 503.0000   | 4.0000        | 507.0000      | 249.7500    | 99.5224     | 4. | 203 | 302 | 305 | 503. | 2.9000               | 96673.1115        | 0.0040         |
| 7         | 4.0000     | 502.0000   | 4.0000        | 507.0000      | 241.7300    | 98.0134     | 4. | 203 | 206 | 304 | 502. | 8.0200               | 267208.6360       | 0.0020         |
| 8         | 4.0000     | 406.0000   | 4.0000        | 507.0000      | 235.5200    | 107.0442    | 4. | 104 | 206 | 304 | 406. | 6.2100               | 181504.6472       | 0.0034         |
| 9         | 1.0000     | 405.0000   | 1.0000        | 507.0000      | 231.6700    | 107.9912    | 1. | 178 | 205 | 304 | 405. | 3.8500               | 128337.6113       | 0.0030         |
| 10        | 1.0000     | 503.0000   | 1.0000        | 507.0000      | 236.4300    | 118.3901    | 1. | 201 | 205 | 304 | 503. | -4.7600              | -238083.3292      | 0.0020         |
| 11        | 1.0000     | 504.0000   | 1.0000        | 507.0000      | 241.4100    | 123.0297    | 1. | 202 | 253 | 304 | 504. | -4.9800              | -165983.4017      | 0.0030         |
| 12        | 1.0000     | 502.0000   | 1.0000        | 507.0000      | 216.4800    | 122.0340    | 1. | 104 | 205 | 303 | 502. | 24.9300              | 1246250.7499      | 0.0020         |
| 13        | 0.0000     | 504.0000   | 0.0000        | 507.0000      | 214.4300    | 126.4876    | 0. | 104 | 204 | 304 | 504. | 2.0500               | 102500.0000       | 0.0020         |

///////////////////////////
//// OMITED UNTIL 1000 ////
///////////////////////////

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
