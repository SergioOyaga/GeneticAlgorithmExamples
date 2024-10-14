# CudaPolyImageMaker

The CudaPolyImageMaker tackles the challenge of representing images using a collection of colored shapes. This technique, often demonstrated by recreating iconic artworks like the Mona Lisa, can be applied to virtually any image by deconstructing it into overlapping shapes. This project leverages the power of CUDA programming for image manipulation, showcasing the flexibility of the OptimizationLib framework. With minor adjustments, various technologies can be seamlessly integrated into the optimization process.

## Examples

| Image                     | Evolution                                                                                                                                                                                                            | Reference                                                                                                                                                                                                                 |
|---------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Galaxy (300x300)          | <img src="https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/out/ImageMaker/galaxy.gif"  title="Solution for the galaxy" alt="Solution for the Galaxy" width="300" height="300" />              | <img src="https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/resources/ImageMaker/galaxy.jpg"  title="Reference galaxy" alt="Reference galaxy" width="300" height="300" />                      |
| Big Landscape (1920x1280) | <img src="https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/out/ImageMaker/big_landscape.gif"  title="Solution for big landscape" alt="Solution for big landscape" width="300" height="200" /> | <img src="https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/resources/ImageMaker/big_landscape.png"  title="Reference big landscape" alt="Reference big landscape" width="300" height="200" /> |

## Project Structure

This folder contains the following:

- **[SimplePolyImageMaker](https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/java/org/soyaga/examples/ImageMaker/SimplePolyImageMaker):**  Directory containing class descriptions.
- **Modified Classes:**
   - **[PolyImageObjectiveFunction](#polyimageobjectivefunction):** Implements the `ObjectiveFunction` interface.
   - **[Kernel](#kernel):** Folder containing the CUDA kernel.
      - **[ColorDifKernel](#colordifkernel):** Class adhering to the NVRTC structure for kernel building.
      - **[JCudaColorDistance.cu](#jcudacolordistancecu):** C code for the NVRTC compiler.
   - **[RunPolyImageOptimization](#runpolyimageoptimization):** Main class, responsible for instantiating and configuring the `CustomGeneticAlgorithm` object.

### PolyImageObjectiveFunction

This function evaluates the effectiveness of a solution by calculating the L2 norm of the RGBA color differences between the generated image and the reference image.

**Output:** A Double value representing the average Euclidean color deviation per pixel between the generated and reference images.

**Implementation Details:**

- Pixel difference calculations are performed on the GPU using a [custom kernel](#colordifkernel).
- Pixel value summation is also executed on the GPU using the `JCublas2` class.


````java
// Move polyImage to device reserved cunk of memory
cuMemcpyHtoD(this.devicePolyImage, Pointer.to(polyImageArray),(long) this.width *this.height * Sizeof.INT);
// Perform pixel color difference in GPU. (result in the Pointer to GPU chunk: this.deviceInternalDifferenceVector)
this.colorDifKernel.runKernel( polyImageArray.length,this.deviceReferenceImage, this.devicePolyImage,this.deviceInternalDifferenceVector);
// Add vector values to gather the result using JCublas2.
JCublas2.cublasDasum(this.handle,this.width *this.height,this.deviceInternalDifferenceVector,1,Pointer.to(result));
````

### [Kernel](https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/java/org/soyaga/examples/ImageMaker/CudaPolyImageMaker/Kernel)

This folder contains the CUDA kernel class and function file.

#### [ColorDifKernel](https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/java/org/soyaga/examples/ImageMaker/CudaPolyImageMaker/Kernel/ColorDifKernel.java)

This class manages the information required to build a custom CUDA kernel function and provides the ability to launch the kernel with specified parameters.

#### [JCudaColorDistance.cu](https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/java/org/soyaga/examples/ImageMaker/CudaPolyImageMaker/Kernel/JCudaColorDistance.cu)

This C++-style program enables the use of NVRTC (NVIDIA Runtime Compilation library for C++). The program is loaded into a function within the `ColorDifKernel` class. 


   ```cpp
   extern "C"
    __global__ void computeColorDif(int length, int *rgb_ref, int *rgb_poly, double *result)
    {
    int i = blockIdx.x * blockDim.x + threadIdx.x;
    int color1 = rgb_ref[i];
    int color2 = rgb_poly[i];
    int color1b = color1 & 0xff;
    int color1g = (color1 & 0xff00) >> 8;
    int color1r = (color1 & 0xff0000) >> 16;
    int color1a = (color1 & 0xff000000) >> 24;
    int color2b = color2 & 0xff;
    int color2g = (color2 & 0xff00) >> 8;
    int color2r = (color2 & 0xff0000) >> 16;
    int color2a = (color2 & 0xff000000) >> 24;
    double dr = (double)color1r - color2r;
    double dg = (double)color1g - color2g;
    double db = (double)color1b - color2b;
    double da = (double)color1a - color2a;
    result[i] = sqrt(dr * dr + dg * dg + db * db + da * da)/length;
    };
   ```

### [RunPolyImageOptimization](https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/java/org/soyaga/examples/ImageMaker/CudaPolyImageMaker/RunPolyImageOptimization.java):
Similar to [SimplePolyImageMaker](https://github.com/SergioOyaga/GeneticAlgorithmExamples/blob/master/src/main/java/org/soyaga/examples/ImageMaker/SimplePolyImageMaker).
But we added the path to the cuda c++ style file:
````java
//Ej.: String with the path to the image we want to recreate.
String cudaFileNamePath = "path/to/JCudaColorDistance.cu";
````

## Comment:

It's important to note that this example, while functional, doesn't represent optimal usage of the JCuda framework. The performance gains are negligible due to the overhead of transferring images between the CPU and GPU. The time required for data transfer is comparable to the time it would take to compute the color distance directly on the CPU.

However, this example effectively demonstrates the potential for integrating the optimization process with external technologies like:

- GPU computing
- AI predictions
- Deep learning recommendations

While the combination of these technologies offers significant power and flexibility, it's crucial to remember that "with great power comes great responsibility." Careful consideration of performance implications and best practices is essential for successful implementation. 



