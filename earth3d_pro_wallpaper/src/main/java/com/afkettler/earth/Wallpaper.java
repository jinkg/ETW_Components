package com.afkettler.earth;

import android.content.SharedPreferences;
import android.os.Build.VERSION;
import android.preference.PreferenceManager;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import com.xmodpp.addons.licensing.Licensing;
import com.xmodpp.addons.wallpaper.XMODWallpaperService;
import com.xmodpp.application.Application;

public class Wallpaper extends XMODWallpaperService {

  public class C0599a extends XMODWallpaperEngine {

    final /* synthetic */ Wallpaper f893a;

    public C0599a(Wallpaper wallpaper) {
      super();
      this.f893a = wallpaper;
    }

    public void onCreate(SurfaceHolder surfaceHolder) {
      super.onCreate(surfaceHolder);
      SharedPreferences defaultSharedPreferences = PreferenceManager
          .getDefaultSharedPreferences(this.f893a.getApplicationContext());
      Application.LoadLibrary(this.f893a.getApplicationContext(), "libEarth");
    }

    public void onDestroy() {
      super.onDestroy();
    }

    public void onTouchEvent(MotionEvent motionEvent) {
      if (motionEvent.getAction() == 0) {
        Licensing.check(this.f893a.getApplicationContext());
      }

      super.onTouchEvent(motionEvent);
    }

    public void onVisibilityChanged(boolean z) {
      if (z) {
        Licensing.check(this.f893a.getApplicationContext());
        if (VERSION.SDK_INT >= 17) {
          try {
            if (!Daydream.f892a) {
              super.onVisibilityChanged(z);
              return;
            }
            return;
          } catch (Throwable th) {
            super.onVisibilityChanged(z);
            return;
          }
        }
        super.onVisibilityChanged(z);
        return;
      }
      super.onVisibilityChanged(false);
    }
  }

  public Engine onCreateEngine() {
    return new C0599a(this);
  }
}
