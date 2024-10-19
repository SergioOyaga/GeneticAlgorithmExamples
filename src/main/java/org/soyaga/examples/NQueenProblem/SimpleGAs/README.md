# SimpleGA

This folder provides examples of using the OptimizationLib's built-in classes for implementing Genetic Algorithms (GA) for optimization.

While a complete GA implementation might involve over 16 different classes (see [CustomGA](https://github.com/SergioOyaga/GeneticAlgorithmExamples/tree/master/src/main/java/org/soyaga/examples/NQueenProblem/CustomGA)), a minimal implementation using 4-5 classes (depending on the problem) can effectively tackle computationally challenging problems (P, NP, NP-Complete, and NP-Hard).

These examples serve as a starting point for developers new to designing Genetic Algorithms using the OptimizationLib framework.

## Implementations

This folder contains three different implementations for solving the N-Queens optimization problem:

1. **[ArrayLists](https://github.com/SergioOyaga/GeneticAlgorithmExamples/tree/master/src/main/java/org/soyaga/examples/NQueenProblem/SimpleGAs/ArrayLists):** Genomes and Chromosomes store genetic information using `ArrayLists`.

2. **[HashSets](https://github.com/SergioOyaga/GeneticAlgorithmExamples/tree/master/src/main/java/org/soyaga/examples/NQueenProblem/SimpleGAs/HashSets):** Genomes and Chromosomes store genetic information using `HashSets`.

3. **[HashMaps](https://github.com/SergioOyaga/GeneticAlgorithmExamples/tree/master/src/main/java/org/soyaga/examples/NQueenProblem/SimpleGAs/HashMaps):** Genomes and Chromosomes store genetic information using `HashMaps`.

## Data Structures and Customization

These three data structures (`ArrayList`, `HashSet`, `HashMap`) are commonly used for storing genetic information in GA implementations. Most optimization problems can be effectively represented using these structures.

However, the OptimizationLib framework doesn't restrict you to these specific structures. You have the flexibility to define and utilize your own custom `Genome`, `Chromosome`, `Gen`, or other `GeneticInformationContainer` implementations.

For examples of custom `GeneticInformationContainers`:

- Refer to the [CustomGA](https://github.com/SergioOyaga/GeneticAlgorithmExamples/tree/master/src/main/java/org/soyaga/examples/NQueenProblem/CustomGA) implementation.
- The [AdHocGA](https://github.com/SergioOyaga/GeneticAlgorithmExamples/tree/master/src/main/java/org/soyaga/examples/NQueenProblem/AdHocGA) implementation demonstrates replacing Chromosomes within the Genome with a more suitable structure, an array of integers. 

