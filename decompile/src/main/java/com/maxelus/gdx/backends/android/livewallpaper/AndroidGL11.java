package com.maxelus.gdx.backends.android.livewallpaper;

import com.badlogic.gdx.graphics.GL11;
import java.nio.Buffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import javax.microedition.khronos.opengles.GL10;

final class AndroidGL11 extends AndroidGL10 implements GL11 {
    private final javax.microedition.khronos.opengles.GL11 gl;

    public AndroidGL11(GL10 gl) {
        super(gl);
        this.gl = (javax.microedition.khronos.opengles.GL11) gl;
    }

    public void glBindBuffer(int target, int buffer) {
        this.gl.glBindBuffer(target, buffer);
    }

    public void glBufferData(int target, int size, Buffer data, int usage) {
        this.gl.glBufferData(target, size, data, usage);
    }

    public void glBufferSubData(int target, int offset, int size, Buffer data) {
        this.gl.glBufferSubData(target, offset, size, data);
    }

    public void glClipPlanef(int plane, FloatBuffer equation) {
        this.gl.glClipPlanef(plane, equation);
    }

    public void glColor4ub(byte red, byte green, byte blue, byte alpha) {
        this.gl.glColor4ub(red, green, blue, alpha);
    }

    public void glDeleteBuffers(int n, IntBuffer buffers) {
        this.gl.glDeleteBuffers(n, buffers);
    }

    public void glGenBuffers(int n, IntBuffer buffers) {
        this.gl.glGenBuffers(n, buffers);
    }

    public void glGetBooleanv(int pname, IntBuffer params) {
        this.gl.glGetBooleanv(pname, params);
    }

    public void glGetBufferParameteriv(int target, int pname, IntBuffer params) {
        this.gl.glGetBufferParameteriv(target, pname, params);
    }

    public void glGetClipPlanef(int pname, FloatBuffer eqn) {
        this.gl.glGetClipPlanef(pname, eqn);
    }

    public void glGetFloatv(int pname, FloatBuffer params) {
        this.gl.glGetFloatv(pname, params);
    }

    public void glGetLightfv(int light, int pname, FloatBuffer params) {
        this.gl.glGetLightfv(light, pname, params);
    }

    public void glGetMaterialfv(int face, int pname, FloatBuffer params) {
        this.gl.glGetMaterialfv(face, pname, params);
    }

    public void glGetPointerv(int pname, Buffer[] params) {
        this.gl.glGetPointerv(pname, params);
    }

    public void glGetTexEnviv(int env, int pname, IntBuffer params) {
        this.gl.glGetTexEnviv(env, pname, params);
    }

    public void glGetTexParameterfv(int target, int pname, FloatBuffer params) {
        this.gl.glGetTexParameterfv(target, pname, params);
    }

    public void glGetTexParameteriv(int target, int pname, IntBuffer params) {
        this.gl.glGetTexParameteriv(target, pname, params);
    }

    public boolean glIsBuffer(int buffer) {
        return this.gl.glIsBuffer(buffer);
    }

    public boolean glIsEnabled(int cap) {
        return this.gl.glIsEnabled(cap);
    }

    public boolean glIsTexture(int texture) {
        return this.gl.glIsTexture(texture);
    }

    public void glPointParameterf(int pname, float param) {
        this.gl.glPointParameterf(pname, param);
    }

    public void glPointParameterfv(int pname, FloatBuffer params) {
        this.gl.glPointParameterfv(pname, params);
    }

    public void glPointSizePointerOES(int type, int stride, Buffer pointer) {
        this.gl.glPointSizePointerOES(type, stride, pointer);
    }

    public void glTexEnvi(int target, int pname, int param) {
        this.gl.glTexEnvi(target, pname, param);
    }

    public void glTexEnviv(int target, int pname, IntBuffer params) {
        this.gl.glTexEnviv(target, pname, params);
    }

    public void glTexParameterfv(int target, int pname, FloatBuffer params) {
        this.gl.glTexParameterfv(target, pname, params);
    }

    public void glTexParameteri(int target, int pname, int param) {
        this.gl.glTexParameteri(target, pname, param);
    }

    public void glTexParameteriv(int target, int pname, IntBuffer params) {
        this.gl.glTexParameteriv(target, pname, params);
    }

    public void glClipPlanef(int plane, float[] equation, int offset) {
        this.gl.glClipPlanef(plane, equation, offset);
    }

    public void glDeleteBuffers(int n, int[] buffers, int offset) {
        this.gl.glDeleteBuffers(n, buffers, offset);
    }

    public void glGenBuffers(int n, int[] buffers, int offset) {
        this.gl.glGenBuffers(n, buffers, offset);
    }

    public void glGetBooleanv(int pname, boolean[] params, int offset) {
        this.gl.glGetBooleanv(pname, params, offset);
    }

    public void glGetBufferParameteriv(int target, int pname, int[] params, int offset) {
        this.gl.glGetBufferParameteriv(target, pname, params, offset);
    }

    public void glGetClipPlanef(int pname, float[] eqn, int offset) {
        this.gl.glGetClipPlanef(pname, eqn, offset);
    }

    public void glGetFloatv(int pname, float[] params, int offset) {
        this.gl.glGetFloatv(pname, params, offset);
    }

    public void glGetLightfv(int light, int pname, float[] params, int offset) {
        this.gl.glGetLightfv(light, pname, params, offset);
    }

    public void glGetMaterialfv(int face, int pname, float[] params, int offset) {
        this.gl.glGetMaterialfv(face, pname, params, offset);
    }

    public void glGetTexEnviv(int env, int pname, int[] params, int offset) {
        this.gl.glGetTexEnviv(env, pname, params, offset);
    }

    public void glGetTexParameterfv(int target, int pname, float[] params, int offset) {
        this.gl.glGetTexParameterfv(target, pname, params, offset);
    }

    public void glGetTexParameteriv(int target, int pname, int[] params, int offset) {
        this.gl.glGetTexParameteriv(target, pname, params, offset);
    }

    public void glPointParameterfv(int pname, float[] params, int offset) {
        this.gl.glPointParameterfv(pname, params, offset);
    }

    public void glTexEnviv(int target, int pname, int[] params, int offset) {
        this.gl.glTexEnviv(target, pname, params, offset);
    }

    public void glTexParameterfv(int target, int pname, float[] params, int offset) {
        this.gl.glTexParameterfv(target, pname, params, offset);
    }

    public void glTexParameteriv(int target, int pname, int[] params, int offset) {
        this.gl.glTexParameteriv(target, pname, params, offset);
    }

    public void glColorPointer(int size, int type, int stride, int pointer) {
        this.gl.glColorPointer(size, type, stride, pointer);
    }

    public void glNormalPointer(int type, int stride, int pointer) {
        this.gl.glNormalPointer(type, stride, pointer);
    }

    public void glTexCoordPointer(int size, int type, int stride, int pointer) {
        this.gl.glTexCoordPointer(size, type, stride, pointer);
    }

    public void glVertexPointer(int size, int type, int stride, int pointer) {
        this.gl.glVertexPointer(size, type, stride, pointer);
    }

    public void glDrawElements(int mode, int count, int type, int indices) {
        this.gl.glDrawElements(mode, count, type, indices);
    }
}
