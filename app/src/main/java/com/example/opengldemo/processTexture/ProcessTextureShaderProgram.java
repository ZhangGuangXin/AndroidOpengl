package com.example.opengldemo.processTexture;

import android.content.Context;
import android.opengl.GLES20;

import com.example.shaderUtil.BasicShaderProgram;

/**
 * Created by L on 2017/8/8.
 */

public class ProcessTextureShaderProgram extends BasicShaderProgram {
    private final int uRadianAngleLocation;
    private float radianAngle;

    public ProcessTextureShaderProgram(Context context, int vertexShaderResourceId, int fragmentShaderResourceId) {
        super(context, vertexShaderResourceId, fragmentShaderResourceId);
        uRadianAngleLocation = GLES20.glGetUniformLocation(program, "radianAngle");
    }

    @Override
    public void setUniform(float[] matrix, int textureId) {
        super.setUniform(matrix, textureId);

        radianAngle += 0.05;
        GLES20.glUniform1f(uRadianAngleLocation, radianAngle);
    }
}
