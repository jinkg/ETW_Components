package com.afkettler.crackedscreen3d;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.preference.PreferenceManager;
import android.view.SurfaceHolder;

import com.xmodpp.core.Assets;
import com.xmodpp.core.XModPreferences;
import com.xmodpp.gles.TextureUtils;


public class Wallpaper extends gb {

    public Wallpaper() {
        super("MyWallpaper");
    }

    public void onConfigurationChanged(Configuration configuration) {
    }

    public void onCreate() {
        LibraryLoader.m447a(this);
        super.onCreate();
        Assets.m467a(getApplicationContext());
        TextureUtils.f298a = getApplicationContext();
        XModPreferences.m468a(getApplicationContext());
    }

    public Engine onCreateEngine() {
        return new fs();
    }

    public class fs extends gc {
        BroadcastReceiver f420a = new ft(this);

        public fs() {
            super();
        }

        @SuppressLint({"NewApi"})
        public void onCreate(SurfaceHolder surfaceHolder) {
            super.onCreate(surfaceHolder);
            try {
                setOffsetNotificationsEnabled(false);
            } catch (Exception e) {
            }
        }

        public void onDestroy() {
            super.onDestroy();
        }

        public void onVisibilityChanged(boolean z) {
//            if (z) {
//                try {
//                    registerReceiver(this.f420a,
//                            new IntentFilter("android.intent.action.BATTERY_CHANGED"));
//                } catch (Exception ignore) {
//                }
//            } else {
//                unregisterReceiver(this.f420a);
//            }
            super.onVisibilityChanged(z);
        }
    }

    class ft extends BroadcastReceiver {
        final /* synthetic */ fs f422a;

        ft(fs fsVar) {
            this.f422a = fsVar;
        }

        public void onReceive(Context context, Intent intent) {
            float f = 0.0f;
            float intExtra = (float) intent.getIntExtra("level", 0);
            float intExtra2 = (float) intent.getIntExtra("scale", 0);
            SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(context).edit();
            String str = "batlevel";
            if (intExtra2 != 0.0f) {
                f = intExtra / intExtra2;
            }
            edit.putFloat(str, f).putInt("batstat", intent.getIntExtra("status", -1)).putInt("batplug", intent.getIntExtra("plugged", -1)).commit();
        }
    }

}
