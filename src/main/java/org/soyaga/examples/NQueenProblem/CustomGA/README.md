# CustomGA
Solution of the NQueen optimization problem using Sets as base structure to store information.

## In this folder:
We find 17 different classes that defines the problem structures that we decided to create (Implementing their 
corresponding OptimizationLib.ga interfaces).
1. [CustomGeneticAlgorithm](#customgeneticalgorithm): Implements GeneticAlgorithm.
2. [ChessGAInitializer](#chessgainitializer): Extends GAInitializer.
3. [ChessFeasibilityFunction](#chessfeasibilityfunction):Extends FeasibilityFunction.
4. [ChessObjectiveFunction](#chessobjectivefunction): Extends ObjectiveFunction.
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
17. [RunNQueenOptimization](#runnqueenoptimization): This is the main class. Here we instantiate our ChessGA Object with all his 
components.

### [CustomGeneticAlgorithm](https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/java/org/soyaga/examples/NQueenProblem/CustomGA/CustomGeneticAlgorithm.java):
This class implements GeneticAlgorithm, which by extension makes it an Optimizer instance. In other words this class 
can be optimized and its results can be gathered.
````code
public void optimize(){...}
public Object getResult(){...}
````
The <i>optimize</i> function is implemented in a way that the generation number is passed as VarArg to most the actions
executed by the CustomGeneticAlgorithm parts (CrossoverPolicy, MutationPolicy...). The StoppingCriteria also receives the
last best solution Fitness. The <i>getResults</i> function returns a String that will be [printed in the console](#result-example)
with the best solution found and the history of the best individual performance.

### [ChessGAInitializer](https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/java/org/soyaga/examples/NQueenProblem/CustomGA/ChessGAInitializer.java):
Initializes a new individual from zero. In our case, uses CustomGenome, CustomChromosome, CustomGen  and CustomBase to 
store the information of a randomly initialized individual.
````code
public Individual initializeIndividual()
````
In this case, all information is stored in the CustomBase. N/2 Chromosomes are created. Each Chromosome contains 2 
CustomGenes (N Genes in total that represents where queens are placed). And each CustomGen contains 2 CustomBases. 
This bases try to represent the DNA bases, but in our case each one represent one number, the colum or the row numbers. 
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
This function evaluates the feasibility of a solution. In the case of the NQueen problem it may or may not have sense 
to talk about feasibility. We decided to give it the mission of ensuring that The N Queens are placed in different rows 
and columns


### [ChessObjectiveFunction](https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/java/org/soyaga/examples/NQueenProblem/CustomGA/ChessObjectiveFunction.java):
This function evaluates the objective function of a solution. In the case of the NQueen problem in this design, 
we have to only count the number of collisions (confronted Queens) looking in the diagonals.

This function return a Double containing the number diagonal collisions between queens.

### [CustomGenome](https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/java/org/soyaga/examples/NQueenProblem/CustomGA/CustomGenome.java):
A Genome that contains the genetic information contained in a HashSet &lt;CustomChromosome&gt;. It is composed of N/2 
CustomChromosomes.

### [CustomChromosome](https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/java/org/soyaga/examples/NQueenProblem/CustomGA/CustomChromosome.java):
A Chromosome that contains the genetic information contained in a HashSet &lt;CustomGen&gt;. It contains 2 CustomGenes.

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
A MutationPolicy that applies mutations to the CustomBase Objects. In even iterations columns are mutated and in odd 
iterations rows.

### [CustomMutation](https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/java/org/soyaga/examples/NQueenProblem/CustomGA/CustomMutation.java):
A Mutation that takes a CustomBase object and changes randomly its value between 0 and N-1.

### [CustomCrossoverPolicy](https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/java/org/soyaga/examples/NQueenProblem/CustomGA/CustomCrossoverPolicy.java):
A CrossoverPolicy that creates different number of crossed individuals depending on the iteration parity. 

### [CustomCrossover](https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/java/org/soyaga/examples/NQueenProblem/CustomGA/CustomCrossover.java):
A Crossover that although receives the iteration number in the VarArgs, it just performs a HeuristicCrossover.

### [CustomSelection](https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/java/org/soyaga/examples/NQueenProblem/CustomGA/CustomSelection.java):
A Selection that although receives the iteration number in the VarArgs, it just performs a TournamentSelection.

### [CustomStoppingPolicy](https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/java/org/soyaga/examples/NQueenProblem/CustomGA/CustomStoppingPolicy.java):
A StoppingCriteriaPolicy that stops if we have achieved the maximum number of iterations or if we have found an optimal 
solution.

### [CustomElitismPolicy](https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/java/org/soyaga/examples/NQueenProblem/CustomGA/CustomElitismPolicy.java):
A ElitismPolicy that selects different number of individuals depending on the iteration parity.

### [CustomNewbornPolicy](https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/java/org/soyaga/examples/NQueenProblem/CustomGA/CustomNewbornPolicy.java):
A NewbornPolicy that creates different number of new individuals depending on the iteration parity.

### [RunNQueenOptimization](https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/java/org/soyaga/examples/NQueenProblem/CustomGA/RunNQueenOptimization.java):
This is the main class. Is where the run starts. As simple as instantiate the ChessGA object (previously defined) filled
with its components, optimize it, and retrieve the results.

The specific components for the ChessGA are:
- CustomStoppingPolicy: The StoppingCriteria previously defined.
- CustomCrossoverPolicy: The CrossoverPolicy previously defined.
  - CustomSelection: The Selection previously defined.
  - CustomCrossover: The Crossover  previously defined.
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

#############################
## 2055 GENERATIONS OMITTED ##
#############################

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
Notice that this example is far from being a good usage of the capabilities of the Genetic algorithm library. It's also
a terrible approach to solve the N Queen problem (Genome with N/2 Chromosomes?? 2 Genes per Chromosome?? Bases to store 
an Integer?? Random selections?? No Crossover??). This seems like a weird implementation, but even so, IT WORKS!!!
This implementation is just to illustrate that we can define a totally custom GA that can implement daemon actions, 
directed convergence strategies, intelligent mutations, complex policies, retrieve stats from the optimization process
... We left the library classes open enough to allow the user play around (under his/her own responsibility :relaxed: ).

