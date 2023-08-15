# SimpleGA
Examples using the OptimizationLib built-in classes for the Genetic Algorithm (GA) optimization methodology.

While a complete Genetic Algorithm involves more than 16 different classes 
(see [CustomGA](https://github.com/SergioOyaga/GeneticAlgorithmExamples/tree/master/src/main/java/org/soyaga/examples/NQueenProblem/CustomGA)),
a minimal implementation with 4-5 classes (problem-dependent) can be used to design a GA capable of 
optimizing computationally challenging problems (P, NP, NP-Complete, and/or NP-Hard).

These implementations serve as an introduction for novice developers to the design of Genetic Algorithms 
using the OptimizationLib framework.


## In this folder:
We find 3 different implementations for solving the nQueen optimization problem.
1. [ArrayLists](https://github.com/SergioOyaga/GeneticAlgorithmExamples/tree/master/src/main/java/org/soyaga/examples/NQueenProblem/SimpleGAs/ArrayLists):
Genomes and Chromosomes contain the genetic information stored in ArrayLists.
2. [HashSets](https://github.com/SergioOyaga/GeneticAlgorithmExamples/tree/master/src/main/java/org/soyaga/examples/NQueenProblem/SimpleGAs/HashSets):
Genomes and Chromosomes contain the genetic information stored in HashSets.
3. [HashMaps](https://github.com/SergioOyaga/GeneticAlgorithmExamples/tree/master/src/main/java/org/soyaga/examples/NQueenProblem/SimpleGAs/HashMaps):
Genomes and Chromosomes contain the genetic information stored in HashMaps.

## Comment:
These three structures are the most frequently used for storing information. 
Most of the problems we aim to optimize can be encapsulated within these structures.

However, we do not mandate the use of these structures; you are free to use any Genome, Chromosome, Gen, or other 
GeneticInformationContainer that you define. 
For an example of custom-created GeneticInformationContainers, refer to 
[CustomGA](https://github.com/SergioOyaga/GeneticAlgorithmExamples/tree/master/src/main/java/org/soyaga/examples/NQueenProblem/CustomGA).
For instance, where Chromosomes in the Genome are replaced by a more suitable structure, an array of integers, see 
[AdHocGA](https://github.com/SergioOyaga/GeneticAlgorithmExamples/tree/master/src/main/java/org/soyaga/examples/NQueenProblem/AdHocGA).
