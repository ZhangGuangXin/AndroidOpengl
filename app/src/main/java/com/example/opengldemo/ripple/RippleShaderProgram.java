package com.example.opengldemo.ripple;

import android.content.Context;
import android.opengl.GLES20;

import com.example.shaderUtil.BasicShaderProgram;

/**
 * Created by L on 2017/8/8.
 */

public class RippleShaderProgram extends BasicShaderProgram {
    private final int aTextureUnitLocation;
    private final int uTimeLocation;
    private final int textureCoordinateYLocation;
    private float time;
    private float textureCoordinateY;
    private int tableTexture;

    public RippleShaderProgram(Context context, int vertexShaderResourceId, int fragmentShaderResourceId, int tableTexture) {
        super(context, vertexShaderResourceId, fragmentShaderResourceId);

        aTextureUnitLocation = GLES20.glGetUniformLocation(program, "a_TextureUnit");
        uTimeLocation = GLES20.glGetUniformLocation(program, "time");
        textureCoordinateYLocation = GLES20.glGetUniformLocation(program, "textureCoordinateY");
        this.tableTexture = tableTexture;
    }

    @Override
    public void setUniform(float[] matrix, int textureId) {
        super.setUniform(matrix, textureId);

        GLES20.glActiveTexture(GLES20.GL_TEXTURE1);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, tableTexture);
        GLES20.glUniform1i(aTextureUnitLocation, 1);

        time += 0.1f;
        GLES20.glUniform1f(uTimeLocation, time);

        textureCoordinateY += 0.005;
        GLES20.glUniform1f(textureCoordinateYLocation, textureCoordinateY);
    }
}
