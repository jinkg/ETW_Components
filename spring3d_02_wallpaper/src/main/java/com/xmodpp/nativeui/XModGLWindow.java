package com.xmodpp.nativeui;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

import com.xmodpp.Utils;
import com.xmodpp.core.App;

public class XModGLWindow implements OnTouchListener {
    private String f10218a;
    private boolean f10219b = false;
    private long nativeReference = 0;

    public XModGLWindow(Context context, String str) {
        App.m20002a(context);
        this.f10218a = str;
    }

    public long m20028a() {
        return this.nativeReference;
    }

    public synchronized void m20029a(float f, float f2, float f3, float f4, int i, int i2) {
        if (this.nativeReference != 0) {
            nativeOnWallpaperOffsetsChanged(this.nativeReference, f, f2, f3, f4, i, i2);
        }
    }

    public synchronized void m20030a(int i, int i2) {
        if (this.nativeReference != 0) {
            nativeOnResize(this.nativeReference, i, i2, Utils.jni_GetDisplayRotation());
        }
    }

    public synchronized void m20031b() {
        this.nativeReference = nativeOnCreate(this.f10218a);
        if (this.f10219b) {
            nativeOnResume(this.nativeReference);
        }
    }

    public synchronized void m20032c() {
        if (!this.f10219b) {
            this.f10219b = true;
            if (this.nativeReference != 0) {
                nativeOnResume(this.nativeReference);
            }
        }
    }

    public synchronized void m20033d() {
        this.f10219b = false;
        if (this.nativeReference != 0) {
            nativeOnPause(this.nativeReference);
        }
    }

    public synchronized void m20034e() {
        if (this.nativeReference != 0) {
            nativeOnDestroy(this.nativeReference);
        }
        this.nativeReference = 0;
    }

    public synchronized void m20035f() {
        if (this.nativeReference != 0) {
            nativeOnDrawFrame(this.nativeReference);
        }
    }

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

    public boolean onTouch(View view, MotionEvent motionEvent) {
        int i = 0;
        if (this.nativeReference != 0) {
            switch (motionEvent.getActionMasked()) {
                case 0:
                    nativeOnTouchBegan(this.nativeReference, motionEvent.getPointerId(0), motionEvent.getX(), motionEvent.getY());
                    break;
                case 1:
                    nativeOnTouchEnded(this.nativeReference, motionEvent.getPointerId(0), false);
                    break;
                case 2:
                    int pointerCount = motionEvent.getPointerCount();
                    float[] fArr = new float[(pointerCount * 2)];
                    int[] iArr = new int[pointerCount];
                    while (i < pointerCount) {
                        fArr[i * 2] = motionEvent.getX(i);
                        fArr[(i * 2) + 1] = motionEvent.getY(i);
                        iArr[i] = motionEvent.getPointerId(i);
                        i++;
                    }
                    nativeOnTouchMoved(this.nativeReference, iArr, fArr);
                    break;
                case 3:
                    nativeOnTouchEnded(this.nativeReference, motionEvent.getPointerId(motionEvent.getActionIndex()), true);
                    break;
                case 5:
                    i = motionEvent.getActionIndex();
                    nativeOnTouchBegan(this.nativeReference, motionEvent.getPointerId(i), motionEvent.getX(i), motionEvent.getY(i));
                    break;
                case 6:
                    nativeOnTouchEnded(this.nativeReference, motionEvent.getPointerId(motionEvent.getActionIndex()), false);
                    break;
            }
        }
        return true;
    }
}
