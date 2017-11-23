package com.xmodpp.gles;

import android.view.SurfaceHolder;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;
import javax.microedition.khronos.egl.EGLSurface;
import javax.microedition.khronos.opengles.GL10;

public class EGLHelper {
    EGL10 mEgl;
    EGLConfig f424a;
    EGLContext f425a;
    EGLDisplay mEglDisplay;
    EGLSurface mEglSurface;

    private int m852a(EGL10 egl10, EGLDisplay eGLDisplay, EGLConfig eGLConfig, int i, int i2) {
        int[] iArr = new int[1];
        return egl10.eglGetConfigAttrib(eGLDisplay, eGLConfig, i, iArr) ? iArr[0] : i2;
    }

    public EGLConfig m853a(EGL10 egl10, EGLDisplay eGLDisplay, int i, int i2, int i3, int i4, int i5, int i6) {
        int[] iArr = new int[1];
        int[] iArr2 = new int[]{12324, i, 12323, i2, 12322, i3, 12321, i4, 12325, i5, 12326, i6, 12352, 4, 12344};
        egl10.eglChooseConfig(eGLDisplay, iArr2, null, 0, iArr);
        int i7 = iArr[0];
        if (i7 <= 0) {
            throw new IllegalArgumentException("No configs match configSpec");
        }
        EGLConfig[] eGLConfigArr = new EGLConfig[i7];
        egl10.eglChooseConfig(eGLDisplay, iArr2, eGLConfigArr, i7, iArr);
        EGLConfig eGLConfig = null;
        int i8 = 1000;
        int length = eGLConfigArr.length;
        int i9 = 0;
        while (i9 < length) {
            EGLConfig eGLConfig2 = eGLConfigArr[i9];
            int a = m852a(egl10, eGLDisplay, eGLConfig2, 12325, 0);
            i7 = m852a(egl10, eGLDisplay, eGLConfig2, 12326, 0);
            if (a >= i5 && i7 >= i6) {
                a = ((Math.abs(m852a(egl10, eGLDisplay, eGLConfig2, 12324, 0) - i) + Math.abs(m852a(egl10, eGLDisplay, eGLConfig2, 12323, 0) - i2)) + Math.abs(m852a(egl10, eGLDisplay, eGLConfig2, 12322, 0) - i3)) + Math.abs(m852a(egl10, eGLDisplay, eGLConfig2, 12321, 0) - i4);
                if (a < i8) {
                    i9++;
                    eGLConfig = eGLConfig2;
                    i8 = a;
                }
            }
            a = i8;
            eGLConfig2 = eGLConfig;
            i9++;
            eGLConfig = eGLConfig2;
            i8 = a;
        }
        if (eGLConfig != null) {
            return eGLConfig;
        }
        throw new IllegalArgumentException("No config chosen");
    }

    public EGLSurface m854a(EGL10 egl10, EGLDisplay eGLDisplay, EGLConfig eGLConfig, Object obj) {
        EGLSurface eGLSurface = null;
        while (eGLSurface == null) {
            try {
                eGLSurface = egl10.eglCreateWindowSurface(eGLDisplay, eGLConfig, obj, null);
                if (eGLSurface == null) {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                    }
                }
            } catch (Throwable th) {
                if (eGLSurface == null) {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e2) {
                    }
                }
            }
        }
        return eGLSurface;
    }

    public GL10 createSurface(SurfaceHolder surfaceHolder) {
        if (!(this.mEglSurface == null || this.mEglSurface == EGL10.EGL_NO_SURFACE)) {
            this.mEgl.eglMakeCurrent(this.mEglDisplay, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_CONTEXT);
            destroySurface(this.mEgl, this.mEglDisplay, this.mEglSurface);
        }
        this.mEglSurface = m854a(this.mEgl, this.mEglDisplay, this.f424a, surfaceHolder);
        if (this.mEglSurface == null || this.mEglSurface == EGL10.EGL_NO_SURFACE) {
            throw new RuntimeException("createWindowSurface failed");
        } else if (this.mEgl.eglMakeCurrent(this.mEglDisplay, this.mEglSurface, this.mEglSurface, this.f425a)) {
            return (GL10) this.f425a.getGL();
        } else {
            throw new RuntimeException("eglMakeCurrent failed.");
        }
    }

    public void initialize(int i, int i2, int i3, int i4, int i5, int i6) {
        if (this.mEgl == null) {
            this.mEgl = (EGL10) EGLContext.getEGL();
        }
        if (this.mEglDisplay == null) {
            this.mEglDisplay = this.mEgl.eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY);
        }
        if (this.f424a == null) {
            this.mEgl.eglInitialize(this.mEglDisplay, new int[2]);
            this.f424a = m853a(this.mEgl, this.mEglDisplay, i, i2, i3, i4, i5, i6);
        }
        if (this.f425a == null) {
            this.f425a = this.mEgl.eglCreateContext(this.mEglDisplay, this.f424a, EGL10.EGL_NO_CONTEXT, new int[]{12440, 2, 12344});
            if (this.f425a == null || this.f425a == EGL10.EGL_NO_CONTEXT) {
                throw new RuntimeException("createContext failed");
            }
        }
        this.mEglSurface = null;
    }

    public void destroySurface(EGL10 egl10, EGLDisplay eGLDisplay, EGLSurface eGLSurface) {
        if (this.mEglSurface != null && this.mEglSurface != EGL10.EGL_NO_SURFACE) {
            this.mEgl.eglMakeCurrent(this.mEglDisplay, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_CONTEXT);
            egl10.eglDestroySurface(this.mEglDisplay, this.mEglSurface);
            this.mEglSurface = null;
        }
    }

    public boolean onDrawFrame() {
        this.mEgl.eglSwapBuffers(this.mEglDisplay, this.mEglSurface);
        return this.mEgl.eglGetError() != 12302;
    }
}
