package com.example.opengldemo.skybox;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.opengl.GLES20;
import android.opengl.Matrix;

import com.example.BasicRender;
import com.example.object.Skybox;
import com.example.opengldemo.R;
import com.example.opengldemo.reflectLight.CommonShaderProgram;
import com.example.opengldemo.reflectLight.SkyboxShaderProgam;
import com.example.shaderUtil.MatrixHelper;
import com.example.shaderUtil.ObjLoader;
import com.example.shaderUtil.SkyboxShaderProgram;
import com.example.shaderUtil.TextureHelper;
import com.example.object.Vertices3;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

@SuppressLint("NewApi")
public class SkyBoxRender extends BasicRender{
	private Activity activity;

	private float xAngle = 0;
	private float yAngle = 0;

	private final float[] matrixForSky = new float[16];
	private final float[] projectionMatrix = new float[16];
	private final float[] viewMatrix = new float[16];
	private final float[] viewProjectionMatrix = new float[16];

	private final float[] modelViewMatrix = new float[16];
	private final float[] modelMatrix = new float[16];
	private final float[] tempMatrix = new float[16];
	private final float[] it_modelViewMatrix = new float[16];
	private float[] viewProjectMatrix = new float[16];

	private int tableTexture;
	private SkyboxShaderProgam textureShader;
	private Vertices3 mesh;

	private SkyboxShaderProgram skyboxProgram;
	private Skybox skybox;
	private int skyboxTexture;

	private float cubuRotateY;

	public SkyBoxRender(Activity activity) {
		this.activity = activity;
	}

	@Override
	public void onSurfaceCreated(GL10 arg0, EGLConfig arg1) {
		GLES20.glClearColor(1F, 1F, 1F, 0F);
		GLES20.glEnable(GLES20.GL_DEPTH_TEST);


		tableTexture = TextureHelper.loadTexture(activity, R.drawable.air_hockey_surface);
		textureShader = new SkyboxShaderProgam(activity,
				R.raw.reflect_light_vertext, R.raw.reflect_light_fragment);
		mesh = ObjLoader.load(activity, R.raw.cube);


		skyboxProgram = new SkyboxShaderProgram(activity);
		skybox = new Skybox();
		skyboxTexture = TextureHelper.loadCubeMap(activity, new int[]{
				R.drawable.left, R.drawable.right,
				R.drawable.bottom, R.drawable.top,
				R.drawable.front, R.drawable.back
		});
	}

	@Override
	public void onSurfaceChanged(GL10 arg0, int width, int height) {
		GLES20.glViewport(0, 0, width, height);

		float fov = 60;
		float aspect = ((float) width / (float) height);
		float near = 0.1f;
		float far = 200f;
		MatrixHelper.perspectiveM(projectionMatrix, fov, aspect, near, far);

//		Matrix.setLookAtM(viewMatrix, 0,
//				0f, 0f, 1f,
//				0f, 0.5f, -1f,
//				0f, 1f, 0f);
//
//		Matrix.translateM(viewMatrix, 0, 0, 2, 0);
	}

	@Override
	public void onDrawFrame(GL10 arg0) {
		GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

		drawSkybox();

		drawCube();
	}

	private void drawSkybox() {
		getSkyBoxMatrix();
		skyboxProgram.useProgram();
		skyboxProgram.setUniforms(viewProjectionMatrix, skyboxTexture);
		skybox.bindData(skyboxProgram);
		skybox.draw();
	}

	private void drawCube(){
		float[] matrix = getCubeMatrix();
		textureShader.useProgram();
		textureShader.setUniform(matrix, tableTexture);

		float[] pointLight = {2f, 0f, 1.0f, 1.0f};
		Matrix.multiplyMV(pointLight, 0, viewMatrix, 0, pointLight, 0);
		textureShader.setPointLightUniform(pointLight);
		textureShader.setLightMatrix(modelViewMatrix, it_modelViewMatrix);
		textureShader.setSkyCube(skyboxTexture, matrixForSky);

		int normalLocation = textureShader.getNormalCoordinatesLocation();
		mesh.bind(textureShader.getPositionLocation(),
				textureShader.getTextureCoordinatesLocation(),
				normalLocation);
		mesh.draw();
	}

	private float[] getSkyBoxMatrix(){
		Matrix.setIdentityM(viewMatrix, 0);
		Matrix.rotateM(viewMatrix, 0, xAngle, 1f, 0f, 0f);
		Matrix.rotateM(viewMatrix, 0, yAngle, 0f, 1f, 0f);
		//matrixForSky
		System.arraycopy(viewMatrix, 0, matrixForSky, 0, viewMatrix.length);
		Matrix.multiplyMM(viewProjectionMatrix, 0, projectionMatrix, 0, viewMatrix, 0);
		return  viewProjectionMatrix;
	}

	private float[] getCubeMatrix() {
		Matrix.setIdentityM(viewMatrix, 0);
		Matrix.multiplyMM(viewProjectMatrix, 0, projectionMatrix, 0, viewMatrix, 0);
		Matrix.setIdentityM(modelMatrix, 0);

		Matrix.translateM(modelMatrix, 0, 0f, -2f, -6f);
//		Matrix.rotateM(modelMatrix, 0, xAngle, 1f, 0f, 0f);
		Matrix.rotateM(modelMatrix, 0, cubuRotateY, 0f, 1f, 0f);
		cubuRotateY += 0.2f;
		float[] temp = new float[16];
		float[] resultMatrix = new float[16];
		Matrix.multiplyMM(temp, 0, viewProjectMatrix, 0, modelMatrix, 0);
		System.arraycopy(temp, 0, resultMatrix, 0, temp.length);
		// xAngle += 0.5;

		Matrix.multiplyMM(modelViewMatrix, 0, viewMatrix, 0, modelMatrix, 0);
		Matrix.invertM(tempMatrix, 0, modelViewMatrix, 0);
		Matrix.transposeM(it_modelViewMatrix, 0, tempMatrix, 0);

		return resultMatrix;
	}


	public void rotate(float xAngle, float yAngle) {
		this.xAngle += xAngle;
		this.yAngle += yAngle;
	}
}
