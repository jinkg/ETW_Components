package com.xmodpp.core;

import android.content.Context;

public class App {
    static Context jni_context;

    public static native void OnPause();

    public static native void OnResume();

    public static native void SetLicensed();

    public static synchronized void Init(Context context) {
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
