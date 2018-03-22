package com.kinglloy.wallpaper.nature3d;

import android.content.Context;
import android.service.wallpaper.WallpaperService;

import com.spring.Wallpaper;
import com.yalin.style.engine.IProvider;

/**
 * @author jinyalin
 * @since 2018/3/22.
 */

public class ProviderImpl implements IProvider {
    @Override
    public WallpaperService provideProxy(Context host) {
        return new Wallpaper(host);
    }
}
