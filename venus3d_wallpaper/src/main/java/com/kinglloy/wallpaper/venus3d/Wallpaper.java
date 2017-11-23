package com.kinglloy.wallpaper.venus3d;

import android.content.Context;
import android.os.Build;
import android.view.SurfaceHolder;

import com.xmodpp.core.LibraryLoader;
import com.xmodpp.nativeui.C0565b;

public class Wallpaper extends C0565b {
    public Wallpaper(Context host) {
        super(host);
    }

    public Engine onCreateEngine() {
        return new C0569b();
    }

    public final class C0569b extends C0565b.C0568c {

        public C0569b() {
            super();
        }

        public final void onCreate(SurfaceHolder surfaceHolder) {
            super.onCreate(surfaceHolder);
            LibraryLoader.m8559a(getApplicationContext(), "libVenus");
        }

        public final void onDestroy() {
            super.onDestroy();
        }

        public final void onVisibilityChanged(boolean z) {
            if (!z) {
                super.onVisibilityChanged(false);
            } else if (Build.VERSION.SDK_INT >= 17) {
                try {
                    if (!C0567a.f4967a) {
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
}
