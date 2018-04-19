package com.codekonditor.mars;

import android.content.Context;
import android.view.SurfaceHolder;

import com.xmodpp.core.App;
import com.xmodpp.core.LibraryLoader;
import com.xmodpp.nativeui.XMODWallpaperService;

public class Wallpaper extends XMODWallpaperService {

    public Wallpaper(Context host) {
        super(host);
    }

    public class MyEngine extends XMODWallpaperEngine {
        public MyEngine() {
            super("MyApplication");
        }

        public void onDestroy() {
            super.onDestroy();
        }

        public void onCreate(SurfaceHolder surfaceHolder) {
            super.onCreate(surfaceHolder);
            LibraryLoader.Load(Wallpaper.this.getApplicationContext(), "libMars");
            App.SetLicensed();
        }
    }

    public Engine onCreateEngine() {
        return new MyEngine();
    }
}
