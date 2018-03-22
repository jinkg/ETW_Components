package com.xmodpp.core;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.preference.PreferenceManager;

public class XModPreferences implements OnSharedPreferenceChangeListener {
    long f10175a;
    SharedPreferences f10176b;

    XModPreferences(long j) {
        this.f10175a = j;
        this.f10176b = PreferenceManager.getDefaultSharedPreferences(App.jni_getApplicationContext());
        this.f10176b.registerOnSharedPreferenceChangeListener(this);
    }

    XModPreferences(String str, long j) {
        this.f10175a = j;
        this.f10176b = App.jni_getApplicationContext().getSharedPreferences(str, 0);
        this.f10176b.registerOnSharedPreferenceChangeListener(this);
    }

    public static XModPreferences jni_Create(long j) {
        return new XModPreferences(j);
    }

    public static XModPreferences jni_Create(String str, long j) {
        return new XModPreferences(str, j);
    }

    public static native void nativeOnSharedPreferenceChanged(long j, String str);

    public boolean jni_getBoolean(String str, boolean z) {
        return this.f10176b.getBoolean(str, z);
    }

    public float jni_getFloat(String str, float f) {
        return this.f10176b.getFloat(str, f);
    }

    public int jni_getInt(String str, int i) {
        return this.f10176b.getInt(str, i);
    }

    public long jni_getLong(String str, long j) {
        return this.f10176b.getLong(str, j);
    }

    public String jni_getString(String str, String str2) {
        return this.f10176b.getString(str, str2);
    }

    public boolean jni_putBoolean(String str, boolean z) {
        return this.f10176b.edit().putBoolean(str, z).commit();
    }

    public boolean jni_putFloat(String str, float f) {
        return this.f10176b.edit().putFloat(str, f).commit();
    }

    public boolean jni_putInt(String str, int i) {
        return this.f10176b.edit().putInt(str, i).commit();
    }

    public boolean jni_putLong(String str, long j) {
        return this.f10176b.edit().putLong(str, j).commit();
    }

    public boolean jni_putString(String str, String str2) {
        return this.f10176b.edit().putString(str, str2).commit();
    }

    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String str) {
        nativeOnSharedPreferenceChanged(this.f10175a, str);
    }
}
