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