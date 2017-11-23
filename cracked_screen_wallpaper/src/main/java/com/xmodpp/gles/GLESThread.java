package com.xmodpp.gles;

import android.graphics.Bitmap;
import android.opengl.ETC1Util;
import android.opengl.GLUtils;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.view.SurfaceHolder;

import com.xmodpp.core.Assets;

import java.io.IOException;
import java.io.InputStream;

import javax.microedition.khronos.opengles.GL10;

public class GLESThread {
    static int threadNr = 0;
    private Handler _handler;
    private HandlerThread _handlerThread;
    private EGLHelper _eglHelper;
    private ga f293a;
    public Runnable f294a = new fv(this);
    private GL10 f295a;
    private boolean _running = false;
    private boolean _surfaceValid = false;

    public GLESThread(ga gaVar) {
        threadNr++;
        this._eglHelper = new EGLHelper();
        this.f293a = gaVar;
        this._handlerThread = new HandlerThread("GLESThread" + threadNr);
        this._handlerThread.start();
        this._handler = new Handler(this._handlerThread.getLooper());
    }

    private static boolean m476a(String str) {
        InputStream open = null;
        try {
            open = Assets.m465a().open(str);
            ETC1Util.loadTexture(3553, 0, 0, 6407, 33635, open);

            return true;
        } catch (IOException ignore) {

        } finally {
            if (open != null) {
                try {
                    open.close();
                } catch (IOException ignore) {
                }
            }
        }
        return false;
    }

    static boolean dno_loadTextureFromAsset(String str) {
        if (str.endsWith(".pkm")) {
            return m476a(str);
        }
        Bitmap a = Assets.m466a(str);
        if (a == null) {
            return false;
        }
        GLUtils.texImage2D(3553, 0, a, 0);
        a.recycle();
        return true;
    }

    public void m479a() {
        Looper looper = this._handlerThread.getLooper();
        if (looper != null) {
            looper.quit();
        }
        try {
            this._handlerThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized void surfaceCreated(SurfaceHolder surfaceHolder) {
        this._handler.post(new fw(this, surfaceHolder));
        Barrier barrier = new Barrier(this);
        this._handler.post(barrier);
        try {
            barrier.m859a();
        } catch (InterruptedException e) {
        }
    }

    public synchronized void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
        this._handler.post(new fx(this, surfaceHolder, i2, i3));
        Barrier barrier = new Barrier(this);
        this._handler.post(barrier);
        try {
            barrier.m859a();
        } catch (InterruptedException e) {
        }
    }

    public synchronized boolean startRendering() {
        boolean z = true;
        synchronized (this) {
            if (this._surfaceValid) {
                this._running = true;
                this._handler.post(this.f294a);
            } else {
                z = false;
            }
        }
        return z;
    }

    public synchronized void surfaceDestroyed() {
        this._handler.removeCallbacksAndMessages(null);
        this._handler.post(new fy(this));
        Barrier barrier = new Barrier(this);
        this._handler.post(barrier);
        try {
            barrier.m859a();
        } catch (InterruptedException e) {
        }
        this._running = false;
        this._handler.removeCallbacksAndMessages(null);
    }

    public boolean isSurfaceValid() {
        return this._surfaceValid;
    }

    public synchronized void pauseRendering() {
        this._running = false;
        this._handler.removeCallbacksAndMessages(null);
    }

    public class fv implements Runnable {
        final /* synthetic */ GLESThread f428a;

        public fv(GLESThread gLESThread) {
            this.f428a = gLESThread;
        }

        public void run() {
            if (this.f428a._surfaceValid) {
                this.f428a.f293a.mo59b(this.f428a.f295a);
                if (this.f428a._running) {
                    this.f428a._handler.removeCallbacks(this.f428a.f294a);
                    this.f428a._handler.postDelayed(this.f428a.f294a, 10);
                }
                this.f428a._eglHelper.onDrawFrame();
            }
        }
    }

    public class fw implements Runnable {
        private final /* synthetic */ SurfaceHolder holder;
        final /* synthetic */ GLESThread f430a;

        public fw(GLESThread gLESThread, SurfaceHolder surfaceHolder) {
            this.f430a = gLESThread;
            this.holder = surfaceHolder;
        }

        public void run() {
            try {
                this.f430a._eglHelper.initialize(8, 8, 8, 8, 16, 0);
                this.f430a.f295a = this.f430a._eglHelper.createSurface(this.holder);
                this.f430a.f293a.mo58a(this.f430a.f295a, this.f430a._eglHelper.f424a);
                this.f430a._surfaceValid = true;
            } catch (Exception e) {
                this.f430a._surfaceValid = false;
                this.f430a._running = false;
            }
        }
    }


    public class fx implements Runnable {
        private final /* synthetic */ int width;
        private final /* synthetic */ SurfaceHolder holder;
        final /* synthetic */ GLESThread f433a;
        private final /* synthetic */ int height;

        public fx(GLESThread gLESThread, SurfaceHolder surfaceHolder, int i, int i2) {
            this.f433a = gLESThread;
            this.holder = surfaceHolder;
            this.width = i;
            this.height = i2;
        }

        public void run() {
            try {
                this.f433a._eglHelper.createSurface(this.holder);
                this.f433a.f293a.onResize(this.f433a.f295a, this.width, this.height);
                this.f433a._surfaceValid = true;
                this.f433a._running = true;
            } catch (Exception e) {
                this.f433a._surfaceValid = false;
                this.f433a._running = false;
            }
        }
    }

    public class fy implements Runnable {
        final /* synthetic */ GLESThread f435a;

        public fy(GLESThread gLESThread) {
            this.f435a = gLESThread;
        }

        public void run() {
            this.f435a.f293a.onDestroy(this.f435a.f295a);
            this.f435a._eglHelper.destroySurface(this.f435a._eglHelper.mEgl, this.f435a._eglHelper.mEglDisplay, this.f435a._eglHelper.mEglSurface);
            this.f435a._surfaceValid = false;
        }
    }


    public class Barrier implements Runnable {
        final /* synthetic */ GLESThread f436a;
        public boolean f437a = false;

        public Barrier(GLESThread gLESThread) {
            this.f436a = gLESThread;
        }

        public void m859a() throws InterruptedException {
            synchronized (this) {
                if (!this.f437a) {
                    wait();
                }
            }
        }

        public void run() {
            synchronized (this) {
                this.f437a = true;
                notifyAll();
            }
        }
    }


}
