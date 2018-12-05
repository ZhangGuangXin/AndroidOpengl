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

import java.util.ArrayList;


/**
 * Created by L on 2017/11/25.
 */

@SuppressLint("NewApi")
public class EGLHelper {
    public SurfaceHolder surfaceHolder;
    private EGLConfig eglConfig = null;
    private EGLDisplay eglDisplay = EGL14.EGL_NO_DISPLAY;
    private EGLContext eglContext = EGL14.EGL_NO_CONTEXT;
    private EGLSurface eglSurface;
    private TwirlRender render;
    private Activity activity;

    float redColor = 1f;
    float blueColor = 1f;
    float greenColor = 1f;
    
    private Object writeLock = new Object();
    private Object readLock = new Object();
    private ArrayList<EventListener> eventList = new ArrayList<EventListener>();

    public EGLHelper(SurfaceHolder surfaceHolder, Activity activity) {
        this.surfaceHolder = surfaceHolder;
        render = new TwirlRender(activity);
        render.setDrawFbo(true);
    }

    public void initEgl(){
        eglDisplay = EGL14.eglGetDisplay(EGL14.EGL_DEFAULT_DISPLAY);
        int[] version = new int[2];
        if (!EGL14.eglInitialize(eglDisplay, version, 0, version, 1)) {
            throw new RuntimeException("EGL error " + EGL14.eglGetError());
        }
        int[] configAttribs = {
                EGL14.EGL_RENDERABLE_TYPE, EGL14.EGL_OPENGL_ES2_BIT,
                EGL14.EGL_SURFACE_TYPE, EGL14.EGL_WINDOW_BIT, //EGL_PBUFFER_BIT
                EGL14.EGL_BUFFER_SIZE, 32,
                EGL14.EGL_BLUE_SIZE, 8,
                EGL14.EGL_GREEN_SIZE, 8,
                EGL14.EGL_RED_SIZE, 8,
                EGL14.EGL_ALPHA_SIZE, 8,
                EGL14.EGL_NONE
        };
        int[] numConfigs = new int[1];
        EGLConfig[] configs = new EGLConfig[1];
        if (!EGL14.eglChooseConfig(eglDisplay, configAttribs, 0, configs, 0, configs.length, numConfigs, 0)) {
            throw new RuntimeException("EGL error " + EGL14.eglGetError());
        }
        eglConfig = configs[0];

        final int[] attributes = {
//                EGL14.EGL_WIDTH, 480,
//                EGL14.EGL_HEIGHT, 800,
                EGL14.EGL_NONE};
        eglSurface = EGL14.eglCreateWindowSurface(eglDisplay, eglConfig, surfaceHolder, attributes, 0);
//        eglSurface = EGL14.eglCreatePbufferSurface(eglDisplay, eglConfig, attributes, 0);
        if(eglSurface == EGL14.EGL_NO_SURFACE){
            throw new RuntimeException("EGL error " + EGL14.eglGetError());
        }

        int[] contextAttribs = {
                EGL14.EGL_CONTEXT_CLIENT_VERSION, 2,
                EGL14.EGL_NONE
        };
        eglContext = EGL14.eglCreateContext(eglDisplay, eglConfig, EGL14.EGL_NO_CONTEXT, contextAttribs, 0);
        if (eglContext == EGL14.EGL_NO_CONTEXT) {
            throw new RuntimeException("EGL error " + EGL14.eglGetError());
        }
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

    public void addEvent(EventListener event){
    	synchronized (writeLock) {
    		this.eventList.add(event);
		}
    }
    
    public void onFrame(){
    	synchronized (readLock) {
    		if(!eventList.isEmpty()){
    			EventListener event = eventList.remove(0);
    			event.event();
        	}        	 
		}
        render.onDrawFrame(null);
       /* GLES20.glClearColor(redColor, greenColor, blueColor, 1F);
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);
//          GLES20.glClearColor(1F, 0F, 0F, 0F);

        redColor = redColor - 0.01f;
        blueColor = blueColor - 0.001f;
        greenColor = greenColor - 0.02f;
        if(redColor <= 0){
            redColor = 1f;
        }
        if(greenColor <= 0){
            greenColor = 0;
        }
        if(blueColor <=0 ){
            blueColor = 0;
        }*/
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