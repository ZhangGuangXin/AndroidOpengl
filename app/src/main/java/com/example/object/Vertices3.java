package com.example.object;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import android.opengl.GLES20;

public class Vertices3 {
	final boolean hasColor;
	final boolean hasTexCoords;
	final boolean hasNormals;
	final int vertexSize;
	final FloatBuffer vertices;
	final float[] tmpBuffer;
	final ShortBuffer indices;
	
	public Vertices3(int maxVertices, int maxIndices,
			boolean hasColor, boolean hasTexCoords, boolean hasNormals) {		
		this.hasColor = hasColor;
		this.hasTexCoords = hasTexCoords;
		this.hasNormals = hasNormals;
		this.vertexSize = (3 + (hasColor ? 4 : 0) + (hasTexCoords ? 2 : 0) + (hasNormals ? 3
				: 0)) * 4;
		this.tmpBuffer = new float[maxVertices * vertexSize / 4];

		ByteBuffer buffer = ByteBuffer.allocateDirect(maxVertices * vertexSize);
		buffer.order(ByteOrder.nativeOrder());
		vertices = buffer.asFloatBuffer();

		if (maxIndices > 0) {
			buffer = ByteBuffer.allocateDirect(maxIndices * Short.SIZE / 8);
			buffer.order(ByteOrder.nativeOrder());
			indices = buffer.asShortBuffer();
		} else {
			indices = null;
		}
	}

	public void setVertices(float[] vertices, int offset, int length) {
		this.vertices.clear();
		int len = offset + length;
		for (int i = offset, j = 0; i < len; i++, j++){
			tmpBuffer[j] = vertices[i];//Float.floatToRawIntBits(vertices[i]);
		}
		this.vertices.put(tmpBuffer, 0, length);
		this.vertices.flip();
	}

	public void setIndices(short[] indices, int offset, int length) {
		this.indices.clear();
		this.indices.put(indices, offset, length);
		this.indices.flip();
	}

	public void bind(int positionLocation, int uvLocation) {
//		GL10 gl = glGraphics.getGL();

//		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
//		vertices.position(0);
//		gl.glVertexPointer(3, GL10.GL_FLOAT, vertexSize, vertices);
		
		int positionCount = 3;
		int textureCoordinateCount = 2;
		int normalCount = 3;
		int stride = 0;
		if(hasTexCoords){
			textureCoordinateCount = 2;
		}else{
			textureCoordinateCount = 0;
		}
		
		if(hasNormals){
			normalCount = 3;
		}else{
			normalCount = 0;
		}
		
		stride = (positionCount + textureCoordinateCount + normalCount) * 4;
		
		setVertexAttribPointer(0, positionLocation, positionCount, stride);

		if (hasColor) {
//			gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
//			vertices.position(3);
//			gl.glColorPointer(4, GL10.GL_FLOAT, vertexSize, vertices);
		}

		if (hasTexCoords) {
			setVertexAttribPointer(positionCount, uvLocation, textureCoordinateCount, stride);
//			gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
//			vertices.position(hasColor ? 7 : 3);
//			gl.glTexCoordPointer(2, GL10.GL_FLOAT, vertexSize, vertices);
		}

		if (hasNormals) {
//			setVertexAttribPointer(5, uvLocation, normalCount, stride);
//			gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);
//			int offset = 3;
//			if (hasColor)
//				offset += 4;
//			if (hasTexCoords)
//				offset += 2;
//			vertices.position(offset);
//			gl.glNormalPointer(GL10.GL_FLOAT, vertexSize, vertices);
		}
	}

	public void bind(int positionLocation, int uvLocation, int normalLocation) {
		int positionCount = 3;
		int textureCoordinateCount = 2;
		int normalCount = 3;
		int stride = 0;
		if(hasTexCoords){
			textureCoordinateCount = 2;
		}else{
			textureCoordinateCount = 0;
		}

		if(hasNormals){
			normalCount = 3;
		}else{
			normalCount = 0;
		}
		stride = (positionCount + textureCoordinateCount + normalCount) * 4;
		setVertexAttribPointer(0, positionLocation, positionCount, stride);

		if (hasColor) {
		}

		if (hasTexCoords) {
			setVertexAttribPointer(positionCount, uvLocation, textureCoordinateCount, stride);
		}

		if (hasNormals) {
			int startPosition = positionCount + textureCoordinateCount;
			setVertexAttribPointer(startPosition, normalLocation, normalCount, stride);
		}
	}
	
	public void setVertexAttribPointer(int dataOffset, int attributeLocation,
			int componentCount, int stride) {
		vertices.position(dataOffset);
		GLES20.glVertexAttribPointer(attributeLocation, componentCount, GLES20.GL_FLOAT,
				false, stride, vertices);
		GLES20.glEnableVertexAttribArray(attributeLocation);

		vertices.position(0);
	}

	public void draw() {
//		GL10 gl = glGraphics.getGL();
//		
//		Log.i("", "----numVertices"+numVertices);
//		if (indices != null) {
//			indices.position(offset);
//			gl.glDrawElements(primitiveType, numVertices,
//					GL10.GL_UNSIGNED_SHORT, indices);
//		} else {
//		int triangleCount = numVertices*2;
//		int triangleCount = numVertices;
//		GLES20.GL_TRIANGLES, 0, mesh.getNumVertices()
//
		GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, getNumVertices());
//		}
//		GLES20.glDrawArrays(primitiveType, offset, 36);
	}

	public void unbind() {
//		GL10 gl = glGraphics.getGL();
//		if (hasTexCoords)
//			gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
//
//		if (hasColor)
//			gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
//
//		if (hasNormals)
//			gl.glDisableClientState(GL10.GL_NORMAL_ARRAY);
	}
	
	public int getNumIndices() {
		return indices.limit();
	}
	
	public int getNumVertices() {
		return vertices.limit() / (vertexSize / 4);
	}
}
