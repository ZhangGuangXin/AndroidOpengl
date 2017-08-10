package com.example;

import android.opengl.GLSurfaceView;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by L on 2017/8/9.
 */

public abstract class BasicRender  implements GLSurfaceView.Renderer {
    @Override
    public abstract void onSurfaceCreated(GL10 gl, EGLConfig config);

    @Override
    public abstract void onSurfaceChanged(GL10 gl, int width, int height);

    @Override
    public abstract void onDrawFrame(GL10 gl);

    public abstract void rotate(float xAngle, float yAngle);
}
