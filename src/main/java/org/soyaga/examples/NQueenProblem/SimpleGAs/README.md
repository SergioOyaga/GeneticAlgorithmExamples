# SimpleGA
Examples using the OptimizationLib build-in classes for the Genetic Algorithm (GA) optimization methodology.

Although a complete Genetic algorithm consist in at least 16 different classes (view [CustomGA](https://github.com/SergioOyaga/GeneticAlgorithmExamples/tree/master/src/main/java/org/soyaga/examples/NQueenProblem/CustomGA)),
with a minimal implementation of 4-5 classes (which are problem dependent) we can design a GA that allow us to "optimize" 
some computationally "hard" problems (P, NP, NP-Complete and/or NP-Hard).

## In this folder:
We find 3 different implementations for solving the nQueen optimization problem.
1. [ArrayLists](https://github.com/SergioOyaga/GeneticAlgorithmExamples/tree/master/src/main/java/org/soyaga/examples/NQueenProblem/SimpleGAs/ArrayLists):
Genome, and Chromosomes contains the genetic information stored in ArrayLists.
2. [HashSets](https://github.com/SergioOyaga/GeneticAlgorithmExamples/tree/master/src/main/java/org/soyaga/examples/NQueenProblem/SimpleGAs/HashSets):
Genome, and Chromosomes contains the genetic information stored in HashSets.
3. [HashMaps](https://github.com/SergioOyaga/GeneticAlgorithmExamples/tree/master/src/main/java/org/soyaga/examples/NQueenProblem/SimpleGAs/HashMaps):
Genome, and Chromosomes contains the genetic information stored in HashMaps.

## Comment:
This three structures are the three most frequently used to store information. Most of the problems we will want to 
optimize could be encapsulated in these structures.

However, we do not force the use of these structures, you can have whatever you want as Genome, Chromosome, Gen or any 
GeneticInformationContainer you define. See [CustomGA](https://github.com/SergioOyaga/GeneticAlgorithmExamples/tree/master/src/main/java/org/soyaga/examples/NQueenProblem/CustomGA)
for an example of custom created 
GeneticInformationContainers. See [AdHocGA](https://github.com/SergioOyaga/GeneticAlgorithmExamples/tree/master/src/main/java/org/soyaga/examples/NQueenProblem/AdHocGA)
for an example where Chromosomes on the Genome are replaced by a more suitable structure.