package com.example.shaderUtil;

import android.content.Context;
import android.opengl.GLES20;

abstract class ShaderProgram {
    protected static final String U_MATRIX = "u_Matrix";
    protected static final String U_TEXTURE_UNIT = "u_TextureUnit";
    protected static final String U_POINT_LIGHT = "u_light_point";
    protected static final String U_MVMATRIX = "u_MVMatrix";
    protected static final String U_IT_MVMATRIX = "u_IT_MVMATRIX";

    protected static final String A_POSITION = "a_Position";
    protected static final String A_COLOR = "u_Color";//"a_Color";
    protected static final String A_TEXTURE_COORDINATES = "a_TextureCoordinates";
    protected static final String A_NORMAL_COORDINATES = "a_normalCoordinates"; //

    protected final int program;
    protected ShaderProgram(Context context, int vertexShaderResourceId,
        int fragmentShaderResourceId) {
        program = ShaderHelper.buildProgram(
            TextResourceReader.readTextFileFromResource(
                context, vertexShaderResourceId),
            TextResourceReader.readTextFileFromResource(
                context, fragmentShaderResourceId));
    }        

    public void useProgram() {
    	GLES20.glUseProgram(program);
    }
}
