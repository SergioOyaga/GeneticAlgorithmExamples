# Image Maker

The field of image recreation involves recreating images using various approaches. In this project, we focus on using shapes to recreate images. While polygons are commonly used in the literature, we present a more generalized approach using various shapes.

<table>
  <tr>
    <td> <b>DNA Solution using ShapeImageMaker</b></td>
    <td> <b>DNA Reference </b> </td>
  </tr>
  <tr>
    <td> <img src="https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/out/ImageMaker/dna.gif"  title="Solution for the DNA" alt="Solution for the DNA" width="300" height="300" /></td>
    <td> <img src="https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/resources/ImageMaker/dna.png"  title="Reference DNA" alt="Reference DNA" width="300" height="300" /></td>
  </tr>
  <tr>
    <td colspan="2"> <img src="https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/out/ImageMaker/graphs.png"  title="Evolution" alt="Evolution" width="600" height="500" /></td>
  </tr>
</table>

Our approach involves two distinct phases:

1. Exploratory Convergence: This initial phase of optimization involves constantly incorporating new shapes to enhance the image representation.
2. Adjustment: The second phase of optimization focuses on refining shapes that best fit the image while removing those that contribute little improvement.

These two phases become evident in the sudden increase in the objective value. This increase is attributed to treating the number of shapes in the image as a negative factor. Besides simplifying the image, reducing the number of shapes also leads to quicker evaluation times for the population (from 500ms to 250ms), which is a desirable outcome.

## In this folder:

You will find three distinct design approaches for solving the image replication optimization problem using the Genetic Algorithm (GA) from the OptimizationLib:

1. [SimplePolyImageMaker](https://github.com/SergioOyaga/GeneticAlgorithmExamples/tree/master/src/main/java/org/soyaga/examples/ImageMaker/SimplePolyImageMaker):
   This is the initial approach to solve the image replication optimization problem using polygons with a fixed number of vertices.
2. [CudaPolyImageMaker](https://github.com/SergioOyaga/GeneticAlgorithmExamples/tree/master/src/main/java/org/soyaga/examples/ImageMaker/CudaPolyImageMaker):
   Similar to the SimplePolyImageMaker, this approach tackles the same problem. However, it leverages GPU-based CUDA processing to compute the color distance of each pixel.
3. [ShapeImageMaker](https://github.com/SergioOyaga/GeneticAlgorithmExamples/tree/master/src/main/java/org/soyaga/examples/ImageMaker/ShapeImageMaker):
   This approach offers a more generalized method for image recreation. It combines various shapes and polygons to achieve image replication.

## Comment:

The examples within these folders showcase the capabilities of Genetic Algorithms (GAs) and highlight the user-friendly and straightforward implementation facilitated by the GA library within the OptimizationLib framework. These examples serve as templates, illustrating how to adapt the concepts for your specific problems. Explore the examples and adapt them to your own needs.
