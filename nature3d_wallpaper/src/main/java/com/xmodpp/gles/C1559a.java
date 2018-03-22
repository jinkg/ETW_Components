package com.xmodpp.gles;

import android.view.SurfaceHolder;

import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;
import javax.microedition.khronos.egl.EGLSurface;
import javax.microedition.khronos.opengles.GL10;

public class C1559a {
    private static final boolean f10180f = false;
    EGL10 f10181a;
    EGLDisplay f10182b;
    EGLSurface f10183c;
    EGLContext f10184d;
    EGLConfig f10185e;

    private int m20006a(EGL10 egl10, EGLDisplay eGLDisplay, EGLConfig eGLConfig, int i, int i2) {
        int[] iArr = new int[1];
        return egl10.eglGetConfigAttrib(eGLDisplay, eGLConfig, i, iArr) ? iArr[0] : i2;
    }

    public EGLConfig m20007a(EGL10 egl10, EGLDisplay eGLDisplay, int i, int i2, int i3, int i4, int i5, int i6) {
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
            int a = m20006a(egl10, eGLDisplay, eGLConfig2, 12325, 0);
            i7 = m20006a(egl10, eGLDisplay, eGLConfig2, 12326, 0);
            if (a >= i5 && i7 >= i6) {
                a = ((Math.abs(m20006a(egl10, eGLDisplay, eGLConfig2, 12324, 0) - i) + Math.abs(m20006a(egl10, eGLDisplay, eGLConfig2, 12323, 0) - i2)) + Math.abs(m20006a(egl10, eGLDisplay, eGLConfig2, 12322, 0) - i3)) + Math.abs(m20006a(egl10, eGLDisplay, eGLConfig2, 12321, 0) - i4);
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

    public EGLSurface m20008a(EGL10 egl10, EGLDisplay eGLDisplay, EGLConfig eGLConfig, Object obj) {
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

    public GL10 m20009a(SurfaceHolder surfaceHolder) {
        if (!(this.f10183c == null || this.f10183c == EGL10.EGL_NO_SURFACE)) {
            this.f10181a.eglMakeCurrent(this.f10182b, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_CONTEXT);
            m20010a();
        }
        this.f10183c = m20008a(this.f10181a, this.f10182b, this.f10185e, surfaceHolder);
        if (this.f10183c == null || this.f10183c == EGL10.EGL_NO_SURFACE) {
            throw new RuntimeException("createWindowSurface failed");
        } else if (this.f10181a.eglMakeCurrent(this.f10182b, this.f10183c, this.f10183c, this.f10184d)) {
            return (GL10) this.f10184d.getGL();
        } else {
            throw new RuntimeException("eglMakeCurrent failed.");
        }
    }

    public void m20010a() {
        if (this.f10183c != null && this.f10183c != EGL10.EGL_NO_SURFACE) {
            this.f10181a.eglMakeCurrent(this.f10182b, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_CONTEXT);
            this.f10181a.eglDestroySurface(this.f10182b, this.f10183c);
            this.f10183c = null;
        }
    }

    public void m20011a(int i, int i2, int i3, int i4, int i5, int i6) {
        if (this.f10181a == null) {
            this.f10181a = (EGL10) EGLContext.getEGL();
        }
        if (this.f10182b == null) {
            this.f10182b = this.f10181a.eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY);
        }
        if (this.f10185e == null) {
            this.f10181a.eglInitialize(this.f10182b, new int[2]);
            this.f10185e = m20007a(this.f10181a, this.f10182b, i, i2, i3, i4, i5, i6);
        }
        if (this.f10184d == null) {
            this.f10184d = this.f10181a.eglCreateContext(this.f10182b, this.f10185e, EGL10.EGL_NO_CONTEXT, new int[]{12440, 2, 12344});
            if (this.f10184d == null || this.f10184d == EGL10.EGL_NO_CONTEXT) {
                throw new RuntimeException("createContext failed");
            }
        }
        this.f10183c = null;
    }

    public boolean m20012b() {
        this.f10181a.eglSwapBuffers(this.f10182b, this.f10183c);
        return this.f10181a.eglGetError() != 12302;
    }

    public void m20013c() {
        if (this.f10184d != null) {
            this.f10181a.eglDestroyContext(this.f10182b, this.f10184d);
            this.f10184d = null;
        }
        if (this.f10182b != null) {
            this.f10181a.eglTerminate(this.f10182b);
            this.f10182b = null;
        }
    }
}
