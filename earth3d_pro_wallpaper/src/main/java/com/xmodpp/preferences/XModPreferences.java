package com.xmodpp.preferences;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.preference.PreferenceManager;
import android.util.Log;
import com.xmodpp.application.Application;

public class XModPreferences implements OnSharedPreferenceChangeListener {

    private static final String TAG = "XModPreferences";
    long _nativeReference;
    SharedPreferences _sharedPreferences;

    XModPreferences(long j) {
        this._nativeReference = j;
        this._sharedPreferences = PreferenceManager.getDefaultSharedPreferences(Application.jni_getContext());
        this._sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    XModPreferences(String str, long j) {
        this._nativeReference = j;
        this._sharedPreferences = Application.jni_getContext().getSharedPreferences(str, 0);
        this._sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    public static native void nativeOnSharedPreferenceChanged(long j, String str);

    public boolean jni_getBoolean(String str, boolean z) {
        Log.d(TAG, "jni_getBoolean: " + str + "=" + z);
        return z;
    }

    public float jni_getFloat(String str, float f) {
        Log.d(TAG, "jni_getFloat: " + str + "=" + f);
        return f;
    }

    public int jni_getInt(String str, int i) {
        Log.d(TAG, "jni_getInt: " + str + "=" + i);
        return i;
    }

    public long jni_getLong(String str, long j) {
        Log.d(TAG, "jni_getLong: " + str + "=" + j);
        return j;
    }

    public String jni_getString(String str, String str2) {
        Log.d(TAG, "jni_getString: " + str + "=" + str2);
        return str2;
    }

    public boolean jni_putBoolean(String str, boolean z) {
        return this._sharedPreferences.edit().putBoolean(str, z).commit();
    }

    public boolean jni_putFloat(String str, float f) {
        return this._sharedPreferences.edit().putFloat(str, f).commit();
    }

    public boolean jni_putInt(String str, int i) {
        return this._sharedPreferences.edit().putInt(str, i).commit();
    }

    public boolean jni_putLong(String str, long j) {
        return this._sharedPreferences.edit().putLong(str, j).commit();
    }

    public boolean jni_putString(String str, String str2) {
        return this._sharedPreferences.edit().putString(str, str2).commit();
    }

    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String str) {
        nativeOnSharedPreferenceChanged(this._nativeReference, str);
    }
}
