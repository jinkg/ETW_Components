package org.androidworks.livewallpaperrose;

import android.service.dreams.DreamService;

public class WallpaperDreamService extends DreamService {
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        setInteractive(Prefs.getInteractive());
        setFullscreen(true);
        setContentView(new WallpaperGLSurfaceView(this));
    }
}
