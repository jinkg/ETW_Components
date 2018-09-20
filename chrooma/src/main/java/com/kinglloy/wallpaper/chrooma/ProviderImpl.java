package com.kinglloy.wallpaper.chrooma;

import android.content.Context;
import android.service.wallpaper.WallpaperService;
import com.kinglloy.wallpaper.chrooma.android.AndroidLauncher;
import com.yalin.style.engine.IProvider;

/**
 * @author jinyalin
 * @since 2017/7/28.
 */

public class ProviderImpl implements IProvider {
    @Override
    public WallpaperService provideProxy(Context host) {
        return new AndroidLauncher(host);
    }
}
