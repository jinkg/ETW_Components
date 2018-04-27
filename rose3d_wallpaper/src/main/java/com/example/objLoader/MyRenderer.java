package com.example.objLoader;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLU;
import android.os.Debug;
import android.view.KeyEvent;
import android.view.MotionEvent;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class MyRenderer extends GLSurfaceView implements Renderer {
    private final float TOUCH_SCALE = 0.4f;
    private float[] lightAmbient = new float[]{1.0f, 1.0f, 1.0f, 1.0f};
    private FloatBuffer lightAmbientBuffer;
    private float[] lightDiffuse = new float[]{1.0f, 1.0f, 1.0f, 1.0f};
    private FloatBuffer lightDiffuseBuffer;
    private float[] lightPosition = new float[]{0.0f, -3.0f, 2.0f, 1.0f};
    private FloatBuffer lightPositionBuffer;
    private TDModel model;
    private float oldX;
    private float oldY;
    private OBJParser parser;
    private float xrot;
    private float xspeed;
    private float yrot;
    private float yspeed;
    private float f0z = 50.0f;

    public MyRenderer(Context ctx) {
        super(ctx);
        this.parser = new OBJParser(ctx);
        this.model = this.parser.parseOBJ("/sdcard/windmill.obj");
        Debug.stopMethodTracing();
        setRenderer(this);
        requestFocus();
        setFocusableInTouchMode(true);
        ByteBuffer byteBuf = ByteBuffer.allocateDirect(this.lightAmbient.length * 4);
        byteBuf.order(ByteOrder.nativeOrder());
        this.lightAmbientBuffer = byteBuf.asFloatBuffer();
        this.lightAmbientBuffer.put(this.lightAmbient);
        this.lightAmbientBuffer.position(0);
        byteBuf = ByteBuffer.allocateDirect(this.lightDiffuse.length * 4);
        byteBuf.order(ByteOrder.nativeOrder());
        this.lightDiffuseBuffer = byteBuf.asFloatBuffer();
        this.lightDiffuseBuffer.put(this.lightDiffuse);
        this.lightDiffuseBuffer.position(0);
        byteBuf = ByteBuffer.allocateDirect(this.lightPosition.length * 4);
        byteBuf.order(ByteOrder.nativeOrder());
        this.lightPositionBuffer = byteBuf.asFloatBuffer();
        this.lightPositionBuffer.put(this.lightPosition);
        this.lightPositionBuffer.position(0);
    }

    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        gl.glLightfv(16384, 4608, this.lightAmbientBuffer);
        gl.glLightfv(16384, 4609, this.lightDiffuseBuffer);
        gl.glLightfv(16384, 4611, this.lightPositionBuffer);
        gl.glEnable(16384);
        gl.glShadeModel(7425);
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f);
        gl.glClearDepthf(1.0f);
        gl.glEnable(2929);
        gl.glDepthFunc(515);
        gl.glHint(3152, 4354);
    }

    public void onDrawFrame(GL10 gl) {
        gl.glClear(16640);
        gl.glLoadIdentity();
        gl.glEnable(2896);
        gl.glTranslatef(0.0f, -1.2f, -this.f0z);
        gl.glRotatef(this.xrot, 1.0f, 0.0f, 0.0f);
        gl.glRotatef(this.yrot, 0.0f, 1.0f, 0.0f);
        this.model.draw(gl);
        gl.glLoadIdentity();
        this.xrot += this.xspeed;
        this.yrot += this.yspeed;
    }

    public void onSurfaceChanged(GL10 gl, int width, int height) {
        if (height == 0) {
            height = 1;
        }
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(5889);
        gl.glLoadIdentity();
        GLU.gluPerspective(gl, 45.0f, ((float) width) / ((float) height), 0.1f, 500.0f);
        gl.glMatrixMode(5888);
        gl.glLoadIdentity();
    }

    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        if (event.getAction() == 2) {
            float dx = x - this.oldX;
            float dy = y - this.oldY;
            if (y < ((float) (getHeight() / 10))) {
                this.f0z -= (dx * 0.4f) / 2.0f;
            } else {
                this.xrot += dy * 0.4f;
                this.yrot += dx * 0.4f;
            }
        } else {
            event.getAction();
        }
        this.oldX = x;
        this.oldY = y;
        return true;
    }

    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (!(keyCode == 21 || keyCode == 22)) {
            if (keyCode == 19) {
                this.f0z -= 3.0f;
            } else if (keyCode == 20) {
                this.f0z += 3.0f;
            }
        }
        return true;
    }
}
