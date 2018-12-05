package com.example.opengldemo.normalmapping;

import android.content.Context;
import android.opengl.GLSurfaceView;

/**
 * Created by egslava on 18/03/2017.
 */

public class MyGLSurfaceView extends GLSurfaceView {

    public final MyRenderer mRenderer;

    public MyGLSurfaceView(Context context) {
        super(context);
        setEGLContextClientVersion(2);

        mRenderer = new MyRenderer(context);
        setRenderer(mRenderer);

    }
}
