# CustomGA
This solution to the N-Queens puzzle implements all of the interfaces needed to perform a GA optimization, following the
OptimizationLib GA package guidelines. For a problem as simple as this one, it is not strictly necessary to encapsulate the information 
in deeper levels than the genome itself. However, this example aims to illustrate how developers can create their
own classes to fit specific problem requirements.

## In this folder
We find 17 different classes that define the problem structures that we decided to create (implementing their
corresponding OptimizationLib.ga interfaces).

1.  [CustomGeneticAlgorithm](#customgeneticalgorithm): Implements `GeneticAlgorithm`.
2.  [ChessGAInitializer](#chessgainitializer): Extends `GAInitializer`.
3.  [ChessFeasibilityFunction](#chessfeasibilityfunction): Implements `FeasibilityFunction`.
4.  [ChessObjectiveFunction](#chessobjectivefunction): Implements `ObjectiveFunction`.
5.  [CustomGenome](#customgenome): Implements `Genome`.
6.  [CustomChromosome](#customchromosome): Implements `Chromosome`.
7.  [CustomGen](#customgen): Implements `Gen`.
8.  [CustomBase](#custombase): Implements `GeneticInformationContainer`.
9.  [CustomMutationPolicy](#custommutationpolicy): Implements `MutationPolicy`.
10. [CustomMutation](#custommutation): Implements `Mutation`.
11. [CustomCrossoverPolicy](#customcrossoverpolicy): Implements `CrossoverPolicy`.
12. [CustomCrossover](#customcrossover): Implements `Crossover`.
13. [CustomSelection](#customselection): Implements `Selection`.
14. [CustomStoppingPolicy](#customstoppingpolicy): Implements `StoppingCriteriaPolicy`.
15. [CustomElitismPolicy](#customelitismpolicy): Implements `ElitismPolicy`.
16. [CustomNewbornPolicy](#customnewbornpolicy): Implements `NewbornPolicy`.
17. [RunNQueenOptimization](#runnqueenoptimization): The main class for instantiation and optimization.

### [CustomGeneticAlgorithm](https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/java/org/soyaga/examples/NQueenProblem/CustomGA/CustomGeneticAlgorithm.java)
This class implements `GeneticAlgorithm`, which by extension makes it an `Optimizer` instance. In other words, this class 
can be optimized, and its results can be gathered.

````java
public void optimize(){...}
public Object getResult(){...}
````
The `optimize` function is implemented in a way that the generation number is passed as a vararg to most of the actions
executed by the `CustomGeneticAlgorithm` parts (`CrossoverPolicy`, `MutationPolicy`, etc.). The `StoppingCriteria` also receives the
last best fitness solution. The `getResults` function returns a string that will be [printed in the console](#result-example)
with the best solution found and the history of the best individual's performance.

### [ChessGAInitializer](https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/java/org/soyaga/examples/NQueenProblem/CustomGA/ChessGAInitializer.java)
This class initializes a new individual from scratch.
It uses `CustomGenome`, `CustomChromosome`, `CustomGen`, and `CustomBase` to store the information of a randomly 
initialized individual.
````java
public Individual initializeIndividual()
````
In this scenario, all information is stored in the `CustomBase`. N/2 `Chromosome`s are created. Each `Chromosome` contains two 
`CustomGen`s (N `Gen`s in total that represent where queens are placed). Each `CustomGen` contains two `CustomBase`s. 

These `CustomBase`s are inspired by DNA bases, but in our case, each one represents a single number: either a column or a row number. 
These two `CustomBase`s together represent the coordinates of a queen on the chessboard. 

````mermaid
flowchart TB
    subgraph  ide1 [Unordered Structures ]
      direction TB    
      CustomGenome o--o CustomChromosome1
      CustomGenome o--o CustomChromosomeX
      CustomGenome o--o CustomChromosomeN/2
      CustomChromosome1 o--o CustomGenY1
      CustomChromosome1 o--o CustomGenY2
      CustomChromosomeX o--o CustomGenN/21
      CustomChromosomeX o--o CustomGenN/22
      CustomChromosomeN/2 o--o CustomGen11
      CustomChromosomeN/2 o--o CustomGen12
    end
  subgraph  ide2 [Ordered Structure]
    direction TB
     CustomGenY1 o--o CustomBaseY11
    CustomGenY1 o--o CustomBaseY12
    CustomGenY2 o--o CustomBaseY21
    CustomGenY2 o--o CustomBaseY22
    CustomGenN/21 o--o CustomBaseN/211
    CustomGenN/21 o--o CustomBaseN/212
    CustomGenN/22 o--o CustomBaseN/221
    CustomGenN/22 o--o CustomBaseN/222
    CustomGen11 o--o CustomBase111
    CustomGen11 o--o CustomBase112
    CustomGen12 o--o CustomBase121
    CustomGen12 o--o CustomBase122 
  end
    
````
### [ChessFeasibilityFunction](https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/java/org/soyaga/examples/NQueenProblem/CustomGA/ChessFeasibilityFunction.java)
This function evaluates the feasibility of a solution.
For the N-Queens problem, it ensures that all rows and columns are present in the solution.
The function returns a double indicating the number of missing rows and columns in the solution.

### [ChessObjectiveFunction](https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/java/org/soyaga/examples/NQueenProblem/CustomGA/ChessObjectiveFunction.java)
This function evaluates the objective function of a solution.
For the N-Queens problem in this map design, it counts the number of diagonal collisions (queens that can attack each other diagonally).
The function returns a double indicating the number of diagonal collisions between queens.

### [CustomGenome](https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/java/org/soyaga/examples/NQueenProblem/CustomGA/CustomGenome.java)
A `Genome` that contains the genetic information in a `HashSet<CustomChromosome>`. It is composed of N/2 
`CustomChromosome`s.

### [CustomChromosome](https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/java/org/soyaga/examples/NQueenProblem/CustomGA/CustomChromosome.java)
A `Chromosome` that contains the genetic information in a `HashSet<CustomGen>`. It contains two `CustomGen`s.

### [CustomGen](https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/java/org/soyaga/examples/NQueenProblem/CustomGA/CustomGen.java)
A `Gen` that contains the genetic information contained in two `CustomBase` variables.
````java
    private CustomBase row;
    private CustomBase col;
````

### [CustomBase](https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/java/org/soyaga/examples/NQueenProblem/CustomGA/CustomBase.java):
-   `CustomBase`: A `GeneticInformationContainer` that contains an integer that is interpreted as a row or column number by the `CustomGen`.
````java
    Integer baseVal;
````
### [CustomMutationPolicy](https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/java/org/soyaga/examples/NQueenProblem/CustomGA/CustomMutationPolicy.java)
A `MutationPolicy` that applies mutations to the `CustomBase` objects. Columns are mutated during even iterations,
and rows are mutated during odd iterations.

### [CustomMutation](https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/java/org/soyaga/examples/NQueenProblem/CustomGA/CustomMutation.java)
A `Mutation` that takes a `CustomBase` object and randomly changes its value to an integer between 0 and N-1.

### [CustomCrossoverPolicy](https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/java/org/soyaga/examples/NQueenProblem/CustomGA/CustomCrossoverPolicy.java)
A `CrossoverPolicy` that generates a varying number of offspring individuals based on the parity of the iteration.

### [CustomCrossover](https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/java/org/soyaga/examples/NQueenProblem/CustomGA/CustomCrossover.java)
A `Crossover` that, despite receiving the iteration number in the varargs, always executes a `HeuristicCrossover`.

### [CustomSelection](https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/java/org/soyaga/examples/NQueenProblem/CustomGA/CustomSelection.java)
A `Selection` that, despite receiving the iteration number in the varargs, always executes a `TournamentSelection`.

### [CustomStoppingPolicy](https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/java/org/soyaga/examples/NQueenProblem/CustomGA/CustomStoppingPolicy.java)
A `StoppingCriteriaPolicy` that halts the process either upon reaching a maximum number of iterations or upon 
discovering an optimal solution.

### [CustomElitismPolicy](https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/java/org/soyaga/examples/NQueenProblem/CustomGA/CustomElitismPolicy.java)
An `ElitismPolicy` that chooses a varying number of individuals based on the parity of the iteration.

### [CustomNewbornPolicy](https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/java/org/soyaga/examples/NQueenProblem/CustomGA/CustomNewbornPolicy.java)
A `NewbornPolicy` that creates a varying number of individuals based on the parity of the iteration.

### [RunNQueenOptimization](https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/java/org/soyaga/examples/NQueenProblem/CustomGA/RunNQueenOptimization.java)
This is the main class where the program execution begins.
It involves instantiating the `ChessGA` object (previously defined), optimizing it, and retrieving the results.

The specific components for `ChessGA` include:

-   `CustomStoppingPolicy`: The `StoppingCriteria` previously defined.
-   `CustomCrossoverPolicy`: The `CrossoverPolicy` previously defined.
    -   `CustomSelection`: The `Selection` previously defined.
    -   `CustomCrossover`: The `Crossover` previously defined.
-   `CustomMutationPolicy`: The `MutationPolicy` previously defined.
    -   `CustomMutation`: The `Mutation` previously defined.
-   `CustomElitismPolicy`: The `ElitismPolicy` previously defined.
-   `CustomNewbornPolicy`: The `NewbornPolicy` previously defined.
-   `ChessGAInitializer`: The `Initializer` previously defined.
    -   `ChessFeasibilityFunction`: The `FeasibilityFunction` previously defined.
    -   `ChessObjectiveFunction`: The `ObjectiveFunction` previously defined.

## Result Example
For:

-   `numberOfQueens = 8`
-   `populationSize = 100`
-   `iterations = 20000`
-   `Pmut = 0.02`
-   `crossed = 80~40`
-   `elitist = 50~10`
-   `newborns = 10~50`
````
Generation = 0
/
|Feasibility= 2.0000
|ObjectiveFunction= 7.0000
|Fitness= 207.0000
\

##############################
## 2055 GENERATIONS OMITTED ##
##############################

Generation = 2056
/
|Feasibility= 0.0000
|ObjectiveFunction= 1.0000
|Fitness= 1.0000
\

Generation = 2057
/
|Feasibility= 0.0000
|ObjectiveFunction= 0.0000
|Fitness= 0.0000
\
 _  _  _  _  _  Q  _  _ 
 _  _  _  Q  _  _  _  _ 
 Q  _  _  _  _  _  _  _ 
 _  _  _  _  Q  _  _  _ 
 _  _  _  _  _  _  _  Q 
 _  Q  _  _  _  _  _  _ 
 _  _  _  _  _  _  Q  _ 
 _  _  Q  _  _  _  _  _ 
````
## Comment
Please note that this example is far from utilizing the full capabilities of the Genetic Algorithm library.
It also represents an unusual approach for solving the N-Queens problem (using a `Genome` with N/2 `Chromosome`s, 
2 `Gen`s per `Chromosome`, `CustomBase`s to store integers, random selections, and no crossover). 

Despite its unconventional nature, it still works!

This example serves to illustrate that you can define a completely customized Genetic Algorithm that can incorporate 
daemon actions, directed convergence strategies, intelligent mutations, complex policies, and retrieve statistics from
the optimization process. We designed the library classes to be flexible enough to enable users to experiment 
(though users are responsible for their own code! :relaxed:) and explore various possibilities.
