package com.xmodpp.nativeui;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

import com.xmodpp.gles.ga;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class XModGLViewLink implements OnTouchListener, ga {
    public long nativeReference = 0;

    public void mo56a(GL10 gl10) {
        if (this.nativeReference != 0) {
            nativeOnSurfaceDestroyed(this.nativeReference);
        }
    }

    public void mo57a(GL10 gl10, int i, int i2) {
        if (this.nativeReference != 0) {
            nativeOnSurfaceChanged(this.nativeReference, i, i2);
        }
    }

    public void mo58a(GL10 gl10, EGLConfig eGLConfig) {
        if (this.nativeReference != 0) {
            nativeOnSurfaceCreated(this.nativeReference);
        }
    }

    public void mo59b(GL10 gl10) {
        if (this.nativeReference != 0) {
            nativeOnRender(this.nativeReference);
        }
    }

    public native void nativeOnRender(long j);

    public native void nativeOnSurfaceChanged(long j, int i, int i2);

    public native void nativeOnSurfaceCreated(long j);

    public native void nativeOnSurfaceDestroyed(long j);

    public native void nativeOnTouchBegan(long j, int i, float f, float f2);

    public native void nativeOnTouchEnded(long j, int i, boolean z);

    public native void nativeOnTouchMoved(long j, int[] iArr, float[] fArr);

    public boolean onTouch(View view, MotionEvent motionEvent) {
        int i = 0;
        if (this.nativeReference == 0) {
            return false;
        }
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
        return true;
    }
}
