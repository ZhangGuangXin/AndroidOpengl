package com.example.opengldemo.directLight;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.Matrix;

import com.example.BasicRender;
import com.example.opengldemo.R;
import com.example.shaderUtil.MatrixHelper;
import com.example.shaderUtil.ObjLoader;
import com.example.shaderUtil.TextureHelper;
import com.example.object.Vertices3;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

@SuppressLint("NewApi")
public class DirectLightRender extends BasicRender {
	private Activity activity;
	private int tableTexture;

	private float[] projectionMatrix = new float[16];
	private float[] viewMatrix = new float[16];
	private final float[] modelMatrix = new float[16];
	private float[] viewProjectMatrix = new float[16];
	private float[] it_modelViewMatrix = new float[16];
	private float[] tempMatrix = new float[16];

	private DirectLightShaderProgram textureShader;
	private Vertices3 cube;
	private float xAngle = 0;
	private float yAngle = 0;

	public DirectLightRender(Activity activity) {
		this.activity = activity;
	}

	@Override
	public void onSurfaceCreated(GL10 arg0, EGLConfig arg1) {
		GLES20.glClearColor(1F, 1F, 1F, 0F);

		GLES20.glEnable(GLES20.GL_DEPTH_TEST);

		tableTexture = TextureHelper.loadTexture(activity, R.drawable.air_hockey_surface);
		
		textureShader = new DirectLightShaderProgram(activity,
				R.raw.direct_light_vertex, R.raw.direct_light_fragment);

		cube = ObjLoader.load(activity, R.raw.ball);
	}

	@Override
	public void onSurfaceChanged(GL10 arg0, int width, int height) {
		GLES20.glViewport(0, 0, width, height);

		float fov = 120;
		float aspect = ((float) width / (float) height);
		float near = 0.1f;
		float far = 200f;
		MatrixHelper.perspectiveM(projectionMatrix, fov, aspect, near, far);

		Matrix.setLookAtM(viewMatrix, 0,
				0f, 0f, 1f,
				0f, 0.5f, -1f, 
				0f, 1f, 0f);

		Matrix.translateM(viewMatrix, 0, 0, 0, -2);
	}

	@Override
	public void onDrawFrame(GL10 arg0) {
		GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

		drawCube();
	}

	private void drawCube(){
		float[] matrix = getCubeMatrix();
		textureShader.useProgram();
		textureShader.setUniform(matrix, tableTexture);
		textureShader.setLightMatrix(null, it_modelViewMatrix);

		cube.bind(textureShader.getPositionLocation(),
				textureShader.getTextureCoordinatesLocation(),
				textureShader.getNormalCoordinatesLocation());
		cube.draw();
	}

	private float[] getCubeMatrix() {
		Matrix.setIdentityM(modelMatrix, 0);

		Matrix.translateM(modelMatrix, 0, 0f, -2f, -100f);
		Matrix.rotateM(modelMatrix, 0, xAngle, 1f, 0f, 0f);
		Matrix.rotateM(modelMatrix, 0, yAngle, 0f, 1f, 0f);
		float[] temp = new float[16];
		float[] resultMatrix = new float[16];
		Matrix.multiplyMM(temp, 0, projectionMatrix, 0, modelMatrix, 0);
		System.arraycopy(temp, 0, resultMatrix, 0, temp.length);
		// xAngle += 0.5;

		Matrix.invertM(tempMatrix, 0, modelMatrix, 0);
		Matrix.transposeM(it_modelViewMatrix, 0, tempMatrix, 0);
		return resultMatrix;
	}

	public void rotate(float xAngle, float yAngle) {
		this.xAngle += xAngle;
		this.yAngle += yAngle;
	}

	public void release(){
		int[] textureArray = {tableTexture};
		GLES20.glDeleteTextures(1, textureArray, 0);
	}
}
