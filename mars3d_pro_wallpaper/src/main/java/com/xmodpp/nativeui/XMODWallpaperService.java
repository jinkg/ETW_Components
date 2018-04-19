package com.xmodpp.nativeui;

import android.content.Context;
import android.content.res.Configuration;
import android.service.wallpaper.WallpaperService;
import android.service.wallpaper.WallpaperService.Engine;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import com.xmodpp.core.App;
import com.yalin.style.engine.WallpaperServiceProxy;

import java.util.ArrayList;

public abstract class XMODWallpaperService extends WallpaperServiceProxy {
    private static XMODWallpaperEngine activeEngine;
    private static final boolean logging = false;
    private ArrayList<XMODWallpaperEngine> engines = new ArrayList<>();

    public XMODWallpaperService(Context host) {
        super(host);
    }

    public class XMODWallpaperEngine extends ActiveEngine {
        private XMODSurface surface = new XMODSurface();

        public XMODWallpaperEngine(String windowId) {
            super();
            this.surface.setWindowId(windowId);
        }

        public void onCreate(SurfaceHolder surfaceHolder) {
            super.onCreate(surfaceHolder);
            surfaceHolder.addCallback(this.surface);
            XMODWallpaperService.this.engines.add(this);
        }

        public void onDestroy() {
            XMODWallpaperService.this.engines.remove(this);
            super.onDestroy();
        }

        public void onOffsetsChanged(float xOffset, float yOffset, float xOffsetStep, float yOffsetStep, int xPixelOffset, int yPixelOffset) {
            super.onOffsetsChanged(xOffset, yOffset, xOffsetStep, yOffsetStep, xPixelOffset, yPixelOffset);
            if (!isPreview()) {
                this.surface.onWallpaperOffsetsChanged(xOffset, yOffset, xOffsetStep, yOffsetStep, xPixelOffset, yPixelOffset);
            }
        }

        public void onVisibilityChanged(boolean visible) {
            super.onVisibilityChanged(visible);
            if (visible) {
                XMODWallpaperService.activeEngine = this;
                this.surface.setMode(isPreview() ? 2 : 1);
                this.surface.surfaceChanged(null, 0, getSurfaceHolder().getSurfaceFrame().width(), getSurfaceHolder().getSurfaceFrame().height());
                this.surface.onResume();
                return;
            }
            this.surface.onPause();
        }

        public void onTouchEvent(MotionEvent event) {
            super.onTouchEvent(event);
            this.surface.onTouch(null, event);
        }
    }

    public void onCreate() {
        super.onCreate();
        App.Init(getApplicationContext());
    }

    public static synchronized XMODWallpaperEngine getActiveEngine() {
        XMODWallpaperEngine xMODWallpaperEngine;
        synchronized (XMODWallpaperService.class) {
            xMODWallpaperEngine = activeEngine;
        }
        return xMODWallpaperEngine;
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
