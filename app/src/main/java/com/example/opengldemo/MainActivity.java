package com.example.opengldemo;

import com.example.object.Vertices3;
import com.example.opengldemo.GaussianBlur.GaussianBlurDemoActivity;
import com.example.opengldemo.bumpMapping.BumpMappingDemoActivity;
import com.example.opengldemo.directLight.DirectLightDemoActivity;
import com.example.opengldemo.egl.EglTestActivity;
import com.example.opengldemo.embossed.EmbossedDemoActivity;
import com.example.opengldemo.fbo.FboDemoActivity;
import com.example.opengldemo.fishEye.FishEyeDemoActivity;
import com.example.opengldemo.normalmapping.NormalMappingActivity;
import com.example.opengldemo.pointLight.PointLightDemoActivity;
import com.example.opengldemo.reflectLight.ReflectLightDemoActivity;
import com.example.opengldemo.ripple.RippleDemoActivity;
import com.example.opengldemo.rotate.RotateDemoActivity;
import com.example.opengldemo.shadowMapping.ShadowMappingtDemoActivity;
import com.example.opengldemo.processTexture.ProcessTextureDemoActivity;
import com.example.opengldemo.skybox.SkyBoxDemoActivity;
import com.example.opengldemo.sobelOperator.SobelDemoActivity;
import com.example.opengldemo.twirl.TwirlDemoActivity;
import com.example.opengldemo.wobble.WobbleDemoActivity;
import com.example.opengldemo.rayPickup.RayPickupDemoActivity;
import com.example.shaderUtil.ObjLoader;

import android.app.Activity;
import android.content.Intent;
import android.opengl.Matrix;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends Activity{
	private static Vertices3 waterPlane;
//	private SensorUtil sensorUtil;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_main);

//		sensorUtil = new SensorUtil(this);
		Thread loadTask = new Thread(new LoadPlaneTask());
		loadTask.start();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
//		sensorUtil.stopSensor();
	}

	public void rotateEvent(View view){
		Intent intent = new Intent(this, RotateDemoActivity.class);
		startActivity(intent);
	}
	
	public void WobbleEvent(View view){
		Intent intent = new Intent(this, WobbleDemoActivity.class);
		startActivity(intent);
	}
	
	public void RippleEvent(View view){
		Intent intent = new Intent(this, RippleDemoActivity.class);
		startActivity(intent);
	}
	
	public void RotateFragmentEvent(View view){
		Intent intent = new Intent(this, ProcessTextureDemoActivity.class);
		startActivity(intent);
	}

	public void DirectLightEvent(View view){
		Intent intent = new Intent(this, DirectLightDemoActivity.class);
		startActivity(intent);
	}

	public void PointLightEvent(View view){
		Intent intent = new Intent(this, PointLightDemoActivity.class);
		startActivity(intent);
	}

	public void ReflectLightEvent(View view){
		Intent intent = new Intent(this, ReflectLightDemoActivity.class);
		startActivity(intent);
	}

	public void BumpMappintEvent(View view){
		Intent intent = new Intent(this, BumpMappingDemoActivity.class);
		startActivity(intent);
	}

	public void RayPickupEvent(View view){
		Intent intent = new Intent(this, RayPickupDemoActivity.class);
		startActivity(intent);
	}

	public void SkyboxEvent(View view){
		Intent intent = new Intent(this, SkyBoxDemoActivity.class);
		startActivity(intent);
	}

	public void FboEvent(View view){
		Intent intent = new Intent(this, FboDemoActivity.class);
		startActivity(intent);
	}

	public void SobelEvent(View view){
		Intent intent = new Intent(this, SobelDemoActivity.class);
		startActivity(intent);
	}

	public void GaussianBlureEvent(View view){
		Intent intent = new Intent(this, GaussianBlurDemoActivity.class);
		startActivity(intent);
	}

	public void fishEyeEvent(View view){
		Intent intent = new Intent(this, FishEyeDemoActivity.class);
		startActivity(intent);
	}

	public void EmbossedEvent(View view){
		Intent intent = new Intent(this, EmbossedDemoActivity.class);
		startActivity(intent);
	}

	public void twirlEvent(View view){
		Intent intent = new Intent(this, TwirlDemoActivity.class);
		startActivity(intent);
	}

	public void eglEvent(View view){
		Intent intent = new Intent(this, EglTestActivity.class);
		startActivity(intent);
	}
	
	public void shadowEvent(View view){
		Intent intent = new Intent(this, ShadowMappingtDemoActivity.class);
		startActivity(intent);
	}

	public void normalMappingEvent(View view){
		Intent intent = new Intent(this, NormalMappingActivity.class);
		startActivity(intent);
	}

	private class LoadPlaneTask implements Runnable{
		@Override
		public void run() {
			waterPlane = ObjLoader.load(MainActivity.this, R.raw.water_test);
		}
	}

	public static Vertices3 getWaterPlane() {
		return waterPlane;
	}
}
