package com.example.opengldemo.GaussianBlur;

//import android.util.Log;

public class GaosiUtil {
    //二维高斯算法具体实现
    static float sum=0;
    public static float[][] get2DKernalData(int n, float sigma) {
        int size = 2*n +1;
        float sigma22 = 2*sigma*sigma;
        float sigma22PI = (float)Math.PI * sigma22;
        float[][] kernalData = new float[size][size];


        int row = 0;
        for(int i=-n; i<=n; i++) {
            int column = 0;
            for(int j=-n; j<=n; j++) {
                float xDistance = i*i;
                float yDistance = j*j;
                kernalData[row][column] = (float)Math.exp(-(xDistance + yDistance)/sigma22)/sigma22PI;
                column++;
            }
            row++;
        }
        System.out.println("二维高斯结果");
        for(int i=0; i<size; i++) {
            for(int j=0; j<size; j++) {
                sum +=kernalData[i][j];
//                Log.i("", "" + kernalData[i][j]);
            }
            System.out.println();
            System.out.println("\t ---------------------------");
        }
        return kernalData;
    }

    public static float[][] get2(float[][] kernalData) {
        System.out.println("均值后二维高斯结果");
        for(int i=0; i<kernalData.length; i++) {
            for(int j=0; j<kernalData.length; j++) {
                kernalData[i][j] = kernalData[i][j]/sum;
//                Log.i("", "" + kernalData[i][j]);
            }
            System.out.println();
            System.out.println("\t ---------------------------");
        }
        return kernalData;

    }

    private static float gaosiEquation(float value, float sigma){
        float blurValue = (float) (1.0/(2.0 * Math.PI * sigma) * Math.exp(-(value * value) / (2 * sigma)));
        return blurValue;
    }

     public static float[] getWeight(float sigma, int step){
         float[] weight = new float[step +1];
         float sum = 0;
         weight[0] = gaosiEquation(0, sigma);
         sum += weight[0];

         for(int i=1; i<weight.length; i++){
             weight[i] = gaosiEquation(i, sigma);
             sum += weight[i] * 2;
         }

         for(int i=0; i<weight.length; i++){
             weight[i] = weight[i] / sum;
         }
         return weight;
     }
}