package com.kinglloy.wallpaper.rose3d_2;

import android.content.Context;
import android.service.wallpaper.WallpaperService;

import com.yalin.style.engine.IProvider;

import org.androidworks.livewallpaperrose.Prefs;
import org.androidworks.livewallpaperrose.Wallpaper;

/**
 * @author jinyalin
 * @since 2018/4/27.
 */

public class ProviderImpl implements IProvider {
    @Override
    public WallpaperService provideProxy(Context host) {
        Prefs.background = "1";
        Prefs.petal = "7";
        Prefs.tilt = false;
        Prefs.vignette = true;
        return new Wallpaper(host);
    }
}
