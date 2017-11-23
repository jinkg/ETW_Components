package com.xmodpp.nativeui;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;
import com.xmodpp.core.XModPreferences;
import com.xmodpp.gles.GLESThread;

public class XModManagedActivity extends Activity {
    public static final boolean _logging = true;
    public SurfaceView glSurface;
    public GLESThread glesThread;
    public String name;
    public XModGLWindow window;

    public XModGLWindow getXMODGLWindow() {
        return this.window;
    }

    public XModManagedActivity(String name) {
        this.name = name;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("XMOD++", "XModManagedActivity onCreate");
        XModPreferences.Init(getApplicationContext());
        this.window = new XModGLWindow(getApplicationContext(), this.name);
        this.glesThread = new GLESThread(this.window);
        this.glSurface = new SurfaceView(this);
        this.glSurface.getHolder().addCallback(this.glesThread);
        this.glSurface.setOnTouchListener(this.window);
        setContentView(this.glSurface);
    }

    protected void onResume() {
        Log.d("XMOD++", "XModManagedActivity onResume");
        super.onResume();
        this.glesThread.startRendering();
    }

    protected void onPause() {
        Log.d("XMOD++", "XModManagedActivity onPause");
        this.glesThread.pauseRendering();
        super.onPause();
    }

    public void onDestroy() {
        Log.d("XMOD++", "XModManagedActivity onDestroy");
        super.onDestroy();
    }
}
