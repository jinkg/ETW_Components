package com.xmodpp.nativeui;

import android.content.Context;
import android.content.res.Configuration;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import com.xmodpp.core.App;
import com.yalin.style.engine.WallpaperServiceProxy;

public abstract class C0565b extends WallpaperServiceProxy {
    public static C0568c f4965a;

    public C0565b(Context host) {
        super(host);
    }

    public static synchronized C0568c m8528a() {
        C0568c c0568c;
        synchronized (C0565b.class) {
            c0568c = f4965a;
        }
        return c0568c;
    }

    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
    }

    public void onCreate() {
        super.onCreate();
        App.m8558a(getApplicationContext());
    }

    public class C0568c extends ActiveEngine {
        private XMODSurface f4968a = new XMODSurface();

        public C0568c() {
            super();
        }

        public void onCreate(SurfaceHolder surfaceHolder) {
            super.onCreate(surfaceHolder);
            this.f4968a.m8564a(isPreview() ? 2 : 1);
            surfaceHolder.addCallback(this.f4968a);
        }

        public void onDestroy() {
            super.onDestroy();
        }

        public void onOffsetsChanged(float f, float f2, float f3, float f4, int i, int i2) {
            super.onOffsetsChanged(f, f2, f3, f4, i, i2);
            if (!isPreview()) {
                this.f4968a.m8563a(f, f2, f3, f4, i, i2);
            }
        }

        public void onTouchEvent(MotionEvent motionEvent) {
            super.onTouchEvent(motionEvent);
            this.f4968a.onTouch(null, motionEvent);
        }

        public void onVisibilityChanged(boolean z) {
            super.onVisibilityChanged(z);
            if (z) {
                C0565b.f4965a = this;
                this.f4968a.surfaceChanged(null, 0,
                        getSurfaceHolder().getSurfaceFrame().width(), getSurfaceHolder().getSurfaceFrame().height());
                this.f4968a.m8562a();
                return;
            }
            this.f4968a.m8565b();
        }
    }

}
