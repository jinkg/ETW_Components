package com.kinglloy.spring3d_01.wallpaper;

import android.content.Context;
import android.service.wallpaper.WallpaperService;

import com.spring.Wallpaper;
import com.xmodpp.Config;
import com.yalin.style.engine.IProvider;

/**
 * @author jinyalin
 * @since 2018/3/23.
 */

public class ProviderImpl implements IProvider {
    @Override
    public WallpaperService provideProxy(Context host) {
        Config.theme = 1;
        Config.background = 3;
        return new Wallpaper(host);
    }
}
