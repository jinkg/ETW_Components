package com.example.objLoader;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.util.Vector;

public class TDModelPart {
    ShortBuffer faceBuffer;
    public Vector<Short> faces;
    public Material material;
    private FloatBuffer normalBuffer;
    public Vector<Short> vnPointer;
    public Vector<Short> vtPointer;

    public TDModelPart(Vector<Short> faces, Vector<Short> vtPointer, Vector<Short> vnPointer, Material material, Vector<Float> vn) {
        this.faces = faces;
        this.vtPointer = vtPointer;
        this.vnPointer = vnPointer;
        this.material = material;
        ByteBuffer byteBuf = ByteBuffer.allocateDirect((vnPointer.size() * 4) * 3);
        byteBuf.order(ByteOrder.nativeOrder());
        this.normalBuffer = byteBuf.asFloatBuffer();
        for (int i = 0; i < vnPointer.size(); i++) {
            float x = ((Float) vn.get(((Short) vnPointer.get(i)).shortValue() * 3)).floatValue();
            float y = ((Float) vn.get((((Short) vnPointer.get(i)).shortValue() * 3) + 1)).floatValue();
            float z = ((Float) vn.get((((Short) vnPointer.get(i)).shortValue() * 3) + 2)).floatValue();
            this.normalBuffer.put(x);
            this.normalBuffer.put(y);
            this.normalBuffer.put(z);
        }
        this.normalBuffer.position(0);
        ByteBuffer fBuf = ByteBuffer.allocateDirect(faces.size() * 2);
        fBuf.order(ByteOrder.nativeOrder());
        this.faceBuffer = fBuf.asShortBuffer();
        this.faceBuffer.put(toPrimitiveArrayS(faces));
        this.faceBuffer.position(0);
    }

    public String toString() {
        String str = new String();
        if (this.material != null) {
            str = new StringBuilder(String.valueOf(str)).append("Material name:").append(this.material.getName()).toString();
        } else {
            str = new StringBuilder(String.valueOf(str)).append("Material not defined!").toString();
        }
        return new StringBuilder(String.valueOf(new StringBuilder(String.valueOf(new StringBuilder(String.valueOf(str)).append("\nNumber of faces:").append(this.faces.size()).toString())).append("\nNumber of vnPointers:").append(this.vnPointer.size()).toString())).append("\nNumber of vtPointers:").append(this.vtPointer.size()).toString();
    }

    public ShortBuffer getFaceBuffer() {
        return this.faceBuffer;
    }

    public FloatBuffer getNormalBuffer() {
        return this.normalBuffer;
    }

    private static short[] toPrimitiveArrayS(Vector<Short> vector) {
        short[] s = new short[vector.size()];
        for (int i = 0; i < vector.size(); i++) {
            s[i] = ((Short) vector.get(i)).shortValue();
        }
        return s;
    }

    public int getFacesCount() {
        return this.faces.size();
    }

    public Material getMaterial() {
        return this.material;
    }
}
