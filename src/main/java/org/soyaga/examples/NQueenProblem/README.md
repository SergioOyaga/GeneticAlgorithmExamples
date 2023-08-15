# N Queen Problem
The n queens puzzle is the problem of placing n chess queens on an N×N chessboard so that no two queens threaten
each other; thus, a solution requires that no two queens share the same row, column, or diagonals.

<table>
  <tr>
    <td> <b>Solution for 8-Queens </b></td>
    <td> <b>Solution for 32-Queens </b> </td>
  </tr>
  <tr>
    <td> <img src="https://github.com/SergioOyaga/N-Queen_Problem/blob/master/out/image/8QueenSolution.png"  title="Solution for 8-Queens" alt="Solution for 8-Queens" width="400" height="400" /></td>
    <td> <img src="https://github.com/SergioOyaga/N-Queen_Problem/blob/master/out/image/32QueenSolution.png"  title="Solution for 32-Queens" alt="Solution for 32-Queens" width="400" height="400" /></td>
  </tr>
</table>

There is no known formula for the exact number of solutions when placing n queens on an N × N board.
The 27×27 board is the highest-order board that has been completely enumerated:

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

As you can observe, the number of solutions increases as we raise the number of queens. 
However, simultaneously, our search space expands significantly.
This makes inviable a Linear approach for solving the N-Queen problem.


For more information about the problem, refer 
[Here](https://en.wikipedia.org/wiki/Eight_queens_puzzle#Counting_solutions_for_other_sizes_n)

## In This Folder:
We find 3 different design approaches for solving the N-Queen optimization problem using the Genetic Algorithm (GA) 
from the OptimizationLib.
1. [SimpleGA](https://github.com/SergioOyaga/GeneticAlgorithmExamples/tree/master/src/main/java/org/soyaga/examples/NQueenProblem/SimpleGAs):
   Three different ways of solving the problem using built-in structures. These implementations aim to help developers 
become familiar with the GA package of the OptimizationLib framework.
2. [AdHocGA](https://github.com/SergioOyaga/GeneticAlgorithmExamples/tree/master/src/main/java/org/soyaga/examples/NQueenProblem/AdHocGA):
   Solving the problem by adjusting the GeneticInformationContainers to minimal structures. 
   Additionally, we introduce the StatsRetrievalPolicy here, which allows developers to gather information from the GA 
   during execution.
3. [CustomGA](https://github.com/SergioOyaga/GeneticAlgorithmExamples/tree/master/src/main/java/org/soyaga/examples/NQueenProblem/CustomGA):
   In contrast to AdHoc, a fully customized solver performs the optimization. It implements (in dummy ways) most of the 
   capabilities of the GA package, showing how the developer can customize and fine-tune a GA.

## Comment:
The examples in these folders are meant to illustrate the power of GAs and the simplicity of use/implementation 
that the GA library in the OptimizationLib framework provides. The problem is solved using 5 different configurations; 
some configurations are more suitable for this problem than others. Just remember that these examples can serve as 
templates that you can adapt for your specific problems.
