package com.example.opengldemo.embossed;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

import com.example.ViewThouchListener;

public class EmbossedDemoActivity extends Activity {
	private GLSurfaceView view;
	private EmbossedRender render;

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
		render = new EmbossedRender(this);
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
