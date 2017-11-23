package com.xmodpp.nativeui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceView;
import com.xmodpp.application.Application;

public class XMODSurfaceView extends SurfaceView {
    private XMODSurface surface = new XMODSurface();

    public XMODSurfaceView(Context context) {
        super(context);
        init(context);
    }

    public XMODSurfaceView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context);
    }

    public XMODSurfaceView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        init(context);
    }

    public XMODSurface getSurface() {
        return this.surface;
    }

    void init(Context context) {
        Application.Init(context.getApplicationContext());
        getHolder().addCallback(this.surface);
    }

    public void onPause() {
        this.surface.onPause();
    }

    public void onResume() {
        this.surface.surfaceChanged(null, 0, getWidth(), getHeight());
        this.surface.onResume();
    }

    @SuppressLint({"ClickableViewAccessibility"})
    public boolean onTouchEvent(MotionEvent motionEvent) {
        this.surface.onTouch(this, motionEvent);
        super.onTouchEvent(motionEvent);
        return true;
    }

    void setID(int i) {
    }

    public void updateRotation() {
        this.surface.updateRotation();
    }
}
