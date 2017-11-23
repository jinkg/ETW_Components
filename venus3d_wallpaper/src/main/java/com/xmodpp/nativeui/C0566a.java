package com.xmodpp.nativeui;

import android.content.res.Configuration;
import android.service.dreams.DreamService;
import com.xmodpp.core.App;

public abstract class C0566a extends DreamService {
    private XMODSurfaceView f4966a;

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        setInteractive(false);
        setFullscreen(true);
        setContentView(this.f4966a);
    }

    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
    }

    public void onCreate() {
        super.onCreate();
        App.m8558a(getApplicationContext());
        this.f4966a = new XMODSurfaceView(this);
        this.f4966a.m8569c().m8564a(3);
    }

    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    public void onDreamingStarted() {
        super.onDreamingStarted();
        this.f4966a.m8569c().m8564a(3);
        this.f4966a.m8568b();
    }

    public void onDreamingStopped() {
        super.onDreamingStopped();
        this.f4966a.m8567a();
    }
}
