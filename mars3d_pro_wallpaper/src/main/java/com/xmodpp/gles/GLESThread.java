package com.xmodpp.gles;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;

import com.xmodpp.nativeui.XModGLWindow;

public class GLESThread implements Callback {
    private static final boolean _logging = false;
    static int threadNr = 0;
    private EGLHelper _eglHelper = new EGLHelper();
    private Handler _handler;
    private HandlerThread _handlerThread;
    private boolean _running = false;
    private boolean _surfaceValid = false;
    private XModGLWindow _window;
    Runnable onResumeRunnable = new C01611();
    Runnable renderRunnable = new C01622();

    class C01611 implements Runnable {
        C01611() {
        }

        public void run() {
        }
    }

    class C01622 implements Runnable {
        private int delay = 13;
        private long framecount;
        private long lastTimestamp;

        C01622() {
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
                    int i = (int) (1.0E9d / ((double) timePerFrame));
                }
            }
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

    public GLESThread(XModGLWindow window) {
        this._window = window;
    }

    public synchronized void surfaceCreated(final SurfaceHolder holder) {
        StringBuilder stringBuilder = new StringBuilder("GLESThread");
        int i = threadNr;
        threadNr = i + 1;
        this._handlerThread = new HandlerThread(stringBuilder.append(i).toString());
        this._handlerThread.start();
        this._handler = new Handler(this._handlerThread.getLooper());
        Runnable1 surfaceCreateRunnable = new Runnable1(holder);
        this._handler.post(surfaceCreateRunnable);
        surfaceCreateRunnable.waitUntilDone();
    }

    public synchronized void surfaceChanged(final SurfaceHolder holder, int format, final int width, final int height) {
        Runnable2 surfaceChangedRunnable = new Runnable2(holder, width, height);
        this._handler.post(surfaceChangedRunnable);
        surfaceChangedRunnable.waitUntilDone();
        if (this._surfaceValid) {
            this._window.onResume();
        }
        if (this._running) {
            this._handler.post(this.renderRunnable);
        }
    }

    public synchronized void surfaceDestroyed(SurfaceHolder holder) {
        this._handler.removeCallbacksAndMessages(null);
        Runnable3 surfaceDestroyedRunnable = new Runnable3();
        this._handler.post(surfaceDestroyedRunnable);
        surfaceDestroyedRunnable.waitUntilDone();
        Looper looper = this._handlerThread.getLooper();
        if (looper != null) {
            looper.quit();
        }
        try {
            this._handlerThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this._handlerThread = null;
        this._handler = null;
    }

    public synchronized boolean renderOneFrame() {
        if (this._handler != null) {
            this._handler.post(this.renderRunnable);
        }
        return true;
    }

    public synchronized boolean startRendering() {
        if (this._surfaceValid) {
            this._window.onResume();
        }
        this._running = true;
        if (this._handler != null) {
            this._handler.post(this.renderRunnable);
        }
        return true;
    }

    public synchronized void pauseRendering() {
        this._running = false;
        if (this._handler != null) {
            this._handler.removeCallbacksAndMessages(null);
        }
        this._window.onPause();
    }

    public boolean isSurfaceValid() {
        return this._surfaceValid;
    }

    class Runnable1 implements Runnable {
        private boolean isDone = false;
        private boolean success = false;

        private SurfaceHolder holder;

        public Runnable1(SurfaceHolder holder) {
            this.holder = holder;
        }

        public void run() {
            try {
                GLESThread.this._eglHelper.initialize(8, 8, 8, 8, 16, 0);
                GLESThread.this._eglHelper.createSurface(holder);
                GLESThread.this._window.onCreate();
                this.success = true;
            } catch (Exception e) {
            }
            synchronized (this) {
                this.isDone = true;
                notifyAll();
            }
        }

        public boolean waitUntilDone() {
            boolean z;
            synchronized (this) {
                if (!this.isDone) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                z = this.success;
            }
            return z;
        }
    }

    class Runnable2 implements Runnable {
        private boolean isDone = false;
        private boolean success = false;

        private SurfaceHolder holder;
        private int width, height;

        public Runnable2(SurfaceHolder holder, int width, int height) {
            this.holder = holder;
            this.width = width;
            this.height = height;
        }

        public void run() {
            try {
                GLESThread.this._eglHelper.createSurface(holder);
                GLESThread.this._window.onResize(width, height);
                GLESThread.this._surfaceValid = true;
                this.success = true;
            } catch (Exception e) {
                GLESThread.this._surfaceValid = false;
            }
            synchronized (this) {
                this.isDone = true;
                notifyAll();
            }
        }

        public boolean waitUntilDone() {
            boolean z;
            synchronized (this) {
                if (!this.isDone) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                z = this.success;
            }
            return z;
        }
    }

    class Runnable3 implements Runnable {
        private boolean isDone = false;
        private boolean success = false;

        public void run() {
            GLESThread.this._window.onDestroy();
            GLESThread.this._eglHelper.destroySurface();
            GLESThread.this._surfaceValid = false;
            this.success = true;
            synchronized (this) {
                this.isDone = true;
                notifyAll();
            }
        }

        public boolean waitUntilDone() {
            boolean z;
            synchronized (this) {
                if (!this.isDone) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                z = this.success;
            }
            return z;
        }
    }
}
