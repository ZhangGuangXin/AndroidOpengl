package com.example.opengldemo.ripple;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;

import com.example.ViewThouchListener;

public class RippleDemoActivity extends Activity {
	private GLSurfaceView view;
	private RippleRender render;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		initView();
		setContentView(view);
	}

	private void initView() {
		view = new GLSurfaceView(this);

		view.setEGLContextClientVersion(2);
		view.setEGLConfigChooser(8, 8, 8, 8, 16, 0);
		render = new RippleRender(this, view);
		view.setRenderer(render);

//		ViewThouchListener touchListener = new ViewThouchListener(view, render);
		view.setOnTouchListener(new ViewThouchListener());
	}

	@Override
	protected void onPause() {
		super.onPause();
		view.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		view.onResume();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		render.releaseCamera();
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
			Log.i("intersectPoint", "----touch point: "+normalizedX+" ,"+normalizedY);
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				view.queueEvent(new Runnable() {
					@Override
					public void run() {
						render.handleTouchPress(normalizedX ,normalizedY);
					}
				});
			}
//			else if(event.getAction() == MotionEvent.ACTION_MOVE){
//				render.handleTouchPress(normalizedX, normalizedY, false);
//			}
			return true;
		}
	}
}
