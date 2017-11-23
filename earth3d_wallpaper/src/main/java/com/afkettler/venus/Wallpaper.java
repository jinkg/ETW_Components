package com.afkettler.venus;

import android.content.Context;
import android.os.Build.VERSION;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import com.xmodpp.addons.wallpaper.XMODWallpaperService;
import com.xmodpp.application.Application;

public class Wallpaper extends XMODWallpaperService {

    public Wallpaper(Context host) {
        super(host);
    }

    public class C0599a extends XMODWallpaperEngine {

        public C0599a() {
            super();
        }

        public void onCreate(SurfaceHolder surfaceHolder) {
            Application.LoadLibrary(getApplicationContext(), "libEarth");
            super.onCreate(surfaceHolder);
        }

        public void onDestroy() {
            super.onDestroy();
        }

        public void onTouchEvent(MotionEvent motionEvent) {
            super.onTouchEvent(motionEvent);
        }

        public void onVisibilityChanged(boolean z) {
            if (!z) {
                super.onVisibilityChanged(false);
            } else if (VERSION.SDK_INT >= 17) {
                try {
                    if (!C0600a.f893a) {
                        super.onVisibilityChanged(z);
                    }
                } catch (Throwable th) {
                    super.onVisibilityChanged(z);
                }
            } else {
                super.onVisibilityChanged(z);
            }
        }
    }

    public Engine onCreateEngine() {
        return new C0599a();
    }
}
