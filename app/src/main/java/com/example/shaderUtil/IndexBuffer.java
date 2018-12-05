package com.example.shaderUtil;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ShortBuffer;

import android.annotation.SuppressLint;
import android.opengl.GLES20;

public class IndexBuffer {

	private int bufferID;
	
	public IndexBuffer(short indexData[]){
	    int buffers[] = new int[1];
		GLES20.glGenBuffers(buffers.length, buffers, 0);
		
		this.bufferID = buffers[0];
		GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, bufferID);
		int bytesPerShort = 2;
		ShortBuffer vertexArray = ByteBuffer.allocateDirect(indexData.length * bytesPerShort).order(ByteOrder.nativeOrder()).asShortBuffer().put(indexData);
		vertexArray.position(0);
		GLES20.glBufferData(GLES20.GL_ELEMENT_ARRAY_BUFFER, vertexArray.capacity() * bytesPerShort, vertexArray, GLES20.GL_STATIC_DRAW);
		GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0);
	}
	
	@SuppressLint("NewApi")
	public void setVertexAttribPosition(int dataOffset, int attributeLocation, int componentCount, int stride){
		GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, bufferID);
		GLES20.glVertexAttribPointer(attributeLocation, componentCount, GLES20.GL_FLOAT, false, stride, dataOffset);
		GLES20.glEnableVertexAttribArray(attributeLocation);
		GLES20.glBindBuffer(GLES20.GL_ELEMENT_ARRAY_BUFFER, 0);
	}

	public int getBufferID() {
		return bufferID;
	}

}
