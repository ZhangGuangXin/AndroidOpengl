package com.example.opengldemo.normalmapping;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.GLUtils;
import android.util.Log;
import android.widget.Toast;

import com.example.opengldemo.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by egslava on 18/03/2017.
 */

public class MyRenderer implements GLSurfaceView.Renderer {

    public static final String TAG = "MyRenderer";
    private final String mRawTextResourceVertexShader;
    private final String mRawTextResourcePixelShader;
    private Context mContext;
    private int mTexture;
    public Triangle triangle;
    private Square square;
    private int mNormal;

    public MyRenderer(Context context) {
        mRawTextResourceVertexShader = getRawTextResource(context, 
        		R.raw.vertex_shader);
        mRawTextResourcePixelShader = getRawTextResource(context, 
        		R.raw.pixel_shader_texture_normals_distance);


        mContext = context;
    }

    private String getRawTextResource(Context context, int resource) {
        final InputStream inputStream = context.getResources().openRawResource(resource);
        final BufferedReader bufferedReaderVertexShader = new BufferedReader(new InputStreamReader(inputStream));

        StringBuilder total = new StringBuilder();
        try {
            String readLine;
            do {
                readLine = bufferedReaderVertexShader.readLine();
                if (readLine != null){
                    total.append( readLine );
                    total.append( "\n");
                }
            }while (readLine != null);

            Log.d(TAG, "getRawTextResource: " + total.toString());
            return total.toString();

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();;
            return null;
        }
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES20.glClearColor(0, 0, 0, 0);
        Log.d(TAG, "onSurfaceCreated: ");

        mTexture = loadTexture(mContext, R.drawable.bricks);
        mNormal = loadTexture(mContext, R.drawable.bricks_norm);
        triangle = new Triangle(mRawTextResourceVertexShader, mRawTextResourcePixelShader, mTexture, mNormal);
        square = new Square();
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
        Log.d(TAG, "onSurfaceChanged: ");
    }

    float time;
    float d = 0.005f;
    final float D = 0.005f;

    @Override
    public void onDrawFrame(GL10 gl) {
        if (time >= 1){
            d = -D;
        }
        if (time <= 0){
            d = D;
        }

        time += d;

        GLES20.glClearColor(time, time, time, 1.0f);
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

        triangle.draw();
    }

    public static int loadShader(int type, String shaderCode){

        int shader = GLES20.glCreateShader(type);
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);
        return shader;

    }

    int loadTexture(Context context, int res){
        int textures[] = {-1};
        GLES20.glGenTextures(1, textures, 0);
        int texture = textures[0];

        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), res);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texture);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_NEAREST);
        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0 );

        bitmap.recycle();

        return texture;
    }
}
