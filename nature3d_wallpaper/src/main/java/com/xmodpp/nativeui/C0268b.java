package com.xmodpp.nativeui;

import android.content.Context;
import android.content.res.Configuration;
import android.service.wallpaper.WallpaperService;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import com.xmodpp.gles.C1560b;
import com.yalin.style.engine.WallpaperServiceProxy;

import java.util.ArrayList;

public class C0268b extends WallpaperServiceProxy {
    static C0270c f1511b;
    String f1512c;
    ArrayList f1513d = new ArrayList();

    public C0268b(Context context, String str) {
        super(context);
        this.f1512c = str;
    }

    public static C0270c m3621a() {
        C0270c c0270c;
        synchronized (C0268b.class) {
            c0270c = f1511b;
        }
        return c0270c;
    }

    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
    }

    public Engine onCreateEngine() {
        return new C0270c();
    }

    public class C0270c extends Engine {
        public XModGLWindow f1524b;
        public C1560b f1525c;
        boolean f1526d = false;

        public C0270c() {
            super();
        }

        public void onCreate(SurfaceHolder surfaceHolder) {
            super.onCreate(surfaceHolder);
            this.f1524b = new XModGLWindow(getApplicationContext(), f1512c);
            this.f1525c = new C1560b(this.f1524b);
            surfaceHolder.addCallback(this.f1525c);
            f1513d.add(this);
        }

        public void onDestroy() {
            f1513d.remove(this);
            super.onDestroy();
        }

        public void onOffsetsChanged(float f, float f2, float f3, float f4, int i, int i2) {
            super.onOffsetsChanged(f, f2, f3, f4, i, i2);
            if (!isPreview()) {
                this.f1524b.m20029a(f, f2, f3, f4, i, i2);
            }
        }

        public void onSurfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
            super.onSurfaceChanged(surfaceHolder, i, i2, i3);
        }

        public void onSurfaceCreated(SurfaceHolder surfaceHolder) {
            super.onSurfaceCreated(surfaceHolder);
        }

        public void onSurfaceDestroyed(SurfaceHolder surfaceHolder) {
            super.onSurfaceDestroyed(surfaceHolder);
            if (this.f1526d) {
                onVisibilityChanged(false);
            }
        }

        public void onTouchEvent(MotionEvent motionEvent) {
            super.onTouchEvent(motionEvent);
            this.f1524b.onTouch(null, motionEvent);
        }

        public void onVisibilityChanged(boolean z) {
            super.onVisibilityChanged(z);
            this.f1526d = z;
            if (this.f1526d) {
                C0268b.f1511b = this;
                this.f1525c.m20021b();
                return;
            }
            this.f1525c.m20022c();
        }
    }

}
