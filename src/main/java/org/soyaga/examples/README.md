# OptimizationLib GA Examples
This folder contains various optimization problems solved using the OptimizationLib framework. 
These examples serve as templates for readers who wish to understand and develop their own Genetic Algorithms (GAs)
using the OptimizationLib framework. By following the structures used in these examples, readers can customize and
fine-tune their GAs to address specific problems.

| n | Package                                                                                                                                                                  | Difficulty [1→5] | Comment                                                                |
|---|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-----------------------|------------------------------------------------------------------------|
| 1 | [NQueen ArrayLists](https://github.com/SergioOyaga/GeneticAlgorithmExamples/tree/master/src/main/java/org/soyaga/examples/NQueenProblem/SimpleGAs/ArrayLists)            | 1                     | NQueen using ArrayLists as Genetic Information Containers.              |
| 2 | [NQueen HashSets](https://github.com/SergioOyaga/GeneticAlgorithmExamples/tree/master/src/main/java/org/soyaga/examples/NQueenProblem/SimpleGAs/HashSets)                | 2                     | NQueen using HashSets as Genetic Information Containers.                |
| 3 | [NQueen HashMaps](https://github.com/SergioOyaga/GeneticAlgorithmExamples/tree/master/src/main/java/org/soyaga/examples/NQueenProblem/SimpleGAs/HashMaps)                | 2                     | NQueen using HashMaps as Genetic Information Containers.                |
| 4 | [NQueen AdHocGA](https://github.com/SergioOyaga/GeneticAlgorithmExamples/tree/master/src/main/java/org/soyaga/examples/NQueenProblem/AdHocGA)                            | 2                     | NQueen truncating the depth of the genome and retrieving statistics.    |
| 5 | [NQueen CustomGA](https://github.com/SergioOyaga/GeneticAlgorithmExamples/tree/master/src/main/java/org/soyaga/examples/NQueenProblem/CustomGA)                          | 3                     | NQueen implementing most of the GA base classes in unnecessary ways.   |
| 6 | [ImageMaker SimplePolyImageMaker](https://github.com/SergioOyaga/GeneticAlgorithmExamples/tree/master/src/main/java/org/soyaga/examples/ImageMaker/SimplePolyImageMaker) | 4                     | ImageMaker's first approach, using polygons.                           |
| 7 | [ImageMaker CudaPolyImageMaker](https://github.com/SergioOyaga/GeneticAlgorithmExamples/tree/master/src/main/java/org/soyaga/examples/ImageMaker/CudaPolyImageMaker)     | 5                     | ImageMaker that computes color differences for each pixel on the GPU.   |
| 8 | [ImageMaker ShapeImageMaker](https://github.com/SergioOyaga/GeneticAlgorithmExamples/tree/master/src/main/java/org/soyaga/examples/ImageMaker/ShapeImageMaker)           | 5                     | ImageMaker's general approach using the Shape interface.               |


The difficulty level ranges from the easiest (1) to the most challenging (5) problems in the repository. These are classical Genetic Algorithm (GA) problems. We recommend that readers start with the easier problems, both conceptually and in terms of implementation.

## In This Folder

This folder contains two different problems solved using the Genetic Algorithm (GA) from the OptimizationLib:

1. [NQueen Problem](https://github.com/SergioOyaga/GeneticAlgorithmExamples/tree/master/src/main/java/org/soyaga/examples/NQueenProblem):
   This problem involves placing N queens on an NxN chessboard such that no queen threatens another.

2. [ImageMaker](https://github.com/SergioOyaga/GeneticAlgorithmExamples/tree/master/src/main/java/org/soyaga/examples/ImageMaker):
   This problem entails recreating an image using only shapes, primarily polygons.

## Comment

The examples in these folders illustrate the power of GAs and the simplicity of use and implementation provided by the GA library in the OptimizationLib framework. These examples can be used as templates to adapt for your specific problems.

