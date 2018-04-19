package com.xmodpp.core;


import android.text.TextUtils;
import android.util.Log;

import org.w3c.dom.Text;

public class XModPreferences {
    private static final String TAG = "XModPreferences";
    long _nativeReference;

    public static native void nativeOnSharedPreferenceChanged(long j, String str);

    XModPreferences(String name, long nativeReference) {
        this._nativeReference = nativeReference;
    }

    XModPreferences(long nativeReference) {
        this._nativeReference = nativeReference;
    }

    public float jni_getFloat(String key, float defValue) {
        Log.d(TAG, "jni_getFloat: key = " + key + " value = " + defValue);
        if (TextUtils.equals(key, "brightness")) {
            return 1.0f;
        } else if (TextUtils.equals(key, "brightness_stars")) {
            return 0.7f;
        } else if (TextUtils.equals(key, "ambient")) {
            return 1.0f;
        }
        return defValue;
    }

    public boolean jni_putFloat(String key, float value) {
        Log.d(TAG, "jni_putFloat: key = " + key + " value = " + value);
        return true;
    }

    public int jni_getInt(String key, int defValue) {
        Log.d(TAG, "jni_getInt: key = " + key + " value = " + defValue);
        if (TextUtils.equals(key, "view")) {
            return 3;
        } else if (TextUtils.equals(key, "anim")) {
            return 0;
        } else if (TextUtils.equals(key, "fade")) {
            return 0;
        } else if (TextUtils.equals(key, "pr")) {
            // 自转
            return 25;
        } else if (TextUtils.equals(key, "0r")) {
            // 公转
            return 100;
        }
        return defValue;
    }

    public boolean jni_putInt(String key, int value) {
        Log.d(TAG, "jni_putInt: key = " + key + " value = " + value);
        return true;
    }

    public long jni_getLong(String key, long defValue) {
        Log.d(TAG, "jni_getLong: key = " + key + " value = " + defValue);
        return defValue;
    }

    public boolean jni_putLong(String key, long value) {
        Log.d(TAG, "jni_putLong: key = " + key + " value = " + value);
        return true;
    }

    public boolean jni_getBoolean(String key, boolean defValue) {
        Log.d(TAG, "jni_getBoolean: key = " + key + " value = " + defValue);
        return defValue;
    }

    public boolean jni_putBoolean(String key, boolean value) {
        Log.d(TAG, "jni_putBoolean: key = " + key + " value = " + value);
        return true;
    }

    public String jni_getString(String key, String defValue) {
        Log.d(TAG, "jni_getString: key = " + key + " value = " + defValue);
        return defValue;
    }

    public boolean jni_putString(String key, String value) {
        Log.d(TAG, "jni_putString: key = " + key + " value = " + value);
        return true;
    }

    public void onSharedPreferenceChanged() {
//        nativeOnSharedPreferenceChanged(this._nativeReference, key);
    }
}
