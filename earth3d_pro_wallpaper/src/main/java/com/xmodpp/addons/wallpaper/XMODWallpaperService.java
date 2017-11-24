package com.xmodpp.addons.wallpaper;

import android.content.res.Configuration;
import android.service.wallpaper.WallpaperService;
import android.service.wallpaper.WallpaperService.Engine;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import com.xmodpp.application.Application;

public abstract class XMODWallpaperService extends WallpaperService {
    private static XMODWallpaperEngine activeEngine;

    public class XMODWallpaperEngine extends Engine {
        protected XMODWallpaperSurface surface = new XMODWallpaperSurface();

        public XMODWallpaperEngine() {
            super();
        }

        public void onConfigurationChanged(Configuration configuration) {
            this.surface.updateRotation();
        }

        public void onCreate(SurfaceHolder surfaceHolder) {
            super.onCreate(surfaceHolder);
            this.surface.setID(isPreview() ? 2 : 1);
            surfaceHolder.addCallback(this.surface);
        }

        public void onDestroy() {
            super.onDestroy();
        }

        public void onOffsetsChanged(float f, float f2, float f3, float f4, int i, int i2) {
            super.onOffsetsChanged(f, f2, f3, f4, i, i2);
            if (!isPreview()) {
                this.surface.onWallpaperOffsetsChanged(f, f2, f3, f4, i, i2);
            }
        }

        public void onTouchEvent(MotionEvent motionEvent) {
            super.onTouchEvent(motionEvent);
            this.surface.onTouch(null, motionEvent);
        }

        public void onVisibilityChanged(boolean z) {
            super.onVisibilityChanged(z);
            if (z) {
                XMODWallpaperService.setActiveEngine(this);
                this.surface.surfaceChanged(null, 0, getSurfaceHolder().getSurfaceFrame().width(), getSurfaceHolder().getSurfaceFrame().height());
                this.surface.onResume();
                return;
            }
            this.surface.onPause();
        }
    }

    public static synchronized XMODWallpaperEngine getActiveEngine() {
        XMODWallpaperEngine xMODWallpaperEngine;
        synchronized (XMODWallpaperService.class) {
            xMODWallpaperEngine = activeEngine;
        }
        return xMODWallpaperEngine;
    }

    private static synchronized void setActiveEngine(XMODWallpaperEngine xMODWallpaperEngine) {
        synchronized (XMODWallpaperService.class) {
            activeEngine = xMODWallpaperEngine;
        }
    }

    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        XMODWallpaperEngine activeEngine = getActiveEngine();
        if (activeEngine != null) {
            activeEngine.onConfigurationChanged(configuration);
        }
    }

    public void onCreate() {
        super.onCreate();
        Application.Init(getApplicationContext());
    }

    public Engine onCreateEngine() {
        return new XMODWallpaperEngine();
    }
}
