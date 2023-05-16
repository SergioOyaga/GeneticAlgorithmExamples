# OptimizationLib GA examples
In this repository we can find different optimization problems. The problems are solved using the OptimizationLib framework. 
The examples are templates for the reader. Who can understand and develop his/her own GAs using the 
OptimizationLib by following the structures used in these examples. 

| n | Package                                                                                                                                                                  | difficulty [1&rarr;5] | comment                                                                  |
|---|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-----------------------|--------------------------------------------------------------------------|
| 1 | NQueen [ArrayLists](https://github.com/SergioOyaga/GeneticAlgorithmExamples/tree/master/src/main/java/org/soyaga/examples/NQueenProblem/SimpleGAs/ArrayLists)            | 1                     | NQueen using ArrayList as GeneticInformationContainers.                  |
| 2 | NQueen [HashSets](https://github.com/SergioOyaga/GeneticAlgorithmExamples/tree/master/src/main/java/org/soyaga/examples/NQueenProblem/SimpleGAs/HashSets)                | 2                     | NQueen using HashSets as GeneticInformationContainers.                   |
| 3 | NQueen [HashMaps](https://github.com/SergioOyaga/GeneticAlgorithmExamples/tree/master/src/main/java/org/soyaga/examples/NQueenProblem/SimpleGAs/HashMaps)                | 2                     | NQueen using HashMaps as GeneticInformationContainers.                   |
| 4 | NQueen [AdHocGA](https://github.com/SergioOyaga/GeneticAlgorithmExamples/tree/master/src/main/java/org/soyaga/examples/NQueenProblem/AdHocGA)                            | 2                     | NQueen truncating the dept of the genome.                                |
| 5 | NQueen [CustomGA](https://github.com/SergioOyaga/GeneticAlgorithmExamples/tree/master/src/main/java/org/soyaga/examples/NQueenProblem/CustomGA)                          | 3                     | NQueen Implementing (in unnecessary ways) all the GA base classes.       |
| 6 | ImageMaker [SimplePolyImageMaker](https://github.com/SergioOyaga/GeneticAlgorithmExamples/tree/master/src/main/java/org/soyaga/examples/ImageMaker/SimplePolyImageMaker) | 4                     | ImageMaker first approach, using Polygons.                               |
| 7 | ImageMaker [CudaPolyImageMaker](https://github.com/SergioOyaga/GeneticAlgorithmExamples/tree/master/src/main/java/org/soyaga/examples/ImageMaker/CudaPolyImageMaker)     | 5                     | ImageMaker that computes in the GPU the color difference for each pixel. |
| 8 | ImageMaker [ShapeImageMaker](https://github.com/SergioOyaga/GeneticAlgorithmExamples/tree/master/src/main/java/org/soyaga/examples/ImageMaker/ShapeImageMaker)           | 5                     | ImageMaker general approach using the Shape interface.                   |

The difficulty level is a measure of the minimum (1) and maximum (5) difficulty level in the repo. These are classical GA 
problems. We recommend the reader to start with the easier ones, conceptually and in implementation.

## Comment:
The examples in this repository are just to illustrate the power of GAs, and the simplicity of use/implementation
that give us the ga library in the OptimizationLib framework. Just remember, they are examples that you can 
use as template to adapt for your specific optimization problems. 