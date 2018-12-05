package com.example.opengldemo.egl;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.opengl.EGLContext;
import android.opengl.GLES20;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.SurfaceHolder;
import android.widget.ImageView;

import java.nio.IntBuffer;

import javax.microedition.khronos.opengles.GL10;

public class PbRender implements Runnable {
	private SurfaceHolder surfaceHolder;
	private Context context;
	private EGLContext shareContext;
	
	private EGLPBHelper eglpbHelper;
	private boolean isRun;
	private ImageView imageView;
	
    private Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bitmap bitmap = (Bitmap) msg.obj;
            imageView.setImageBitmap(bitmap);
        }
    };
    
	public PbRender(SurfaceHolder surfaceHolder, Context context,
                    EGLContext shareContext, ImageView imageView){
		this.surfaceHolder = surfaceHolder;
		this.context = context;
		this.shareContext = shareContext;
		this.imageView = imageView;
	}
	
	public void startRender(){
		isRun = true;
		
		Thread pbThread = new Thread(this);
	    pbThread.start();
	}
	
	public void stopRender(){
		isRun = false;
	}
	

	@Override
	public void run() {
		Activity activity = (Activity)context;
        eglpbHelper = new EGLPBHelper(surfaceHolder, activity);

        eglpbHelper.initEgl(shareContext);
        eglpbHelper.onCreate();
        
        while (isRun) {
            eglpbHelper.onFrame();
            eglpbHelper.swap();

            IntBuffer ib = IntBuffer.allocate(480 * 800);
            GLES20.glReadPixels(0, 0, 480, 800, GL10.GL_RGBA,
            		GL10.GL_UNSIGNED_BYTE, ib);
            Bitmap bitmap = frameToBitmap(480, 800, ib);

            Message msg = new Message();
            msg.obj = bitmap;
            handler.sendMessage(msg);
            Log.i("", "");
        }
        eglpbHelper.destroyEgl();
	}

	private static Bitmap frameToBitmap(int width, int height, IntBuffer ib) {
        int pixs[] = ib.array();
        for (int y = 0; y < height / 2; y++) {
            for (int x = 0; x < width; x++) {
                int pos1 = y * width + x;
                int pos2 = (height - 1 - y) * width + x;

                int tmp = pixs[pos1];
                pixs[pos1] = (pixs[pos2] & 0xFF00FF00) | ((pixs[pos2] >> 16) & 0xff) | ((pixs[pos2] << 16) & 0x00ff0000); // ABGR->ARGB
                pixs[pos2] = (tmp & 0xFF00FF00) | ((tmp >> 16) & 0xff) | ((tmp << 16) & 0x00ff0000);
            }
        }
        if (height % 2 == 1) {
            for (int x = 0; x < width; x++) {
                int pos = (height / 2 + 1) * width + x;
                pixs[pos] = (pixs[pos] & 0xFF00FF00) | ((pixs[pos] >> 16) & 0xff) | ((pixs[pos] << 16) & 0x00ff0000);
            }
        }

        return Bitmap.createBitmap(pixs, width, height, Bitmap.Config.ARGB_8888);
    }
}
