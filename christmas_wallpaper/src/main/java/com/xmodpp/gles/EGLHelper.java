package com.xmodpp.gles;

import android.view.SurfaceHolder;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;
import javax.microedition.khronos.egl.EGLSurface;
import javax.microedition.khronos.opengles.GL10;

public class EGLHelper {
    private static final boolean _logging = false;
    EGL10 mEgl;
    EGLConfig mEglConfig;
    EGLContext mEglContext;
    EGLDisplay mEglDisplay;
    EGLSurface mEglSurface;

    public void initialize(int redBits, int greenBits, int blueBits, int alphaBits, int depthBits, int stencilBits) {
        if (this.mEgl == null) {
            this.mEgl = (EGL10) EGLContext.getEGL();
        }
        if (this.mEglDisplay == null) {
            this.mEglDisplay = this.mEgl.eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY);
        }
        if (this.mEglConfig == null) {
            this.mEgl.eglInitialize(this.mEglDisplay, new int[2]);
            this.mEglConfig = chooseConfig(this.mEgl, this.mEglDisplay, redBits, greenBits, blueBits, alphaBits, depthBits, stencilBits);
        }
        if (this.mEglContext == null) {
            this.mEglContext = this.mEgl.eglCreateContext(this.mEglDisplay, this.mEglConfig, EGL10.EGL_NO_CONTEXT, new int[]{12440, 2, 12344});
            if (this.mEglContext == null || this.mEglContext == EGL10.EGL_NO_CONTEXT) {
                throw new RuntimeException("createContext failed");
            }
        }
        this.mEglSurface = null;
    }

    public GL10 createSurface(SurfaceHolder holder) {
        if (!(this.mEglSurface == null || this.mEglSurface == EGL10.EGL_NO_SURFACE)) {
            this.mEgl.eglMakeCurrent(this.mEglDisplay, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_CONTEXT);
            destroySurface(this.mEgl, this.mEglDisplay, this.mEglSurface);
        }
        this.mEglSurface = createWindowSurface(this.mEgl, this.mEglDisplay, this.mEglConfig, holder);
        if (this.mEglSurface == null || this.mEglSurface == EGL10.EGL_NO_SURFACE) {
            throw new RuntimeException("createWindowSurface failed");
        } else if (this.mEgl.eglMakeCurrent(this.mEglDisplay, this.mEglSurface, this.mEglSurface, this.mEglContext)) {
            return (GL10) this.mEglContext.getGL();
        } else {
            throw new RuntimeException("eglMakeCurrent failed.");
        }
    }

    public EGLSurface createWindowSurface(EGL10 egl, EGLDisplay display, EGLConfig config, Object nativeWindow) {
        EGLSurface eglSurface = null;
        while (eglSurface == null) {
            try {
                eglSurface = egl.eglCreateWindowSurface(display, config, nativeWindow, null);
                if (eglSurface == null) {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                    }
                }
            } catch (Throwable th) {
                if (eglSurface == null) {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e2) {
                    }
                }
            }
        }
        return eglSurface;
    }

    public void destroySurface(EGL10 egl, EGLDisplay display, EGLSurface surface) {
        if (this.mEglSurface != null && this.mEglSurface != EGL10.EGL_NO_SURFACE) {
            this.mEgl.eglMakeCurrent(this.mEglDisplay, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_CONTEXT);
            egl.eglDestroySurface(this.mEglDisplay, this.mEglSurface);
            this.mEglSurface = null;
        }
    }

    public EGLConfig chooseConfig(EGL10 egl, EGLDisplay display, int redBits, int greenBits, int blueBits, int alphaBits, int depthBits, int stencilBits) {
        int[] num_config = new int[1];
        int[] mConfigSpec = new int[]{12324, redBits, 12323, greenBits, 12322, blueBits, 12321, alphaBits, 12325, depthBits, 12326, stencilBits, 12352, 4, 12344};
        egl.eglChooseConfig(display, mConfigSpec, null, 0, num_config);
        int numConfigs = num_config[0];
        if (numConfigs <= 0) {
            throw new IllegalArgumentException("No configs match configSpec");
        }
        EGLConfig[] configs = new EGLConfig[numConfigs];
        egl.eglChooseConfig(display, mConfigSpec, configs, numConfigs, num_config);
        EGLConfig closestConfig = null;
        int closestDistance = 1000;
        for (EGLConfig config : configs) {
            int d = findConfigAttrib(egl, display, config, 12325, 0);
            int s = findConfigAttrib(egl, display, config, 12326, 0);
            if (d >= depthBits && s >= stencilBits) {
                int distance = ((Math.abs(findConfigAttrib(egl, display, config, 12324, 0) - redBits) + Math.abs(findConfigAttrib(egl, display, config, 12323, 0) - greenBits)) + Math.abs(findConfigAttrib(egl, display, config, 12322, 0) - blueBits)) + Math.abs(findConfigAttrib(egl, display, config, 12321, 0) - alphaBits);
                if (distance < closestDistance) {
                    closestDistance = distance;
                    closestConfig = config;
                }
            }
        }
        if (closestConfig != null) {
            return closestConfig;
        }
        throw new IllegalArgumentException("No config chosen");
    }

    private int findConfigAttrib(EGL10 egl, EGLDisplay display, EGLConfig config, int attribute, int defaultValue) {
        int[] mValue = new int[1];
        if (egl.eglGetConfigAttrib(display, config, attribute, mValue)) {
            return mValue[0];
        }
        return defaultValue;
    }

    public boolean swap() {
        this.mEgl.eglSwapBuffers(this.mEglDisplay, this.mEglSurface);
        return this.mEgl.eglGetError() != 12302;
    }

    public void finish() {
        if (this.mEglContext != null) {
            this.mEgl.eglDestroyContext(this.mEglDisplay, this.mEglContext);
            this.mEglContext = null;
        }
        if (this.mEglDisplay != null) {
            this.mEgl.eglTerminate(this.mEglDisplay);
            this.mEglDisplay = null;
        }
    }
}
