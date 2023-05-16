# Image Maker
The recreation of an image can be approached in different ways. In these examples we want to recreate the images using 
Shapes. Polygons are the ones more used in the literature, but we also provide a generalized Shape approach.  

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

As you can see in our approach you can differentiate two phases:
1. Exploratory convergence: the first par of the optimization, where new shapes are constantly included trying to 
   improve the image representation.
2. Adjustment: the second phase of the optimization focuses on improving those shapes that better fits, and removing the 
   ones that give little improvement.

You can see this two phases in the abrupt jump in the objective value. This jump is due to the consideration of the 
number of shapes in the image as something negative. In addition to this image simplification, the reduction in the 
number of shapes also reduces the evaluation time of the population (from 500ms to 250ms), something desirable.  

## In this folder:
We find 3 different design approached for solving the image maker optimization problem using the Genetic Algorithm (GA)
from the OptimizationLib.
1. [SimplePolyImageMaker](https://github.com/SergioOyaga/GeneticAlgorithmExamples/tree/master/src/main/java/org/soyaga/examples/ImageMaker/SimplePolyImageMaker):
Initial approach to solve the image replication optimization problem with polygons of a fixed number of vertexes.
2. [CudaPolyImageMaker](https://github.com/SergioOyaga/GeneticAlgorithmExamples/tree/master/src/main/java/org/soyaga/examples/ImageMaker/CudaPolyImageMaker):
Same problem approach as the SimplePolyImageMaker, but the color distance of each pixel is computed in the GPU using CUDA.
3. [ShapeImageMaker](https://github.com/SergioOyaga/GeneticAlgorithmExamples/tree/master/src/main/java/org/soyaga/examples/ImageMaker/ShapeImageMaker):
More general approach to the image recreation using Shapes. We mix different shapes and polygons to recreate the image.

## Comment:
The examples in these folders are just to illustrate the power of GAs, and the simplicity of use/implementation
that give us the ga library in the OptimizationLib framework. Just remember they are examples that you can 
use as template to adapt for your specific problems. 