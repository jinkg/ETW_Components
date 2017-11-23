package com.doctorkettlers.autumn;

import android.content.Context;
import com.xmodpp.core.Assets;
import com.xmodpp.core.XModPreferences;
import com.xmodpp.nativeui.XModManagedWallpaper;

public class Wallpaper extends XModManagedWallpaper {
    static LibraryLoader libraryLoader = new LibraryLoader();


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
        Assets.Init(getApplicationContext());
        XModPreferences.Init(getApplicationContext());
    }

    public Engine onCreateEngine() {
        return new MyEngine();
    }
}
