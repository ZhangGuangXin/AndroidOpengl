package com.example.opengldemo.ripple;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import com.example.BasicRender;
import com.example.object.Geometry;
import com.example.opengldemo.MainActivity;
import com.example.opengldemo.R;
import com.example.shaderUtil.MatrixHelper;
import com.example.shaderUtil.ObjLoader;
import com.example.shaderUtil.RayPickupUtil;
import com.example.shaderUtil.TextureHelper;
import com.example.object.Vertices3;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.opengl.GLES11Ext;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.Matrix;
import android.util.Log;
import android.view.Surface;

import java.io.IOException;

@SuppressLint("NewApi")
public class RippleRender extends BasicRender {
    private Activity activity;
    private int meshTexture;
    private int tableTexture;

    private float[] projectionMatrix = new float[16];
    private float[] viewMatrix = new float[16];
    private float[] modelViewMatrix = new float[16];
    private float[] it_modelViewMatrix = new float[16];
    private final float[] modelMatrix = new float[16];
    private float[] viewProjectMatrix = new float[16];

    private RippleShaderProgram textureShader;
    private Vertices3 waterPlane;
    private float xAngle = 0;
    private float yAngle = 0;

    private int mCameraId;
    private Camera mCamera;
    private int mOESTextureId;
    private SurfaceTexture mSurfaceTexture;
    private GLSurfaceView mGLSurfaceView;
    private float[] cameraTexmat = new float[16];
    private float[] invertPeojectMatrix = new float[16];
    private float[] tempInvertProjectMatrix = new float[16];
    private Geometry.Ray ray;
    private float[] planeReferPoint = {-11.2765F, 0.0000F, 11.7374F, 0F};
    private float[] resuletPlaneReferPoint = new float[4];
    private float[] planeNormal = {0, 1, 0, 0F};
    private float[] resultPlaneNormal = {0, 0, 0.1f, 0};//new float[4];
    private volatile boolean isDraw = false;
    private float intersectPoint[];

    public RippleRender(Activity activity, GLSurfaceView glSurfaceView) {
        this.activity = activity;
        this.mGLSurfaceView = glSurfaceView;
        initCamera();
    }

    private void initCamera() {
//		mCameraId = Camera.CameraInfo.CAMERA_FACING_BACK;
        mCamera = Camera.open();
//        mCamera.setDisplayOrientation(90);
//        setCameraDisplayOrientation(activity, Camera.CameraInfo.CAMERA_FACING_BACK, mCamera);
	/*	Camera.Parameters parameters = mCamera.getParameters();
//        parameters.setRotation(90);
		parameters.set("orientation", "portrait");
		parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
//		parameters.setPreviewSize(1280, 720);
		mCamera.setDisplayOrientation(180);
//		setCameraDisplayOrientation(mActivity, mCameraId, mCamera);
		mCamera.setParameters(parameters);*/
    }

    public static int createOESTextureObject() {
        int[] tex = new int[1];
        //生成一个纹理
        GLES20.glGenTextures(1, tex, 0);
        //将此纹理绑定到外部纹理上
        GLES20.glBindTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, tex[0]);
        //设置纹理过滤参数
        GLES20.glTexParameterf(GLES11Ext.GL_TEXTURE_EXTERNAL_OES,
                GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST_MIPMAP_NEAREST);
        GLES20.glTexParameterf(GLES11Ext.GL_TEXTURE_EXTERNAL_OES,
                GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);

        GLES20.glTexParameterf(GLES11Ext.GL_TEXTURE_EXTERNAL_OES,
                GL10.GL_TEXTURE_WRAP_S, GL10.GL_CLAMP_TO_EDGE);
        GLES20.glTexParameterf(GLES11Ext.GL_TEXTURE_EXTERNAL_OES,
                GL10.GL_TEXTURE_WRAP_T, GL10.GL_CLAMP_TO_EDGE);
        //解除纹理绑定
        GLES20.glBindTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, 0);
        return tex[0];
    }

    public boolean initSurfaceTexture() {
        //根据外部纹理ID创建SurfaceTexture
        mSurfaceTexture = new SurfaceTexture(mOESTextureId);
        mSurfaceTexture.setOnFrameAvailableListener(new SurfaceTexture.OnFrameAvailableListener() {
            @Override
            public void onFrameAvailable(SurfaceTexture surfaceTexture) {
//                mGLSurfaceView.getMatrix();
                //每获取到一帧数据时请求OpenGL ES进行渲染
				mGLSurfaceView.requestRender();
                Log.i("surfaceTexture", "-----get surfaceTexture data>>>");
            }
        });


//        Camera.CameraInfo info = new Camera.CameraInfo();
//        Camera.getCameraInfo(1, info);
//        int degrees = getDisplayRotation(activity);
//        int result;
//        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
//            result = (info.orientation + degrees) % 360;
//            result = (360 - result) % 360; // compensate the mirror
//        } else { // back-facing
//            result = (info.orientation - degrees + 360) % 360;
//        }
//        mCamera.setDisplayOrientation(result);


        //将此SurfaceTexture作为相机预览输出
        try {
            mCamera.setPreviewTexture(mSurfaceTexture);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //开启预览
        mCamera.startPreview();

        return true;
    }

    @Override
    public void onSurfaceCreated(GL10 arg0, EGLConfig arg1) {
        mOESTextureId = createOESTextureObject();
        initSurfaceTexture();

        GLES20.glClearColor(1F, 1F, 1F, 0F);

        GLES20.glEnable(GLES20.GL_DEPTH_TEST);

        meshTexture = TextureHelper.loadTexture(activity, R.drawable.face);
        tableTexture = TextureHelper.loadTexture(activity, R.drawable.air_hockey_surface);

        textureShader = new RippleShaderProgram(activity,
                R.raw.ripple_vertex_shader, R.raw.ripple_fragment_shader, tableTexture);

//        waterPlane = ObjLoader.load(activity, R.raw.water_test);

        boolean isWait = true;
        while (isWait){
            waterPlane = MainActivity.getWaterPlane();
            if(waterPlane != null){
                isWait = false;
            }else if(waterPlane == null){
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
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
				0f, 0f, -1f,
				0f, 1f, 0f);

		Matrix.translateM(viewMatrix, 0, 0, 0, -3);
    }

    @Override
    public void onDrawFrame(GL10 arg0) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

        drawRipple();
    }

    private void drawRipple() {
        if (mSurfaceTexture != null) {
            mSurfaceTexture.updateTexImage();
            mSurfaceTexture.getTransformMatrix(cameraTexmat);
        }

        float[] matrix = getRippleMatrix();
        textureShader.useProgram();
        textureShader.setUniform(matrix, mOESTextureId);
        textureShader.setItMVmatrix(it_modelViewMatrix);
//        Matrix.rotateM(cameraTexmat, 0, 90,1,0,0);
        textureShader.setCameraTexmat(cameraTexmat);
//        textureShader.setCustomPoint(intersectPoint, modelMatrix);

        waterPlane.bind(textureShader.getPositionLocation(),
                textureShader.getTextureCoordinatesLocation());
        waterPlane.draw();
    }

    private float[] getRippleMatrix() {
        Matrix.setIdentityM(modelMatrix, 0);

//        Matrix.scaleM(modelMatrix, 0, 0.4f, 0f, 0f);
//		Matrix.scaleM(modelMatrix, 0, 2.5f, 2.5f, 2.5f);
        Matrix.translateM(modelMatrix, 0, 0f, 0f, -4F);
        Matrix.translateM(modelMatrix, 0, 1f, 0f, 0);

        Matrix.rotateM(modelMatrix, 0, 90, 1f, 0f, 0f);
        Matrix.rotateM(modelMatrix, 0, 270, 0f, 1f, 0f);
        Matrix.scaleM(modelMatrix, 0, 1.3f, 1f, 1.1f);
//		Matrix.rotateM(modelMatrix, 0, xAngle, 1f, 0f, 0f);
//		Matrix.rotateM(modelMatrix, 0, yAngle, 0f, 1f, 0f);

        float[] temp = new float[16];
        float[] resultMatrix = new float[16];
        Matrix.multiplyMM(modelViewMatrix, 0, viewMatrix, 0, modelMatrix, 0);
        Matrix.multiplyMM(temp, 0, projectionMatrix, 0, modelViewMatrix, 0);
        System.arraycopy(temp, 0, resultMatrix, 0, temp.length);

        Matrix.multiplyMM(tempInvertProjectMatrix, 0, projectionMatrix, 0, viewMatrix, 0);
        Matrix.invertM(invertPeojectMatrix, 0, tempInvertProjectMatrix, 0);

        Matrix.invertM(temp, 0, modelViewMatrix, 0);
        Matrix.transposeM(it_modelViewMatrix, 0, temp, 0);
        // xAngle += 0.5;

        this.isDraw = true;

        return resultMatrix;
    }

    public void rotate(float xAngle, float yAngle) {
        this.xAngle += xAngle;
        this.yAngle += yAngle;
    }

    public void releaseCamera() {
        if (mCamera != null) {
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }
    }

    private float rotateModelMatrix[] = new float[16];

    private Geometry.Vector getPlaneNormal(){
        float[] pointOne = {-11.2765F, 0.0000F, 11.7374F};
        float[] pointTwo = {-11.2765F, 0.0000F, 11.8815F};
        Geometry.Vector vectorOne = new Geometry.Vector(pointOne[0] - pointTwo[0], 0F, pointOne[2] - pointTwo[2]);


//        v  -10.9883 0.0000 12.0256
//        v  -10.9883 0.0000 11.8815
        float[] pointThree = {-10.9883F, 0.0000F, 12.0256F};
//        float[] pointFour =  {-10.9883F, 0.0000F, 11.8815F};
        Geometry.Vector vectorTwo = new Geometry.Vector(pointThree[0] - pointOne[0], 0F, pointThree[2] - pointOne[2]);

        Geometry.Vector normal = vectorOne.crossProduct(vectorTwo);

        return normal;
    }

    public void handleTouchPress(float x, float y) {
        if(!isDraw){
            return;
        }

        try{
            ray = RayPickupUtil.generateRay(x, y, invertPeojectMatrix);

            Matrix.setIdentityM(rotateModelMatrix, 0);
            Matrix.rotateM(rotateModelMatrix, 0, 90, 1f, 0f, 0f);
            Matrix.multiplyMV(resuletPlaneReferPoint, 0, rotateModelMatrix, 0, planeReferPoint, 0);

            Matrix.setIdentityM(rotateModelMatrix, 0);
            Matrix.rotateM(rotateModelMatrix, 0, -90, 1f, 0f, 0f);
            Matrix.multiplyMV(resuletPlaneReferPoint, 0, rotateModelMatrix, 0, resuletPlaneReferPoint, 0);
            int a = 10;

            Matrix.multiplyMM(modelViewMatrix, 0, viewMatrix, 0, rotateModelMatrix, 0);


//            float[] itMatrix = new float[16];
//            Matrix.invertM(itMatrix, 0, rotateModelMatrix, 0);
//            Matrix.transposeM(itMatrix, 0, itMatrix, 0);

//            Geometry.Vector normalVector = getPlaneNormal();
//            resultPlaneNormal[0] = normalVector.x;
//            resultPlaneNormal[1] = normalVector.y;
//            resultPlaneNormal[2] = normalVector.z;
//            Matrix.multiplyMV(resultPlaneNormal, 0, rotateModelMatrix, 0, resultPlaneNormal, 0);

//            Matrix.multiplyMV(resultPlaneNormal, 0, rotateModelMatrix, 0, planeNormal, 0);
//0 0 -1
          /*  resultPlaneNormal[0] = 0F;
            resultPlaneNormal[1] = 0F;
            resultPlaneNormal[2] = -1F;
            float lineVector[] = {ray.vector.x, ray.vector.y, ray.vector.z};
            float linePoint[] = {ray.point.x, ray.point.y, ray.point.z};
            intersectPoint= calPlaneLineIntersectPoint(resultPlaneNormal, resuletPlaneReferPoint, lineVector, linePoint);

            float[] intersecOriginPoint = {intersectPoint[0], intersectPoint[1], intersectPoint[2], 0.0F};
            Matrix.setIdentityM(rotateModelMatrix, 0);
            Matrix.rotateM(rotateModelMatrix, 0, -90, 1f, 0f, 0f);
            float[] result = new float[4];
            Matrix.multiplyMV(result, 0, rotateModelMatrix, 0, intersecOriginPoint, 0);

            Log.i("test", "射线点："+linePoint[0]+", "+linePoint[1]+", "+linePoint[2]);
            Log.i("test", "射线向量："+lineVector[0]+", "+lineVector[1]+", "+lineVector[2]);
            Log.i("test", "平面点："+resuletPlaneReferPoint[0]+", "+resuletPlaneReferPoint[1]+", "+resuletPlaneReferPoint[2]);
            Log.i("test", "平面法线："+resultPlaneNormal[0]+", "+resultPlaneNormal[1]+", "+resultPlaneNormal[2]);

            Log.i("intersectPoint", "----intersecOriginPoint:"+intersecOriginPoint[0]+", "+intersecOriginPoint[1]+", "+intersecOriginPoint[2]);
            isDraw = false;


            float[] tempPoint = new float[4];
            Matrix.setIdentityM(rotateModelMatrix, 0);
            Matrix.rotateM(rotateModelMatrix, 0, 90, 1f, 0f, 0f);
            Matrix.multiplyMV(tempPoint, 0, rotateModelMatrix, 0, planeReferPoint, 0);
            Log.i("testPoint", "----testPoint 1: "+tempPoint[0] + ", "+tempPoint[1]+ ", "+tempPoint[2]);
            Matrix.setIdentityM(rotateModelMatrix, 0);
            Matrix.rotateM(rotateModelMatrix, 0, -90, 1f, 0f, 0f);
            Matrix.multiplyMV(tempPoint, 0, rotateModelMatrix, 0, tempPoint, 0);
            Log.i("testPoint", "----testPoint 2: "+tempPoint[0] + ", "+tempPoint[1]+ ", "+tempPoint[2]);
            x*/
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /// <summary>
    /// 求一条直线与平面的交点
    /// </summary>
    /// <param name="planeVector">平面的法线向量，长度为3</param>
    /// <param name="planePoint">平面经过的一点坐标，长度为3</param>
    /// <param name="lineVector">直线的方向向量，长度为3</param>
    /// <param name="linePoint">直线经过的一点坐标，长度为3</param>
    /// <returns>返回交点坐标，长度为3</returns>
    private float[] calPlaneLineIntersectPoint(float[] planeVector, float[] planePoint, float[] lineVector, float[] linePoint) {
        float[] returnResult = new float[3];
        float vp1, vp2, vp3, n1, n2, n3, v1, v2, v3, m1, m2, m3, t, vpt;
        vp1 = planeVector[0];
        vp2 = planeVector[1];
        vp3 = planeVector[2];
        n1 = planePoint[0];
        n2 = planePoint[1];
        n3 = planePoint[2];
        v1 = lineVector[0];
        v2 = lineVector[1];
        v3 = lineVector[2];
        m1 = linePoint[0];
        m2 = linePoint[1];
        m3 = linePoint[2];
        vpt = v1 * vp1 + v2 * vp2 + v3 * vp3;
        //首先判断直线是否与平面平行
        if (vpt == 0) {
            returnResult = null;
        } else {
            t = ((n1 - m1) * vp1 + (n2 - m2) * vp2 + (n3 - m3) * vp3) / vpt;
            returnResult[0] = m1 + v1 * t;
            returnResult[1] = m2 + v2 * t;
            returnResult[2] = m3 + v3 * t;
        }
        return returnResult;
    }
}
