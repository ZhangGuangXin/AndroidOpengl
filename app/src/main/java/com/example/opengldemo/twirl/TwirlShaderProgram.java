package com.example.opengldemo.twirl;

import android.content.Context;
import android.opengl.GLES20;

import com.example.shaderUtil.BasicShaderProgram;

/**
 * Created by L on 2017/10/6.
 */

public class TwirlShaderProgram extends BasicShaderProgram {
    protected final int uAngleLocation;

    public TwirlShaderProgram(Context context, int vertexShaderResourceId, int fragmentShaderResourceId) {
        super(context, vertexShaderResourceId, fragmentShaderResourceId);
        uAngleLocation = GLES20.glGetUniformLocation(program, "angle");
    }

    public void setAngle(float angle){
        GLES20.glUniform1f(uAngleLocation, angle);
    }
}
