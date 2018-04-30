package com.maxelus.gdx.backends.android.livewallpaper;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import com.badlogic.gdx.Preferences;
import java.util.Map;
import java.util.Map.Entry;

public class AndroidPreferencesLW implements Preferences {
    SharedPreferences sharedPrefs;

    AndroidPreferencesLW(SharedPreferences preferences) {
        this.sharedPrefs = preferences;
    }

    public void putBoolean(String key, boolean val) {
        Editor edit = this.sharedPrefs.edit();
        edit.putBoolean(key, val);
        edit.commit();
    }

    public void putInteger(String key, int val) {
        Editor edit = this.sharedPrefs.edit();
        edit.putInt(key, val);
        edit.commit();
    }

    public void putLong(String key, long val) {
        Editor edit = this.sharedPrefs.edit();
        edit.putLong(key, val);
        edit.commit();
    }

    public void putFloat(String key, float val) {
        Editor edit = this.sharedPrefs.edit();
        edit.putFloat(key, val);
        edit.commit();
    }

    public void putString(String key, String val) {
        Editor edit = this.sharedPrefs.edit();
        edit.putString(key, val);
        edit.commit();
    }

    public void put(Map<String, ?> vals) {
        Editor edit = this.sharedPrefs.edit();
        for (Entry<String, ?> val : vals.entrySet()) {
            if (val.getValue() instanceof Boolean) {
                putBoolean((String) val.getKey(), ((Boolean) val.getValue()).booleanValue());
            }
            if (val.getValue() instanceof Integer) {
                putInteger((String) val.getKey(), ((Integer) val.getValue()).intValue());
            }
            if (val.getValue() instanceof Long) {
                putLong((String) val.getKey(), ((Long) val.getValue()).longValue());
            }
            if (val.getValue() instanceof String) {
                putString((String) val.getKey(), (String) val.getValue());
            }
            if (val.getValue() instanceof Float) {
                putFloat((String) val.getKey(), ((Float) val.getValue()).floatValue());
            }
        }
        edit.commit();
    }

    public boolean getBoolean(String key) {
        return this.sharedPrefs.getBoolean(key, false);
    }

    public int getInteger(String key) {
        return this.sharedPrefs.getInt(key, 0);
    }

    public long getLong(String key) {
        return this.sharedPrefs.getLong(key, 0);
    }

    public float getFloat(String key) {
        return this.sharedPrefs.getFloat(key, 0.0f);
    }

    public String getString(String key) {
        return this.sharedPrefs.getString(key, "");
    }

    public boolean getBoolean(String key, boolean defValue) {
        return this.sharedPrefs.getBoolean(key, defValue);
    }

    public int getInteger(String key, int defValue) {
        return this.sharedPrefs.getInt(key, defValue);
    }

    public long getLong(String key, long defValue) {
        return this.sharedPrefs.getLong(key, defValue);
    }

    public float getFloat(String key, float defValue) {
        return this.sharedPrefs.getFloat(key, defValue);
    }

    public String getString(String key, String defValue) {
        return this.sharedPrefs.getString(key, defValue);
    }

    public Map<String, ?> get() {
        return this.sharedPrefs.getAll();
    }

    public boolean contains(String key) {
        return this.sharedPrefs.contains(key);
    }

    public void clear() {
        Editor edit = this.sharedPrefs.edit();
        edit.clear();
        edit.commit();
    }

    public void flush() {
    }

    public void remove(String key) {
    }
}
