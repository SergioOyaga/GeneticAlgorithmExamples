# CustomGA
Solution of the NQueen optimization problem implementing all the interfaces needed to perform a GA following the
OptimizationLib GA package guidelines. For a problem as simple as this one, it is not necessary to encapsulate the info 
in deeper levels than the genome itself. However, this example aims to illustrate how the developer can create his/her
own classes to fit a specific problem requirement.

## In this folder:
We find 17 different classes that define the problem structures that we decided to create (Implementing their
corresponding OptimizationLib.ga interfaces).
1. [CustomGeneticAlgorithm](#customgeneticalgorithm): Implements GeneticAlgorithm.
2. [ChessGAInitializer](#chessgainitializer): Extends GAInitializer.
3. [ChessFeasibilityFunction](#chessfeasibilityfunction):Implements FeasibilityFunction.
4. [ChessObjectiveFunction](#chessobjectivefunction): Implements ObjectiveFunction.
5. [CustomGenome](#customgenome): Implements Genome.
6. [CustomChromosome](#customchromosome): Implements Chromosome.
7. [CustomGen](#customgen): Implements Gen.
8. [CustomBase](#custombase): Implements GeneticInformationContainer.
9. [CustomMutationPolicy](#custommutationpolicy): Implements MutationPolicy.
10. [CustomMutation](#custommutation): Implements Mutation.
11. [CustomCrossoverPolicy](#customcrossoverpolicy): Implements CrossoverPolicy.
12. [CustomCrossover](#customcrossover): Implements Crossover.
13. [CustomSelection](#customselection): Implements Selection.
14. [CustomStoppingPolicy](#customstoppingpolicy): Implements StoppingCriteriaPolicy.
15. [CustomElitismPolicy](#customelitismpolicy): Implements ElitismPolicy.
16. [CustomNewbornPolicy](#customnewbornpolicy): Implements NewbornPolicy.
17. [RunNQueenOptimization](#runnqueenoptimization): The main class for instantiation and optimization.

### [CustomGeneticAlgorithm](https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/java/org/soyaga/examples/NQueenProblem/CustomGA/CustomGeneticAlgorithm.java):
This class implements GeneticAlgorithm, which by extension makes it an Optimizer instance. In other words, this class 
can be optimized and its results can be gathered.
````code
public void optimize(){...}
public Object getResult(){...}
````
The <i>optimize</i> function is implemented in a way that the generation number is passed as VarArg to most the actions
executed by the CustomGeneticAlgorithm parts (CrossoverPolicy, MutationPolicy...). The StoppingCriteria also receives the
last best Fitness solution. The <i>getResults</i> function returns a String that will be [printed in the console](#result-example)
with the best solution found and the history of the best individual performance.

### [ChessGAInitializer](https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/java/org/soyaga/examples/NQueenProblem/CustomGA/ChessGAInitializer.java):
This class initializes a new individual from scratch.
It uses CustomGenome, CustomChromosome, CustomGen and CustomBase to store the information of a randomly 
initialized individual.
````code
public Individual initializeIndividual()
````
In this scenario, all information is stored in the CustomBase. N/2 Chromosomes are created. Each Chromosome contains 2 
CustomGenes (N Genes in total that represent where queens are placed). And each CustomGen contains 2 CustomBases. 
These bases try to represent the DNA bases, but in our case, each one represents one number, the colum or the row numbers. 
These 2 bases together represent a coordinate where the Queen is placed.  

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

### [ChessFeasibilityFunction](https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/java/org/soyaga/examples/NQueenProblem/CustomGA/ChessFeasibilityFunction.java):
This function evaluates the feasibility of a solution.
For the N-Queen problem, it ensures that all rows and columns are present in the solution.
The function returns a double indicating the number of missing rows and columns in the solution.


### [ChessObjectiveFunction](https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/java/org/soyaga/examples/NQueenProblem/CustomGA/ChessObjectiveFunction.java):
This function evaluates the objective function of a solution.
For the N-Queen problem in this map design, it counts the number of diagonal collisions (confronted Queens).
The function returns a double indicating the number of diagonal collisions between queens.

### [CustomGenome](https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/java/org/soyaga/examples/NQueenProblem/CustomGA/CustomGenome.java):
A Genome that contains the genetic information in a HashSet&lt;CustomChromosome&gt; . It is composed of N/2 
CustomChromosomes.

### [CustomChromosome](https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/java/org/soyaga/examples/NQueenProblem/CustomGA/CustomChromosome.java):
A Chromosome that contains the genetic information in a HashSet&lt;CustomGen&gt; . It contains 2 CustomGenes.

### [CustomGen](https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/java/org/soyaga/examples/NQueenProblem/CustomGA/CustomGen.java):
A Gen that contains the genetic information contained in two CustomBase variables:
````code
    private CustomBase row;
    private CustomBase col;
````

### [CustomBase](https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/java/org/soyaga/examples/NQueenProblem/CustomGA/CustomBase.java):
A GeneticInformationContainer that contains an Integer that is interpreted as row/col number by the CustomGen.
````code
    Integer baseVal;
````

### [CustomMutationPolicy](https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/java/org/soyaga/examples/NQueenProblem/CustomGA/CustomMutationPolicy.java):
A MutationPolicy that applies mutations to the CustomBase objects. Columns are mutated during even iterations,
and rows are mutated during odd iterations.

### [CustomMutation](https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/java/org/soyaga/examples/NQueenProblem/CustomGA/CustomMutation.java):
A Mutation that takes a CustomBase object and randomly changes its value between 0 and N-1.

### [CustomCrossoverPolicy](https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/java/org/soyaga/examples/NQueenProblem/CustomGA/CustomCrossoverPolicy.java):
A CrossoverPolicy that generates a varying number of offspring individuals based on the parity of the iteration.

### [CustomCrossover](https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/java/org/soyaga/examples/NQueenProblem/CustomGA/CustomCrossover.java):
A Crossover that, despite receiving the iteration number in the VarArgs, only executes a HeuristicCrossover.

### [CustomSelection](https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/java/org/soyaga/examples/NQueenProblem/CustomGA/CustomSelection.java):
A Selection that, despite receiving the iteration number in the VarArgs, only executes a TournamentSelection.

### [CustomStoppingPolicy](https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/java/org/soyaga/examples/NQueenProblem/CustomGA/CustomStoppingPolicy.java):
A StoppingCriteriaPolicy that halts the process either upon reaching a maximum number of iterations or upon 
discovering an optimal solution.

### [CustomElitismPolicy](https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/java/org/soyaga/examples/NQueenProblem/CustomGA/CustomElitismPolicy.java):
An ElitismPolicy that chooses a varying number of individuals based on the parity of the iteration.

### [CustomNewbornPolicy](https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/java/org/soyaga/examples/NQueenProblem/CustomGA/CustomNewbornPolicy.java):
A NewbornPolicy that creates a varying number of individuals based on the parity of the iteration.

### [RunNQueenOptimization](https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/java/org/soyaga/examples/NQueenProblem/CustomGA/RunNQueenOptimization.java):
This is the main class where the program execution begins.
It involves instantiating the ChessGA object (previously defined), optimizing it, and retrieving the results.

The specific components for ChessGA include:
- CustomStoppingPolicy: The StoppingCriteria previously defined.
- CustomCrossoverPolicy: The CrossoverPolicy previously defined.
  - CustomSelection: The Selection previously defined.
  - CustomCrossover: The Crossover previously defined.
- CustomMutationPolicy: The MutationPolicy previously defined.
  - CustomMutation: The Mutation previously defined.
- CustomElitismPolicy: The ElitismPolicy previously defined.
- CustomNewbornPolicy: The NewbornPolicy previously defined.
- ChessGAInitializer: The Initializer previously defined.
  - ChessFeasibilityFunction: The FeasibilityFunction previously defined.
  - ChessObjectiveFunction: The ObjectiveFunction previously defined.


## Result example:
For:
- numberOfQueens = 8
- populationSize = 100
- iterations = 20000
- Pmut = 0.02
- crossed = 80~40
- elitist = 50~10
- newborns = 10~50
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

## Comment:
Please note that this example is far from utilizing the capabilities of the Genetic Algorithm library optimally.
It also represents a strange approach for solving the N Queen problem (using a Genome with N/2 Chromosomes, 
2 Genes per Chromosome, Bases to store an Integer, random selections, and no crossover). 
Despite its unconventional nature, it still WORKS!

This example serves to illustrate that you can define a completely customized Genetic Algorithm that can incorporate 
daemon actions, directed convergence strategies, intelligent mutations, complex policies, retrieve statistics from
the optimization process, etc. We designed the library classes to be flexible enough to enable users to experiment 
(though with their own responsibility :relaxed:) and explore various possibilities.