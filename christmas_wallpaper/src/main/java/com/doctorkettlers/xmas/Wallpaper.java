package com.doctorkettlers.xmas;

import android.content.Context;

import com.xmodpp.nativeui.XModManagedWallpaper;

public class Wallpaper extends XModManagedWallpaper {
    static LibraryLoader libraryLoader = new LibraryLoader();

    public Wallpaper(Context host) {
        super(host);
    }

    class MyEngine extends GLEngine {
        MyEngine() {
            super();
        }

        public void onDestroy() {
            super.onDestroy();
        }
    }

    public void onCreate() {
        super.onCreate();
        libraryLoader.loadFallback(this);
    }

    public Engine onCreateEngine() {
        return new MyEngine();
    }
}
