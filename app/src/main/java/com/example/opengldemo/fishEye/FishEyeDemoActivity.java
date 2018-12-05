package com.example.opengldemo.fishEye;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

import com.example.ViewThouchListener;

public class FishEyeDemoActivity extends Activity {
	private GLSurfaceView view;
	private FishEyeRender render;

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
		render = new FishEyeRender(this);
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
