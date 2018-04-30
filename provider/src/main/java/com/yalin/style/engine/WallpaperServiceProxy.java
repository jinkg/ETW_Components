package com.yalin.style.engine;

import android.content.Context;
import android.service.wallpaper.WallpaperService;

/**
 * @author jinyalin
 * @since 2017/8/3.
 */

public class WallpaperServiceProxy extends WallpaperService {
    public WallpaperServiceProxy(Context host) {
        attachBaseContext(host);
    }

    @Override
    public Engine onCreateEngine() {
        return null;
    }

    public class ActiveEngine extends Engine {
    }
}
