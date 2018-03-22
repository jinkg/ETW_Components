package com.xmodpp.nativeui;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;

import com.xmodpp.gles.C1560b;

public class C1567a extends Activity {
    public static final boolean f10220a = true;
    public String f10221b;
    public XModGLWindow f10222c;
    public SurfaceView f10223d;
    public C1560b f10224e;

    public C1567a(String str) {
        this.f10221b = str;
    }

    public XModGLWindow m20036a() {
        return this.f10222c;
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Log.d("XMOD++", "XModManagedActivity onCreate");
        this.f10222c = new XModGLWindow(getApplicationContext(), this.f10221b);
        this.f10224e = new C1560b(this.f10222c);
        this.f10223d = new SurfaceView(this);
        this.f10223d.getHolder().addCallback(this.f10224e);
        this.f10223d.setOnTouchListener(this.f10222c);
        setContentView(this.f10223d);
    }

    public void onDestroy() {
        Log.d("XMOD++", "XModManagedActivity onDestroy");
        super.onDestroy();
    }

    protected void onPause() {
        Log.d("XMOD++", "XModManagedActivity onPause");
        this.f10224e.m20022c();
        super.onPause();
    }

    protected void onResume() {
        Log.d("XMOD++", "XModManagedActivity onResume");
        super.onResume();
        this.f10224e.m20021b();
    }
}
