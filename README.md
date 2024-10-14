# OptimizationLib GA Examples
This repository contains various optimization problems solved using the OptimizationLib framework. The examples serve as templates for readers, enabling them to understand and develop their own Genetic Algorithms (GAs) by following the structures used in these examples.


| n | Package                                                                                                                                                                  | Difficulty [1&rarr;5] | Comment                                                                  |
|---|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-----------------------|--------------------------------------------------------------------------|
| 1 | NQueen [ArrayLists](https://github.com/SergioOyaga/GeneticAlgorithmExamples/tree/master/src/main/java/org/soyaga/examples/NQueenProblem/SimpleGAs/ArrayLists)            | 1                     | NQueen using ArrayList as Genetic Information Containers.                |
| 2 | NQueen [HashSets](https://github.com/SergioOyaga/GeneticAlgorithmExamples/tree/master/src/main/java/org/soyaga/examples/NQueenProblem/SimpleGAs/HashSets)                | 2                     | NQueen using HashSets as Genetic Information Containers.                 |
| 3 | NQueen [HashMaps](https://github.com/SergioOyaga/GeneticAlgorithmExamples/tree/master/src/main/java/org/soyaga/examples/NQueenProblem/SimpleGAs/HashMaps)                | 2                     | NQueen using HashMaps as Genetic Information Containers.                 |
| 4 | NQueen [AdHocGA](https://github.com/SergioOyaga/GeneticAlgorithmExamples/tree/master/src/main/java/org/soyaga/examples/NQueenProblem/AdHocGA)                            | 2                     | NQueen truncating the depth of the genome.                               |
| 5 | NQueen [CustomGA](https://github.com/SergioOyaga/GeneticAlgorithmExamples/tree/master/src/main/java/org/soyaga/examples/NQueenProblem/CustomGA)                          | 3                     | NQueen implementing all the GA base classes in unnecessary ways.         |
| 6 | ImageMaker [SimplePolyImageMaker](https://github.com/SergioOyaga/GeneticAlgorithmExamples/tree/master/src/main/java/org/soyaga/examples/ImageMaker/SimplePolyImageMaker) | 4                     | ImageMaker's first approach, using polygons.                             |
| 7 | ImageMaker [CudaPolyImageMaker](https://github.com/SergioOyaga/GeneticAlgorithmExamples/tree/master/src/main/java/org/soyaga/examples/ImageMaker/CudaPolyImageMaker)     | 5                     | ImageMaker that computes the color difference for each pixel on the GPU. |
| 8 | ImageMaker [ShapeImageMaker](https://github.com/SergioOyaga/GeneticAlgorithmExamples/tree/master/src/main/java/org/soyaga/examples/ImageMaker/ShapeImageMaker)           | 5                     | ImageMaker's general approach using the Shape interface.                 |

The difficulty level ranges from a minimum of (1) to a maximum of (5) in this repository. These are classical GA problems. We recommend that readers start with the easier ones, both conceptually and in implementation.

## Comment:
The examples in this repository illustrate the power of Genetic Algorithms (GAs) and the simplicity of use and implementation provided by the GA library in the OptimizationLib framework. Remember, these examples serve as templates that you can adapt for your specific optimization problems.
