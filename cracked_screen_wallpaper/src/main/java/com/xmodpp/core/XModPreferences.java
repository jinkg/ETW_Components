package com.xmodpp.core;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.preference.PreferenceManager;

public class XModPreferences implements OnSharedPreferenceChangeListener {
    static Context f286a;
    long f287a;
    SharedPreferences f288a;

    XModPreferences(long j) {
        this.f287a = j;
        this.f288a = PreferenceManager.getDefaultSharedPreferences(f286a);
        this.f288a.registerOnSharedPreferenceChangeListener(this);
    }

    XModPreferences(String str, long j) {
        this.f287a = j;
        this.f288a = f286a.getSharedPreferences(str, 0);
        this.f288a.registerOnSharedPreferenceChangeListener(this);
    }

    public static void m468a(Context context) {
        f286a = context;
    }

    static XModPreferences dno_Create(long j) {
        return new XModPreferences(j);
    }

    static XModPreferences dno_Create(String str, long j) {
        return new XModPreferences(str, j);
    }

    public static native void nativeOnSharedPreferenceChanged(long j, String str);

    public boolean dno_getBoolean(String str, boolean z) {
        return this.f288a.getBoolean(str, z);
    }

    public float dno_getFloat(String str, float f) {
        return this.f288a.getFloat(str, f);
    }

    public int dno_getInt(String str, int i) {
        return this.f288a.getInt(str, i);
    }

    public long dno_getLong(String str, long j) {
        return this.f288a.getLong(str, j);
    }

    public String dno_getString(String str, String str2) {
        return this.f288a.getString(str, str2);
    }

    public boolean dno_putBoolean(String str, boolean z) {
        return this.f288a.edit().putBoolean(str, z).commit();
    }

    public boolean dno_putFloat(String str, float f) {
        return this.f288a.edit().putFloat(str, f).commit();
    }

    public boolean dno_putInt(String str, int i) {
        return this.f288a.edit().putInt(str, i).commit();
    }

    public boolean dno_putLong(String str, long j) {
        return this.f288a.edit().putLong(str, j).commit();
    }

    public boolean dno_putString(String str, String str2) {
        return this.f288a.edit().putString(str, str2).commit();
    }

    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String str) {
        nativeOnSharedPreferenceChanged(this.f287a, str);
    }
}
