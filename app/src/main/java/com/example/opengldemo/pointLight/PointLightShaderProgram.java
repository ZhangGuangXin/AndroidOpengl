package com.example.opengldemo.pointLight;

import android.content.Context;
import android.opengl.GLES20;

import com.example.shaderUtil.BasicShaderProgram;

/**
 * Created by L on 2017/8/9.
 */

public class PointLightShaderProgram extends BasicShaderProgram {
    private float textureMove;
    private final int textureMoveLocation;
    private final int uLightPointLocation;
    private final int uMVMatrixLocation;
    private final int uIT_MVMatrixLocation;

    private final int aNormalCoordinatesLocation;

    public PointLightShaderProgram(Context context, int vertexShaderResourceId, int fragmentShaderResourceId) {
        super(context, vertexShaderResourceId, fragmentShaderResourceId);

        textureMoveLocation = GLES20.glGetUniformLocation(program, "textureMove");
        uLightPointLocation = GLES20.glGetUniformLocation(program, U_POINT_LIGHT);
        uMVMatrixLocation = GLES20.glGetUniformLocation(program, U_MVMATRIX);
        uIT_MVMatrixLocation = GLES20.glGetUniformLocation(program, U_IT_MVMATRIX);

        aNormalCoordinatesLocation = GLES20.glGetAttribLocation(program, A_NORMAL_COORDINATES);
    }

    @Override
    public void setUniform(float[] matrix, int textureId) {
        super.setUniform(matrix, textureId);

        GLES20.glUniform1f(textureMoveLocation, textureMove);
    }

    public void setPointLightUniform(float[] lightPoint){
        GLES20.glUniform4fv(uLightPointLocation, 1, lightPoint, 0);
    }

    public void setLightMatrix(float mvMatrix[], float it_mvMatrix[]){
        if(mvMatrix != null) {
            GLES20.glUniformMatrix4fv(uMVMatrixLocation, 1, false, mvMatrix, 0);
        }
        GLES20.glUniformMatrix4fv(uIT_MVMatrixLocation, 1, false, it_mvMatrix, 0);
    }

    public int getNormalCoordinatesLocation() {
        return aNormalCoordinatesLocation;
    }
}
