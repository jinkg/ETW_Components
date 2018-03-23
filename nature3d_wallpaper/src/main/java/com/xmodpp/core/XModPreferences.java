package com.xmodpp.core;

import android.text.TextUtils;

public class XModPreferences {
    private static final String TAG = "XModPreferences";
    long f10175a;

    XModPreferences(long j) {
        this.f10175a = j;
    }

    XModPreferences(String str, long j) {
        this.f10175a = j;
    }

    public static XModPreferences jni_Create(long j) {
        return new XModPreferences(j);
    }

    public static XModPreferences jni_Create(String str, long j) {
        return new XModPreferences(str, j);
    }

    public static native void nativeOnSharedPreferenceChanged(long j, String str);

    public boolean jni_getBoolean(String str, boolean z) {
        return z;
    }

    public float jni_getFloat(String str, float f) {
        return f;
    }

    public int jni_getInt(String str, int i) {
        if (TextUtils.equals(str, "theme")) {
            return 1;
        } else if (TextUtils.equals(str, "lbQuality")) {
            return 0;
        } else if (TextUtils.equals(str, "lbFocalPlane")) {
            return 10;
        }
        return i;
    }

    public long jni_getLong(String str, long j) {
        return j;
    }

    public String jni_getString(String str, String str2) {
        return str2;
    }

    public boolean jni_putBoolean(String str, boolean z) {
        return true;
    }

    public boolean jni_putFloat(String str, float f) {
        return true;
    }

    public boolean jni_putInt(String str, int i) {
        return true;
    }

    public boolean jni_putLong(String str, long j) {
        return true;
    }

    public boolean jni_putString(String str, String str2) {
        return true;
    }

}
