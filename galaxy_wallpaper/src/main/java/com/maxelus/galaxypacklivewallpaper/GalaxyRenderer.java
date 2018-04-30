package com.maxelus.galaxypacklivewallpaper;

import android.content.SharedPreferences.OnSharedPreferenceChangeListener;

import com.maxelus.gdx.backends.android.livewallpaper.InputProcessorLW;
import com.maxelus.gdxlw.LibdgxWallpaperApp;

public abstract class GalaxyRenderer extends LibdgxWallpaperApp implements OnSharedPreferenceChangeListener, InputProcessorLW {
    public abstract void setType(int i);
}
