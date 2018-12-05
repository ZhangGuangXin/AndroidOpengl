package com.example.shaderUtil;

import android.opengl.Matrix;

import com.example.object.Geometry;

public class RayPickupUtil {

    public static Geometry.Ray generateRay(float normalizedX, float normalizedY, float[] invertedViewProjectMatrix){
        float[] nearPointNdc = {normalizedX, normalizedY, -1, 1};
        float[] farPointNdc = {normalizedX, normalizedY, 1, 1};

        float[] nearPointWorld = new float[4];
        float[] farPointWorld = new float[4];

        Matrix.multiplyMV(nearPointWorld, 0, invertedViewProjectMatrix, 0, nearPointNdc, 0);
        Matrix.multiplyMV(farPointWorld, 0, invertedViewProjectMatrix, 0, farPointNdc, 0);

        divideByW(nearPointWorld);
        divideByW(farPointWorld);

        Geometry.Point nearPointRay = new Geometry.Point(nearPointWorld[0], nearPointWorld[1], nearPointWorld[2]);
        Geometry.Point farPointRay = new Geometry.Point(farPointWorld[0], farPointWorld[1], farPointWorld[2]);

        Geometry.Ray ray = new Geometry.Ray(nearPointRay, Geometry.vectorBetween(nearPointRay, farPointRay));

        return ray;
    }

    private static void divideByW(float[] vector){
        vector[0] /= vector[3];
        vector[1] /= vector[3];
        vector[2] /= vector[3];
    }
}
