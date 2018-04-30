package com.maxelus.galaxypacklivewallpaper;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;

import com.maxelus.galaxypacklivewallpaper.config.GalaxySharedPreferences;
import com.mybadlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.maxelus.gdx.backends.android.livewallpaper.AndroidApplicationLW;
import com.maxelus.gdxlw.LibdgxWallpaperService;

public class WallpaperService extends LibdgxWallpaperService {

    public WallpaperService(Context host) {
        super(host);
    }

    public class WallpaperEngine extends LibdgxWallpaperEngine implements OnSharedPreferenceChangeListener {
        public WallpaperEngine(LibdgxWallpaperService libdgxWallpaperService) {
            super(libdgxWallpaperService);
        }

        protected void initialize(AndroidApplicationLW androidApplicationLW) {
            GalaxyPackWallpaper app = new GalaxyPackWallpaper(new GalaxySharedPreferences());
            setWallpaperListener(app);
            AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
            config.useAccelerometer = false;
            config.useCompass = false;
            config.useGL20 = false;
            androidApplicationLW.initialize(app, config);
        }

        public void onSharedPreferenceChanged(SharedPreferences arg0, String arg1) {
        }
    }

    public android.service.wallpaper.WallpaperService.Engine onCreateEngine() {
        return new WallpaperEngine(this);
    }
}
