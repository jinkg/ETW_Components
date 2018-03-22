package com.spring;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.service.wallpaper.WallpaperService;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import com.xmodpp.nativeui.C0268b;
import com.xmodpp.core.LibraryLoader;


public class Wallpaper extends C0268b {
    public Wallpaper(Context host) {
        super(host, "MyWallpaper");
    }

    public WallpaperService.Engine onCreateEngine() {
        return new ar();
    }

    public class ar extends C0270c {
        private GestureDetector f1529f;

        public ar() {
            super();
        }

        public void m3629a() {

        }

        public void onCreate(SurfaceHolder surfaceHolder) {
            super.onCreate(surfaceHolder);
            LibraryLoader.m20003a(getApplicationContext(), "libMyApplication");
            this.f1529f = new GestureDetector(getApplicationContext(), new as(this));
            SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            if (isPreview() && defaultSharedPreferences.getBoolean("shownews", false)) {
                m3629a();
            }
        }

        public void onTouchEvent(MotionEvent motionEvent) {
            this.f1529f.onTouchEvent(motionEvent);
            super.onTouchEvent(motionEvent);
        }

        class as extends GestureDetector.SimpleOnGestureListener {

            as(ar arVar) {
            }

            public boolean onDoubleTap(MotionEvent motionEvent) {
                return super.onDoubleTap(motionEvent);
            }
        }
    }


}
