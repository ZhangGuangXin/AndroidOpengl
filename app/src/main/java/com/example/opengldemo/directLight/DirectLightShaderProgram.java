package com.example.opengldemo.directLight;

import android.content.Context;
import android.opengl.GLES20;

import com.example.object.Geometry;
import com.example.shaderUtil.BasicShaderProgram;

import java.util.Random;

/**
 * Created by L on 2017/8/8.
 */

public class DirectLightShaderProgram extends BasicShaderProgram {
    private final int uLightVector;
    private Geometry.Vector vectorToLight = new Geometry.Vector(-61f, 64f, 12f).normalize();
    private Random random = new Random(10);

    private float mvMatrix[];
    private float it_mvMatrix[];

    private final int uMVMatrixLocation;
    private final int uIT_MVMatrixLocation;
    private final int aNormalCoordinatesLocation;

    public DirectLightShaderProgram(Context context, int vertexShaderResourceId, int fragmentShaderResourceId) {
        super(context, vertexShaderResourceId, fragmentShaderResourceId);
        uLightVector = GLES20.glGetUniformLocation(program, "lightVector");
        uMVMatrixLocation = GLES20.glGetUniformLocation(program, U_MVMATRIX);
        uIT_MVMatrixLocation = GLES20.glGetUniformLocation(program, U_IT_MVMATRIX);

        aNormalCoordinatesLocation = GLES20.glGetAttribLocation(program, A_NORMAL_COORDINATES);
    }

    @Override
    public void setUniform(float[] matrix, int textureId) {
        super.setUniform(matrix, textureId);

        GLES20.glUniform3f(uLightVector, vectorToLight.x, vectorToLight.y, vectorToLight.z);
        float lightX = vectorToLight.x+random.nextFloat();
        float lightY = vectorToLight.y+random.nextFloat();
        float lightZ = vectorToLight.z+random.nextFloat();
        vectorToLight = new Geometry.Vector(lightX, lightY, lightZ).normalize();
    }

    public void setLightMatrix(float mvMatrix[], float it_mvMatrix[]){
        if(mvMatrix != null) {
            GLES20.glUniformMatrix4fv(uMVMatrixLocation, 1, false, mvMatrix, 0);
        }
        GLES20.glUniformMatrix4fv(uIT_MVMatrixLocation, 1, false, it_mvMatrix, 0);
    }


    public int getuMVMatrixLocation() {
        return uMVMatrixLocation;
    }

    public int getuIT_MVMatrixLocation() {
        return uIT_MVMatrixLocation;
    }

    public int getNormalCoordinatesLocation() {
        return aNormalCoordinatesLocation;
    }
}
