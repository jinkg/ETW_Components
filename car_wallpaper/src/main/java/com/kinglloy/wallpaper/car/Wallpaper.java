package com.kinglloy.wallpaper.car;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;

import com.kinglloy.wallpaper.car.config.CarSharedPreferences;

import org.androidworks.livewallpapercar.CarRenderer;
import org.androidworks.livewallpapercar.Prefs;
import org.androidworks.livewallpapertulips.common.BaseRenderer;

public class Wallpaper extends BaseWallpaper {

    public Wallpaper(Context host) {
        super(host);
    }

    public class CarEngine extends WallpaperEngine {
        private Boolean bDoubleTap;
        private long lastTouchTime;
        private GestureDetector mGesDetect;
        private float prevX;
        private float prevY;

        class C00042 implements Runnable {
            C00042() {
            }

            public void run() {
                CarEngine.this.bDoubleTap = Prefs.getDoubleTap(CarEngine.this.mPreferences);
            }
        }

        class C00053 implements Runnable {
            C00053() {
            }

            public void run() {
                ((CarRenderer) CarEngine.this.renderer).setLandscape(Prefs.getLandscape(CarEngine.this.mPreferences));
            }
        }

        class C00064 implements Runnable {
            C00064() {
            }

            public void run() {
                ((CarRenderer) CarEngine.this.renderer).setCarName(Prefs.getCar(CarEngine.this.mPreferences));
            }
        }

        class C00075 implements Runnable {
            C00075() {
            }

            public void run() {
                ((CarRenderer) CarEngine.this.renderer).setCarColor(Prefs.getColor(CarEngine.this.mPreferences));
            }
        }

        class C00086 implements Runnable {
            C00086() {
            }

            public void run() {
                ((CarRenderer) CarEngine.this.renderer).setRimTexture(Prefs.getRim(CarEngine.this.mPreferences));
            }
        }

        class C00108 implements Runnable {
            C00108() {
            }

            public void run() {
                ((CarRenderer) CarEngine.this.renderer).setSpeed((float) Prefs.getSpeed(CarEngine.this.mPreferences));
            }
        }

        class C00119 implements Runnable {
            C00119() {
            }

            public void run() {
                ((CarRenderer) CarEngine.this.renderer).setLicensePlate(Prefs.getPlate(CarEngine.this.mPreferences));
            }
        }

        public class DoubleTapGestureDetector extends SimpleOnGestureListener {
            public boolean onDoubleTap(MotionEvent e) {
                return true;
            }
        }

        public CarEngine(SharedPreferences preferences) {
            super(preferences);
            this.bDoubleTap = Boolean.FALSE;
            this.bDoubleTap = Prefs.getDoubleTap(this.mPreferences);
            this.mGesDetect = new GestureDetector(Wallpaper.this.getContext(), new DoubleTapGestureDetector());
        }

        protected BaseRenderer getRenderer() {
            final BaseRenderer r = new CarRenderer(Wallpaper.this.getApplicationContext(), Wallpaper.this);
            r.setResources(Wallpaper.this.getResources());
//            Wallpaper.this.registerReceiver(new BroadcastReceiver() {
//                int level = -1;
//                int scale = -1;
//
//                public void onReceive(Context context, Intent intent) {
//                    this.level = intent.getIntExtra("level", -1);
//                    this.scale = intent.getIntExtra("scale", -1);
//                    ((CarRenderer) r).setBatteryLevel(((float) this.level) / ((float) this.scale));
//                }
//            }, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
            ((CarRenderer) r).setLandscape(Prefs.getLandscape(this.mPreferences));
            ((CarRenderer) r).setCarName(Prefs.getCar(this.mPreferences));
            ((CarRenderer) r).setCarColor(Prefs.getColor(this.mPreferences));
            ((CarRenderer) r).setRimTexture(Prefs.getRim(this.mPreferences));
            ((CarRenderer) r).setAutoRotate(Prefs.getAutoRotate(this.mPreferences));
            ((CarRenderer) r).setSpeed((float) Prefs.getSpeed(this.mPreferences));
            ((CarRenderer) r).setLicensePlate(Prefs.getPlate(this.mPreferences));
            ((CarRenderer) r).setWeather(Prefs.getWeather(this.mPreferences));
            ((CarRenderer) r).setCorona(Prefs.getCorona(this.mPreferences));
            ((CarRenderer) r).setRoadSpeed(Prefs.getRoadSpeed(this.mPreferences));
            ((CarRenderer) r).setCameraDistance(Prefs.getCameraDistance(this.mPreferences));
            ((CarRenderer) r).setTilt(Prefs.getTilt(this.mPreferences));
            ((CarRenderer) r).setLeftLane(Prefs.getLeftLane(this.mPreferences));
            ((CarRenderer) r).setLensDust(Prefs.getLensDust(this.mPreferences));
            ((CarRenderer) r).setLensFlare(Prefs.getLensFlare(this.mPreferences));
            return r;
        }

        public void onResume() {
            super.onResume();
        }

        public void onOffsetsChanged(float xOffset, float yOffset, float xOffsetStep, float yOffsetStep, int xPixelOffset, int yPixelOffset) {
            super.onOffsetsChanged(xOffset, yOffset, xOffsetStep, yOffsetStep, xPixelOffset, yPixelOffset);
            if (isPreview()) {
                this.renderer.setYOffset(0.5f);
                this.renderer.setXOffset(0.5f);
            } else if (yOffset == 0.0f) {
                this.renderer.setYOffset(0.5f);
            } else {
                this.renderer.setYOffset(yOffset);
            }
        }

        public void onTouchEvent(MotionEvent event) {
            this.mGesDetect.onTouchEvent(event);
            if (event.getAction() == 0) {
                this.prevX = event.getX();
                this.prevY = event.getY();
                this.lastTouchTime = System.currentTimeMillis();
            }
            if (event.getAction() == 1) {
                float deltaX = event.getX() - this.prevX;
                float deltaY = event.getY() - this.prevY;
                if (Math.abs(deltaX) > 30.0f && Math.abs(deltaX / deltaY) > 0.5f && System.currentTimeMillis() - this.lastTouchTime < 700) {
                    ((CarRenderer) this.renderer).changeSpeed(deltaX);
                }
            }
        }

        public void onSharedPreferenceChanged(final SharedPreferences prefs, String key) {
            if (key.equals(Prefs.OPT_DOUBLETAP)) {
                queueEvent(new C00042());
            }
            if (key.equals(Prefs.OPT_LANDSCAPE)) {
                queueEvent(new C00053());
            }
            if (key.equals(Prefs.OPT_CAR)) {
                queueEvent(new C00064());
            }
            if (key.equals(Prefs.OPT_COLOR)) {
                queueEvent(new C00075());
            }
            if (key.equals(Prefs.OPT_RIM)) {
                queueEvent(new C00086());
            }
            if (key.equals(Prefs.OPT_AUTOROTATE)) {
                queueEvent(new Runnable() {
                    public void run() {
                        ((CarRenderer) CarEngine.this.renderer).setAutoRotate(Prefs.getAutoRotate(prefs));
                    }
                });
            }
            if (key.equals(Prefs.OPT_SPEED)) {
                queueEvent(new C00108());
            }
            if (key.equals(Prefs.OPT_PLATE)) {
                queueEvent(new C00119());
            }
            if (key.equals(Prefs.OPT_WEATHER)) {
                queueEvent(new Runnable() {
                    public void run() {
                        ((CarRenderer) CarEngine.this.renderer).setWeather(Prefs.getWeather(CarEngine.this.mPreferences));
                    }
                });
            }
            if (key.equals(Prefs.OPT_CORONA)) {
                queueEvent(new Runnable() {
                    public void run() {
                        ((CarRenderer) CarEngine.this.renderer).setCorona(Prefs.getCorona(CarEngine.this.mPreferences));
                    }
                });
            }
            if (key.equals(Prefs.OPT_ROAD_SPEED)) {
                queueEvent(new Runnable() {
                    public void run() {
                        ((CarRenderer) CarEngine.this.renderer).setRoadSpeed(Prefs.getRoadSpeed(CarEngine.this.mPreferences));
                    }
                });
            }
            if (key.equals(Prefs.OPT_CAMERA_DISTANCE)) {
                queueEvent(new Runnable() {
                    public void run() {
                        ((CarRenderer) CarEngine.this.renderer).setCameraDistance(Prefs.getCameraDistance(CarEngine.this.mPreferences));
                    }
                });
            }
            if (key.equals(Prefs.OPT_TILT)) {
                queueEvent(new Runnable() {
                    public void run() {
                        ((CarRenderer) CarEngine.this.renderer).setTilt(Prefs.getTilt(CarEngine.this.mPreferences));
                    }
                });
            }
            if (key.equals(Prefs.OPT_LEFT_LANE)) {
                queueEvent(new Runnable() {
                    public void run() {
                        ((CarRenderer) CarEngine.this.renderer).setLeftLane(Prefs.getLeftLane(CarEngine.this.mPreferences));
                    }
                });
            }
            if (key.equals(Prefs.OPT_LENS_DUST)) {
                queueEvent(new Runnable() {
                    public void run() {
                        ((CarRenderer) CarEngine.this.renderer).setLensDust(Prefs.getLensDust(CarEngine.this.mPreferences));
                    }
                });
            }
            if (key.equals(Prefs.OPT_LENS_FLARE)) {
                queueEvent(new Runnable() {
                    public void run() {
                        ((CarRenderer) CarEngine.this.renderer).setLensFlare(Prefs.getLensFlare(CarEngine.this.mPreferences));
                    }
                });
            }
        }
    }

    public Engine onCreateEngine() {
        return new CarEngine(new CarSharedPreferences());
    }
}
