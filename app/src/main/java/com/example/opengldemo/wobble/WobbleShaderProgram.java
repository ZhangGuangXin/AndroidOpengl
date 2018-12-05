package com.example.opengldemo.wobble;

import android.content.Context;
import android.opengl.GLES20;

import com.example.shaderUtil.BasicShaderProgram;

/**
 * Created by L on 2017/8/7.
 */

public class WobbleShaderProgram extends BasicShaderProgram {
    private final int uTimeLocation;
    private float time;

    public WobbleShaderProgram(Context context, int vertexShaderResourceId, int fragmentShaderResourceId) {
        super(context, vertexShaderResourceId, fragmentShaderResourceId);
        uTimeLocation = GLES20.glGetUniformLocation(program, "time");
    }

    @Override
    public void setUniform(float[] matrix, int textureId) {
        super.setUniform(matrix, textureId);

        time += 0.05f;
        GLES20.glUniform1f(uTimeLocation, time);
    }
}
