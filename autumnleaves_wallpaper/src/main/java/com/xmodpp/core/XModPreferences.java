package com.xmodpp.core;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.preference.PreferenceManager;

public class XModPreferences implements OnSharedPreferenceChangeListener {
    static Context _context;
    long _nativeReference;
    SharedPreferences _sharedPreferences;

    public static native void nativeOnSharedPreferenceChanged(long j, String str);

    public static void Init(Context context) {
        _context = context;
    }

    static XModPreferences jni_Create(String name, long nativeReference) {
        return new XModPreferences(name, nativeReference);
    }

    static XModPreferences jni_Create(long nativeReference) {
        return new XModPreferences(nativeReference);
    }

    XModPreferences(String name, long nativeReference) {
        this._nativeReference = nativeReference;
        this._sharedPreferences = _context.getSharedPreferences(name, 0);
        this._sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    XModPreferences(long nativeReference) {
        this._nativeReference = nativeReference;
        this._sharedPreferences = PreferenceManager.getDefaultSharedPreferences(_context);
        this._sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    public float jni_getFloat(String key, float defValue) {
        return this._sharedPreferences.getFloat(key, defValue);
    }

    public boolean jni_putFloat(String key, float value) {
        return this._sharedPreferences.edit().putFloat(key, value).commit();
    }

    public int jni_getInt(String key, int defValue) {
        return this._sharedPreferences.getInt(key, defValue);
    }

    public boolean jni_putInt(String key, int value) {
        return this._sharedPreferences.edit().putInt(key, value).commit();
    }

    public long jni_getLong(String key, long defValue) {
        return this._sharedPreferences.getLong(key, defValue);
    }

    public boolean jni_putLong(String key, long value) {
        return this._sharedPreferences.edit().putLong(key, value).commit();
    }

    public boolean jni_getBoolean(String key, boolean defValue) {
        return this._sharedPreferences.getBoolean(key, defValue);
    }

    public boolean jni_putBoolean(String key, boolean value) {
        return this._sharedPreferences.edit().putBoolean(key, value).commit();
    }

    public String jni_getString(String key, String defValue) {
        return this._sharedPreferences.getString(key, defValue);
    }

    public boolean jni_putString(String key, String value) {
        return this._sharedPreferences.edit().putString(key, value).commit();
    }

    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        nativeOnSharedPreferenceChanged(this._nativeReference, key);
    }
}
