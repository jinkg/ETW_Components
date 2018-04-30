package com.maxelus.gdxlw;

import android.os.Bundle;
import android.service.wallpaper.WallpaperService;
import android.service.wallpaper.WallpaperService.Engine;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import com.maxelus.gdx.backends.android.livewallpaper.AndroidApplicationLW;
import com.maxelus.gdx.backends.android.livewallpaper.AndroidGraphicsLW;
import com.maxelus.gdx.backends.android.livewallpaper.AndroidInputLW;
import com.maxelus.gdx.backends.android.livewallpaper.surfaceview.GLBaseSurfaceView;

public abstract class LibdgxWallpaperService extends WallpaperService {
    private boolean DEBUG = false;
    private LibdgxWallpaperEngine previousEngine;

    public abstract class LibdgxWallpaperEngine extends Engine {
        protected AndroidApplicationLW app;
        protected GLBaseSurfaceView view;
        protected LibdgxWallpaperListener wallpaperListener;

        protected abstract void initialize(AndroidApplicationLW androidApplicationLW);

        public LibdgxWallpaperEngine(LibdgxWallpaperService libdgxWallpaperService) {
            super();
            this.app = new AndroidApplicationLW(libdgxWallpaperService, this);
            initialize(this.app);
            view = ((AndroidGraphicsLW) this.app.getGraphics()).getView();
        }

        public void setWallpaperListener(LibdgxWallpaperListener wallpaperListener) {
            this.wallpaperListener = wallpaperListener;
        }

        public void onCreate(SurfaceHolder surfaceHolder) {
            super.onCreate(surfaceHolder);
            if (LibdgxWallpaperService.this.previousEngine != null) {
                LibdgxWallpaperService.this.previousEngine.view.onPause();
            }
            LibdgxWallpaperService.this.previousEngine = this;
            this.wallpaperListener.setIsPreview(isPreview());
            setTouchEventsEnabled(true);
        }

        public void onDestroy() {
            this.app.onDestroy();
            this.view.onDestroy();
            super.onDestroy();
        }

        public void onPause() {
            this.app.onPause();
            this.view.onPause();
        }

        public void onResume() {
            this.app.onResume();
            this.view.onResume();
        }

        public void onSurfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            super.onSurfaceChanged(holder, format, width, height);
        }

        public void onSurfaceCreated(SurfaceHolder holder) {
            super.onSurfaceCreated(holder);
        }

        public void onSurfaceDestroyed(SurfaceHolder holder) {
            super.onSurfaceDestroyed(holder);
        }

        public void onVisibilityChanged(boolean visible) {
            if (visible) {
                onResume();
            } else {
                onPause();
            }
            super.onVisibilityChanged(visible);
        }

        public void onOffsetsChanged(float xOffset, float yOffset, float xOffsetStep, float yOffsetStep, int xPixelOffset, int yPixelOffset) {
            final float f = xOffset;
            final float f2 = yOffset;
            final float f3 = xOffsetStep;
            final float f4 = yOffsetStep;
            final int i = xPixelOffset;
            final int i2 = yPixelOffset;
            this.app.postRunnable(new Runnable() {
                public void run() {
                    LibdgxWallpaperEngine.this.wallpaperListener.offsetChange(f, f2, f3, f4, i, i2);
                }
            });
            super.onOffsetsChanged(xOffset, yOffset, xOffsetStep, yOffsetStep, xPixelOffset, yPixelOffset);
        }

        public Bundle onCommand(String pAction, int pX, int pY, int pZ, Bundle pExtras, boolean pResultRequested) {
            if (!pAction.equals("android.wallpaper.tap")) {
                pAction.equals("android.home.drop");
            }
            return super.onCommand(pAction, pX, pY, pZ, pExtras, pResultRequested);
        }

        public void onTouchEvent(MotionEvent e) {
            ((AndroidInputLW) this.app.getInput()).onTouch(e);
            super.onTouchEvent(e);
        }
    }

    public abstract Engine onCreateEngine();

    public void onCreate() {
        super.onCreate();
    }

    public void onDestroy() {
        super.onDestroy();
    }
}
