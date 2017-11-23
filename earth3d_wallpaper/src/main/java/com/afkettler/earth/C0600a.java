package com.afkettler.earth;

import android.annotation.TargetApi;
import com.xmodpp.addons.wallpaper.XMODWallpaperService;
import com.xmodpp.addons.wallpaper.XMODWallpaperService.XMODWallpaperEngine;
import com.xmodpp.nativeui.XMODDreamService;

@TargetApi(17)
public class C0600a extends XMODDreamService {
    static boolean f893a = false;

    public void onCreate() {
        super.onCreate();
    }

    public void onDreamingStarted() {
        XMODWallpaperEngine activeEngine = XMODWallpaperService.getActiveEngine();
        if (activeEngine != null) {
            activeEngine.onVisibilityChanged(false);
        }
        super.onDreamingStarted();
        f893a = true;
    }

    public void onDreamingStopped() {
        super.onDreamingStopped();
        f893a = false;
        XMODWallpaperEngine activeEngine = XMODWallpaperService.getActiveEngine();
        if (activeEngine != null && activeEngine.isVisible()) {
            activeEngine.onVisibilityChanged(true);
        }
    }
}
