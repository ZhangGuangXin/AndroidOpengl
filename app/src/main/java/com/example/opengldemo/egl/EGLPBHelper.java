package com.example.opengldemo.egl;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.opengl.EGL14;
import android.opengl.EGLConfig;
import android.opengl.EGLContext;
import android.opengl.EGLDisplay;
import android.opengl.EGLSurface;
import android.view.SurfaceHolder;

import com.example.opengldemo.twirl.TwirlRender;


/**
 * Created by L on 2017/11/25.
 */

@SuppressLint("NewApi")
public class EGLPBHelper {
    public SurfaceHolder surfaceHolder;
    public EGLConfig eglConfig = null;
    public EGLDisplay eglDisplay = EGL14.EGL_NO_DISPLAY;
    public EGLContext eglContext = EGL14.EGL_NO_CONTEXT;
    private EGLSurface eglSurface;
    private TwirlRender render;
    private Activity activity;


    public EGLPBHelper(SurfaceHolder surfaceHolder, Activity activity) {
        this.surfaceHolder = surfaceHolder;
        render = new TwirlRender(activity);
        render.setDrawFbo(false);
    }

    public void initEgl(EGLContext shareContext){
        // 获取显示设备(默认的显示设??)
        eglDisplay = EGL14.eglGetDisplay(EGL14.EGL_DEFAULT_DISPLAY);
        // 初始??
        int[] version = new int[2];
        if (!EGL14.eglInitialize(eglDisplay, version, 0, version, 1)) {
            throw new RuntimeException("EGL error " + EGL14.eglGetError());
        }
        // 获取FrameBuffer格式和能??
        int[] configAttribs = {
//                EGL14.EGL_RENDERABLE_TYPE, EGL14.EGL_OPENGL_ES2_BIT,
//                EGL14.EGL_SURFACE_TYPE, EGL14.EGL_PBUFFER_BIT, //EGL_WINDOW_BIT
//                EGL14.EGL_BUFFER_SIZE, 32,
//                EGL14.EGL_BLUE_SIZE, 8,
//                EGL14.EGL_GREEN_SIZE, 8,
//                EGL14.EGL_RED_SIZE, 8,
//                EGL14.EGL_ALPHA_SIZE, 8,

                EGL14.EGL_BUFFER_SIZE, 32,
                EGL14.EGL_ALPHA_SIZE, 8,
                EGL14.EGL_BLUE_SIZE, 8,
                EGL14.EGL_GREEN_SIZE, 8,
                EGL14.EGL_RED_SIZE, 8,
                EGL14.EGL_RENDERABLE_TYPE, EGL14.EGL_OPENGL_ES2_BIT,
                EGL14.EGL_SURFACE_TYPE, EGL14.EGL_PBUFFER_BIT,
                EGL14.EGL_NONE
        };
        int[] numConfigs = new int[1];
        EGLConfig[] configs = new EGLConfig[1];
        if (!EGL14.eglChooseConfig(eglDisplay, configAttribs, 0, configs, 0, configs.length, numConfigs, 0)) {
            throw new RuntimeException("EGL error " + EGL14.eglGetError());
        }
        eglConfig = configs[0];
        // 创建OpenGL上下??(可以先不设置EGLSurface，但EGLContext必须创建??
        // 因为后面调用GLES方法基本都要依赖于EGLContext)

        final int[] attributes = {
                EGL14.EGL_WIDTH, 480,
                EGL14.EGL_HEIGHT, 800,
                EGL14.EGL_NONE};
//        eglSurface = EGL14.eglCreateWindowSurface(eglDisplay, eglConfig, surfaceHolder, attributes, 0);
        eglSurface = EGL14.eglCreatePbufferSurface(eglDisplay, eglConfig, attributes, 0);
        if(eglSurface == EGL14.EGL_NO_SURFACE){
            throw new RuntimeException("EGL error " + EGL14.eglGetError());
        }

        int[] contextAttribs = {
                EGL14.EGL_CONTEXT_CLIENT_VERSION, 2,
                EGL14.EGL_NONE
        };
//        shareContext = EGL14.eglGetCurrentContext();
        eglContext = EGL14.eglCreateContext(eglDisplay, eglConfig,/* EGL14.EGL_NO_CONTEXT*/ shareContext, contextAttribs, 0);
        if (eglContext == EGL14.EGL_NO_CONTEXT) {
            throw new RuntimeException("EGL error " + EGL14.eglGetError());
        }

        // 设置默认的上下文环境和输出缓冲区(小米4上如果不设置有效的eglSurface后面创建??色器会失败，可以先创建一个默认的eglSurface)
        //EGL14.eglMakeCurrent(eglDisplay, surface.eglSurface, surface.eglSurface, eglContext);
        EGL14.eglMakeCurrent(eglDisplay, eglSurface, eglSurface, eglContext);
    }

    public void swap(){
        EGL14.eglSwapBuffers(eglDisplay, eglSurface);
    }

    public void onCreate(){
        /*GLES20.glClearColor(redColor, greenColor, blueColor, 1F);
        GLES20.glViewport(0, 0, 500, 500);*/
        render.onSurfaceCreated(null, null);
        render.onSurfaceChanged(null, 480, 800);
    }

    public void onFrame() {
        render.onDrawFrame(null);
    }
    
    public void destroyEgl(){
    	render.destroy();
    	
    	EGL14.eglMakeCurrent(eglDisplay, EGL14.EGL_NO_SURFACE, EGL14.EGL_NO_SURFACE, EGL14.EGL_NO_CONTEXT);
    	EGL14.eglDestroyContext(eglDisplay, eglContext);
    	EGL14.eglDestroySurface(eglDisplay, eglSurface);
    	EGL14.eglTerminate(eglDisplay);
    	    
    	eglDisplay = EGL14.EGL_NO_DISPLAY;
    	eglSurface = EGL14.EGL_NO_SURFACE;
    	eglContext = EGL14.EGL_NO_CONTEXT;
    }
}