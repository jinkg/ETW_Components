package com.afkettler.earth;

import android.annotation.TargetApi;
import com.xmodpp.addons.wallpaper.XMODWallpaperService;
import com.xmodpp.addons.wallpaper.XMODWallpaperService.XMODWallpaperEngine;
import com.xmodpp.application.Application;
import com.xmodpp.nativeui.XMODDreamService;

@TargetApi(17)
public class Daydream extends XMODDreamService {
    static boolean f892a = false;

    public void onCreate() {
        super.onCreate();
        Application.LoadLibrary(getApplicationContext(), "libEarth");
    }

    public void onDreamingStarted() {
        XMODWallpaperEngine activeEngine = XMODWallpaperService.getActiveEngine();
        if (activeEngine != null) {
            activeEngine.onVisibilityChanged(false);
        }
        super.onDreamingStarted();
        f892a = true;
    }

    public void onDreamingStopped() {
        super.onDreamingStopped();
        f892a = false;
        XMODWallpaperEngine activeEngine = XMODWallpaperService.getActiveEngine();
        if (activeEngine != null && activeEngine.isVisible()) {
            activeEngine.onVisibilityChanged(true);
        }
    }
}
