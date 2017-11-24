package com.xmodpp.addons.wallpaper;

import android.view.Surface;
import android.view.SurfaceHolder;
import com.xmodpp.nativeui.XMODSurface;

public class XMODWallpaperSurface extends XMODSurface {
    public static final int ID_WALLPAPER = 1;
    public static final int ID_WALLPAPER_PREVIEW = 2;
    static final String TAG = "XMODWallpaperSurface";

    public native long nativeOnCreate(Surface surface, int i);

    public native void nativeOnWallpaperOffsetsChanged(long j, float f, float f2, float f3, float f4, int i, int i2);

    public void onWallpaperOffsetsChanged(float f, float f2, float f3, float f4, int i, int i2) {
        nativeOnWallpaperOffsetsChanged(this.nativeReference, f, f2, f3, f4, i, i2);
    }

    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        this.nativeReference = nativeOnCreate(surfaceHolder.getSurface(), this.mID);
        if (this.running) {
            nativeOnResume(this.nativeReference);
            updateRotation();
        }
    }
}
