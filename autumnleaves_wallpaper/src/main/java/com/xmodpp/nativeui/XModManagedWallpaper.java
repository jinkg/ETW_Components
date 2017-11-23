package com.xmodpp.nativeui;

import android.content.Context;
import android.service.wallpaper.WallpaperService;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import com.xmodpp.core.App;
import com.xmodpp.gles.GLESThread;

import java.util.ArrayList;

public class XModManagedWallpaper extends WallpaperService {
    static GLEngine activeEngine;
    static final boolean logging = false;
    String _name = "MyWallpaper";
    ArrayList<GLEngine> engines = new ArrayList<>();

    public class GLEngine extends Engine {
        public GLESThread glesThread;
        boolean isVisible = false;
        public XModGLWindow window;

        public GLEngine() {
            super();
        }

        public void onCreate(SurfaceHolder surfaceHolder) {
            super.onCreate(surfaceHolder);
            this.window = new XModGLWindow(XModManagedWallpaper.this.getApplicationContext(), XModManagedWallpaper.this._name);
            this.glesThread = new GLESThread(this.window);
            surfaceHolder.addCallback(this.glesThread);
            XModManagedWallpaper.this.engines.add(this);
        }

        public void onDestroy() {
            XModManagedWallpaper.this.engines.remove(this);
            super.onDestroy();
        }

        public void onSurfaceCreated(SurfaceHolder holder) {
            super.onSurfaceCreated(holder);
        }

        public void onSurfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            super.onSurfaceChanged(holder, format, width, height);
        }

        public void onSurfaceDestroyed(SurfaceHolder holder) {
            super.onSurfaceDestroyed(holder);
            if (this.isVisible) {
                onVisibilityChanged(false);
            }
        }

        public void onOffsetsChanged(float xOffset, float yOffset, float xOffsetStep, float yOffsetStep, int xPixelOffset, int yPixelOffset) {
            super.onOffsetsChanged(xOffset, yOffset, xOffsetStep, yOffsetStep, xPixelOffset, yPixelOffset);
            if (!isPreview()) {
                this.window.onWallpaperOffsetsChanged(xOffset, yOffset, xOffsetStep, yOffsetStep, xPixelOffset, yPixelOffset);
            }
        }

        public void onVisibilityChanged(boolean visible) {
            super.onVisibilityChanged(visible);
            this.isVisible = visible;
            if (this.isVisible) {
                XModManagedWallpaper.activeEngine = this;
                this.glesThread.startRendering();
                return;
            }
            this.glesThread.pauseRendering();
        }

        public void onTouchEvent(MotionEvent event) {
            super.onTouchEvent(event);
            this.window.onTouch(null, event);
        }
    }

    public void onCreate() {
        super.onCreate();
        App.Init(getApplicationContext());
    }


    public Engine onCreateEngine() {
        return new GLEngine();
    }

    public static GLEngine getActiveEngine() {
        GLEngine gLEngine;
        synchronized (XModManagedWallpaper.class) {
            gLEngine = activeEngine;
        }
        return gLEngine;
    }
}
