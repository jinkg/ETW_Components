package com.kinglloy.wallpaper.chrooma.android;

import android.content.Context;
import android.util.Log;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.kinglloy.wallpaper.chrooma.ActionResolver;
import com.kinglloy.wallpaper.chrooma.ChroomaLWP;
import com.kinglloy.wallpaper.chrooma.GamePreferences;
import com.yalin.style.engine.GDXWallpaperServiceProxy;

public class AndroidLauncher extends GDXWallpaperServiceProxy implements ActionResolver {
    public static boolean isRunning = false;
    private String TAG = "AndroidLauncher";
    private ApplicationListener listener;

    public AndroidLauncher(Context host) {
        super(host);
    }

    public void onCreateApplication() {
        super.onCreateApplication();
        isRunning = true;
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        config.useCompass = true;
        config.useAccelerometer = true;
        Log.d(this.TAG, "onCreateApplication");
        this.listener = new ChroomaLWP(this);
        initialize(this.listener, config);
    }

    public Engine onCreateEngine() {
        return new GDXActiveEngine() {
            public void onPause() {
                super.onPause();
                GamePreferences.instance.saveCurrentTime(System.currentTimeMillis());
                AndroidLauncher.this.listener.pause();
            }
        };
    }

    public void setActionLauncher(int index) {
        try {
            //LiveWallpaperSource.with(this).setBitmapSynchronous(new PaletteBitmap(PaletteBitmap.colorToInt(Palette.getPalette(index)), false).getBitmap(100, 100)).run();
        } catch (OutOfMemoryError e) {
        } catch (IllegalArgumentException e2) {
        } catch (IllegalStateException e3) {
        }
    }

    public void shareImage(String path) {
    }
}
