package com.xmodpp.nativeui;

import android.content.Context;
import android.view.WindowManager;

public abstract class XModWindowLink {
    Context f299a;
    public long nativeReference = 0;

    public XModWindowLink(Context context) {
        this.f299a = context;
    }

    public int dno_getRotation() {
        switch (((WindowManager) this.f299a.getSystemService("window")).getDefaultDisplay().getRotation()) {
            case 0:
                return 0;
            case 1:
                return 90;
            case 2:
                return 180;
            case 3:
                return 270;
            default:
                return 0;
        }
    }

    public abstract Object dno_getView(String str);

    public native long nativeCreate(String str);

    public native void nativeDestroy(long j);

    public native void nativeOffsetsChanged(long j, float f, float f2, float f3, float f4, int i, int i2);

    public native void nativePause(long j);

    public native void nativeResume(long j);
}
