package org.androidworks.livewallpaperrose;

import android.content.Context;
import android.opengl.GLSurfaceView.EGLConfigChooser;
import android.opengl.GLSurfaceView.EGLContextFactory;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import com.yalin.style.engine.GLWallpaperServiceProxy;

import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;


public class Wallpaper extends GLWallpaperServiceProxy {
    private static String TAG = "Rose LWP";
    public static Context mContext;
    private long lastTouchTime;
    private float prevX;
    private float prevY;

    public Wallpaper(Context host) {
        super(host);
        mContext = this;
    }

    public static class ConfigChooser implements EGLConfigChooser {
        private static int EGL_OPENGL_ES2_BIT = 4;
        private static int[] s_configAttribs2 = new int[]{12325, 16, 12352, EGL_OPENGL_ES2_BIT, 12344};
        protected int mAlphaSize;
        protected int mBlueSize;
        protected int mDepthSize;
        protected int mGreenSize;
        protected int mRedSize;
        protected int mStencilSize;
        private int[] mValue = new int[1];

        public ConfigChooser(int r, int g, int b, int a, int depth, int stencil) {
            this.mRedSize = r;
            this.mGreenSize = g;
            this.mBlueSize = b;
            this.mAlphaSize = a;
            this.mDepthSize = depth;
            this.mStencilSize = stencil;
        }

        public EGLConfig chooseConfig(EGL10 egl, EGLDisplay display) {
            int[] num_config = new int[1];
            egl.eglChooseConfig(display, s_configAttribs2, null, 0, num_config);
            int numConfigs = num_config[0];
            if (numConfigs <= 0) {
                throw new IllegalArgumentException("No configs match configSpec");
            }
            EGLConfig[] configs = new EGLConfig[numConfigs];
            egl.eglChooseConfig(display, s_configAttribs2, configs, numConfigs, num_config);
            return chooseConfig(egl, display, configs);
        }

        public EGLConfig chooseConfig(EGL10 egl, EGLDisplay display, EGLConfig[] configs) {
            for (EGLConfig config : configs) {
                int d = findConfigAttrib(egl, display, config, 12325, 0);
                int s = findConfigAttrib(egl, display, config, 12326, 0);
                if (d >= this.mDepthSize && s >= this.mStencilSize) {
                    int r = findConfigAttrib(egl, display, config, 12324, 0);
                    int g = findConfigAttrib(egl, display, config, 12323, 0);
                    int b = findConfigAttrib(egl, display, config, 12322, 0);
                    int a = findConfigAttrib(egl, display, config, 12321, 0);
                    if (r >= this.mRedSize && g >= this.mGreenSize && b >= this.mBlueSize && a >= this.mAlphaSize) {
                        return config;
                    }
                }
            }
            return null;
        }

        private int findConfigAttrib(EGL10 egl, EGLDisplay display, EGLConfig config, int attribute, int defaultValue) {
            if (egl.eglGetConfigAttrib(display, config, attribute, this.mValue)) {
                return this.mValue[0];
            }
            return defaultValue;
        }
    }

    public static class ContextFactory implements EGLContextFactory {
        private static int EGL_CONTEXT_CLIENT_VERSION = 12440;

        public EGLContext createContext(EGL10 egl, EGLDisplay display, EGLConfig eglConfig) {
            Log.w(Wallpaper.TAG, "creating OpenGL ES 2.0 context");
            Wallpaper.checkEglError("Before eglCreateContext", egl);
            EGLContext context = egl.eglCreateContext(display, eglConfig, EGL10.EGL_NO_CONTEXT, new int[]{EGL_CONTEXT_CLIENT_VERSION, 2, 12344});
            Wallpaper.checkEglError("After eglCreateContext", egl);
            return context;
        }

        public void destroyContext(EGL10 egl, EGLDisplay display, EGLContext context) {
            egl.eglDestroyContext(display, context);
        }
    }

    class WallpaperEngine extends GLActiveEngine {
        private static final String TAG = "BlurredLinesLiveWallpaperEngine";
        private GestureDetector mGesDetect;
        private RoseRenderer renderer;

        public class DoubleTapGestureDetector extends SimpleOnGestureListener {
            public boolean onDoubleTap(MotionEvent e) {

                return true;
            }
        }

        public WallpaperEngine() {
            super();
            this.mGesDetect = new GestureDetector(Wallpaper.mContext, new DoubleTapGestureDetector());
            setEGLContextFactory(new ContextFactory());
            setEGLConfigChooser(new ConfigChooser(5, 6, 5, 0, 16, 0));
            this.renderer = new RoseRenderer(Wallpaper.this.getApplicationContext());
            this.renderer.setResources(Wallpaper.this.getResources());
            this.renderer.setRotate(Prefs.getRotate());
            this.renderer.setAutoRotate(Prefs.getAutoRotate());
            this.renderer.setTilt(Prefs.getTilt());
            try {
                this.renderer.setBackground(Integer.parseInt(Prefs.getBackground()));
            } catch (NumberFormatException e) {
            }
            try {
                this.renderer.setPetal(Integer.parseInt(Prefs.getPetal()));
            } catch (NumberFormatException e2) {
            }
            this.renderer.setVignette(Prefs.getVignette());
            this.renderer.setRandomize(Prefs.getRandomize());
            try {
                this.renderer.setRotationDirection(Integer.parseInt(Prefs.getRotateDir()));
            } catch (NumberFormatException e3) {
            }
            this.renderer.setPreferences();
            setRenderer(this.renderer);
            setRenderMode(1);
        }

        public void onCreate(SurfaceHolder surfaceHolder) {
            super.onCreate(surfaceHolder);
            setTouchEventsEnabled(true);
            if (!isPreview() && Prefs.getFirstLaunch()) {
                Prefs.updateFirstLaunch();
            }
        }

        public void onOffsetsChanged(float xOffset, float yOffset, float xOffsetStep,
                                     float yOffsetStep, int xPixelOffset, int yPixelOffset) {
            super.onOffsetsChanged(xOffset, yOffset, xOffsetStep,
                    yOffsetStep, xPixelOffset, yPixelOffset);
            this.renderer.setXOffset(xOffset);
        }

        public void onTouchEvent(MotionEvent event) {
            this.mGesDetect.onTouchEvent(event);
            super.onTouchEvent(event);
            if (event.getAction() == 0) {
                Wallpaper.this.prevX = event.getX();
                Wallpaper.this.prevY = event.getY();
                Wallpaper.this.lastTouchTime = System.currentTimeMillis();
            }
            if (event.getAction() == 2) {
                float deltaY = Wallpaper.this.prevY - event.getY();
                if (Math.abs(deltaY) >= 10.0f) {
                    this.renderer.setYOffset(deltaY);
                }
            }
            if (event.getAction() == 1) {
                float deltaX = event.getX() - Wallpaper.this.prevX;
                float deltaY = event.getY() - Wallpaper.this.prevY;
                if (Math.abs(deltaX) > 30.0f && Math.abs(deltaX / deltaY) > 0.5f
                        && System.currentTimeMillis() - Wallpaper.this.lastTouchTime < 700) {
                    this.renderer.changeSpeed(deltaX);
                }
            }
        }
    }

    private static void checkEglError(String prompt, EGL10 egl) {
        while (egl.eglGetError() != 12288) {
            Log.e(TAG, String.format("%s: EGL error: 0x%x", prompt, 123));
        }
    }

    public Engine onCreateEngine() {
        return new WallpaperEngine();
    }
}
