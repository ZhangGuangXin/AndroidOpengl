package com.example.shaderUtil;

import android.content.Context;
import android.opengl.GLES20;

/**
 * Created by L on 2017/8/7.
 */

public class BasicShaderProgram extends ShaderProgram {
    protected final int uMatrixLocation;
    protected final int uTextureUnitLocation;

    protected final int aPositionLocation;
    protected final int aTextureCoordinatesLocation;

    public BasicShaderProgram(Context context, int vertexShaderResourceId, int fragmentShaderResourceId) {
        super(context, vertexShaderResourceId, fragmentShaderResourceId);

        uMatrixLocation = GLES20.glGetUniformLocation(program, U_MATRIX);
        uTextureUnitLocation = GLES20.glGetUniformLocation(program, U_TEXTURE_UNIT);

        aPositionLocation = GLES20.glGetAttribLocation(program, A_POSITION);
        aTextureCoordinatesLocation = GLES20.glGetAttribLocation(program, A_TEXTURE_COORDINATES);
    }

    public void setUniform(float[] matrix, int textureId){
        GLES20.glUniformMatrix4fv(uMatrixLocation, 1, false, matrix, 0);
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId);
        GLES20.glUniform1i(uTextureUnitLocation, 0);
    }

    public int getPositionLocation() {
        return aPositionLocation;
    }

    public int getTextureCoordinatesLocation() {
        return aTextureCoordinatesLocation;
    }
}
