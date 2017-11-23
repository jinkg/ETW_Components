package com.xmodpp.core;

import android.content.Context;
import android.util.Log;
import android.view.WindowManager;

public class App {
    static Context _context;

    public static native void OnPause();

    public static native void OnResume();

    public static native void SetLicensed();

    public static synchronized void Init(Context context) {
        synchronized (App.class) {
            Log.d("XMOD++", "Init app");
            _context = context;
        }
    }

    public static synchronized Context jni_getApplicationContext() {
        Context context;
        synchronized (App.class) {
            context = _context;
        }
        return context;
    }

    public static int getRotation() {
        return ((WindowManager) _context.getSystemService("window")).getDefaultDisplay().getRotation();
    }
}
