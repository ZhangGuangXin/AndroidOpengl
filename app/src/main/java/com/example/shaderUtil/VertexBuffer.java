package com.example.shaderUtil;

import static android.opengl.GLES20.GL_ARRAY_BUFFER;
import static android.opengl.GLES20.GL_FLOAT;
import static android.opengl.GLES20.GL_STATIC_DRAW;
import static android.opengl.GLES20.glBindBuffer;
import static android.opengl.GLES20.glEnableVertexAttribArray;
import static android.opengl.GLES20.glVertexAttribPointer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import android.annotation.SuppressLint;
import android.opengl.GLES20;
/***
 * Excerpted from "OpenGL ES for Android",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/kbogla for more book information.
***/

public class VertexBuffer {
    private final int bufferId;
    
    public VertexBuffer(float[] vertexData) {
    	 int buffers[] = new int[1];
 		GLES20.glGenBuffers(buffers.length, buffers, 0);
 		
 		bufferId = buffers[0];
 		GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, buffers[0]);
 		int bytesPerFloat = 4;
 		FloatBuffer vertexArray = ByteBuffer
 				.allocateDirect(vertexData.length * bytesPerFloat)
 				.order(ByteOrder.nativeOrder())
 				.asFloatBuffer()
 				.put(vertexData);
 		vertexArray.position(0);
 		
 		int capacity = vertexArray.capacity();
 		int size = capacity * bytesPerFloat;
// 		GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, size , vertexArray, GLES20.GL_STATIC_DRAW);
 		 GLES20.glBufferData(GL_ARRAY_BUFFER, vertexArray.capacity() * 4,
 	            vertexArray, GL_STATIC_DRAW);   
 		GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);
 		
 		
//        // Allocate a buffer.
//        int buffers[] = new int[1];
//        GLES20.glGenBuffers(buffers.length, buffers, 0);
//        if (buffers[0] == 0) {
//            throw new RuntimeException("Could not create a new vertex buffer object.");
//        }
//        bufferId = buffers[0];
//        
//        // Bind to the buffer. 
//        GLES20.glBindBuffer(GL_ARRAY_BUFFER, buffers[0]);
//        
//        // Transfer data to native memory.        
//        FloatBuffer vertexArray = ByteBuffer
//            .allocateDirect(vertexData.length * 4)
//            .order(ByteOrder.nativeOrder())
//            .asFloatBuffer()
//            .put(vertexData);
//        vertexArray.position(0);
//        
//        // Transfer data from native memory to the GPU buffer.              
//        GLES20.glBufferData(GL_ARRAY_BUFFER, vertexArray.capacity() * 4,
//            vertexArray, GL_STATIC_DRAW);                      
//         
//        // IMPORTANT: Unbind from the buffer when we're done with it.
//        GLES20.glBindBuffer(GL_ARRAY_BUFFER, 0);
//        // We let vertexArray go out of scope, but it won't be released
//        // until the next time the garbage collector is run.
    }
        
    @SuppressLint("NewApi")
	public void setVertexAttribPointer(int dataOffset, int attributeLocation,
        int componentCount, int stride) {
        glBindBuffer(GL_ARRAY_BUFFER, bufferId);
        // This call is slightly different than the glVertexAttribPointer we've
        // used in the past: the last parameter is set to dataOffset, to tell OpenGL
        // to begin reading data at this position of the currently bound buffer.
        glVertexAttribPointer(attributeLocation, componentCount, GL_FLOAT, 
            false, stride, dataOffset);
        glEnableVertexAttribArray(attributeLocation);
        glBindBuffer(GL_ARRAY_BUFFER, 0);        
    }     
}

//public class VertexBuffer {
//	private int bufferID;
//	
//	public VertexBuffer(float vertexData[]){
//	    int buffers[] = new int[1];
//		GLES20.glGenBuffers(buffers.length, buffers, 0);
//		
//		this.bufferID = buffers[0];
//		GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, buffers[0]);
//		int bytesPerFloat = 4;
//		FloatBuffer vertexArray = ByteBuffer.allocate(vertexData.length * bytesPerFloat)
//				.order(ByteOrder.nativeOrder())
//				.asFloatBuffer()
//				.put(vertexData);
//		vertexArray.position(0);
//		
//		int capacity = vertexArray.capacity();
//		int size = capacity * bytesPerFloat;
//		GLES20.glBufferData(GLES20.GL_ARRAY_BUFFER, size , vertexArray, GLES20.GL_STATIC_DRAW);
//		GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);
//	}
//	
//	@SuppressLint("NewApi")
//	public void setVertexAttribPosition(int dataOffset, int attributeLocation, int componentCount, int stride){
//		GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, bufferID);
//		GLES20.glVertexAttribPointer(attributeLocation, componentCount, GLES20.GL_FLOAT, false, stride, dataOffset);
//		GLES20.glEnableVertexAttribArray(attributeLocation);
//		GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, 0);
//	}
//}
