package com.xmodpp.nativeui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceView;
import com.xmodpp.core.App;

public class XMODSurfaceView extends SurfaceView {
    private XMODSurface f5022a = new XMODSurface();

    public XMODSurfaceView(Context context) {
        super(context);
        m8566a(context);
    }

    public XMODSurfaceView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        m8566a(context);
    }

    public XMODSurfaceView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        m8566a(context);
    }

    private void m8566a(Context context) {
        getHolder().addCallback(this.f5022a);
        App.m8558a(context.getApplicationContext());
    }

    public final void m8567a() {
        this.f5022a.m8565b();
    }

    public final void m8568b() {
        this.f5022a.surfaceChanged(null, 0, getWidth(), getHeight());
        this.f5022a.m8562a();
    }

    public final XMODSurface m8569c() {
        return this.f5022a;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        this.f5022a.onTouch(this, motionEvent);
        super.onTouchEvent(motionEvent);
        return true;
    }
}
