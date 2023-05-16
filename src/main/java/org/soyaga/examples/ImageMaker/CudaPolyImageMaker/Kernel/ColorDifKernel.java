package org.soyaga.examples.ImageMaker.CudaPolyImageMaker.Kernel;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import jcuda.Pointer;
import jcuda.driver.*;
import jcuda.nvrtc.JNvrtc;
import jcuda.nvrtc.nvrtcProgram;
import static jcuda.driver.JCudaDriver.*;
import static jcuda.nvrtc.JNvrtc.*;

/**
 * Class that contains the information of the CUDA kernel and perform tasks such as initialize tha kernel program, pass
 * the pointers and launch the kernel.
 */
public class ColorDifKernel {

    /**
     * Grid size in the X direction.
     */
    int gridSizeX=1;
    /**
     * Grid size in the Y direction.
     */
    int gridSizeY=1;
    /**
     * Grid size in the z direction.
     */
    int gridSizeZ=1;
    /**
     * Block size in the X direction.
     */
    int blockSizeX=1;
    /**
     * Block size in the Y direction.
     */
    int blockSizeY=1;
    /**
     * Block size in the Z direction.
     */
    int blockSizeZ=1;
    /**
     * Amount of dynamic shared memory that will be available to each thread block.
     */
    int sharedMemBytes=0;
    /**
     * cuLaunchKernel() can optionally be associated to a stream by passing a non-zero hStream argument.
     */
    CUstream hStream=null;
    /**
     * extra params for the launcher.
     */
    Pointer extra=null;
    /**
     * Function computed for the kernel.
     */
    CUfunction function;

    /**
     * This function sets the kernel and creates the function that is going to be executed in the GPU.
     * It reads a Cuda file "example.cu".
     * @param cudaFileNamePath : String with the path name of the cuda file Ej.: "C://your/path/example.cu".
     * @throws IOException
     */
    public ColorDifKernel(String cudaFileNamePath) throws IOException {
        this.function = computeKernelFunction(cudaFileNamePath);
    }

    /**
     * This function fills the Kernel with grid/block parameters to know threads and block executing those threads
     * @param gridSizeX Grid size in the X direction.
     * @param gridSizeY Grid size in the Y direction.
     * @param gridSizeZ Grid size in the Z direction.
     * @param blockSizeX Block size in the X direction.
     * @param blockSizeY Block size in the Y direction.
     * @param blockSizeZ Block size in the Z direction.
     * @param sharedMemBytes  Amount of dynamic shared memory that will be available to each thread block.
     * @param hStream cuLaunchKernel() can optionally be associated to a stream by passing a non-zero hStream argument.
     * @param extra
     */
    public void fillKernel(int gridSizeX, int gridSizeY, int gridSizeZ, int blockSizeX, int blockSizeY, int blockSizeZ,
                           int sharedMemBytes, CUstream hStream, Pointer extra) {
        this.gridSizeX = gridSizeX;
        this.gridSizeY = gridSizeY;
        this.gridSizeZ = gridSizeZ;
        this.blockSizeX = blockSizeX;
        this.blockSizeY = blockSizeY;
        this.blockSizeZ = blockSizeZ;
        this.sharedMemBytes = sharedMemBytes;
        this.hStream = hStream;
        this.extra = extra;
    }

    /**
     * Computes the kernel function by compiling the .cu file that contains what the kernel has to do.
     * @param cudaFileNamePath  String with the cuda filepath-name.
     * @return  CUfunction with the cuda function that can be "called" to perform a computation.
     * @throws IOException
     */
    public static CUfunction computeKernelFunction(String cudaFileNamePath) throws IOException {
        //String programSourceCode = new String(ColorDifKernel.class.getClassLoader().getResourceAsStream(cudaFileName).readAllBytes());
        // Retrieve program source code as string
        String programSourceCode = new String(Files.readAllBytes(Paths.get(cudaFileNamePath)));
        // Enable exceptions and omit all subsequent error checks
        JCudaDriver.setExceptionsEnabled(true);
        JNvrtc.setExceptionsEnabled(true);

        // Initialize the driver and create a context for the first device.
        cuInit(0);
        CUdevice device = new CUdevice();
        cuDeviceGet(device, 0);
        CUcontext context = new CUcontext();
        cuCtxCreate(context, 0, device);


        // Use the NVRTC to create a program by compiling the source code
        nvrtcProgram program = new nvrtcProgram();
        nvrtcCreateProgram(
                program, programSourceCode, null, 0, null, null);
        nvrtcCompileProgram(program, 0, null);

        // Print the compilation log (for the case there are any warnings)
        String programLog[] = new String[1];
        nvrtcGetProgramLog(program, programLog);

        // Obtain the PTX ("CUDA Assembler") code of the compiled program
        String[] ptx = new String[1];
        nvrtcGetPTX(program, ptx);
        nvrtcDestroyProgram(program);

        // Create a CUDA module from the PTX code
        CUmodule module = new CUmodule();
        cuModuleLoadData(module, ptx[0]);

        // Obtain the function pointer to the "add" function from the module
        CUfunction function = new CUfunction();
        cuModuleGetFunction(function, module, "computeColorDif");
        return function;
    }

    /**
     * Function that launches the kerel with the pointer to the memory chunks that over which the computations are performed.
     * @param length    Integer with the length of the array of pixels.
     * @param deviceReferenceImage Pointer that references the device memory-chunk with the original image.
     * @param devicePolyImage Pointer that references the device memory-chunk with the poly-image.
     * @param deviceInternalDifferenceVector Pointer that references the device memory-chunk with the vector that contains the distance for each pixel.
     */
    public  void runKernel( int length, CUdeviceptr deviceReferenceImage,CUdeviceptr devicePolyImage,
                           CUdeviceptr deviceInternalDifferenceVector){

        // Set up the kernel parameters: A pointer to an array
        // of pointers which point to the actual values.
        Pointer kernelParams = Pointer.to(
                Pointer.to(new int[]{length}),
                Pointer.to(deviceReferenceImage),
                Pointer.to(devicePolyImage),
                Pointer.to(deviceInternalDifferenceVector)
        );

        // Call the kernel function, which was obtained from the
        // module that was compiled at runtime
        cuLaunchKernel(function,
                gridSizeX,  gridSizeY, gridSizeZ,      // Grid dimension
                blockSizeX, blockSizeY, blockSizeZ,      // Block dimension
                sharedMemBytes, hStream,               // Shared memory size and stream
                kernelParams, extra // Kernel- and extra parameters
        );
        cuCtxSynchronize(); // synchronization of the task
    }
}
