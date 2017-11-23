package com.xmodpp.nativeui;

import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceHolder.Callback2;
import android.view.View.OnTouchListener;
import android.view.WindowManager;

import com.xmodpp.application.Application;

import static android.view.MotionEvent.ACTION_CANCEL;
import static android.view.MotionEvent.ACTION_DOWN;
import static android.view.MotionEvent.ACTION_POINTER_DOWN;
import static android.view.MotionEvent.ACTION_POINTER_UP;
import static android.view.MotionEvent.ACTION_UP;

public class XMODSurface implements Callback, Callback2, OnTouchListener {
    public static final int ID_DEFAULT = 0;
    public static final int ID_SCREENSAVER = 3;
    public static final int ID_SCREENSAVER_PREVIEW = 4;
    protected int mID = 0;
    protected long nativeReference = 0;
    protected boolean running = false;

    public native long nativeOnCreate(Surface surface, int i);

    public native void nativeOnDestroy(long j);

    public native void nativeOnPause(long j);

    public native void nativeOnResize(long j, int i, int i2);

    public native void nativeOnResume(long j);

    public native void nativeOnRotate(long j, int i);

    public native void nativeOnTouchBegan(long j, int i, float f, float f2);

    public native void nativeOnTouchEnded(long j, int i, boolean z);

    public native void nativeOnTouchMoved(long j, int[] iArr, float[] fArr, float[] fArr2);

    public void onPause() {
        if (this.running) {
            nativeOnPause(this.nativeReference);
            this.running = false;
        }
    }

    public void onResume() {
        if (!this.running) {
            nativeOnResume(this.nativeReference);
            updateRotation();
            this.running = true;
        }
    }

    @android.annotation.SuppressLint({"ClickableViewAccessibility"})
    public boolean onTouch(android.view.View paramView, android.view.MotionEvent paramMotionEvent) {
        int j = 0;
        int i = 0;
        switch (paramMotionEvent.getActionMasked()) {
            case ACTION_DOWN:
                nativeOnTouchBegan(this.nativeReference, paramMotionEvent.getPointerId(0),
                        paramMotionEvent.getX(), paramMotionEvent.getY());
                return true;
            case ACTION_UP:
                nativeOnTouchEnded(this.nativeReference, paramMotionEvent.getPointerId(0), false);
                return true;
            case ACTION_POINTER_DOWN:
                i = paramMotionEvent.getActionIndex();
                nativeOnTouchBegan(this.nativeReference, paramMotionEvent.getPointerId(i),
                        paramMotionEvent.getX(i), paramMotionEvent.getY(i));
                return true;
            case ACTION_POINTER_UP:
                nativeOnTouchEnded(this.nativeReference, paramMotionEvent.getPointerId(paramMotionEvent.getActionIndex()), false);
                return true;
            case ACTION_CANCEL:
                while (i < paramMotionEvent.getPointerCount()) {
                    nativeOnTouchEnded(this.nativeReference, paramMotionEvent.getPointerId(i), true);
                    i += 1;
                }
                break;
            case 4:
            default:
                break;
        }
        int k = paramMotionEvent.getPointerCount();
        float[] floats = new float[k * 2];
        float[] arrayOfFloat = new float[k];
        int[] arrayOfInt = new int[k];
        i = j;
        while (i < k) {
            floats[(i * 2)] = paramMotionEvent.getX(i);
            floats[(i * 2 + 1)] = paramMotionEvent.getY(i);
            arrayOfFloat[i] = paramMotionEvent.getPressure(i);
            arrayOfInt[i] = paramMotionEvent.getPointerId(i);
            i += 1;
        }
        nativeOnTouchMoved(this.nativeReference, arrayOfInt, floats, arrayOfFloat);
        return true;
    }

    public void setID(int i) {
        this.mID = i;
    }

    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
        nativeOnResize(this.nativeReference, i2, i3);
        updateRotation();
    }

    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        this.nativeReference = nativeOnCreate(surfaceHolder.getSurface(), this.mID);
        if (this.running) {
            nativeOnResume(this.nativeReference);
            updateRotation();
        }
    }

    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        onPause();
        nativeOnDestroy(this.nativeReference);
        this.nativeReference = 0;
    }

    public void surfaceRedrawNeeded(SurfaceHolder surfaceHolder) {
    }

    public void updateRotation() {
        switch (((WindowManager) Application.jni_getContext().getSystemService("window")).getDefaultDisplay().getRotation()) {
            case 0:
                nativeOnRotate(this.nativeReference, 0);
                return;
            case 1:
                nativeOnRotate(this.nativeReference, 90);
                return;
            case 2:
                nativeOnRotate(this.nativeReference, 180);
                return;
            case 3:
                nativeOnRotate(this.nativeReference, 270);
                return;
            default:
                return;
        }
    }
}
