package com.xmodpp.nativeui;

import android.annotation.TargetApi;
import android.content.res.Configuration;
import android.service.dreams.DreamService;
import com.xmodpp.core.App;

@TargetApi(17)
public abstract class XMODDreamService extends DreamService {
    private static final boolean logging = false;
    private XMODSurfaceView surfaceView;

    public void setWindowID(String id) {
        this.surfaceView.setWindowId(id);
    }

    public void onCreate() {
        super.onCreate();
        App.Init(getApplicationContext());
        this.surfaceView = new XMODSurfaceView(this);
    }

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        setInteractive(false);
        setFullscreen(true);
        setContentView(this.surfaceView);
    }

    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    public void onDreamingStarted() {
        super.onDreamingStarted();
        this.surfaceView.getSurface().setMode(3);
        this.surfaceView.onResume();
    }

    public void onDreamingStopped() {
        super.onDreamingStopped();
        this.surfaceView.onPause();
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
