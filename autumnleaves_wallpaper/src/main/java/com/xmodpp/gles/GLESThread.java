package com.xmodpp.gles;

import android.graphics.Bitmap;
import android.opengl.ETC1Util;
import android.opengl.GLUtils;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;

import com.xmodpp.core.Assets;
import com.xmodpp.nativeui.XModGLWindow;

import java.io.IOException;
import java.io.InputStream;

public class GLESThread implements Callback {
    private static final boolean _logging = true;
    static int threadNr = 0;
    private EGLHelper _eglHelper = new EGLHelper();
    private Handler _handler;
    private HandlerThread _handlerThread;
    private boolean _running = false;
    private boolean _surfaceValid = false;
    private XModGLWindow _window;
    Runnable renderRunnable = new C00791();

    class C00791 implements Runnable {
        private int delay = 13;
        private long framecount;
        private long lastTimestamp;

        C00791() {
        }

        public void run() {
            if (GLESThread.this._surfaceValid) {
                GLESThread.this._eglHelper.swap();
                if (GLESThread.this._running) {
                    GLESThread.this._handler.removeCallbacks(GLESThread.this.renderRunnable);
                    GLESThread.this._handler.postDelayed(GLESThread.this.renderRunnable, (long) this.delay);
                }
                GLESThread.this._window.onDrawFrame();
                long j = this.framecount;
                this.framecount = 1 + j;
                if (j % 60 == 0) {
                    long timestamp = System.nanoTime();
                    long timePerFrame = (timestamp - this.lastTimestamp) / 60;
                    this.lastTimestamp = timestamp;
                    Log.d("XMOD++", "FPS: " + ((int) (1.0E9d / ((double) timePerFrame))) + " delay: " + this.delay);
                }
            }
        }
    }

    class C00824 implements Runnable {
        C00824() {
        }

        public void run() {
            Log.i("XMOD++", this + " surfaceDestroyed exec");
            GLESThread.this._window.onDestroy();
            GLESThread.this._eglHelper.destroySurface(GLESThread.this._eglHelper.mEgl, GLESThread.this._eglHelper.mEglDisplay, GLESThread.this._eglHelper.mEglSurface);
            GLESThread.this._surfaceValid = false;
        }
    }

    class Barrier implements Runnable {
        public boolean isDone = false;

        Barrier() {
        }

        public void run() {
            synchronized (this) {
                this.isDone = true;
                notifyAll();
            }
        }

        public void waitUntilDone() throws InterruptedException {
            synchronized (this) {
                if (!this.isDone) {
                    wait();
                }
            }
        }
    }

    static class TextureInfo {
        public int jni_height;
        public int jni_width;

        TextureInfo(int width, int height) {
            this.jni_width = width;
            this.jni_height = height;
        }
    }

    public GLESThread(XModGLWindow window) {
        this._window = window;
    }

    public synchronized void surfaceCreated(final SurfaceHolder holder) {
        Log.i("XMOD++", this + " surfaceCreated");
        StringBuilder stringBuilder = new StringBuilder("GLESThread");
        int i = threadNr;
        threadNr = i + 1;
        this._handlerThread = new HandlerThread(stringBuilder.append(i).toString());
        this._handlerThread.start();
        this._handler = new Handler(this._handlerThread.getLooper());
        this._handler.post(new Runnable() {
            public void run() {
                Log.i("XMOD++", this + " surfaceCreated exec");
                try {
                    GLESThread.this._eglHelper.initialize(8, 8, 8, 8, 16, 0);
                    GLESThread.this._eglHelper.createSurface(holder);
                    GLESThread.this._window.onCreate();
                    GLESThread.this._surfaceValid = true;
                } catch (Exception e) {
                    GLESThread.this._surfaceValid = false;
                }
            }
        });
        Barrier barrier = new Barrier();
        this._handler.post(barrier);
        try {
            barrier.waitUntilDone();
        } catch (InterruptedException e) {
        }
        this._handler.post(this.renderRunnable);
    }

    public synchronized void surfaceChanged(final SurfaceHolder holder, int format, final int width, final int height) {
        Log.i("XMOD++", this + " surfaceChanged");
        this._handler.post(new Runnable() {
            public void run() {
                Log.i("XMOD++", this + " surfaceChanged exec");
                try {
                    GLESThread.this._eglHelper.createSurface(holder);
                    GLESThread.this._window.onResize(width, height);
                    GLESThread.this._surfaceValid = true;
                } catch (Exception e) {
                    GLESThread.this._surfaceValid = false;
                }
            }
        });
        Barrier barrier = new Barrier();
        this._handler.post(barrier);
        try {
            barrier.waitUntilDone();
        } catch (InterruptedException e) {
        }
        this._handler.post(this.renderRunnable);
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.i("XMOD++", this + " surfaceDestroyed");
        this._handler.removeCallbacksAndMessages(null);
        this._handler.post(new C00824());
        Barrier barrier = new Barrier();
        this._handler.post(barrier);
        try {
            barrier.waitUntilDone();
        } catch (InterruptedException e) {
        }
        Looper looper = this._handlerThread.getLooper();
        if (looper != null) {
            looper.quit();
        }
        try {
            this._handlerThread.join();
            Log.d("XMOD++", String.valueOf(this._handlerThread.getName()) + " finished");
        } catch (InterruptedException e2) {
            e2.printStackTrace();
        }
        this._handlerThread = null;
        this._handler = null;
    }

    public synchronized boolean renderOneFrame() {
        Log.i("XMOD++", this + " renderOneFrame");
        this._handler.post(this.renderRunnable);
        return true;
    }

    public synchronized boolean startRendering() {
        Log.i("XMOD++", this + " startRendering");
        this._window.onResume();
        this._running = true;
        if (this._handler != null) {
            this._handler.post(this.renderRunnable);
        }
        return true;
    }

    public synchronized void pauseRendering() {
        Log.i("XMOD++", this + " pauseRendering");
        this._running = false;
        if (this._handler != null) {
            this._handler.removeCallbacksAndMessages(null);
        }
        this._window.onPause();
    }

    public boolean isSurfaceValid() {
        return this._surfaceValid;
    }

    static Object jni_loadTextureFromAsset(String assetName) {
        Log.i("XMOD++", "Loading " + assetName);
        if (assetName.endsWith(".pkm")) {
            return loadETC1TextureFromAsset(assetName);
        }
        Bitmap bitmap = Assets.getBitmapFromAsset(assetName);
        if (bitmap == null) {
            return new TextureInfo(0, 0);
        }
        Log.i("XMOD++", "w" + bitmap.getWidth() + " h" + bitmap.getHeight());
        Object result = new TextureInfo(bitmap.getWidth(), bitmap.getHeight());
        GLUtils.texImage2D(3553, 0, bitmap, 0);
        bitmap.recycle();
        return result;
    }

    private static Object loadETC1TextureFromAsset(String str) {
        InputStream open = null;
        try {
            open = Assets.getAssetManager().open(str);
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
}
