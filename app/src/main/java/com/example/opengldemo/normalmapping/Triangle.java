package com.example.opengldemo.normalmapping;

import android.graphics.PointF;
import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

/**
 * Created by egslava on 18/03/2017.
 */

public class Triangle {


    private final String mVertexShaderCode;

    private final String mFragmentShaderCode;
    private final int mNormal;
    private int mTexture;

    static final float coordinates[] = {
        -1.0f,  3.0f, 0.0f, // top
        -1.0f, -1.0f, 0.0f, // bottom left
         3.0f, -1.0f, 0.0f  // bottom right
    };

    static final float tex_coordinates[] = {
        0.0f,  2.0f, 0.0f, // top
        0.0f,  0.0f, 0.0f, // bottom left
        6.0f,  0.0f, 0.0f  // bottom right
    };

    static final int numVertex = 3;

    float colors[] = { 0.63671875f, 0.76953125f, 0.22265625f, 1.0f };
    private FloatBuffer mVertexBuffer;
    private FloatBuffer mTextureBuffer;
    private int mVertexShader;
    private int mPixelShader;
    private int mProgram;

    PointF vLightPosition = new PointF(0.0f, 0.0f);

    public Triangle(String mVertexShaderCode, String mFragmentShaderCode, int texture, int normal){
        this.mVertexShaderCode = mVertexShaderCode;
        this.mFragmentShaderCode = mFragmentShaderCode;
        mTexture = texture;
        mNormal = normal;


        // vertex buffer
        ByteBuffer bb = ByteBuffer.allocateDirect(coordinates.length * 4);
        bb.order(ByteOrder.nativeOrder());
        mVertexBuffer = bb.asFloatBuffer();
        mVertexBuffer.put(coordinates);
        mVertexBuffer.position(0);



        // texture buffer
        ByteBuffer tb = ByteBuffer.allocateDirect(tex_coordinates.length * 4);
        tb.order(ByteOrder.nativeOrder());
        mTextureBuffer = tb.asFloatBuffer();
        mTextureBuffer.put(tex_coordinates);
        mTextureBuffer.position(0);



        // loading shaders
        mVertexShader = MyRenderer.loadShader(GLES20.GL_VERTEX_SHADER, this.mVertexShaderCode);
        mPixelShader = MyRenderer.loadShader(GLES20.GL_FRAGMENT_SHADER, this.mFragmentShaderCode);

        mProgram = GLES20.glCreateProgram();
        GLES20.glAttachShader(mProgram, mVertexShader);
        GLES20.glAttachShader(mProgram, mPixelShader);
        GLES20.glLinkProgram(mProgram);
    }

    public void draw(){
        GLES20.glUseProgram( mProgram );

        // vertex shader
        final int vPosition = GLES20.glGetAttribLocation(mProgram, "vPosition");
        GLES20.glEnableVertexAttribArray(vPosition);
        GLES20.glVertexAttribPointer(vPosition, 3, GLES20.GL_FLOAT, false, 4 * 3, mVertexBuffer);

        int u_texture = GLES20.glGetUniformLocation(mProgram, "u_Texture");
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mTexture);
        GLES20.glUniform1i(u_texture, 0);

        int u_Normal = GLES20.glGetUniformLocation(mProgram, "u_Normal");
        GLES20.glActiveTexture(GLES20.GL_TEXTURE1);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, mNormal);
        GLES20.glUniform1i(u_Normal, 1);

        final int vTexture = GLES20.glGetAttribLocation(mProgram, "vTexture");
        GLES20.glEnableVertexAttribArray(vTexture);
        GLES20.glVertexAttribPointer(vTexture, 3, GLES20.GL_FLOAT, false, 4 * 3, mTextureBuffer);

        final int vColor = GLES20.glGetUniformLocation(mProgram, "vColor");
        GLES20.glUniform4fv(vColor, 1, colors, 0);

        final int uLightPosition = GLES20.glGetUniformLocation(mProgram, "vLightPosition");
        GLES20.glUniform2f(uLightPosition, vLightPosition.x, vLightPosition.y);

        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 3);

        GLES20.glDisableVertexAttribArray(vPosition);

    }

}
