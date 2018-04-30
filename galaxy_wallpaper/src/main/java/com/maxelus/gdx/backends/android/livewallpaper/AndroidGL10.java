package com.maxelus.gdx.backends.android.livewallpaper;

import com.mybadlogic.gdx.graphics.GL10;

import java.nio.Buffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

class AndroidGL10 implements GL10 {
    private final javax.microedition.khronos.opengles.GL10 gl;

    public AndroidGL10(javax.microedition.khronos.opengles.GL10 gl) {
        this.gl = gl;
    }

    public final void glActiveTexture(int texture) {
        this.gl.glActiveTexture(texture);
    }

    public final void glAlphaFunc(int func, float ref) {
        this.gl.glAlphaFunc(func, ref);
    }

    public final void glBindTexture(int target, int texture) {
        this.gl.glBindTexture(target, texture);
    }

    public final void glBlendFunc(int sfactor, int dfactor) {
        this.gl.glBlendFunc(sfactor, dfactor);
    }

    public final void glClear(int mask) {
        this.gl.glClear(mask);
    }

    public final void glClearColor(float red, float green, float blue, float alpha) {
        this.gl.glClearColor(red, green, blue, alpha);
    }

    public final void glClearDepthf(float depth) {
        this.gl.glClearDepthf(depth);
    }

    public final void glClearStencil(int s) {
        this.gl.glClearStencil(s);
    }

    public final void glClientActiveTexture(int texture) {
        this.gl.glClientActiveTexture(texture);
    }

    public final void glColor4f(float red, float green, float blue, float alpha) {
        this.gl.glColor4f(red, green, blue, alpha);
    }

    public final void glColorMask(boolean red, boolean green, boolean blue, boolean alpha) {
        this.gl.glColorMask(red, green, blue, alpha);
    }

    public final void glColorPointer(int size, int type, int stride, Buffer pointer) {
        this.gl.glColorPointer(size, type, stride, pointer);
    }

    public final void glCompressedTexImage2D(int target, int level, int internalformat, int width, int height, int border, int imageSize, Buffer data) {
        this.gl.glCompressedTexImage2D(target, level, internalformat, width, height, border, imageSize, data);
    }

    public final void glCompressedTexSubImage2D(int target, int level, int xoffset, int yoffset, int width, int height, int format, int imageSize, Buffer data) {
        this.gl.glCompressedTexSubImage2D(target, level, xoffset, yoffset, width, height, format, imageSize, data);
    }

    public final void glCopyTexImage2D(int target, int level, int internalformat, int x, int y, int width, int height, int border) {
        this.gl.glCopyTexImage2D(target, level, internalformat, x, y, width, height, border);
    }

    public final void glCopyTexSubImage2D(int target, int level, int xoffset, int yoffset, int x, int y, int width, int height) {
        this.gl.glCopyTexSubImage2D(target, level, xoffset, yoffset, x, y, width, height);
    }

    public final void glCullFace(int mode) {
        this.gl.glCullFace(mode);
    }

    public final void glDeleteTextures(int n, IntBuffer textures) {
        this.gl.glDeleteTextures(n, textures);
    }

    public final void glDepthFunc(int func) {
        this.gl.glDepthFunc(func);
    }

    public final void glDepthMask(boolean flag) {
        this.gl.glDepthMask(flag);
    }

    public final void glDepthRangef(float zNear, float zFar) {
        this.gl.glDepthRangef(zNear, zFar);
    }

    public final void glDisable(int cap) {
        this.gl.glDisable(cap);
    }

    public final void glDisableClientState(int array) {
        this.gl.glDisableClientState(array);
    }

    public final void glDrawArrays(int mode, int first, int count) {
        this.gl.glDrawArrays(mode, first, count);
    }

    public final void glDrawElements(int mode, int count, int type, Buffer indices) {
        this.gl.glDrawElements(mode, count, type, indices);
    }

    public final void glEnable(int cap) {
        this.gl.glEnable(cap);
    }

    public final void glEnableClientState(int array) {
        this.gl.glEnableClientState(array);
    }

    public final void glFinish() {
        this.gl.glFinish();
    }

    public final void glFlush() {
        this.gl.glFlush();
    }

    public final void glFogf(int pname, float param) {
        this.gl.glFogf(pname, param);
    }

    public final void glFogfv(int pname, FloatBuffer params) {
        this.gl.glFogfv(pname, params);
    }

    public final void glFrontFace(int mode) {
        this.gl.glFrontFace(mode);
    }

    public final void glFrustumf(float left, float right, float bottom, float top, float zNear, float zFar) {
        this.gl.glFrustumf(left, right, bottom, top, zNear, zFar);
    }

    public final void glGenTextures(int n, IntBuffer textures) {
        this.gl.glGenTextures(n, textures);
    }

    public final int glGetError() {
        return this.gl.glGetError();
    }

    public final void glGetIntegerv(int pname, IntBuffer params) {
        this.gl.glGetIntegerv(pname, params);
    }

    public final String glGetString(int name) {
        return this.gl.glGetString(name);
    }

    public final void glHint(int target, int mode) {
        this.gl.glHint(target, mode);
    }

    public final void glLightModelf(int pname, float param) {
        this.gl.glLightModelf(pname, param);
    }

    public final void glLightModelfv(int pname, FloatBuffer params) {
        this.gl.glLightModelfv(pname, params);
    }

    public final void glLightf(int light, int pname, float param) {
        this.gl.glLightf(light, pname, param);
    }

    public final void glLightfv(int light, int pname, FloatBuffer params) {
        this.gl.glLightfv(light, pname, params);
    }

    public final void glLineWidth(float width) {
        this.gl.glLineWidth(width);
    }

    public final void glLoadIdentity() {
        this.gl.glLoadIdentity();
    }

    public final void glLoadMatrixf(FloatBuffer m) {
        this.gl.glLoadMatrixf(m);
    }

    public final void glLogicOp(int opcode) {
        this.gl.glLogicOp(opcode);
    }

    public final void glMaterialf(int face, int pname, float param) {
        this.gl.glMaterialf(face, pname, param);
    }

    public final void glMaterialfv(int face, int pname, FloatBuffer params) {
        this.gl.glMaterialfv(face, pname, params);
    }

    public final void glMatrixMode(int mode) {
        this.gl.glMatrixMode(mode);
    }

    public final void glMultMatrixf(FloatBuffer m) {
        this.gl.glMultMatrixf(m);
    }

    public final void glMultiTexCoord4f(int target, float s, float t, float r, float q) {
        this.gl.glMultiTexCoord4f(target, s, t, r, q);
    }

    public final void glNormal3f(float nx, float ny, float nz) {
        this.gl.glNormal3f(nx, ny, nz);
    }

    public final void glNormalPointer(int type, int stride, Buffer pointer) {
        this.gl.glNormalPointer(type, stride, pointer);
    }

    public final void glOrthof(float left, float right, float bottom, float top, float zNear, float zFar) {
        this.gl.glOrthof(left, right, bottom, top, zNear, zFar);
    }

    public final void glPixelStorei(int pname, int param) {
        this.gl.glPixelStorei(pname, param);
    }

    public final void glPointSize(float size) {
        this.gl.glPointSize(size);
    }

    public final void glPolygonOffset(float factor, float units) {
        this.gl.glPolygonOffset(factor, units);
    }

    public final void glPopMatrix() {
        this.gl.glPopMatrix();
    }

    public final void glPushMatrix() {
        this.gl.glPushMatrix();
    }

    public final void glReadPixels(int x, int y, int width, int height, int format, int type, Buffer pixels) {
        this.gl.glReadPixels(x, y, width, height, format, type, pixels);
    }

    public final void glRotatef(float angle, float x, float y, float z) {
        this.gl.glRotatef(angle, x, y, z);
    }

    public final void glSampleCoverage(float value, boolean invert) {
        this.gl.glSampleCoverage(value, invert);
    }

    public final void glScalef(float x, float y, float z) {
        this.gl.glScalef(x, y, z);
    }

    public final void glScissor(int x, int y, int width, int height) {
        this.gl.glScissor(x, y, width, height);
    }

    public final void glShadeModel(int mode) {
        this.gl.glShadeModel(mode);
    }

    public final void glStencilFunc(int func, int ref, int mask) {
        this.gl.glStencilFunc(func, ref, mask);
    }

    public final void glStencilMask(int mask) {
        this.gl.glStencilMask(mask);
    }

    public final void glStencilOp(int fail, int zfail, int zpass) {
        this.gl.glStencilOp(fail, zfail, zpass);
    }

    public final void glTexCoordPointer(int size, int type, int stride, Buffer pointer) {
        this.gl.glTexCoordPointer(size, type, stride, pointer);
    }

    public final void glTexEnvf(int target, int pname, float param) {
        this.gl.glTexEnvf(target, pname, param);
    }

    public final void glTexEnvfv(int target, int pname, FloatBuffer params) {
        this.gl.glTexEnvfv(target, pname, params);
    }

    public final void glTexImage2D(int target, int level, int internalformat, int width, int height, int border, int format, int type, Buffer pixels) {
        this.gl.glTexImage2D(target, level, internalformat, width, height, border, format, type, pixels);
    }

    public final void glTexParameterf(int target, int pname, float param) {
        this.gl.glTexParameterf(target, pname, param);
    }

    public final void glTexSubImage2D(int target, int level, int xoffset, int yoffset, int width, int height, int format, int type, Buffer pixels) {
        this.gl.glTexSubImage2D(target, level, xoffset, yoffset, width, height, format, type, pixels);
    }

    public final void glTranslatef(float x, float y, float z) {
        this.gl.glTranslatef(x, y, z);
    }

    public final void glVertexPointer(int size, int type, int stride, Buffer pointer) {
        this.gl.glVertexPointer(size, type, stride, pointer);
    }

    public final void glViewport(int x, int y, int width, int height) {
        this.gl.glViewport(x, y, width, height);
    }

    public final void glDeleteTextures(int n, int[] textures, int offset) {
        this.gl.glDeleteTextures(n, textures, offset);
    }

    public final void glFogfv(int pname, float[] params, int offset) {
        this.gl.glFogfv(pname, params, offset);
    }

    public final void glGenTextures(int n, int[] textures, int offset) {
        this.gl.glGenTextures(n, textures, offset);
    }

    public final void glGetIntegerv(int pname, int[] params, int offset) {
        this.gl.glGetIntegerv(pname, params, offset);
    }

    public final void glLightModelfv(int pname, float[] params, int offset) {
        this.gl.glLightModelfv(pname, params, offset);
    }

    public final void glLightfv(int light, int pname, float[] params, int offset) {
        this.gl.glLightfv(light, pname, params, offset);
    }

    public final void glLoadMatrixf(float[] m, int offset) {
        this.gl.glLoadMatrixf(m, offset);
    }

    public final void glMaterialfv(int face, int pname, float[] params, int offset) {
        this.gl.glMaterialfv(face, pname, params, offset);
    }

    public final void glMultMatrixf(float[] m, int offset) {
        this.gl.glMultMatrixf(m, offset);
    }

    public final void glTexEnvfv(int target, int pname, float[] params, int offset) {
        this.gl.glTexEnvfv(target, pname, params, offset);
    }

    public void glPolygonMode(int face, int mode) {
    }
}
