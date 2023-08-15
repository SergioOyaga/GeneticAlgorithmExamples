# OptimizationLib GA Examples
In this folder, we can find different optimization problems solved using the OptimizationLib framework. 
These examples serve as templates for readers who wish to understand and develop their own Genetic Algorithms (GAs)
using the OptimizationLib framework. By following the structures used in these examples, readers can customize and
fine-tune their GAs to address specific problems.

| n | Package                                                                                                                                                                  | Difficulty [1&rarr;5] | Comment                                                                |
|---|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-----------------------|------------------------------------------------------------------------|
| 1 | [NQueen ArrayLists](https://github.com/SergioOyaga/GeneticAlgorithmExamples/tree/master/src/main/java/org/soyaga/examples/NQueenProblem/SimpleGAs/ArrayLists)            | 1                     | NQueen using ArrayLists as GeneticInformationContainers.               |
| 2 | [NQueen HashSets](https://github.com/SergioOyaga/GeneticAlgorithmExamples/tree/master/src/main/java/org/soyaga/examples/NQueenProblem/SimpleGAs/HashSets)                | 2                     | NQueen using HashSets as GeneticInformationContainers.                 |
| 3 | [NQueen HashMaps](https://github.com/SergioOyaga/GeneticAlgorithmExamples/tree/master/src/main/java/org/soyaga/examples/NQueenProblem/SimpleGAs/HashMaps)                | 2                     | NQueen using HashMaps as GeneticInformationContainers.                 |
| 4 | [NQueen AdHocGA](https://github.com/SergioOyaga/GeneticAlgorithmExamples/tree/master/src/main/java/org/soyaga/examples/NQueenProblem/AdHocGA)                            | 2                     | NQueen truncating the depth of the genome and retrieving stats.        |
| 5 | [NQueen CustomGA](https://github.com/SergioOyaga/GeneticAlgorithmExamples/tree/master/src/main/java/org/soyaga/examples/NQueenProblem/CustomGA)                          | 3                     | NQueen implementing (in unnecessary ways) most of the GA base classes. |
| 6 | [ImageMaker SimplePolyImageMaker](https://github.com/SergioOyaga/GeneticAlgorithmExamples/tree/master/src/main/java/org/soyaga/examples/ImageMaker/SimplePolyImageMaker) | 4                     | ImageMaker first approach, using polygons.                             |
| 7 | [ImageMaker CudaPolyImageMaker](https://github.com/SergioOyaga/GeneticAlgorithmExamples/tree/master/src/main/java/org/soyaga/examples/ImageMaker/CudaPolyImageMaker)     | 5                     | ImageMaker that computes color differences for each pixel in the GPU.  |
| 8 | [ImageMaker ShapeImageMaker](https://github.com/SergioOyaga/GeneticAlgorithmExamples/tree/master/src/main/java/org/soyaga/examples/ImageMaker/ShapeImageMaker)           | 5                     | ImageMaker general approach using the Shape interface.                 |

The difficulty level indicates the range from the easiest (1) to the most challenging (5) problems in the repository.
These are classical GA problems. We recommend that readers start with the easier ones, both conceptually and in 
terms of implementation.

## In This Folder:
We find 2 different problems solved using the Genetic Algorithm (GA) from the OptimizationLib.
1. [NQueen Problem](https://github.com/SergioOyaga/GeneticAlgorithmExamples/tree/master/src/main/java/org/soyaga/examples/NQueenProblem):
   This problem involves placing N queens on an NxN chessboard such that no queen threatens another queen.
2. [ImageMaker](https://github.com/SergioOyaga/GeneticAlgorithmExamples/tree/master/src/main/java/org/soyaga/examples/ImageMaker):
   This problem entails recreating an image using only shapes, primarily polygons.

## Comment:
The examples in these folders are meant to illustrate the power of GAs and the simplicity of use/implementation 
provided by the GA library in the OptimizationLib framework. Keep in mind that these are examples you can use as 
templates to adapt for your specific problems.
