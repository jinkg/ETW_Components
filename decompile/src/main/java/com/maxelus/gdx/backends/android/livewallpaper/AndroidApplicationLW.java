package com.maxelus.gdx.backends.android.livewallpaper;

import android.app.Activity;
import android.os.Build.VERSION;
import android.os.Debug;
import android.os.Handler;
import android.service.wallpaper.WallpaperService;
import android.service.wallpaper.WallpaperService.Engine;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.badlogic.gdx.backends.android.AndroidFiles;
import com.badlogic.gdx.backends.android.surfaceview.FillResolutionStrategy;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxNativesLoader;

public class AndroidApplicationLW extends Activity implements Application {
    protected AndroidAudioLW audio;
    private Engine engine;
    protected final Array<Runnable> executedRunnables = new Array();
    protected AndroidFiles files;
    protected boolean firstResume = true;
    protected AndroidGraphicsLW graphics;
    protected Handler handler;
    protected AndroidInputLW input;
    private ApplicationListener listener;
    protected final Array<Runnable> runnables = new Array();
    protected WallpaperService service;

    static {
        GdxNativesLoader.load();
    }

    public AndroidApplicationLW(WallpaperService service, Engine engine) {
        this.service = service;
        this.engine = engine;
    }

    public void initialize(ApplicationListener listener, boolean useGL2IfAvailable) {
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        config.useGL20 = useGL2IfAvailable;
        initialize(listener, config);
    }

    public void initialize(ApplicationListener listener, AndroidApplicationConfiguration config) {
        this.graphics = new AndroidGraphicsLW(this, config.useGL20, config.resolutionStrategy == null ? new FillResolutionStrategy() : config.resolutionStrategy);
        this.input = new AndroidInputLW(this, config);
        this.audio = new AndroidAudioLW(getService());
        this.files = new AndroidFiles(getService().getAssets());
        this.listener = listener;
        this.handler = new Handler();
        Gdx.app = this;
        Gdx.input = getInput();
        Gdx.audio = getAudio();
        Gdx.files = getFiles();
        Gdx.graphics = getGraphics();
    }

    public void onPause() {
        this.graphics.pause();
        if (this.audio != null) {
            this.audio.pause();
        }
        this.input.unregisterSensorListeners();
    }

    public void onResume() {
        Gdx.app = this;
        Gdx.input = getInput();
        Gdx.audio = getAudio();
        Gdx.files = getFiles();
        Gdx.graphics = getGraphics();
        ((AndroidInputLW) getInput()).registerSensorListeners();
        if (this.audio != null) {
            this.audio.resume();
        }
        if (this.firstResume) {
            this.firstResume = false;
        } else {
            this.graphics.resume();
        }
    }

    public void onDestroy() {
        this.graphics.destroy();
        if (this.audio != null) {
            this.audio.dispose();
        }
    }

    public Audio getAudio() {
        return this.audio;
    }

    public Files getFiles() {
        return this.files;
    }

    public Graphics getGraphics() {
        return this.graphics;
    }

    public Input getInput() {
        return this.input;
    }

    public void log(String tag, String message) {
    }

    public ApplicationType getType() {
        return ApplicationType.Android;
    }

    public int getVersion() {
        return VERSION.SDK.charAt(0) - 48;
    }

    public long getJavaHeap() {
        return Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
    }

    public long getNativeHeap() {
        return Debug.getNativeHeapAllocatedSize();
    }

    public WallpaperService getService() {
        return this.service;
    }

    public Engine getEngine() {
        return this.engine;
    }

    public ApplicationListener getListener() {
        return this.listener;
    }

    public Preferences getPreferences(String name) {
        return new AndroidPreferencesLW(getService().getSharedPreferences(name, 0));
    }

    public void postRunnable(Runnable runnable) {
        synchronized (this.runnables) {
            this.runnables.add(runnable);
        }
    }

    public void log(String tag, String message, Exception exception) {
    }

    public void error(String tag, String message) {
    }

    @Override
    public void error(String tag, String message, Exception exception) {

    }

    public void error(String tag, String message, Throwable exception) {
    }

    public void debug(String tag, String message) {
    }

    public void debug(String tag, String message, Throwable exception) {
    }

    public void setLogLevel(int logLevel) {
    }

    public void exit() {
    }
}
