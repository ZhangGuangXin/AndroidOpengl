package com.example.opengldemo.egl;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.opengl.EGL14;
import android.opengl.EGLContext;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;

public class OpenglView extends SurfaceView implements SurfaceHolder.Callback, Runnable {
    private SurfaceHolder surfaceHolder;
    private EGLHelper eglHelper;
    private EGLContext shareContext;
   
    private ImageView imageView;
    private boolean isRun = false;
    private PbRender pbRender;
    private int index = 0;
    
    public OpenglView(Context context) {
        super(context);

        this.surfaceHolder = getHolder();
        this.surfaceHolder.addCallback(this);
    }

    public void setImageView(ImageView imageView) {
    	this.imageView = imageView;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
    	isRun = true;
    	
    	ViewThouchListener touchListener = new ViewThouchListener();
    	this.setOnTouchListener(touchListener);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Thread graphicThread = new Thread(this);
        graphicThread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
    	isRun = false;
    	pbRender.stopRender();
    }
    
    @SuppressLint("NewApi")
	@Override
    public void run() { //该线程为前台渲染线程
	//为前台渲染线程配置egl, 注意这里的surfaceHolder很重要，在配置的egl里创建surface的时候需要用到。相当于surfaceHolder是egl和view之间的桥梁
        Activity activity = (Activity)getContext();
        eglHelper = new EGLHelper(surfaceHolder, activity);
        eglHelper.initEgl();
        shareContext = EGL14.eglGetCurrentContext();
        eglHelper.onCreate();

	//创建后台渲染线程，在后台渲染线程中会创建egl
        Context context = OpenglView.this.getContext();
        pbRender = new PbRender(surfaceHolder, context, shareContext, imageView);
        pbRender.startRender();

        while (isRun) {
            eglHelper.onFrame();
            eglHelper.swap();
        }
        eglHelper.destroyEgl();
    }

    private class ViewThouchListener implements OnTouchListener {
		private float lastX = 0;
		private float lastY = 0;

		@Override
		public boolean onTouch(View v, final MotionEvent event) {
			if(event == null){
				return false;
			}

			final float normalizedX = (event.getX() / (float) v.getWidth()) * 2 - 1;
			final float normalizedY = -((event.getY() / (float) v.getHeight()) * 2 - 1);
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				eglHelper.addEvent(new DownListener());
			}else if(event.getAction() == MotionEvent.ACTION_MOVE){
				eglHelper.addEvent(new TouchListener());
			}
			return true;
		}
	}
    
    private class DownListener implements EventListener {

		@Override
		public void event() {
			Log.i("", "------>>>down move event: "+index++);
		}
    	
    }
    
    private class TouchListener implements EventListener {

		@Override
		public void event() {
			Log.i("", "------>>>touch move event"+index++);
		}
    	
    }
}
