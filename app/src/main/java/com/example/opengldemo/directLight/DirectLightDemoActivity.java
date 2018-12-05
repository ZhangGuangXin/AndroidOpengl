package com.example.opengldemo.directLight;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

import com.example.ViewThouchListener;

public class DirectLightDemoActivity extends Activity {
	private GLSurfaceView view;
	private DirectLightRender render;

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
		render = new DirectLightRender(this);
		view.setRenderer(render);

		ViewThouchListener touchListener = new ViewThouchListener(view, render);
		view.setOnTouchListener(touchListener);

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

}
