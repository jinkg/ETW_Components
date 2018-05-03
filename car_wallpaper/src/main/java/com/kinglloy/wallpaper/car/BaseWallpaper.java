package com.kinglloy.wallpaper.car;

import android.content.Context;
import android.content.SharedPreferences;
import android.opengl.GLSurfaceView;
import android.os.Build;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import com.example.objLoader.TDModel;
import com.kinglloy.wallpaper.car.config.CarSharedPreferences;
import com.yalin.style.engine.GLWallpaperServiceProxy;

import org.androidworks.livewallpapertulips.common.BaseRenderer;
import org.androidworks.livewallpapertulips.common.IWallpaper;

import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;

/**
 * @author jinyalin
 * @since 2018/5/3.
 */

public class BaseWallpaper extends GLWallpaperServiceProxy implements IWallpaper {
    private static final boolean DEBUG = false;
    private static String TAG = "Base Tulips LWP";
    public static TDModel modelLeaves;
    public static TDModel modelRose;
    public static TDModel modelSphere;
    public static TDModel modelStem;
    public Context mContext = this;
    public TDModel modelTest;
    private float prevX;
    private float prevY;
    protected int surfaceHeight;
    protected int surfaceWidth;

    public BaseWallpaper(Context host) {
        super(host);
    }

    private static void checkEglError(String var0, EGL10 var1) {
        while (true) {
            int var2 = var1.eglGetError();
            if (var2 == 12288) {
                return;
            }

            Log.e(TAG, String.format("%s: EGL error: 0x%x", var0, var2));
        }
    }

    public Context getContext() {
        return this.mContext;
    }

    public TDModel getModelTest() {
        return this.modelTest;
    }

    public int getSurfaceHeight() {
        return this.surfaceHeight;
    }

    public int getSurfaceWidth() {
        return this.surfaceWidth;
    }

    public Engine onCreateEngine() {
        return new BaseWallpaper.WallpaperEngine(new CarSharedPreferences());
    }

    public static class ConfigChooser implements GLSurfaceView.EGLConfigChooser {
        private static int EGL_CONTEXT_CLIENT_VERSION = 12440;
        private static final int EGL_COVERAGE_BUFFERS_NV = 12512;
        private static final int EGL_COVERAGE_SAMPLES_NV = 12513;
        private static int EGL_OPENGL_ES2_BIT = 4;
        private static int[] s_configAttribs2;
        private static int[] s_configAttribsAA;
        private static int[] s_configAttribsNV;
        protected Boolean bUseAA;
        protected int mAlphaSize;
        protected int mBlueSize;
        protected int mDepthSize;
        protected int mGreenSize;
        protected int mRedSize;
        protected int mStencilSize;
        private int[] mValue = new int[1];

        static {
            s_configAttribs2 = new int[]{12325, 16, 12352, EGL_OPENGL_ES2_BIT, 12344};
            s_configAttribsAA = new int[]{12325, 16, 12352, EGL_OPENGL_ES2_BIT, 12338, 1, 12337, 2, 12344};
            s_configAttribsNV = new int[]{12325, 16, 12352, EGL_OPENGL_ES2_BIT, 12512, 1, 12513, 2, 12344};
        }

        public ConfigChooser(int var1, int var2, int var3, int var4, int var5, int var6, Boolean var7) {
            this.mRedSize = var1;
            this.mGreenSize = var2;
            this.mBlueSize = var3;
            this.mAlphaSize = var4;
            this.mDepthSize = var5;
            this.mStencilSize = var6;
            this.bUseAA = var7;
        }

        private int findConfigAttrib(EGL10 var1, EGLDisplay var2, EGLConfig var3, int var4, int var5) {
            if (var1.eglGetConfigAttrib(var2, var3, var4, this.mValue)) {
                var5 = this.mValue[0];
            }

            return var5;
        }

        private void printConfig(EGL10 var1, EGLDisplay var2, EGLConfig var3) {
            int[] var6 = new int[]{12320, 12321, 12322, 12323, 12324, 12325, 12326, 12327, 12328, 12329, 12330, 12331, 12332, 12333, 12334, 12335, 12336, 12337, 12338, 12339, 12340, 12343, 12342, 12341, 12345, 12346, 12347, 12348, 12349, 12350, 12351, 12352, 12354};
            int[] var7 = new int[1];

            for (int var4 = 0; var4 < var6.length; ++var4) {
                int var5 = var6[var4];
                String var8 = (new String[]{"EGL_BUFFER_SIZE", "EGL_ALPHA_SIZE", "EGL_BLUE_SIZE", "EGL_GREEN_SIZE", "EGL_RED_SIZE", "EGL_DEPTH_SIZE", "EGL_STENCIL_SIZE", "EGL_CONFIG_CAVEAT", "EGL_CONFIG_ID", "EGL_LEVEL", "EGL_MAX_PBUFFER_HEIGHT", "EGL_MAX_PBUFFER_PIXELS", "EGL_MAX_PBUFFER_WIDTH", "EGL_NATIVE_RENDERABLE", "EGL_NATIVE_VISUAL_ID", "EGL_NATIVE_VISUAL_TYPE", "EGL_PRESERVED_RESOURCES", "EGL_SAMPLES", "EGL_SAMPLE_BUFFERS", "EGL_SURFACE_TYPE", "EGL_TRANSPARENT_TYPE", "EGL_TRANSPARENT_RED_VALUE", "EGL_TRANSPARENT_GREEN_VALUE", "EGL_TRANSPARENT_BLUE_VALUE", "EGL_BIND_TO_TEXTURE_RGB", "EGL_BIND_TO_TEXTURE_RGBA", "EGL_MIN_SWAP_INTERVAL", "EGL_MAX_SWAP_INTERVAL", "EGL_LUMINANCE_SIZE", "EGL_ALPHA_MASK_SIZE", "EGL_COLOR_BUFFER_TYPE", "EGL_RENDERABLE_TYPE", "EGL_CONFORMANT"})[var4];
                if (var1.eglGetConfigAttrib(var2, var3, var5, var7)) {
                    Log.w(BaseWallpaper.TAG, String.format("  %s: %d\n", new Object[]{var8, Integer.valueOf(var7[0])}));
                } else {
                    while (var1.eglGetError() != 12288) {
                        ;
                    }
                }
            }

        }

        private void printConfigs(EGL10 var1, EGLDisplay var2, EGLConfig[] var3) {
            int var5 = var3.length;
            Log.w(BaseWallpaper.TAG, String.format("%d configurations", new Object[]{Integer.valueOf(var5)}));

            for (int var4 = 0; var4 < var5; ++var4) {
                Log.w(BaseWallpaper.TAG, String.format("Configuration %d:\n", new Object[]{Integer.valueOf(var4)}));
                this.printConfig(var1, var2, var3[var4]);
            }

        }

        public EGLConfig chooseConfig(EGL10 var1, EGLDisplay var2) {
            int var3;
            Boolean var5;
            Boolean var6;
            int[] var8;
            label32:
            {
                var5 = Boolean.valueOf(true);
                Boolean var7 = Boolean.valueOf(true);
                var8 = new int[1];
                var1.eglChooseConfig(var2, s_configAttribsNV, (EGLConfig[]) null, 0, var8);
                int var4 = var8[0];
                var3 = var4;
                if (var4 <= 0) {
                    var6 = Boolean.valueOf(false);
                    if (!this.bUseAA.booleanValue()) {
                        var1.eglChooseConfig(var2, s_configAttribs2, (EGLConfig[]) null, 0, var8);
                        var4 = var8[0];
                        var3 = var4;
                        var5 = var7;
                        if (var4 <= 0) {
                            throw new IllegalArgumentException("No configs match configSpec");
                        }
                        break label32;
                    }

                    var1.eglChooseConfig(var2, s_configAttribsAA, (EGLConfig[]) null, 0, var8);
                    var4 = var8[0];
                    var5 = var6;
                    var3 = var4;
                    if (var4 <= 0) {
                        var5 = Boolean.valueOf(false);
                        var1.eglChooseConfig(var2, s_configAttribs2, (EGLConfig[]) null, 0, var8);
                        var4 = var8[0];
                        var3 = var4;
                        if (var4 <= 0) {
                            throw new IllegalArgumentException("No configs match configSpec");
                        }
                        break label32;
                    }
                }

                var6 = var5;
                var5 = var7;
            }

            EGLConfig[] var9 = new EGLConfig[var3];
            if (var6.booleanValue()) {
                var1.eglChooseConfig(var2, s_configAttribsNV, var9, var3, var8);
            } else if (this.bUseAA.booleanValue() && var5.booleanValue()) {
                var1.eglChooseConfig(var2, s_configAttribsAA, var9, var3, var8);
            } else {
                var1.eglChooseConfig(var2, s_configAttribs2, var9, var3, var8);
            }

            return this.chooseConfig(var1, var2, var9);
        }

        public EGLConfig chooseConfig(EGL10 var1, EGLDisplay var2, EGLConfig[] var3) {
            int var5 = var3.length;

            for (int var4 = 0; var4 < var5; ++var4) {
                EGLConfig var10 = var3[var4];
                int var6 = this.findConfigAttrib(var1, var2, var10, 12325, 0);
                int var7 = this.findConfigAttrib(var1, var2, var10, 12326, 0);
                if (var6 >= this.mDepthSize && var7 >= this.mStencilSize) {
                    var6 = this.findConfigAttrib(var1, var2, var10, 12324, 0);
                    var7 = this.findConfigAttrib(var1, var2, var10, 12323, 0);
                    int var8 = this.findConfigAttrib(var1, var2, var10, 12322, 0);
                    int var9 = this.findConfigAttrib(var1, var2, var10, 12321, 0);
                    if (var6 >= this.mRedSize && var7 >= this.mGreenSize && var8 >= this.mBlueSize && var9 >= this.mAlphaSize) {
                        return var10;
                    }
                }
            }

            return null;
        }
    }

    public static class ContextFactory implements GLSurfaceView.EGLContextFactory {
        private static int EGL_CONTEXT_CLIENT_VERSION = 12440;

        public ContextFactory() {
        }

        public EGLContext createContext(EGL10 var1, EGLDisplay var2, EGLConfig var3) {
            Log.w(BaseWallpaper.TAG, "creating OpenGL ES 2.0 context");
            BaseWallpaper.checkEglError("Before eglCreateContext", var1);
            int var4 = EGL_CONTEXT_CLIENT_VERSION;
            EGLContext var5 = var1.eglCreateContext(var2, var3, EGL10.EGL_NO_CONTEXT, new int[]{var4, 2, 12344});
            BaseWallpaper.checkEglError("After eglCreateContext", var1);
            return var5;
        }

        public void destroyContext(EGL10 var1, EGLDisplay var2, EGLContext var3) {
            var1.eglDestroyContext(var2, var3);
        }
    }

    public class WallpaperEngine extends GLActiveEngine implements SharedPreferences.OnSharedPreferenceChangeListener {
        protected static final String TAG = "Base Tulips LWP";
        public SharedPreferences mPreferences;
        public BaseRenderer renderer;

        public WallpaperEngine(SharedPreferences var2) {
            super();
            this.mPreferences = var2;
            this.mPreferences.registerOnSharedPreferenceChangeListener(this);
            this.setEGLContextFactory(new BaseWallpaper.ContextFactory());
            this.setEGLConfigChooser(new BaseWallpaper.ConfigChooser(5, 6, 5, 0, 16, 0, this.useAA()));
            this.loadModels();
            this.renderer = this.getRenderer();
            this.setRenderer(this.renderer);
            this.setRenderMode(1);
        }

        protected BaseRenderer getRenderer() {
            BaseRenderer var1 = new BaseRenderer(BaseWallpaper.this.getApplicationContext(), BaseWallpaper.this);
            var1.setResources(BaseWallpaper.this.getResources());
            return var1;
        }

        protected void loadModels() {
        }

        public void onCreate(SurfaceHolder var1) {
            super.onCreate(var1);
            this.setTouchEventsEnabled(true);
        }

        public void onDestroy() {
            if (this.renderer != null) {
                this.renderer.freeGLResources();
            }

            super.onDestroy();
        }

        public void onOffsetsChanged(float var1, float var2, float var3, float var4, int var5, int var6) {
            super.onOffsetsChanged(var1, var2, var3, var4, var5, var6);
            this.renderer.setXOffset(var1);
        }

        public void onSharedPreferenceChanged(SharedPreferences var1, String var2) {
        }

        public void onSurfaceChanged(SurfaceHolder var1, int var2, int var3, int var4) {
            super.onSurfaceChanged(var1, var2, var3, var4);
            BaseWallpaper.this.surfaceHeight = var4;
            BaseWallpaper.this.surfaceWidth = var3;
        }

        public void onTouchEvent(MotionEvent var1) {
            super.onTouchEvent(var1);
            if (var1.getAction() == 0) {
                BaseWallpaper.this.prevX = var1.getX();
                BaseWallpaper.this.prevY = var1.getY();
            }

            if (var1.getAction() == 2) {
                float var2 = BaseWallpaper.this.prevY - var1.getY();
                if (Math.abs(var2) >= 10.0F) {
                    this.renderer.setYOffset(var2);
                }
            }

        }

        protected Boolean useAA() {
            return Build.VERSION.SDK_INT >= 16 ? Boolean.valueOf(true) : Boolean.valueOf(false);
        }
    }
}