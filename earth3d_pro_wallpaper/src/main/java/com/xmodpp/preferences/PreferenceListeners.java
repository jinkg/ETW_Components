package com.xmodpp.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.preference.PreferenceManager;
import com.xmodpp.application.Application;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;
import java.util.WeakHashMap;

public class PreferenceListeners {

    private static class Impl implements OnSharedPreferenceChangeListener {
        static Impl instance;
        HashMap<String, Set<OnSharedPreferenceChangeListener>> listeners = new HashMap();

        private Impl() {
        }

        public static synchronized Impl Get() {
            Impl impl;
            synchronized (Impl.class) {
                if (instance == null) {
                    instance = new Impl();
                    Context jni_getContext = Application.jni_getContext();
                    if (jni_getContext != null) {
                        PreferenceManager.getDefaultSharedPreferences(jni_getContext).registerOnSharedPreferenceChangeListener(instance);
                    }
                }
                impl = instance;
            }
            return impl;
        }

        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String str) {
            Set<OnSharedPreferenceChangeListener> set = (Set) this.listeners.get(str);
            if (set != null) {
                for (OnSharedPreferenceChangeListener onSharedPreferenceChanged : set) {
                    onSharedPreferenceChanged.onSharedPreferenceChanged(sharedPreferences, str);
                }
            }
        }
    }

    public static synchronized void registerListenerForKey(String str, OnSharedPreferenceChangeListener onSharedPreferenceChangeListener) {
        synchronized (PreferenceListeners.class) {
            Set set = (Set) Impl.Get().listeners.get(str);
            if (set == null) {
                set = Collections.newSetFromMap(new WeakHashMap());
                Impl.Get().listeners.put(str, set);
            }
            set.add(onSharedPreferenceChangeListener);
        }
    }

    public static synchronized void unregisterListener(OnSharedPreferenceChangeListener onSharedPreferenceChangeListener) {
        synchronized (PreferenceListeners.class) {
            for (Set remove : Impl.Get().listeners.values()) {
                remove.remove(onSharedPreferenceChangeListener);
            }
        }
    }

    public static synchronized void unregisterListenerForKey(String str) {
        synchronized (PreferenceListeners.class) {
            Set set = (Set) Impl.Get().listeners.remove(str);
        }
    }

    public static synchronized void unregisterListenerForKey(String str, OnSharedPreferenceChangeListener onSharedPreferenceChangeListener) {
        synchronized (PreferenceListeners.class) {
            Set set = (Set) Impl.Get().listeners.get(str);
            if (set != null) {
                set.remove(onSharedPreferenceChangeListener);
            }
        }
    }
}
