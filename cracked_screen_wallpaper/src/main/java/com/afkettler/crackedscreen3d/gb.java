package com.afkettler.crackedscreen3d;

import android.content.Context;
import android.service.wallpaper.WallpaperService;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import com.xmodpp.gles.GLESThread;
import com.xmodpp.nativeui.XModGLViewLink;
import com.xmodpp.nativeui.XModWindowLink;

public class gb extends WallpaperService {
    static gc f282a;
    String f283a;

    public gb(String str) {
        this.f283a = str;
    }

    public static gc m464a() {
        gc gcVar;
        synchronized (gb.class) {
            gcVar = f282a;
        }
        return gcVar;
    }

    public Engine onCreateEngine() {
        return new gc();
    }

    public class gc extends Engine {
        public GLESThread f414a;
        public XModGLViewLink f415a;
        public XModWindowLink f416a;
        boolean f418a = false;
        boolean f419b;

        public gc() {
            super();
        }

        public void onCreate(SurfaceHolder surfaceHolder) {
            super.onCreate(surfaceHolder);
            this.f419b = isPreview();
            this.f415a = new XModGLViewLink();
            this.f416a = new XModWallpaperGLEngine(this, getApplicationContext());
            this.f416a.nativeReference = this.f416a.nativeCreate(f283a);
            this.f414a = new GLESThread(this.f415a);
        }

        public void onDestroy() {
            super.onDestroy();
            if (this.f418a) {
                onVisibilityChanged(false);
            }
            this.f414a.m479a();
            this.f416a.nativeDestroy(this.f416a.nativeReference);
            this.f415a = null;
            this.f416a = null;
        }

        public void onOffsetsChanged(float f, float f2, float f3, float f4, int i, int i2) {
            super.onOffsetsChanged(f, f2, f3, f4, i, i2);
            if (!this.f419b && this.f416a.nativeReference != 0) {
                this.f416a.nativeOffsetsChanged(this.f416a.nativeReference, f, f2, f3, f4, i, i2);
            }
        }

        public void onSurfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
            super.onSurfaceChanged(surfaceHolder, i, i2, i3);
            this.f414a.surfaceChanged(surfaceHolder, i, i2, i3);
        }

        public void onSurfaceCreated(SurfaceHolder surfaceHolder) {
            super.onSurfaceCreated(surfaceHolder);
            this.f414a.surfaceCreated(surfaceHolder);
        }

        public void onSurfaceDestroyed(SurfaceHolder surfaceHolder) {
            super.onSurfaceDestroyed(surfaceHolder);
            this.f414a.surfaceDestroyed();
        }

        public void onTouchEvent(MotionEvent motionEvent) {
            super.onTouchEvent(motionEvent);
            this.f415a.onTouch(null, motionEvent);
        }

        public void onVisibilityChanged(boolean z) {
            super.onVisibilityChanged(z);
            this.f418a = z;
            if (z) {
                synchronized (gb.class) {
                    gb.f282a = this;
                }
                if (this.f416a != null) {
                    if (this.f416a.nativeReference != 0) {
                        this.f416a.nativeResume(this.f416a.nativeReference);
                    }
                    this.f414a.startRendering();
                }
            } else if (this.f416a != null) {
                this.f414a.pauseRendering();
                if (this.f416a.nativeReference != 0) {
                    this.f416a.nativePause(this.f416a.nativeReference);
                }
            }
        }
    }

    public class XModWallpaperGLEngine extends XModWindowLink {
        final /* synthetic */ gc f300a;

        public XModWallpaperGLEngine(gc gcVar, Context context) {
            super(context);
            this.f300a = gcVar;
        }

        public Object dno_getView(String str) {
            return this.f300a.f415a;
        }
    }
}
