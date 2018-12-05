package com.example;

import android.opengl.GLSurfaceView;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by L on 2017/8/9.
 */

public class ViewThouchListener implements View.OnTouchListener {
    private GLSurfaceView view;
    private BasicRender render;

    private float lastX = 0;
    private float lastY = 0;

    public ViewThouchListener(GLSurfaceView view, BasicRender render){
        this.view = view;
        this.render = render;
    }

    @Override
    public boolean onTouch(View v, final MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            lastX = 0;
            lastY = 0;
        }

        view.queueEvent(new Runnable() {

            @Override
            public void run() {
                float currentX = event.getX();
                float currentY = event.getY();

                float yAngle = 0;
                float xAngle = 0;
                yAngle = (currentX - lastX) * 0.5f;
                xAngle = (currentY - lastY) * 0.5f;
                if (lastY != 0 && lastX != 0) {
                    render.rotate(xAngle, yAngle);
                }
                lastX = currentX;
                lastY = currentY;
            }
        });

        return true;
    }
}
