package com.example.opengldemo.ripple;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.opengl.GLSurfaceView;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Toast;

public class RippleDemoActivity extends Activity {
	private GLSurfaceView view;
	private RippleRender render;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.applyCamerapermission();
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
		if(view != null) {
			try{
				view.onPause();
			}catch (Exception e){

			}

		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		if(view != null) {
			try{
				view.onResume();
			}catch (Exception e){

			}

		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if(view != null) {
			try{
				render.releaseCamera();
			}catch (Exception e){

			}

		}
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


	public void applyCamerapermission(){
		if(Build.VERSION.SDK_INT>=23){
			//检查是否已经给了权限
			int checkpermission= ContextCompat.checkSelfPermission(this.getApplicationContext(),
					Manifest.permission.CAMERA);
			if(checkpermission!= PackageManager.PERMISSION_GRANTED){//没有给权限
				Log.e("permission","动态申请");
				//参数分别是当前活动，权限字符串数组，requestcode
				ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA}, 1);
			}else {
				initView();
				setContentView(view);
			}
		}else {
			initView();
			setContentView(view);
		}
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		//grantResults数组与权限字符串数组对应，里面存放权限申请结果
		if(grantResults[0]== PackageManager.PERMISSION_GRANTED){
			try{
				initView();
				setContentView(view);
			}catch (Exception e){
				e.printStackTrace();
				Toast.makeText(this, "请开启摄像头权限", Toast.LENGTH_LONG).show();
				finish();
			}
//			Toast.makeText(this,"已授权",Toast.LENGTH_SHORT).show();
		}else{
			Toast.makeText(this, "请开启摄像头权限", Toast.LENGTH_LONG).show();
		}
	}
}
