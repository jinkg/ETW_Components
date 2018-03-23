package com.xmodpp.gles;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;

import com.xmodpp.nativeui.XModGLWindow;

public class C1560b implements Callback {
    static int f10186a = 0;
    private static final boolean f10187j = false;
    Runnable f10188b = new C1561c(this);
    Runnable f10189c = new C1565g(this);
    private XModGLWindow f10190d;
    private HandlerThread f10191e;
    private Handler f10192f;
    private C1559a f10193g = new C1559a();
    private boolean f10194h = false;
    private boolean f10195i = false;

    public C1560b(XModGLWindow xModGLWindow) {
        this.f10190d = xModGLWindow;
    }

    public synchronized boolean m20020a() {
        if (this.f10192f != null) {
            this.f10192f.post(this.f10189c);
        }
        return true;
    }

    public synchronized boolean m20021b() {
        if (this.f10195i) {
            this.f10190d.m20032c();
        }
        this.f10194h = true;
        if (this.f10192f != null) {
            this.f10192f.post(this.f10189c);
        }
        return true;
    }

    public synchronized void m20022c() {
        this.f10194h = false;
        if (this.f10192f != null) {
            this.f10192f.removeCallbacksAndMessages(null);
        }
        this.f10190d.m20033d();
    }

    public boolean m20023d() {
        return this.f10195i;
    }

    public synchronized void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
        C1562d c1562d = new C1562d(this, surfaceHolder, i2, i3);
        this.f10192f.post(c1562d);
        c1562d.m20024a();
        if (this.f10195i) {
            this.f10190d.m20032c();
        }
        if (this.f10194h) {
            this.f10192f.post(this.f10189c);
        }
    }

    public synchronized void surfaceCreated(SurfaceHolder surfaceHolder) {
        StringBuilder stringBuilder = new StringBuilder("GLESThread");
        int i = f10186a;
        f10186a = i + 1;
        this.f10191e = new HandlerThread(stringBuilder.append(i).toString());
        this.f10191e.start();
        this.f10192f = new Handler(this.f10191e.getLooper());
        C1563e c1563e = new C1563e(this, surfaceHolder);
        this.f10192f.post(c1563e);
        c1563e.m20025a();
    }

    public synchronized void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        this.f10192f.removeCallbacksAndMessages(null);
        C1564f c1564f = new C1564f(this);
        this.f10192f.post(c1564f);
        c1564f.m20026a();
        Looper looper = this.f10191e.getLooper();
        if (looper != null) {
            looper.quit();
        }
        try {
            this.f10191e.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.f10191e = null;
        this.f10192f = null;
    }

    class C1562d implements Runnable {
        final /* synthetic */ C1560b f10197a;
        private boolean f10198b = false;
        private boolean f10199c = false;
        private final /* synthetic */ SurfaceHolder f10200d;
        private final /* synthetic */ int f10201e;
        private final /* synthetic */ int f10202f;

        C1562d(C1560b c1560b, SurfaceHolder surfaceHolder, int i, int i2) {
            this.f10197a = c1560b;
            this.f10200d = surfaceHolder;
            this.f10201e = i;
            this.f10202f = i2;
        }

        public boolean m20024a() {
            boolean z;
            synchronized (this) {
                if (!this.f10198b) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                z = this.f10199c;
            }
            return z;
        }

        public void run() {
            try {
                this.f10197a.f10193g.m20009a(this.f10200d);
                this.f10197a.f10190d.m20030a(this.f10201e, this.f10202f);
                this.f10197a.f10195i = true;
                this.f10199c = true;
            } catch (Exception e) {
                this.f10197a.f10195i = false;
            }
            synchronized (this) {
                this.f10198b = true;
                notifyAll();
            }
        }
    }

    class C1563e implements Runnable {
        final /* synthetic */ C1560b f10203a;
        private boolean f10204b = false;
        private boolean f10205c = false;
        private final /* synthetic */ SurfaceHolder f10206d;

        C1563e(C1560b c1560b, SurfaceHolder surfaceHolder) {
            this.f10203a = c1560b;
            this.f10206d = surfaceHolder;
        }

        public boolean m20025a() {
            boolean z;
            synchronized (this) {
                if (!this.f10204b) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                z = this.f10205c;
            }
            return z;
        }

        public void run() {
            try {
                this.f10203a.f10193g.m20011a(8, 8, 8, 8, 16, 0);
                this.f10203a.f10193g.m20009a(this.f10206d);
                this.f10203a.f10190d.m20031b();
                this.f10205c = true;
            } catch (Exception e) {
            }
            synchronized (this) {
                this.f10204b = true;
                notifyAll();
            }
        }
    }

    class C1564f implements Runnable {
        final /* synthetic */ C1560b f10207a;
        private boolean f10208b = false;
        private boolean f10209c = false;

        C1564f(C1560b c1560b) {
            this.f10207a = c1560b;
        }

        public boolean m20026a() {
            boolean z;
            synchronized (this) {
                if (!this.f10208b) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                z = this.f10209c;
            }
            return z;
        }

        public void run() {
            this.f10207a.f10190d.m20034e();
            this.f10207a.f10193g.m20010a();
            this.f10207a.f10195i = false;
            this.f10209c = true;
            synchronized (this) {
                this.f10208b = true;
                notifyAll();
            }
        }
    }

    class C1565g implements Runnable {
        final /* synthetic */ C1560b f10210a;
        private long f10211b;
        private long f10212c;
        private int f10213d = 13;

        C1565g(C1560b c1560b) {
            this.f10210a = c1560b;
        }

        public void run() {
            if (this.f10210a.f10195i) {
                this.f10210a.f10193g.m20012b();
                if (this.f10210a.f10194h) {
                    this.f10210a.f10192f.removeCallbacks(this.f10210a.f10189c);
                    this.f10210a.f10192f.postDelayed(this.f10210a.f10189c, (long) this.f10213d);
                }
                this.f10210a.f10190d.m20035f();
                long j = this.f10212c;
                this.f10212c = 1 + j;
                if (j % 60 == 0) {
                    j = System.nanoTime();
                    long j2 = (j - this.f10211b) / 60;
                    this.f10211b = j;
                    int i = (int) (1.0E9d / ((double) j2));
                }
            }
        }
    }
}
