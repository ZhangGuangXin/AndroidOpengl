package com.example.opengldemo.normalmapping;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

/**
 * Created by egslava on 18/03/2017.
 */

/**
 * @deprecated isn't used
 */
public class Square {

    static final float[] vertices = {
        -0.5f,  0.5f, 0.0f,   // top left
        -0.5f, -0.5f, 0.0f,   // bottom left
        0.5f, -0.5f, 0.0f,   // bottom right
        0.5f,  0.5f, 0.0f // top right
    };
    private final FloatBuffer vertexBuffer;
    private final ShortBuffer indexBuffer;
    short indices[] = { 0, 1, 2, 0, 2, 3 }; // order to draw vertices


    public Square() {

        ByteBuffer bb = ByteBuffer.allocateDirect(vertices.length * 4);
        bb.order(ByteOrder.nativeOrder());

        vertexBuffer = bb.asFloatBuffer();
        vertexBuffer.put(vertices);
        vertexBuffer.position(0);

        final ByteBuffer bb2 = ByteBuffer.allocateDirect(indices.length * 4);
        indexBuffer = bb2.asShortBuffer();
        indexBuffer.put(indices);
        indexBuffer.position(0);
    }
}
