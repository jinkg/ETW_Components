package com.xmodpp.core;

import android.content.Context;
import android.view.WindowManager;

public class App {
    static Context jni_context;

    public static native void OnPause();

    public static native void OnResume();

    public static native void SetLicensed();

    private static int m8557a() {
        return ((WindowManager) jni_context.getSystemService("window")).getDefaultDisplay().getRotation();
    }

    public static synchronized void m8558a(Context context) {
        synchronized (App.class) {
            jni_context = context;
        }
    }

    public static synchronized Context jni_getApplicationContext() {
        Context context;
        synchronized (App.class) {
            context = jni_context;
        }
        return context;
    }
}
