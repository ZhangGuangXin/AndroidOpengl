package com.example.opengldemo.ripple;

import com.example.shaderUtil.BasicShaderProgram;

import android.content.Context;
import android.opengl.GLES11Ext;
import android.opengl.GLES20;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.MainThread;

import java.util.Random;

/**
 * Created by L on 2017/8/8.
 */

public class RippleShaderProgram extends BasicShaderProgram implements Runnable{
    private final int aTextureUnitLocation;
    private final int uTimeLocation;
    private final int textureCoordinateYLocation;
    private final int uCustomPointLocation;
    private final int uModelMatrixLocation;
    private final int uItModelViewMatrixLocation;
    private final int uCameraTexmatLocation;
    private float time;
    private float textureCoordinateY;
    private int tableTexture;

    private volatile boolean isWave = false;
    private Random random = new Random();
//    private float randomX, randomZ;
    private float[] customPoint = new float[2];

    public RippleShaderProgram(Context context, int vertexShaderResourceId, int fragmentShaderResourceId, int tableTexture) {
        super(context, vertexShaderResourceId, fragmentShaderResourceId);

        aTextureUnitLocation = GLES20.glGetUniformLocation(program, "a_TextureUnit");
        uTimeLocation = GLES20.glGetUniformLocation(program, "time");
        textureCoordinateYLocation = GLES20.glGetUniformLocation(program, "textureCoordinateY");
        this.tableTexture = tableTexture;
        uCustomPointLocation = GLES20.glGetUniformLocation(program, "customPoint");
        uModelMatrixLocation = GLES20.glGetUniformLocation(program, "u_modelMatrix");
        uItModelViewMatrixLocation = GLES20.glGetUniformLocation(program, "it_Matrix");
        uCameraTexmatLocation = GLES20.glGetUniformLocation(program, "cameraTexmat");
    }

    Thread loadTask = null;

    @Override
    public void setUniform(float[] matrix, int textureId) {
        super.setUniform(matrix, textureId);

        if(loadTask == null) {
            customPoint[0] = random.nextInt(20)-10;
            customPoint[1] = random.nextInt(20)-10;
            GLES20.glUniform2fv(uCustomPointLocation, 1, customPoint, 0);

            loadTask = new Thread(this);
            loadTask.start();
        }

        GLES20.glActiveTexture(GLES20.GL_TEXTURE1);
//        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, tableTexture);
        GLES20.glBindTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, tableTexture);
        GLES20.glUniform1i(aTextureUnitLocation, 1);


//        if(time < 15){
            if(isWave) {
                time += 0.0015f;
                GLES20.glUniform1f(uTimeLocation, time);
                isWave = false;
            }else if(!isWave && time > 1){
//                time = -1.0f;
//                GLES20.glUniform1f(uTimeLocation, time);

                customPoint[0] = random.nextInt(20)-10;
                customPoint[1] = random.nextInt(20)-10;
                GLES20.glUniform2fv(uCustomPointLocation, 1, customPoint, 0);
                time = 0;

            }
//        }else {
//            time = 0;
//            GLES20.glUniform1f(uTimeLocation, time);
//        }

//        textureCoordinateY += 0.005;
        GLES20.glUniform1f(textureCoordinateYLocation, textureCoordinateY);
    }

    public void setItMVmatrix(float[] itMVmatrix){
        GLES20.glUniformMatrix4fv(uItModelViewMatrixLocation, 1, false, itMVmatrix, 0);
    }

    public void setCameraTexmat(float[] cameraTexmat){
        GLES20.glUniformMatrix4fv(uCameraTexmatLocation, 1, false, cameraTexmat, 0);
    }

    private void setCustomPoint( float[] customPoint, float[] modelMatrix){
        if(customPoint == null){
            return;
        }

        try{//java.lang.IllegalArgumentException: length - offset < count*3 < needed
            GLES20.glUniform3fv(uCustomPointLocation, 1, customPoint, 0);
//            uModelMatrixLocation
            GLES20.glUniform3fv(uModelMatrixLocation, 1, modelMatrix, 0);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    int waveCount = 0;

    @Override
    public void run() {
        while (true){
            if(time <= 1){
                try {
                    Thread.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                isWave = true;
                waveCount++;
            }
        }
    }
}
