package com.xmodpp.nativeui;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.View;
import android.view.View.OnTouchListener;

public class XMODSurface implements Callback, OnTouchListener {
    static final boolean logging = true;
    private int mode = 0;
    private long nativeReference = 0;
    private boolean running = false;
    private String windowId = "main";

    public native long nativeOnCreate(String str, Surface surface);

    public native void nativeOnDestroy(long j);

    public native void nativeOnPause(long j);

    public native void nativeOnResize(long j, int i, int i2);

    public native void nativeOnResume(long j, int i);

    public native void nativeOnTouchBegan(long j, int i, float f, float f2);

    public native void nativeOnTouchEnded(long j, int i, boolean z);

    public native void nativeOnTouchMoved(long j, int[] iArr, float[] fArr);

    public native void nativeOnWallpaperOffsetsChanged(long j, float f, float f2, float f3, float f4, int i, int i2);

    public void setWindowId(String windowId_) {
        this.windowId = windowId_;
    }

    public void surfaceCreated(SurfaceHolder holder) {
        Log.d("XMOD++", this + " surfaceCreated");
        this.nativeReference = nativeOnCreate(this.windowId, holder.getSurface());
        if (this.running) {
            nativeOnResume(this.nativeReference, this.mode);
        }
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.d("XMOD++", this + " surfaceChanged");
        nativeOnResize(this.nativeReference, width, height);
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.d("XMOD++", this + " surfaceDestroyed");
        onPause();
        nativeOnDestroy(this.nativeReference);
        this.nativeReference = 0;
    }

    public void onResume() {
        if (!this.running) {
            Log.d("XMOD++", this + " onResume");
            nativeOnResume(this.nativeReference, this.mode);
            this.running = true;
        }
    }

    public void onPause() {
        if (this.running) {
            Log.d("XMOD++", this + " onPause");
            nativeOnPause(this.nativeReference);
            this.running = false;
        }
    }

    @SuppressLint({"ClickableViewAccessibility"})
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getActionMasked()) {
            case 0:
                nativeOnTouchBegan(this.nativeReference, event.getPointerId(0), event.getX(), event.getY());
                break;
            case 1:
                nativeOnTouchEnded(this.nativeReference, event.getPointerId(0), false);
                break;
            case 2:
                int pc = event.getPointerCount();
                float[] coordinates = new float[(pc * 2)];
                int[] pointerIDs = new int[pc];
                for (int i = 0; i < pc; i++) {
                    coordinates[i * 2] = event.getX(i);
                    coordinates[(i * 2) + 1] = event.getY(i);
                    pointerIDs[i] = event.getPointerId(i);
                }
                nativeOnTouchMoved(this.nativeReference, pointerIDs, coordinates);
                break;
            case 3:
                nativeOnTouchEnded(this.nativeReference, event.getPointerId(event.getActionIndex()), true);
                break;
            case 5:
                int ai = event.getActionIndex();
                nativeOnTouchBegan(this.nativeReference, event.getPointerId(ai), event.getX(ai), event.getY(ai));
                break;
            case 6:
                nativeOnTouchEnded(this.nativeReference, event.getPointerId(event.getActionIndex()), false);
                break;
        }
        return true;
    }

    public void onWallpaperOffsetsChanged(float xOffset, float yOffset, float xOffsetStep, float yOffsetStep, int xPixelOffset, int yPixelOffset) {
        nativeOnWallpaperOffsetsChanged(this.nativeReference, xOffset, yOffset, xOffsetStep, yOffsetStep, xPixelOffset, yPixelOffset);
    }

    public void setMode(int mode_) {
        Log.d("XMOD++", this + " setMode(" + mode_ + ")");
        this.mode = mode_;
    }
}
