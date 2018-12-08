package com.example.opengldemo.reflectLight;

import android.content.Context;
import android.opengl.GLES20;

public class SkyboxShaderProgam extends CommonShaderProgram {
    private final int skyCubeLoation;

    public SkyboxShaderProgam(Context context, int vertexShaderResourceId, int fragmentShaderResourceId) {
        super(context, vertexShaderResourceId, fragmentShaderResourceId);

        skyCubeLoation = GLES20.glGetUniformLocation(program, "skyCube");
    }

    public void setSkyCube(int skyCube){
        GLES20.glActiveTexture(GLES20.GL_TEXTURE1);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_CUBE_MAP, skyCube);
        GLES20.glUniform1i(skyCubeLoation, 1);
    }

}
