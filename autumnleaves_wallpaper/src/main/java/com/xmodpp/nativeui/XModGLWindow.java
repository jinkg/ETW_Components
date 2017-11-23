package com.xmodpp.nativeui;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import com.xmodpp.Utils;

public class XModGLWindow implements OnTouchListener {
    private Context context;
    private boolean isRunning = false;
    private long nativeReference = 0;
    private String windowId;

    public native long nativeOnCreate(String str);

    public native void nativeOnDestroy(long j);

    public native void nativeOnDrawFrame(long j);

    public native void nativeOnPause(long j);

    public native void nativeOnResize(long j, int i, int i2, int i3);

    public native void nativeOnResume(long j);

    public native void nativeOnTouchBegan(long j, int i, float f, float f2);

    public native void nativeOnTouchEnded(long j, int i, boolean z);

    public native void nativeOnTouchMoved(long j, int[] iArr, float[] fArr);

    public native void nativeOnWallpaperOffsetsChanged(long j, float f, float f2, float f3, float f4, int i, int i2);

    public long getNativeReference() {
        return this.nativeReference;
    }

    public XModGLWindow(Context context, String windowId) {
        this.context = context;
        this.windowId = windowId;
    }

    public boolean onTouch(View v, MotionEvent event) {
        if (this.nativeReference != 0) {
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
        }
        return true;
    }

    public synchronized void onWallpaperOffsetsChanged(float xOffset, float yOffset, float xOffsetStep, float yOffsetStep, int xPixelOffset, int yPixelOffset) {
        if (this.nativeReference != 0) {
            nativeOnWallpaperOffsetsChanged(this.nativeReference, xOffset, yOffset, xOffsetStep, yOffsetStep, xPixelOffset, yPixelOffset);
        }
    }

    public synchronized void onCreate() {
        this.nativeReference = nativeOnCreate(this.windowId);
        if (this.isRunning) {
            nativeOnResume(this.nativeReference);
        }
    }

    public synchronized void onResume() {
        this.isRunning = true;
        if (this.nativeReference != 0) {
            nativeOnResume(this.nativeReference);
        }
    }

    public synchronized void onPause() {
        this.isRunning = false;
        if (this.nativeReference != 0) {
            nativeOnPause(this.nativeReference);
        }
    }

    public synchronized void onResize(int width, int height) {
        if (this.nativeReference != 0) {
            nativeOnResize(this.nativeReference, width, height, Utils.getDisplayRotation(this.context));
        }
    }

    public synchronized void onDestroy() {
        if (this.nativeReference != 0) {
            nativeOnDestroy(this.nativeReference);
        }
        this.nativeReference = 0;
    }

    public synchronized void onDrawFrame() {
        if (this.nativeReference != 0) {
            nativeOnDrawFrame(this.nativeReference);
        }
    }

    public int jni_getRotation() {
        return Utils.getDisplayRotation(this.context);
    }
}
