package com.example.object;

import static android.opengl.GLES20.glDrawArrays;

import android.opengl.GLES20;

import com.example.shaderUtil.Constants;
import com.example.shaderUtil.TextureShaderProgram;
import com.example.shaderUtil.VertexArray;

public class ThirdMesh {

    private static final int POSITION_COMPONENT_COUNT = 2;
    private static final int TEXTURE_COORDINATES_COMPONENT_COUNT = 2;
    private static final int STRIDE = (POSITION_COMPONENT_COUNT 
        + TEXTURE_COORDINATES_COMPONENT_COUNT) * Constants.BYTES_PER_FLOAT;
    
    private static final float[] VERTEX_DATA = {
        // Order of coordinates: X, Y, S, T

        // Triangle Fan
//           0f,    0f, 0.5f, 0.5f, 
//        -0.5f, -0.8f,   0f, 0.9f,  
//         0.5f, -0.8f,   1f, 0.9f, 
         -0.2f,  -0.5f,   0.9f, 0f, 
         -0.9f,  -0.8f,   -0.1f, 0f, 
         -0.2f, -0.3f,   -0.1f, 0.8f };
    
    private final VertexArray vertexArray;
    
    public ThirdMesh() {
        vertexArray = new VertexArray(VERTEX_DATA);
    }
    
    public void bindData(TextureShaderProgram textureProgram) {
        vertexArray.setVertexAttribPointer(
            0, 
            textureProgram.getPositionAttributeLocation(), 
            POSITION_COMPONENT_COUNT,
            STRIDE);
        
        vertexArray.setVertexAttribPointer(
            POSITION_COMPONENT_COUNT, 
            textureProgram.getTextureCoordinatesAttributeLocation(),
            TEXTURE_COORDINATES_COMPONENT_COUNT, 
            STRIDE);
    }
    
    public void draw() {                      
    	glDrawArrays(GLES20.GL_TRIANGLES, 0, 3);
//        glDrawArrays(GL_TRIANGLE_FAN, 0, 6);    	 
    }

}
