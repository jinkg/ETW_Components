package com.xmodpp.preferences;

import android.content.SharedPreferences;

public class XModPreferences {
    long _nativeReference;

    XModPreferences(long j) {
        this._nativeReference = j;
    }

    XModPreferences(String str, long j) {
        this._nativeReference = j;
    }

    public static native void nativeOnSharedPreferenceChanged(long j, String str);

    public boolean jni_getBoolean(String str, boolean z) {
        return z;
    }

    public float jni_getFloat(String str, float f) {
        return f;
    }

    public int jni_getInt(String str, int i) {
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

    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String str) {
        nativeOnSharedPreferenceChanged(this._nativeReference, str);
    }
}
