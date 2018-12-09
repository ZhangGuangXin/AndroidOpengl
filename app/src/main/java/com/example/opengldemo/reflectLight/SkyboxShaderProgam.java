package com.example.opengldemo.reflectLight;

import android.content.Context;
import android.opengl.GLES20;

public class SkyboxShaderProgam extends CommonShaderProgram {
    private final int skyCubeLoation;
    private final int matrixForSkyLocation;

    public SkyboxShaderProgam(Context context, int vertexShaderResourceId, int fragmentShaderResourceId) {
        super(context, vertexShaderResourceId, fragmentShaderResourceId);

        skyCubeLoation = GLES20.glGetUniformLocation(program, "skyCube");
        matrixForSkyLocation= GLES20.glGetUniformLocation(program, "matrixForSky");
    }

    public void setSkyCube(int skyCube, float[] skyMatrix){
        GLES20.glActiveTexture(GLES20.GL_TEXTURE1);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_CUBE_MAP, skyCube);
        GLES20.glUniform1i(skyCubeLoation, 1);

        GLES20.glUniformMatrix4fv(matrixForSkyLocation, 1, false, skyMatrix, 0);
    }

}
