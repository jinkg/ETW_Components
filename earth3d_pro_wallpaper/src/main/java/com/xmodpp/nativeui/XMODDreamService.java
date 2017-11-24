package com.xmodpp.nativeui;

import android.annotation.TargetApi;
import android.content.res.Configuration;
import android.service.dreams.DreamService;
import com.xmodpp.application.Application;

@TargetApi(17)
public abstract class XMODDreamService extends DreamService {
    private XMODSurfaceView surfaceView;

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        setInteractive(false);
        setFullscreen(true);
        setContentView(this.surfaceView);
    }

    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
    }

    public void onCreate() {
        super.onCreate();
        Application.Init(getApplicationContext());
        this.surfaceView = new XMODSurfaceView(this);
        this.surfaceView.getSurface().setID(3);
    }

    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    public void onDreamingStarted() {
        super.onDreamingStarted();
        this.surfaceView.getSurface().setID(3);
        this.surfaceView.onResume();
    }

    public void onDreamingStopped() {
        super.onDreamingStopped();
        this.surfaceView.onPause();
    }
}
