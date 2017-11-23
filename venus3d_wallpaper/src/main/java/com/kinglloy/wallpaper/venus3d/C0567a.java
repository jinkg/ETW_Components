package com.kinglloy.wallpaper.venus3d;

import android.preference.PreferenceManager;
import com.xmodpp.core.LibraryLoader;
import com.xmodpp.nativeui.C0565b;
import com.xmodpp.nativeui.C0566a;

public final class C0567a extends C0566a {
    static boolean f4967a = false;

    public final void onCreate() {
        super.onCreate();
        LibraryLoader.m8559a(getApplicationContext(), "libVenus");
        PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
    }

    public final void onDreamingStarted() {
        if (C0565b.m8528a() != null) {
            C0565b.m8528a().onVisibilityChanged(false);
        }
        super.onDreamingStarted();
        f4967a = true;
    }

    public final void onDreamingStopped() {
        super.onDreamingStopped();
        f4967a = false;
        if (C0565b.m8528a() != null && C0565b.m8528a().isVisible()) {
            C0565b.m8528a().onVisibilityChanged(true);
        }
    }
}
