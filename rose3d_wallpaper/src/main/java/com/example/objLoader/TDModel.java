package com.example.objLoader;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.Vector;

import javax.microedition.khronos.opengles.GL10;

public class TDModel {
    public Vector<TDModelPart> parts;
    public Vector<Float> f5v;
    FloatBuffer vertexBuffer;
    public Vector<Float> vn;
    public Vector<Float> vt;

    public TDModel(Vector<Float> v, Vector<Float> vn, Vector<Float> vt, Vector<TDModelPart> parts) {
        this.f5v = v;
        this.vn = vn;
        this.vt = vt;
        this.parts = parts;
    }

    public String toString() {
        String str = new StringBuilder(String.valueOf(new StringBuilder(String.valueOf(new StringBuilder(String.valueOf(new StringBuilder(String.valueOf(new StringBuilder(String.valueOf(new String())).append("Number of parts: ").append(this.parts.size()).toString())).append("\nNumber of vertexes: ").append(this.f5v.size()).toString())).append("\nNumber of vns: ").append(this.vn.size()).toString())).append("\nNumber of vts: ").append(this.vt.size()).toString())).append("\n/////////////////////////\n").toString();
        for (int i = 0; i < this.parts.size(); i++) {
            str = new StringBuilder(String.valueOf(new StringBuilder(String.valueOf(new StringBuilder(String.valueOf(str)).append("Part ").append(i).append('\n').toString())).append(((TDModelPart) this.parts.get(i)).toString()).toString())).append("\n/////////////////////////").toString();
        }
        return str;
    }

    public void draw(GL10 gl) {
        gl.glVertexPointer(3, 5126, 0, this.vertexBuffer);
        gl.glEnableClientState(32884);
        for (int i = 0; i < this.parts.size(); i++) {
            TDModelPart t = (TDModelPart) this.parts.get(i);
            Material m = t.getMaterial();
            if (m != null) {
                FloatBuffer a = m.getAmbientColorBuffer();
                FloatBuffer d = m.getDiffuseColorBuffer();
                FloatBuffer s = m.getSpecularColorBuffer();
                gl.glMaterialfv(1032, 4608, a);
                gl.glMaterialfv(1032, 4610, s);
                gl.glMaterialfv(1032, 4609, d);
            }
            gl.glEnableClientState(32885);
            gl.glNormalPointer(5126, 0, t.getNormalBuffer());
            gl.glDrawElements(4, t.getFacesCount(), 5123, t.getFaceBuffer());
            gl.glDisableClientState(32885);
        }
    }

    public void buildVertexBuffer() {
        ByteBuffer vBuf = ByteBuffer.allocateDirect(this.f5v.size() * 4);
        vBuf.order(ByteOrder.nativeOrder());
        this.vertexBuffer = vBuf.asFloatBuffer();
        this.vertexBuffer.put(toPrimitiveArrayF(this.f5v));
        this.vertexBuffer.position(0);
    }

    private static float[] toPrimitiveArrayF(Vector<Float> vector) {
        float[] f = new float[vector.size()];
        for (int i = 0; i < vector.size(); i++) {
            f[i] = ((Float) vector.get(i)).floatValue();
        }
        return f;
    }
}
