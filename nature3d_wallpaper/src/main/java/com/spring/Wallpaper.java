package com.spring;

import android.content.Context;
import android.service.wallpaper.WallpaperService;
import android.view.SurfaceHolder;

import com.xmodpp.nativeui.C0268b;
import com.xmodpp.core.LibraryLoader;


public class Wallpaper extends C0268b {
    public Wallpaper(Context host) {
        super(host, "MyWallpaper");
    }

    public WallpaperService.Engine onCreateEngine() {
        return new ar();
    }

    public class ar extends C0270c {

        public ar() {
            super();
        }

        public void onCreate(SurfaceHolder surfaceHolder) {
            super.onCreate(surfaceHolder);
            LibraryLoader.m20003a(getApplicationContext(), "libMyApplication");
        }
    }


}
