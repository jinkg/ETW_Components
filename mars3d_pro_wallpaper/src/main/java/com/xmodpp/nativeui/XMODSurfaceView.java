package com.xmodpp.nativeui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.SurfaceView;
import com.xmodpp.core.App;

public class XMODSurfaceView extends SurfaceView {
    private static final boolean logging = false;
    private XMODSurface surface = new XMODSurface();

    public XMODSurfaceView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    public XMODSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public XMODSurfaceView(Context context) {
        super(context);
        init(context);
    }

    @SuppressLint({"ClickableViewAccessibility"})
    void init(Context context) {
        getHolder().addCallback(this.surface);
        setOnTouchListener(this.surface);
        App.Init(context.getApplicationContext());
    }

    public void setWindowId(String windowId) {
        this.surface.setWindowId(windowId);
    }

    public void onPause() {
        this.surface.onPause();
    }

    public void onResume() {
        this.surface.surfaceChanged(null, 0, getWidth(), getHeight());
        this.surface.onResume();
    }

    public XMODSurface getSurface() {
        return this.surface;
    }
}
