package com.example.opengldemo.rotate;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import com.example.opengldemo.R;
import com.example.shaderUtil.BasicShaderProgram;
import com.example.shaderUtil.MatrixHelper;
import com.example.shaderUtil.TextureHelper;
import com.example.object.Triangle;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.Matrix;

@SuppressLint("NewApi")
public class RotateRender implements Renderer {
	private Activity activity;
	private int meshTexture;
	private int tableTexture;

	private float[] projectionMatrix = new float[16];
	private float[] viewMatrix = new float[16];
	private final float[] modelMatrix = new float[16];
	private float[] viewProjectMatrix = new float[16];

	private BasicShaderProgram textureShader;

	private Triangle firstTriangle;
	private Triangle secondTriangle;
	private Triangle thirdMesh;

	private float firstRotateAngle = 0;
	private float secondRotateAngle = 0;
	private float thirdRotateAngle = 0;

	public RotateRender(Activity activity) {
		this.activity = activity;
	}

	@Override
	public void onSurfaceCreated(GL10 arg0, EGLConfig arg1) {
		GLES20.glClearColor(1F, 1F, 1F, 0F);
		GLES20.glEnable(GLES20.GL_DEPTH_TEST);
//		GLES20.glEnable(GLES20.GL_CULL_FACE);

		meshTexture = TextureHelper.loadTexture(activity, R.drawable.face);
		tableTexture = TextureHelper.loadTexture(activity, R.drawable.air_hockey_surface);

		textureShader = new BasicShaderProgram(activity,  R.raw.rotate_vertex_shader, R.raw.rotate_fragment_shader);

		firstTriangle = new Triangle(new float[]{
				2.1f,  2.4f,   1f, 0.1f,
				1.8f,  2.7f,   0f, 0.1f,
				1.1f, -1.2f,   0f, 0.9f });

		secondTriangle = new Triangle(new float[]{
				-0.1f,  -0.4f,   1f, 0.1f,
				-0.8f,  -0.7f,   0f, 0.1f,
				-0.1f, -0.2f,   0f, 0.9f
		});

		thirdMesh = new Triangle(new float[]{
				-0.2f,  -0.5f,   0.9f, 0f,
				-0.9f,  -0.8f,   -0.1f, 0f,
				-0.2f, -0.3f,   -0.1f, 0.8f
		});
	}

	@Override
	public void onSurfaceChanged(GL10 arg0, int width, int height) {
		GLES20.glViewport(0, 0, width, height);

		float fov = 120; //视角
		float aspect = ((float) width / (float) height);//屏幕的宽高比
		float near = 0.1f; //到近平面距离
		float far = 200f; //到远平面的距离
		//设置透视投影矩阵
		MatrixHelper.perspectiveM(projectionMatrix, fov, aspect, near, far);

		//设置视图矩阵
		Matrix.setLookAtM(viewMatrix, 0, 0f, 0f, 1f, 1f, 0.5f, -1f, 0f, 1f, 0f);
		Matrix.translateM(viewMatrix, 0, 0, 0, -2);
	}

	@Override
	public void onDrawFrame(GL10 arg0) {
		GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

		drawFristTriangle();
		drawSecondTriangle();
		drawThirdTrigangle();
	}

	private void drawFristTriangle(){
		textureShader.useProgram();
		float[] matrix = getFristMatrix();
		textureShader.setUniform(matrix, tableTexture);
		firstTriangle.bindData(textureShader);
		firstTriangle.draw();
	}

	private void drawSecondTriangle(){
		textureShader.useProgram();
		float[] matrix = getSecondMatrix();
		textureShader.setUniform(matrix, tableTexture);
		secondTriangle.bindData(textureShader);
		secondTriangle.draw();
	}

	private void drawThirdTrigangle(){
		textureShader.useProgram();
		float[] matrix = getThirdMatrix();
		textureShader.setUniform(matrix, meshTexture);
		thirdMesh.bindData(textureShader);
		thirdMesh.draw();
	}

	private float[] getFristMatrix() {
		Matrix.multiplyMM(viewProjectMatrix, 0, projectionMatrix, 0, viewMatrix, 0);

		Matrix.setIdentityM(modelMatrix, 0);
		Matrix.translateM(modelMatrix, 0, 1.1f, -1.2f, 0);
		Matrix.rotateM(modelMatrix, 0, firstRotateAngle, 0f, 1f, 0f);
		Matrix.translateM(modelMatrix, 0, -1.1f, 1.2f, 0);

		float[] resultMatrix = new float[16];
		Matrix.multiplyMM(resultMatrix, 0, viewProjectMatrix, 0, modelMatrix, 0);
		firstRotateAngle++;
		return resultMatrix;
	}

	private float[] getSecondMatrix() {
		secondRotateAngle++;

		Matrix.multiplyMM(viewProjectMatrix, 0, projectionMatrix, 0, viewMatrix, 0);

		Matrix.setIdentityM(modelMatrix, 0);
		Matrix.translateM(modelMatrix, 0, 1.1f, -1.2f, 0);
		Matrix.rotateM(modelMatrix, 0, secondRotateAngle, 0f, 1f, 0f);
		Matrix.translateM(modelMatrix, 0, -1.1f, 1.2f, 0);

		float[] resultMatrix = new float[16];
		Matrix.multiplyMM(resultMatrix, 0, viewProjectMatrix, 0, modelMatrix, 0);

		return resultMatrix;
	}

	private float[] getThirdMatrix() {
		float point[] = new float[4];
		float originPoint[] = { -0.1f, -0.2f, 0, 1 };//该点为第二个三角形中的一个顶点A
		Matrix.multiplyMV(point, 0, modelMatrix, 0, originPoint, 0); //计算出第二个三角形中顶点A的当前位置
		Matrix.multiplyMM(viewProjectMatrix, 0, projectionMatrix, 0, viewMatrix, 0);

		Matrix.setIdentityM(modelMatrix, 0);

		//以下三行代码,使得第三个三角形围绕第二个三角形的顶点A旋转
		Matrix.translateM(modelMatrix, 0, 1 * point[0], 1 * point[1], 0);
		Matrix.rotateM(modelMatrix, 0, secondRotateAngle, 0f, 1f, 0f);
		Matrix.translateM(modelMatrix, 0, -1 * point[0], -1 * point[1], 0);

		//以下三行代码，使得第三个三角形围绕自己的一个顶点（-0.2f, -0.3f, 0）旋转
		Matrix.translateM(modelMatrix, 0, -0.2f, -0.3f, 0);
		Matrix.rotateM(modelMatrix, 0, secondRotateAngle, 1f, 0f, 0f);
		Matrix.translateM(modelMatrix, 0, 0.2f, 0.3f, 0);

		float[] resultMatrix = new float[16];
		Matrix.multiplyMM(resultMatrix, 0, viewProjectMatrix, 0, modelMatrix, 0);
		thirdRotateAngle += 3;
		return resultMatrix;
	}
}
