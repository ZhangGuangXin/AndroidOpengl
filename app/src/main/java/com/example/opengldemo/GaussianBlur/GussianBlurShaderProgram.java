package com.example.opengldemo.GaussianBlur;

import android.content.Context;
import android.opengl.GLES20;

import com.example.shaderUtil.BasicShaderProgram;


/**
 * Created by L on 2017/9/5.
 */

public class GussianBlurShaderProgram extends BasicShaderProgram {
    private final int uGrivityMatrixLocation;
    private final int uStepLocation;

    protected GussianBlurShaderProgram(Context context, int vertexShaderResourceId, int fragmentShaderResourceId) {
        super(context, vertexShaderResourceId, fragmentShaderResourceId);
        uGrivityMatrixLocation =  GLES20.glGetUniformLocation(program, "weightArray");
        uStepLocation = GLES20.glGetUniformLocation(program, "step");

    }
    public void setGrivityMatrix(float[] grivityMatrix){
//        GLES20.glUniformMatrix4fv(uIT_MVMatrixLocation, 1, false, it_mvMatrix, 0);

//        float matrix[] = new float[grivityMatrix.length * grivityMatrix.length];
//        int index = 0;
//        for(int row=0;row<grivityMatrix[0].length; row++){
//            for(int col = 0; col<grivityMatrix[row].length; col++){
//                matrix[index] = grivityMatrix[row][col];
//                index++;
//            }
//        }
//        GLES20.glUniform1f(uStepLocation, step);
        GLES20.glUniform1fv(uGrivityMatrixLocation, grivityMatrix.length, grivityMatrix, 0);

//        GLES20.glUniformMatrix3fv(uGrivityMatrixLocation, 1, false, matrix, 0);
    }
}
