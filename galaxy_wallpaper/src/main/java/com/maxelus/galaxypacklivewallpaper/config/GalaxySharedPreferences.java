package com.maxelus.galaxypacklivewallpaper.config;

import android.content.SharedPreferences;
import android.text.TextUtils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author jinyalin
 * @since 2018/4/30.
 */

public class GalaxySharedPreferences implements SharedPreferences {
    private Set<OnSharedPreferenceChangeListener> mListeners = new HashSet<>();

    private Map<String, Object> mMap = new HashMap<>();

    @Override
    public Map<String, ?> getAll() {
        return mMap;
    }

    @Override
    public String getString(String key, String defValue) {
        if (TextUtils.equals(key, "galaxy_pref")) {
            return GalaxyConfig.galaxyType + "";
        } else if (TextUtils.equals(key, "galaxy_back_pref")) {
            return GalaxyConfig.galaxyBg + "";
        }
        String result = defValue;
        if (mMap.containsKey(key)) {
            try {
                result = (String) mMap.get(key);
            } catch (Throwable t) {
                // ignore
            }
        }
        return result;
    }

    @Override
    public Set<String> getStringSet(String key, Set<String> defValues) {
        return defValues;
    }

    @Override
    public int getInt(String key, int defValue) {
        int result = defValue;
        if (mMap.containsKey(key)) {
            try {
                result = (int) mMap.get(key);
            } catch (Throwable t) {
                // ignore
            }
        }
        return result;
    }

    @Override
    public long getLong(String key, long defValue) {
        long result = defValue;
        if (mMap.containsKey(key)) {
            try {
                result = (long) mMap.get(key);
            } catch (Throwable t) {
                // ignore
            }
        }
        return result;
    }

    @Override
    public float getFloat(String key, float defValue) {
        float result = defValue;
        if (mMap.containsKey(key)) {
            try {
                result = (float) mMap.get(key);
            } catch (Throwable t) {
                // ignore
            }
        }
        return result;
    }

    @Override
    public boolean getBoolean(String key, boolean defValue) {
        boolean result = defValue;
        if (mMap.containsKey(key)) {
            try {
                result = (boolean) mMap.get(key);
            } catch (Throwable t) {
                // ignore
            }
        }
        return result;
    }

    @Override
    public boolean contains(String key) {
        return mMap.containsKey(key);
    }

    @Override
    public Editor edit() {
        return new GalaxyEditor(this);
    }

    @Override
    public void registerOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener listener) {
        mListeners.add(listener);
    }

    @Override
    public void unregisterOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener listener) {
        mListeners.remove(listener);
    }

    private class GalaxyEditor implements Editor {
        private GalaxySharedPreferences sp;

        private Map<String, Object> mTempMap = new HashMap<>();

        public GalaxyEditor(GalaxySharedPreferences sp) {
            this.sp = sp;
        }

        @Override
        public Editor putString(String key, String value) {
            mTempMap.put(key, value);
            return this;
        }

        @Override
        public Editor putStringSet(String key, Set<String> values) {
            return this;
        }

        @Override
        public Editor putInt(String key, int value) {
            mTempMap.put(key, value);
            return this;
        }

        @Override
        public Editor putLong(String key, long value) {
            mTempMap.put(key, value);
            return this;
        }

        @Override
        public Editor putFloat(String key, float value) {
            mTempMap.put(key, value);
            return this;
        }

        @Override
        public Editor putBoolean(String key, boolean value) {
            mTempMap.put(key, value);
            return this;
        }

        @Override
        public Editor remove(String key) {
            mTempMap.remove(key);
            return this;
        }

        @Override
        public Editor clear() {
            mTempMap.clear();
            return this;
        }

        @Override
        public boolean commit() {
            apply();
            return true;
        }

        @Override
        public void apply() {
            for (String key : mTempMap.keySet()) {
                sp.mMap.put(key, mTempMap.get(key));
                notifyListeners(key);
            }
        }

        private void notifyListeners(String key) {
            for (OnSharedPreferenceChangeListener listener : sp.mListeners) {
                listener.onSharedPreferenceChanged(sp, key);
            }
        }
    }
}
