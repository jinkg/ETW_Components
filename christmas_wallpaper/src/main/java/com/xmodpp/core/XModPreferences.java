package com.xmodpp.core;


public class XModPreferences {
    long _nativeReference;

    public static native void nativeOnSharedPreferenceChanged(long j, String str);

    static XModPreferences jni_Create(String name, long nativeReference) {
        return new XModPreferences(name, nativeReference);
    }

    static XModPreferences jni_Create(long nativeReference) {
        return new XModPreferences(nativeReference);
    }

    XModPreferences(String name, long nativeReference) {
        this._nativeReference = nativeReference;
    }

    XModPreferences(long nativeReference) {
        this._nativeReference = nativeReference;
    }

    public float jni_getFloat(String key, float defValue) {
        return defValue;
    }

    public boolean jni_putFloat(String key, float value) {
        return true;
    }

    public int jni_getInt(String key, int defValue) {
        return defValue;
    }

    public boolean jni_putInt(String key, int value) {
        return true;
    }

    public long jni_getLong(String key, long defValue) {
        return defValue;
    }

    public boolean jni_putLong(String key, long value) {
        return true;
    }

    public boolean jni_getBoolean(String key, boolean defValue) {
        return defValue;
    }

    public boolean jni_putBoolean(String key, boolean value) {
        return true;
    }

    public String jni_getString(String key, String defValue) {
        return defValue;
    }

    public boolean jni_putString(String key, String value) {
        return true;
    }
}
