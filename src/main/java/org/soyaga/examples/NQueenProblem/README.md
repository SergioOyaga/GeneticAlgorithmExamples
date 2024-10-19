# N-Queens Problem

The N-Queens puzzle presents the challenge of positioning *N* chess queens on an *N×N* chessboard such that no two queens threaten each other. A valid solution requires that no two queens occupy the same row, column, or diagonal.

| Solution for 8-Queens                                                                                                                                                                     | Solution for 32-Queens                                                                                                                                                                       |
|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| <img src="https://github.com/SergioOyaga/N-Queen_Problem/blob/master/out/image/8QueenSolution.png"  title="Solution for 8-Queens" alt="Solution for 8-Queens" width="400" height="400" /> | <img src="https://github.com/SergioOyaga/N-Queen_Problem/blob/master/out/image/32QueenSolution.png"  title="Solution for 32-Queens" alt="Solution for 32-Queens" width="400" height="400" /> |

Currently, there is no known formula to determine the exact number of solutions for placing *N* queens on an *N × N* board. The largest board size (*N*) for which all possible solutions have been enumerated is 27×27. 


| n  | fundamental solutions | all solutions      | total combinations                                 |
|----|-----------------------|--------------------|----------------------------------------------------|
| 1  | 1                     | 1                  | 1                                                  |
| 2  | 0                     | 0                  | 6                                                  |
| 3  | 0                     | 0                  | 84                                                 |
| 4  | 1                     | 2                  | 1820                                               |
| 5  | 2                     | 10                 | 53130                                              |
| 6  | 1                     | 4                  | 1947792                                            |
| 7  | 6                     | 40                 | 85900584                                           |
| 8  | 12                    | 92                 | 4426165368                                         |
| 9  | 46                    | 352                | 260887834350                                       |
| 10 | 92                    | 724                | 17310309456440                                     |
| 11 | 341                   | 2680               | 1276749965026536                                   |
| 12 | 1787                  | 14200              | 103619293824707388                                 |
| 13 | 9233                  | 73712              | 9176358300744339432                                |
| 14 | 45752                 | 365596             | 880530516383349192480                              |
| 15 | 285053                | 2279184            | 91005567811177478095440                            |
| 16 | 1846955               | 14772512           | 10078751602022313874633200                         |
| 17 | 11977939              | 95815104           | 1190739044344491048895397910                       |
| 18 | 83263591              | 666090624          | 149482492334195165714038760136                     |
| 19 | 621012754             | 4968057848         | 19870867053543756004133247695400                   |
| 20 | 4878666808            | 39029188884        | 2788360983670896737872851072994080                 |
| 21 | 39333324973           | 314666222712       | 411887396336567398822620727355402190               |
| 22 | 336376244042          | 2691008701644      | 63887407766986865702182544710470036560             |
| 23 | 3029242658210         | 24233937684440     | 10381758958529585222885358558747563185920          |
| 24 | 28439272956934        | 227514171973736    | 1763783520005146433232953016554504214270600        |
| 25 | 275986683743434       | 2207893435808352   | 312690620414907617326451186807195415244296900      |
| 26 | 2789712466510289      | 22317699616364044  | 57746226578042013138408988185727715132037050952    |
| 27 | 29363495934315694     | 234907967154122528 | 11091107763254898773425731705373527055193637625824 |

As you can observe, the number of possible solutions increases as the board size (and the number of queens) grows. However, this also leads to a significant expansion of the search space, making linear approaches to solving the N-Queens problem computationally infeasible.

For more information about the problem, refer to the [Eight Queens Puzzle Wikipedia page](https://en.wikipedia.org/wiki/Eight_queens_puzzle#Counting_solutions_for_other_sizes_n).

## Solution Approaches

This folder contains three different design approaches for solving the N-Queens optimization problem using Genetic Algorithms (GA) from the OptimizationLib framework:

1. **[SimpleGA](https://github.com/SergioOyaga/GeneticAlgorithmExamples/tree/master/src/main/java/org/soyaga/examples/NQueenProblem/SimpleGAs):** Demonstrates three straightforward solutions using built-in structures. These implementations are ideal for developers new to the OptimizationLib GA package.

2. **[AdHocGA](https://github.com/SergioOyaga/GeneticAlgorithmExamples/tree/master/src/main/java/org/soyaga/examples/NQueenProblem/AdHocGA):**  Solves the problem by tailoring the `GeneticInformationContainers` to minimal structures. This approach also introduces the `StatsRetrievalPolicy`, allowing developers to gather runtime information from the GA.

3. **[CustomGA](https://github.com/SergioOyaga/GeneticAlgorithmExamples/tree/master/src/main/java/org/soyaga/examples/NQueenProblem/CustomGA):**  Presents a fully customized solver for the optimization problem. It provides simplified implementations of most GA package capabilities, showcasing how developers can customize and fine-tune a GA for their specific needs.

## Note on Examples

The examples in these folders highlight the power and flexibility of Genetic Algorithms and the ease of use offered by the OptimizationLib GA library. While the problem is solved using different configurations, remember that these examples serve as adaptable templates for your own optimization challenges. 

