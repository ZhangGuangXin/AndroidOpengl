package com.example.opengldemo.normalmapping;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;

public class NormalMappingActivity extends Activity {

    private static final String TAG = NormalMappingActivity.class.getSimpleName();
    private MyGLSurfaceView myGLSurfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myGLSurfaceView = new MyGLSurfaceView(this);
        setContentView(myGLSurfaceView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onResume() {
        super.onResume();

        goFullScreen();
    }

    private void goFullScreen() {
        View decorView = getWindow().getDecorView();
//        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                | View.SYSTEM_UI_FLAG_FULLSCREEN
//                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        int widthPixels = getResources().getDisplayMetrics().widthPixels;
        int heightPixels = getResources().getDisplayMetrics().heightPixels;
        float x = event.getX() / (float) widthPixels;
        float y = event.getY() / (float) heightPixels;


        myGLSurfaceView.mRenderer.triangle.vLightPosition.set(2.0f*(x - 0.5f), (-y + 0.5f) * 2.0f); // going to a monitor coords
        Log.d(TAG, "onTouchEvent, x: " + x + ", y: " + y);
        return true;
    }
}
