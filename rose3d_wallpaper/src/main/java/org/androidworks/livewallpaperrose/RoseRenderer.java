package org.androidworks.livewallpaperrose;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.ETC1Util;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLUtils;
import android.opengl.Matrix;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.Date;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

class RoseRenderer implements Renderer {
    private static final int FLOAT_SIZE_BYTES = 4;
    private static String TAG = "Rose LWP Renderer";
    private static final int TRIANGLE_VERTICES_DATA_POS_OFFSET = 0;
    private static final int TRIANGLE_VERTICES_DATA_STRIDE_BYTES = 20;
    private static final int TRIANGLE_VERTICES_DATA_UV_OFFSET = 3;
    private final float CAMERA_JITTER = 8.0f;
    private final float ROSE_PITCH = -25.0f;
    private final float ROSE_PITCH_LIMITS = 15.0f;
    private float anglePitch;
    private float angleYaw;
    private long autoRotationTimer;
    private boolean bAutoRotate;
    private boolean bRandomize;
    private boolean bReloadPetal = false;
    private boolean bReloadSphere = false;
    private boolean bRotate;
    private boolean bTilt;
    private boolean bVignette;
    private double cameraTimer;
    private int frameCount;
    private float jitterX;
    private float jitterY;
    private long lastFrameTime = 0;
    private final String mAlphaFragmentShader = "precision mediump float;\nvarying mediump vec2 vTextureCoord;\nuniform sampler2D sTexture;\nuniform sampler2D sAlpha;\nvoid main() {\n vec4 base = texture2D(sTexture, vTextureCoord);\n vec4 mask = texture2D(sAlpha, vTextureCoord);\n gl_FragColor = base;\n if(mask.g < 0.5){ discard; }\n}";
    private int mAlphaProgram;
    private int mAlpha_aPositionHandle;
    private int mAlpha_aTextureHandle;
    private int mAlpha_sAlphaHandle;
    private int mAlpha_sTextureHandle;
    private int mAlpha_uMVPMatrixHandle;
    private Context mContext;
    private final String mDummyFragmentShader = "precision mediump float;\nvarying mediump vec2 vTextureCoord;\nuniform sampler2D sTexture;\nvoid main() {\n  vec4 base = texture2D(sTexture, vTextureCoord);\n  gl_FragColor = base;\n}\n";
    private int mDummyProgram;
    private int mDummy_aPositionHandle;
    private int mDummy_aTextureHandle;
    private int mDummy_sTextureHandle;
    private int mDummy_uMVPMatrixHandle;
    private final String mFragmentShader = "precision mediump float;\nvarying mediump vec2 vTextureCoord;\nuniform sampler2D sTexture;\nvoid main() {\n  vec4 base = texture2D(sTexture, vTextureCoord);\n  if(base.a < 0.5){ discard; }\n  gl_FragColor = base;\n}\n";
    private float[] mMMatrix = new float[16];
    private float[] mMVPMatrix = new float[16];
    private float[] mOrthoMatrix = new float[16];
    private int mProgram;
    private float[] mProjMatrix = new float[16];
    private float[] mQuadTriangles = new float[]{-1.0f, -1.0f, -5.0f, 0.0f, 0.0f, 1.0f, -1.0f, -5.0f, 1.0f, 0.0f, -1.0f, 1.0f, -5.0f, 0.0f, 1.0f, 1.0f, 1.0f, -5.0f, 1.0f, 1.0f};
    private int mRoseAlphaTextureID;
    private int mRoseTextureID;
    private int mSphereTextureID;
    private int mStemAlphaTextureID;
    private int mStemTextureID;
    private float[] mTempRotateMatrix = new float[32];
    private float[] mTriangleVerticesData = new float[]{-1.0f, -0.5f, 0.0f, -0.5f, 0.0f, 1.0f, -0.5f, 0.0f, 1.5f, -0.0f, 0.0f, 1.118034f, 0.0f, 0.5f, 1.618034f, -1.5f, -1.5f, 0.0f, -0.5f, 0.0f, 1.5f, -1.5f, 0.0f, 1.5f, -0.0f, 0.5f, 0.11803399f, 0.0f, 0.5f, 1.618034f};
    private FloatBuffer mTriangleVerticesRose;
    private FloatBuffer mTriangleVerticesSphere;
    private FloatBuffer mTriangleVerticesStem;
    private FloatBuffer mTriangleVerticesVignette;
    private float[] mVMatrix = new float[16];
    private final String mVertexShader = "uniform highp mat4 uMVPMatrix;\nattribute highp vec4 aPosition;\nattribute highp vec2 aTextureCoord;\nvarying mediump vec2 vTextureCoord;\nvoid main() {\n  gl_Position = uMVPMatrix * aPosition;\n  vTextureCoord = aTextureCoord;\n}\n";
    private int mVignetteTextureID;
    private int maPositionHandle;
    private int maTextureHandle;
    private int msTextureHandle;
    private int muMVPMatrixHandle;
    private int numPolysRose;
    private int numPolysSphere;
    private int numPolysStem;
    private int petalTextureNo;
    private int pixelShader;
    private Date prevDate;
    private Resources resources;
    private int rotationDirection = -1;
    private float rotationImpulse;
    private float rotationSwipeSize;
    private int sphereTextureNo;
    private int vertexShader;
    private float xOffset;
    private float yOffset;

    public RoseRenderer(Context context) {
        this.mContext = context;
        this.mTriangleVerticesSphere = new ModelContainer(this.mContext, "models/sphere.bin").getBuffer();
        this.numPolysSphere = this.mTriangleVerticesSphere.capacity() / 5;
        this.mTriangleVerticesRose = new ModelContainer(this.mContext, "models/rose.bin").getBuffer();
        this.numPolysRose = this.mTriangleVerticesRose.capacity() / 5;
        this.mTriangleVerticesStem = new ModelContainer(this.mContext, "models/stem.bin").getBuffer();
        this.numPolysStem = this.mTriangleVerticesStem.capacity() / 5;
        this.anglePitch = -25.0f;
        this.sphereTextureNo = 1;
        this.petalTextureNo = 1;
        this.mTriangleVerticesVignette = ByteBuffer.allocateDirect(this.mQuadTriangles.length * FLOAT_SIZE_BYTES).order(ByteOrder.nativeOrder()).asFloatBuffer();
        this.mTriangleVerticesVignette.put(this.mQuadTriangles).position(TRIANGLE_VERTICES_DATA_POS_OFFSET);
    }

    public void rotateMatrix(float[] m, int mOffset, float a, float x, float y, float z) {
        Matrix.setRotateM(this.mTempRotateMatrix, TRIANGLE_VERTICES_DATA_POS_OFFSET, a, x, y, z);
        Matrix.multiplyMM(this.mTempRotateMatrix, 16, m, mOffset, this.mTempRotateMatrix, TRIANGLE_VERTICES_DATA_POS_OFFSET);
        System.arraycopy(this.mTempRotateMatrix, 16, m, mOffset, 16);
    }

    public void onDrawFrame(GL10 glUnused) {
        this.frameCount++;
        if (this.bRandomize) {
            Date now = new Date(System.currentTimeMillis());
            if (this.prevDate == null) {
                this.prevDate = now;
            }
            if (now.getDate() != this.prevDate.getDate()) {
                Prefs.randomize();
            }
            this.prevDate = now;
        }
        GLES20.glClear(16640);
        if (this.bReloadSphere) {
            unloadTexture(this.mSphereTextureID);
            this.mSphereTextureID = loadTexture("textures/sphere-" + this.sphereTextureNo + ".png");
            this.bReloadSphere = false;
        }
        if (this.bReloadPetal) {
            unloadTexture(this.mRoseTextureID);
            unloadTexture(this.mRoseAlphaTextureID);
            this.mRoseTextureID = loadETC1Texture("textures/petal-" + this.petalTextureNo + ".png");
            this.mRoseAlphaTextureID = loadETC1Texture("textures/petal-" + this.petalTextureNo + "-alpha.png");
            this.bReloadPetal = false;
        }
        updateRotations();
        long currTimeMillis = System.currentTimeMillis();
        if (this.lastFrameTime == 0) {
            this.lastFrameTime = currTimeMillis;
        }
        GLES20.glDisable(3042);
        GLES20.glEnable(2884);
        GLES20.glCullFace(1029);
        GLES20.glUseProgram(this.mDummyProgram);
        GLES20.glActiveTexture(33984);
        GLES20.glBindTexture(3553, this.mSphereTextureID);
        GLES20.glUniform1i(this.mDummy_sTextureHandle, TRIANGLE_VERTICES_DATA_POS_OFFSET);
        drawSphere();
        GLES20.glDisable(2884);
        GLES20.glUseProgram(this.mAlphaProgram);
        GLES20.glActiveTexture(33984);
        GLES20.glBindTexture(3553, this.mStemTextureID);
        GLES20.glUniform1i(this.mAlpha_sTextureHandle, TRIANGLE_VERTICES_DATA_POS_OFFSET);
        GLES20.glActiveTexture(33985);
        GLES20.glBindTexture(3553, this.mStemAlphaTextureID);
        GLES20.glUniform1i(this.mAlpha_sAlphaHandle, 1);
        drawStemAT();
        GLES20.glUseProgram(this.mAlphaProgram);
        GLES20.glActiveTexture(33984);
        GLES20.glBindTexture(3553, this.mRoseTextureID);
        GLES20.glUniform1i(this.mAlpha_sTextureHandle, TRIANGLE_VERTICES_DATA_POS_OFFSET);
        GLES20.glActiveTexture(33985);
        GLES20.glBindTexture(3553, this.mRoseAlphaTextureID);
        GLES20.glUniform1i(this.mAlpha_sAlphaHandle, 1);
        drawRoseAT();
        if (this.bVignette) {
            GLES20.glEnable(3042);
            GLES20.glBlendFunc(TRIANGLE_VERTICES_DATA_POS_OFFSET, 768);
            GLES20.glUseProgram(this.mDummyProgram);
            GLES20.glActiveTexture(33984);
            GLES20.glBindTexture(3553, this.mVignetteTextureID);
            GLES20.glUniform1i(this.mDummy_sTextureHandle, TRIANGLE_VERTICES_DATA_POS_OFFSET);
            drawVignette();
        }
        this.lastFrameTime = currTimeMillis;
    }

    private void updateRotations() {
        long currTimeMillis = System.currentTimeMillis();
        if (this.bAutoRotate) {
            if (!(this.rotationImpulse == 1.0f || this.rotationImpulse == -1.0f)) {
                this.rotationImpulse *= 1.0f - (((float) (currTimeMillis - this.lastFrameTime)) / 600.0f);
                if (Math.abs(this.rotationImpulse) < 1.0f) {
                    if (this.rotationImpulse < 0.0f) {
                        this.rotationImpulse = -1.0f;
                    } else {
                        this.rotationImpulse = 1.0f;
                    }
                }
            }
            if (Math.abs(this.rotationImpulse) > 50.0f) {
                if (this.rotationImpulse < 0.0f) {
                    this.rotationImpulse = -50.0f;
                } else {
                    this.rotationImpulse = 50.0f;
                }
            }
            this.angleYaw = (float) (((double) this.angleYaw) + ((((((double) currTimeMillis) - ((double) this.lastFrameTime)) / 1000.0d) * ((double) this.rotationImpulse)) * 4.0d));
            this.angleYaw %= 360.0f;
        }
        if (this.bRotate) {
            this.cameraTimer += (((double) currTimeMillis) - ((double) this.lastFrameTime)) / 25000.0d;
            this.cameraTimer %= 1.0d;
            double a = this.cameraTimer * 6.283185005187988d;
            this.jitterX = ((float) (Math.sin(a) * 8.0d)) / 2.0f;
            this.jitterY = (float) (Math.cos(a) * 8.0d);
            return;
        }
        this.jitterX = 0.0f;
        this.jitterY = 0.0f;
    }

    private void drawRoseAT() {
        Matrix.setLookAtM(this.mVMatrix, TRIANGLE_VERTICES_DATA_POS_OFFSET, 0.0f, 0.0f, -5.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f);
        this.mTriangleVerticesRose.position(TRIANGLE_VERTICES_DATA_POS_OFFSET);
        GLES20.glVertexAttribPointer(this.mAlpha_aPositionHandle, TRIANGLE_VERTICES_DATA_UV_OFFSET, 5126, false, TRIANGLE_VERTICES_DATA_STRIDE_BYTES, this.mTriangleVerticesRose);
        this.mTriangleVerticesRose.position(TRIANGLE_VERTICES_DATA_UV_OFFSET);
        GLES20.glEnableVertexAttribArray(this.mAlpha_aPositionHandle);
        GLES20.glVertexAttribPointer(this.mAlpha_aTextureHandle, 2, 5126, false, TRIANGLE_VERTICES_DATA_STRIDE_BYTES, this.mTriangleVerticesRose);
        GLES20.glEnableVertexAttribArray(this.mAlpha_aTextureHandle);
        this.mMMatrix = new float[16];
        Matrix.setRotateM(this.mMMatrix, TRIANGLE_VERTICES_DATA_POS_OFFSET, this.anglePitch + this.jitterX, 1.0f, 0.0f, 0.0f);
        rotateMatrix(this.mMMatrix, TRIANGLE_VERTICES_DATA_POS_OFFSET, this.angleYaw + this.jitterY, 0.0f, 1.0f, 0.0f);
        Matrix.scaleM(this.mMMatrix, TRIANGLE_VERTICES_DATA_POS_OFFSET, 0.1f, 0.1f, 0.1f);
        Matrix.translateM(this.mMMatrix, TRIANGLE_VERTICES_DATA_POS_OFFSET, 0.0f, this.anglePitch / 4.0f, 0.0f);
        Matrix.multiplyMM(this.mMVPMatrix, TRIANGLE_VERTICES_DATA_POS_OFFSET, this.mVMatrix, TRIANGLE_VERTICES_DATA_POS_OFFSET, this.mMMatrix, TRIANGLE_VERTICES_DATA_POS_OFFSET);
        Matrix.multiplyMM(this.mMVPMatrix, TRIANGLE_VERTICES_DATA_POS_OFFSET, this.mProjMatrix, TRIANGLE_VERTICES_DATA_POS_OFFSET, this.mMVPMatrix, TRIANGLE_VERTICES_DATA_POS_OFFSET);
        GLES20.glUniformMatrix4fv(this.mAlpha_uMVPMatrixHandle, 1, false, this.mMVPMatrix, TRIANGLE_VERTICES_DATA_POS_OFFSET);
        GLES20.glDrawArrays(FLOAT_SIZE_BYTES, TRIANGLE_VERTICES_DATA_POS_OFFSET, this.numPolysRose);
        checkGlError("glDrawArrays");
    }

    private void drawStem() {
        Matrix.setLookAtM(this.mVMatrix, TRIANGLE_VERTICES_DATA_POS_OFFSET, 0.0f, 0.0f, -5.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f);
        this.mTriangleVerticesStem.position(TRIANGLE_VERTICES_DATA_POS_OFFSET);
        GLES20.glVertexAttribPointer(this.maPositionHandle, TRIANGLE_VERTICES_DATA_UV_OFFSET, 5126, false, TRIANGLE_VERTICES_DATA_STRIDE_BYTES, this.mTriangleVerticesStem);
        this.mTriangleVerticesStem.position(TRIANGLE_VERTICES_DATA_UV_OFFSET);
        GLES20.glEnableVertexAttribArray(this.maPositionHandle);
        GLES20.glVertexAttribPointer(this.maTextureHandle, 2, 5126, false, TRIANGLE_VERTICES_DATA_STRIDE_BYTES, this.mTriangleVerticesStem);
        GLES20.glEnableVertexAttribArray(this.maTextureHandle);
        this.mMMatrix = new float[16];
        Matrix.setRotateM(this.mMMatrix, TRIANGLE_VERTICES_DATA_POS_OFFSET, this.anglePitch + this.jitterX, 1.0f, 0.0f, 0.0f);
        rotateMatrix(this.mMMatrix, TRIANGLE_VERTICES_DATA_POS_OFFSET, this.angleYaw + this.jitterY, 0.0f, 1.0f, 0.0f);
        Matrix.scaleM(this.mMMatrix, TRIANGLE_VERTICES_DATA_POS_OFFSET, 0.1f, 0.1f, 0.1f);
        Matrix.translateM(this.mMMatrix, TRIANGLE_VERTICES_DATA_POS_OFFSET, 0.0f, this.anglePitch / 4.0f, 0.0f);
        Matrix.multiplyMM(this.mMVPMatrix, TRIANGLE_VERTICES_DATA_POS_OFFSET, this.mVMatrix, TRIANGLE_VERTICES_DATA_POS_OFFSET, this.mMMatrix, TRIANGLE_VERTICES_DATA_POS_OFFSET);
        Matrix.multiplyMM(this.mMVPMatrix, TRIANGLE_VERTICES_DATA_POS_OFFSET, this.mProjMatrix, TRIANGLE_VERTICES_DATA_POS_OFFSET, this.mMVPMatrix, TRIANGLE_VERTICES_DATA_POS_OFFSET);
        GLES20.glUniformMatrix4fv(this.muMVPMatrixHandle, 1, false, this.mMVPMatrix, TRIANGLE_VERTICES_DATA_POS_OFFSET);
        GLES20.glDrawArrays(FLOAT_SIZE_BYTES, TRIANGLE_VERTICES_DATA_POS_OFFSET, this.numPolysStem);
        checkGlError("glDrawArrays");
    }

    private void drawStemAT() {
        Matrix.setLookAtM(this.mVMatrix, TRIANGLE_VERTICES_DATA_POS_OFFSET, 0.0f, 0.0f, -5.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f);
        this.mTriangleVerticesStem.position(TRIANGLE_VERTICES_DATA_POS_OFFSET);
        GLES20.glVertexAttribPointer(this.mAlpha_aPositionHandle, TRIANGLE_VERTICES_DATA_UV_OFFSET, 5126, false, TRIANGLE_VERTICES_DATA_STRIDE_BYTES, this.mTriangleVerticesStem);
        this.mTriangleVerticesStem.position(TRIANGLE_VERTICES_DATA_UV_OFFSET);
        GLES20.glEnableVertexAttribArray(this.mAlpha_aPositionHandle);
        GLES20.glVertexAttribPointer(this.mAlpha_aTextureHandle, 2, 5126, false, TRIANGLE_VERTICES_DATA_STRIDE_BYTES, this.mTriangleVerticesStem);
        GLES20.glEnableVertexAttribArray(this.mAlpha_aTextureHandle);
        this.mMMatrix = new float[16];
        Matrix.setRotateM(this.mMMatrix, TRIANGLE_VERTICES_DATA_POS_OFFSET, this.anglePitch + this.jitterX, 1.0f, 0.0f, 0.0f);
        rotateMatrix(this.mMMatrix, TRIANGLE_VERTICES_DATA_POS_OFFSET, this.angleYaw + this.jitterY, 0.0f, 1.0f, 0.0f);
        Matrix.scaleM(this.mMMatrix, TRIANGLE_VERTICES_DATA_POS_OFFSET, 0.1f, 0.1f, 0.1f);
        Matrix.translateM(this.mMMatrix, TRIANGLE_VERTICES_DATA_POS_OFFSET, 0.0f, this.anglePitch / 4.0f, 0.0f);
        Matrix.multiplyMM(this.mMVPMatrix, TRIANGLE_VERTICES_DATA_POS_OFFSET, this.mVMatrix, TRIANGLE_VERTICES_DATA_POS_OFFSET, this.mMMatrix, TRIANGLE_VERTICES_DATA_POS_OFFSET);
        Matrix.multiplyMM(this.mMVPMatrix, TRIANGLE_VERTICES_DATA_POS_OFFSET, this.mProjMatrix, TRIANGLE_VERTICES_DATA_POS_OFFSET, this.mMVPMatrix, TRIANGLE_VERTICES_DATA_POS_OFFSET);
        GLES20.glUniformMatrix4fv(this.mAlpha_uMVPMatrixHandle, 1, false, this.mMVPMatrix, TRIANGLE_VERTICES_DATA_POS_OFFSET);
        GLES20.glDrawArrays(FLOAT_SIZE_BYTES, TRIANGLE_VERTICES_DATA_POS_OFFSET, this.numPolysStem);
        checkGlError("glDrawArrays");
    }

    private void drawSphere() {
        Matrix.setLookAtM(this.mVMatrix, TRIANGLE_VERTICES_DATA_POS_OFFSET, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 10.0f, 0.0f, 1.0f, 0.0f);
        this.mTriangleVerticesSphere.position(TRIANGLE_VERTICES_DATA_POS_OFFSET);
        GLES20.glVertexAttribPointer(this.mDummy_aPositionHandle, TRIANGLE_VERTICES_DATA_UV_OFFSET, 5126, false, TRIANGLE_VERTICES_DATA_STRIDE_BYTES, this.mTriangleVerticesSphere);
        this.mTriangleVerticesSphere.position(TRIANGLE_VERTICES_DATA_UV_OFFSET);
        GLES20.glEnableVertexAttribArray(this.mDummy_aPositionHandle);
        GLES20.glVertexAttribPointer(this.mDummy_aTextureHandle, 2, 5126, false, TRIANGLE_VERTICES_DATA_STRIDE_BYTES, this.mTriangleVerticesSphere);
        GLES20.glEnableVertexAttribArray(this.mDummy_aTextureHandle);
        this.mMMatrix = new float[16];
        Matrix.setRotateM(this.mVMatrix, TRIANGLE_VERTICES_DATA_POS_OFFSET, (this.anglePitch + this.jitterX) / 2.0f, 1.0f, 0.0f, 0.0f);
        rotateMatrix(this.mVMatrix, TRIANGLE_VERTICES_DATA_POS_OFFSET, this.angleYaw + (this.jitterY / 2.0f), 0.0f, 1.0f, 0.0f);
        Matrix.multiplyMM(this.mMVPMatrix, TRIANGLE_VERTICES_DATA_POS_OFFSET, this.mVMatrix, TRIANGLE_VERTICES_DATA_POS_OFFSET, this.mMMatrix, TRIANGLE_VERTICES_DATA_POS_OFFSET);
        Matrix.multiplyMM(this.mMVPMatrix, TRIANGLE_VERTICES_DATA_POS_OFFSET, this.mProjMatrix, TRIANGLE_VERTICES_DATA_POS_OFFSET, this.mVMatrix, TRIANGLE_VERTICES_DATA_POS_OFFSET);
        GLES20.glUniformMatrix4fv(this.mDummy_uMVPMatrixHandle, 1, false, this.mMVPMatrix, TRIANGLE_VERTICES_DATA_POS_OFFSET);
        GLES20.glDrawArrays(FLOAT_SIZE_BYTES, TRIANGLE_VERTICES_DATA_POS_OFFSET, this.numPolysSphere);
        checkGlError("glDrawArrays");
    }

    private void drawVignette() {
        Matrix.setLookAtM(this.mVMatrix, TRIANGLE_VERTICES_DATA_POS_OFFSET, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 10.0f, 0.0f, 1.0f, 0.0f);
        this.mTriangleVerticesVignette.position(TRIANGLE_VERTICES_DATA_POS_OFFSET);
        GLES20.glVertexAttribPointer(this.mDummy_aPositionHandle, TRIANGLE_VERTICES_DATA_UV_OFFSET, 5126, false, TRIANGLE_VERTICES_DATA_STRIDE_BYTES, this.mTriangleVerticesVignette);
        this.mTriangleVerticesVignette.position(TRIANGLE_VERTICES_DATA_UV_OFFSET);
        GLES20.glEnableVertexAttribArray(this.mDummy_aPositionHandle);
        GLES20.glVertexAttribPointer(this.mDummy_aTextureHandle, 2, 5126, false, TRIANGLE_VERTICES_DATA_STRIDE_BYTES, this.mTriangleVerticesVignette);
        GLES20.glEnableVertexAttribArray(this.mDummy_aTextureHandle);
        this.mMMatrix = new float[16];
        Matrix.setRotateM(this.mVMatrix, TRIANGLE_VERTICES_DATA_POS_OFFSET, 0.0f, 1.0f, 0.0f, 0.0f);
        rotateMatrix(this.mVMatrix, TRIANGLE_VERTICES_DATA_POS_OFFSET, 0.0f, 0.0f, 1.0f, 0.0f);
        Matrix.multiplyMM(this.mMVPMatrix, TRIANGLE_VERTICES_DATA_POS_OFFSET, this.mVMatrix, TRIANGLE_VERTICES_DATA_POS_OFFSET, this.mMMatrix, TRIANGLE_VERTICES_DATA_POS_OFFSET);
        Matrix.multiplyMM(this.mMVPMatrix, TRIANGLE_VERTICES_DATA_POS_OFFSET, this.mOrthoMatrix, TRIANGLE_VERTICES_DATA_POS_OFFSET, this.mVMatrix, TRIANGLE_VERTICES_DATA_POS_OFFSET);
        GLES20.glUniformMatrix4fv(this.mDummy_uMVPMatrixHandle, 1, false, this.mMVPMatrix, TRIANGLE_VERTICES_DATA_POS_OFFSET);
        GLES20.glDrawArrays(5, TRIANGLE_VERTICES_DATA_POS_OFFSET, FLOAT_SIZE_BYTES);
        checkGlError("glDrawArrays");
    }

    public void onSurfaceChanged(GL10 glUnused, int width, int height) {
        GLES20.glViewport(TRIANGLE_VERTICES_DATA_POS_OFFSET, TRIANGLE_VERTICES_DATA_POS_OFFSET, width, height);
        float ratio = ((float) width) / ((float) height);
        Matrix.frustumM(this.mProjMatrix, TRIANGLE_VERTICES_DATA_POS_OFFSET, -ratio, ratio, -1.0f, 1.0f, 2.0f, 250.0f);
        Matrix.orthoM(this.mOrthoMatrix, TRIANGLE_VERTICES_DATA_POS_OFFSET, -1.0f, 1.0f, -1.0f, 1.0f, 2.0f, 250.0f);
        this.rotationSwipeSize = ((float) Math.min(width, height)) * 0.75f;
        this.lastFrameTime = System.currentTimeMillis();
    }

    public void onSurfaceCreated(GL10 glUnused, EGLConfig config) {
        this.mDummyProgram = createProgram("uniform highp mat4 uMVPMatrix;\nattribute highp vec4 aPosition;\nattribute highp vec2 aTextureCoord;\nvarying mediump vec2 vTextureCoord;\nvoid main() {\n  gl_Position = uMVPMatrix * aPosition;\n  vTextureCoord = aTextureCoord;\n}\n", "precision mediump float;\nvarying mediump vec2 vTextureCoord;\nuniform sampler2D sTexture;\nvoid main() {\n  vec4 base = texture2D(sTexture, vTextureCoord);\n  gl_FragColor = base;\n}\n");
        if (this.mDummyProgram != 0) {
            GLES20.glUseProgram(this.mDummyProgram);
            this.mDummy_aPositionHandle = GLES20.glGetAttribLocation(this.mDummyProgram, "aPosition");
            checkGlError("glGetAttribLocation aPosition");
            if (this.mDummy_aPositionHandle == -1) {
                throw new RuntimeException("Could not get attrib location for aPosition");
            }
            this.mDummy_aTextureHandle = GLES20.glGetAttribLocation(this.mDummyProgram, "aTextureCoord");
            checkGlError("glGetAttribLocation aTextureCoord");
            if (this.mDummy_aTextureHandle == -1) {
                throw new RuntimeException("Could not get attrib location for aTextureCoord");
            }
            this.mDummy_uMVPMatrixHandle = GLES20.glGetUniformLocation(this.mDummyProgram, "uMVPMatrix");
            checkGlError("glGetUniformLocation uMVPMatrix");
            if (this.mDummy_uMVPMatrixHandle == -1) {
                throw new RuntimeException("Could not get attrib location for uMVPMatrix");
            }
            this.mDummy_sTextureHandle = GLES20.glGetUniformLocation(this.mDummyProgram, "sTexture");
            checkGlError("glGetUniformLocation sTexture");
            if (this.mDummy_sTextureHandle == -1) {
                throw new RuntimeException("Could not get attrib location for sTexture");
            }
            this.mAlphaProgram = createProgram("uniform highp mat4 uMVPMatrix;\nattribute highp vec4 aPosition;\nattribute highp vec2 aTextureCoord;\nvarying mediump vec2 vTextureCoord;\nvoid main() {\n  gl_Position = uMVPMatrix * aPosition;\n  vTextureCoord = aTextureCoord;\n}\n", "precision mediump float;\nvarying mediump vec2 vTextureCoord;\nuniform sampler2D sTexture;\nuniform sampler2D sAlpha;\nvoid main() {\n vec4 base = texture2D(sTexture, vTextureCoord);\n vec4 mask = texture2D(sAlpha, vTextureCoord);\n gl_FragColor = base;\n if(mask.g < 0.5){ discard; }\n}");
            if (this.mAlphaProgram != 0) {
                GLES20.glUseProgram(this.mAlphaProgram);
                this.mAlpha_aPositionHandle = GLES20.glGetAttribLocation(this.mAlphaProgram, "aPosition");
                checkGlError("glGetAttribLocation aPosition");
                if (this.mAlpha_aPositionHandle == -1) {
                    throw new RuntimeException("Could not get attrib location for aPosition");
                }
                this.mAlpha_aTextureHandle = GLES20.glGetAttribLocation(this.mAlphaProgram, "aTextureCoord");
                checkGlError("glGetAttribLocation aTextureCoord");
                if (this.mAlpha_aTextureHandle == -1) {
                    throw new RuntimeException("Could not get attrib location for aTextureCoord");
                }
                this.mAlpha_uMVPMatrixHandle = GLES20.glGetUniformLocation(this.mAlphaProgram, "uMVPMatrix");
                checkGlError("glGetUniformLocation uMVPMatrix");
                if (this.mAlpha_uMVPMatrixHandle == -1) {
                    throw new RuntimeException("Could not get attrib location for uMVPMatrix");
                }
                this.mAlpha_sTextureHandle = GLES20.glGetUniformLocation(this.mAlphaProgram, "sTexture");
                checkGlError("glGetUniformLocation sTexture");
                if (this.mAlpha_sTextureHandle == -1) {
                    throw new RuntimeException("Could not get attrib location for sTexture");
                }
                this.mAlpha_sAlphaHandle = GLES20.glGetUniformLocation(this.mAlphaProgram, "sAlpha");
                checkGlError("glGetUniformLocation sAlpha");
                if (this.mAlpha_sAlphaHandle == -1) {
                    throw new RuntimeException("Could not get attrib location for sAlpha");
                }
                this.mRoseTextureID = loadETC1Texture("textures/petal-" + this.petalTextureNo + ".png");
                this.mRoseAlphaTextureID = loadETC1Texture("textures/petal-" + this.petalTextureNo + "-alpha.png");
                this.mSphereTextureID = loadTexture("textures/sphere-" + this.sphereTextureNo + ".png");
                this.mStemTextureID = loadETC1Texture("textures/leaves.pkm");
                this.mStemAlphaTextureID = loadETC1Texture("textures/leaves-alpha.pkm");
                this.mVignetteTextureID = loadTexture("textures/vignette.png");
                Matrix.setLookAtM(this.mVMatrix, TRIANGLE_VERTICES_DATA_POS_OFFSET, 0.0f, 0.0f, -5.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f);
                GLES20.glEnable(2929);
                GLES20.glDepthFunc(515);
                GLES20.glClearDepthf(1.0f);
                GLES20.glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
                GLES20.glClear(16640);
            }
        }
    }

    private void unloadTexture(int id) {
        GLES20.glDeleteTextures(1, new int[]{id}, TRIANGLE_VERTICES_DATA_POS_OFFSET);
    }

    private int loadTexture(String filename) {
        int[] textures = new int[1];
        GLES20.glGenTextures(1, textures, TRIANGLE_VERTICES_DATA_POS_OFFSET);
        int textureID = textures[TRIANGLE_VERTICES_DATA_POS_OFFSET];
        GLES20.glBindTexture(3553, textureID);
        GLES20.glTexParameterf(3553, 10241, 9728.0f);
        GLES20.glTexParameterf(3553, 10240, 9729.0f);
        GLES20.glTexParameteri(3553, 10242, 10497);
        GLES20.glTexParameteri(3553, 10243, 10497);
        InputStream is = null;
        try {
            is = Wallpaper.mContext.getAssets().open(filename);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        try {
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                }
            }
            GLUtils.texImage2D(3553, TRIANGLE_VERTICES_DATA_POS_OFFSET, bitmap, TRIANGLE_VERTICES_DATA_POS_OFFSET);
            bitmap.recycle();
            return textureID;
        } catch (Throwable th) {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e2) {
                }
            }
            return textureID;
        }
    }

    protected int loadETC1Texture(String str) {

        String localObject1 = str.replace(".png", ".pkm");
        int[] textures = new int[1];
        GLES20.glGenTextures(1, textures, 0);
        int i = textures[0];
        GLES20.glBindTexture(3553, i);
        GLES20.glTexParameterf(3553, 10241, 9728.0F);
        GLES20.glTexParameterf(3553, 10240, 9729.0F);
        GLES20.glTexParameteri(3553, 10242, 10497);
        GLES20.glTexParameteri(3553, 10243, 10497);
        InputStream is = null;
        try {
            is = Wallpaper.mContext.getAssets().open(localObject1);
            ETC1Util.loadTexture(3553, 0, 0, 6407,
                    33635, is);
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (Exception e) {
                // ignore
            }
        }
        return i;
    }

    private int loadShader(int shaderType, String source) {
        int shader = GLES20.glCreateShader(shaderType);
        if (shader == 0) {
            return shader;
        }
        GLES20.glShaderSource(shader, source);
        GLES20.glCompileShader(shader);
        int[] compiled = new int[1];
        GLES20.glGetShaderiv(shader, 35713, compiled, TRIANGLE_VERTICES_DATA_POS_OFFSET);
        if (compiled[TRIANGLE_VERTICES_DATA_POS_OFFSET] != 0) {
            return shader;
        }
        Log.e(TAG, "Could not compile shader " + shaderType + ":");
        Log.e(TAG, GLES20.glGetShaderInfoLog(shader));
        GLES20.glDeleteShader(shader);
        return TRIANGLE_VERTICES_DATA_POS_OFFSET;
    }

    private int createProgram(String vertexSource, String fragmentSource) {
        this.vertexShader = loadShader(35633, vertexSource);
        if (this.vertexShader == 0) {
            return TRIANGLE_VERTICES_DATA_POS_OFFSET;
        }
        this.pixelShader = loadShader(35632, fragmentSource);
        if (this.pixelShader == 0) {
            return TRIANGLE_VERTICES_DATA_POS_OFFSET;
        }
        int program = GLES20.glCreateProgram();
        if (program == 0) {
            return program;
        }
        GLES20.glAttachShader(program, this.vertexShader);
        checkGlError("glAttachShader");
        GLES20.glAttachShader(program, this.pixelShader);
        checkGlError("glAttachShader");
        GLES20.glLinkProgram(program);
        checkGlError("glLinkProgram");
        int[] linkStatus = new int[1];
        GLES20.glGetProgramiv(program, 35714, linkStatus, TRIANGLE_VERTICES_DATA_POS_OFFSET);
        if (linkStatus[TRIANGLE_VERTICES_DATA_POS_OFFSET] == 1) {
            return program;
        }
        Log.e(TAG, "Could not link program: ");
        Log.e(TAG, GLES20.glGetProgramInfoLog(program));
        GLES20.glDeleteProgram(program);
        return TRIANGLE_VERTICES_DATA_POS_OFFSET;
    }

    private void checkGlError(String op) {
        int error;
        do {
            error = GLES20.glGetError();
            if (error != 0) {
                Log.e(TAG, String.valueOf(op) + ": glError " + error);
            } else {
                return;
            }
        } while (error == 1286);
        throw new RuntimeException(String.valueOf(op) + ": glError " + error);
    }

    public void setResources(Resources res) {
        this.resources = res;
    }

    public void setXOffset(float xOffset) {
        if (!this.bAutoRotate) {
            this.xOffset = xOffset;
            this.angleYaw = (360.0f * xOffset) * ((float) this.rotationDirection);
        }
    }

    public void setYOffset(float deltaY) {
        if (this.bTilt) {
            this.yOffset += deltaY / 4.0f;
            if (this.yOffset > this.rotationSwipeSize) {
                this.yOffset = this.rotationSwipeSize;
            }
            if (this.yOffset < (-this.rotationSwipeSize)) {
                this.yOffset = -this.rotationSwipeSize;
            }
            this.anglePitch = ((this.yOffset / this.rotationSwipeSize) * 15.0f) - 0.1796875f;
            return;
        }
        this.anglePitch = -25.0f;
    }

    public void setRotate(boolean bRotate) {
        this.bRotate = bRotate;
    }

    public void setAutoRotate(boolean bAutoRotate) {
        this.bAutoRotate = bAutoRotate;
    }

    public void setTilt(boolean bTilt) {
        this.bTilt = bTilt;
        if (!bTilt) {
            this.anglePitch = -25.0f;
        }
    }

    public void setBackground(int num) {
        if (this.sphereTextureNo != num) {
            this.bReloadSphere = true;
        }
        this.sphereTextureNo = num;
    }

    public void setPetal(int num) {
        if (this.petalTextureNo != num) {
            this.bReloadPetal = true;
        }
        this.petalTextureNo = num;
    }

    public void freeGLResources() {
        unloadTexture(this.mSphereTextureID);
        unloadTexture(this.mRoseTextureID);
        unloadTexture(this.mRoseAlphaTextureID);
        unloadTexture(this.mStemTextureID);
        unloadTexture(this.mStemAlphaTextureID);
        unloadTexture(this.mVignetteTextureID);
        GLES20.glDeleteProgram(this.mDummyProgram);
        GLES20.glDeleteProgram(this.mAlphaProgram);
    }

    public void setVignette(boolean bVignette) {
        this.bVignette = bVignette;
    }

    public void setRandomize(boolean bRandomize) {
        this.bRandomize = bRandomize;
    }

    public void setPreferences() {
    }

    public void setRotationDirection(int rotationDirection) {
        this.rotationDirection = rotationDirection;
    }

    public void changeSpeed(float impulse) {
        if (!this.bAutoRotate) {
            return;
        }
        if (impulse < 0.0f) {
            this.rotationImpulse = (float) (this.rotationDirection * 50);
        } else {
            this.rotationImpulse = (float) (this.rotationDirection * -50);
        }
    }
}
