package com.example.shaderUtil;

//import com.example.firstopengldemo.R;

import android.content.Context;
import android.opengl.GLES20;
//import android.util.Log;

import com.example.object.Geometry;
import com.example.opengldemo.R;

import java.util.Random;


public class TextureShaderProgram extends ShaderProgram {

	private final int uMatrixLocation;
	private final int uTextureUnitLocation;
	private final int aTextureUnitLocation;
	private final int uTimeLocation;
	private final int uLightVector;
	// private final int uSideLocation;

	private final int aPositionLocation;
	private final int aTextureCoordinatesLocation;
    private final int aNormalCoordinatesLocation;
	private final int uLightPointLocation;
	private final int uMVMatrixLocation;
	private final int uIT_MVMatrixLocation;

	public TextureShaderProgram(Context context) {
		super(context, R.raw.simple_vertex_shader, R.raw.simple_fragment_shader);

		uMatrixLocation = GLES20.glGetUniformLocation(program, U_MATRIX);
		aTextureUnitLocation = GLES20.glGetUniformLocation(program, "a_TextureUnit");
		uTextureUnitLocation = GLES20.glGetUniformLocation(program, U_TEXTURE_UNIT);
		uTimeLocation = GLES20.glGetUniformLocation(program, "time");
		uLightVector = GLES20.glGetUniformLocation(program, "lightVector");
		uLightPointLocation = GLES20.glGetUniformLocation(program, U_POINT_LIGHT);
		uMVMatrixLocation = GLES20.glGetUniformLocation(program, U_MVMATRIX);
		uIT_MVMatrixLocation = GLES20.glGetUniformLocation(program, U_IT_MVMATRIX);
		// uSideLocation = GLES20.glGetUniformLocation(program, "side");

		aPositionLocation = GLES20.glGetAttribLocation(program, A_POSITION);
		aTextureCoordinatesLocation = GLES20.glGetAttribLocation(program, A_TEXTURE_COORDINATES);
        aNormalCoordinatesLocation = GLES20.glGetAttribLocation(program, A_NORMAL_COORDINATES);
	}
	
	public TextureShaderProgram(Context context, int vertexShader, int fragmentShader) {
		super(context, vertexShader, fragmentShader);

		uMatrixLocation = GLES20.glGetUniformLocation(program, U_MATRIX);
		aTextureUnitLocation = GLES20.glGetUniformLocation(program, "a_TextureUnit");
		uTextureUnitLocation = GLES20.glGetUniformLocation(program, U_TEXTURE_UNIT);
		uTimeLocation = GLES20.glGetUniformLocation(program, "time");
		// uSideLocation = GLES20.glGetUniformLocation(program, "side");
		uLightVector = GLES20.glGetUniformLocation(program, "lightVector");
		uLightPointLocation = GLES20.glGetUniformLocation(program, U_POINT_LIGHT);

		uMVMatrixLocation = GLES20.glGetUniformLocation(program, U_MVMATRIX);
		uIT_MVMatrixLocation = GLES20.glGetUniformLocation(program, U_IT_MVMATRIX);

		aPositionLocation = GLES20.glGetAttribLocation(program, A_POSITION);
		aTextureCoordinatesLocation = GLES20.glGetAttribLocation(program, A_TEXTURE_COORDINATES);
        aNormalCoordinatesLocation = GLES20.glGetAttribLocation(program, A_NORMAL_COORDINATES);
	}

	public void setWobbleUniform(float[] matrix, int textureId){
		GLES20.glUniformMatrix4fv(uMatrixLocation, 1, false, matrix, 0);
		GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId);
		GLES20.glUniform1i(uTextureUnitLocation, 0);
		time += 0.05f;
		GLES20.glUniform1f(uTimeLocation, time);
	}

	public void setUniforms(float[] matrix, int textureId) {
		// GLES20.glUniform1f(uSideLocation, 0.5f);
		GLES20.glUniformMatrix4fv(uMatrixLocation, 1, false, matrix, 0);
		GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId);
		GLES20.glUniform1i(uTextureUnitLocation, 0);


//		if (time <= 10 && isUp) {
//			time += 0.15f;
//		} else if ((time > 10 && isUp) || (time > 0 && !isUp)) {
//			time -= 0.15f;
//			isUp = false;
//		}
//		else if(time <= 0 && !isUp){
//			time += 0.15f;
//			isUp = true;
//		}
//		time += 0.05f;
		GLES20.glUniform1f(uTimeLocation, time);
		GLES20.glUniform3f(uLightVector, vectorToLight.x, vectorToLight.y, vectorToLight.z);
		float lightX = vectorToLight.x+random.nextFloat();
		float lightY = vectorToLight.y+random.nextFloat();
		float lightZ = vectorToLight.z+random.nextFloat();
		vectorToLight = new Geometry.Vector(lightX, lightY, lightZ).normalize();
	}

	Random random = new Random(10);
	public void setPointLightUniform(float[] lightPoint){
//		Log.i("", "----light position: "+uLightPointLocation);
		GLES20.glUniform4fv(uLightPointLocation, 1, lightPoint, 0);
//		GLES20.glUniform3f(uLightPointLocation, pointLightX, pointLightY, pointLightZ);
//		GLES20.gluniform
	}

	public void setLightMatrix(float mvMatrix[], float it_mvMatrix[]){
		if(mvMatrix != null) {
			GLES20.glUniformMatrix4fv(uMVMatrixLocation, 1, false, mvMatrix, 0);
		}
		GLES20.glUniformMatrix4fv(uIT_MVMatrixLocation, 1, false, it_mvMatrix, 0);
	}

private Geometry.Vector vectorToLight = new Geometry.Vector(-61f, 64f, 12f).normalize();

	public void setUniforms(float[] matrix, int textureId, int tableTexture) {
		// GLES20.glUniform1f(uSideLocation, 0.5f);
		GLES20.glUniformMatrix4fv(uMatrixLocation, 1, false, matrix, 0);

		GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureId);
		GLES20.glUniform1i(uTextureUnitLocation, 0);

		GLES20.glActiveTexture(GLES20.GL_TEXTURE1);
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, tableTexture);
		GLES20.glUniform1i(aTextureUnitLocation, 1);

//		if (time <= 10 && isUp) {
//			time += 0.15f;
//		} else if ((time > 10 && isUp) || (time > 0 && !isUp)) {
//			time -= 0.15f;
//			isUp = false;
//		}
//		else if(time <= 0 && !isUp){
//			time += 0.15f;
//			isUp = true;
//		}
		time += 0.05f;
		GLES20.glUniform1f(uTimeLocation, time);
	}
	
	private boolean isUp = true;
	private float time;

	public int getPositionAttributeLocation() {
		return aPositionLocation;
	}

	public int getTextureCoordinatesAttributeLocation() {
		return aTextureCoordinatesLocation;
	}

    public int getNormalCoordinatesLocation() {
        return aNormalCoordinatesLocation;
    }

	public int getPositionLocation() {
		return aPositionLocation;
	}

	public int getLightPointLocation() {
		return uLightPointLocation;
	}

	public int getMVMatrixLocation() {
		return uMVMatrixLocation;
	}

	public int getIT_MVMatrixLocation() {
		return uIT_MVMatrixLocation;
	}
}
