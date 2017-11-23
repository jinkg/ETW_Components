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
    static int f289a = 0;
    private Handler f290a;
    private HandlerThread f291a;
    private fu f292a;
    private ga f293a;
    public Runnable f294a = new fv(this);
    private GL10 f295a;
    private boolean f296a = false;
    private boolean f297b = false;

    public GLESThread(ga gaVar) {
        f289a++;
        this.f292a = new fu();
        this.f293a = gaVar;
        this.f291a = new HandlerThread("GLESThread" + f289a);
        this.f291a.start();
        this.f290a = new Handler(this.f291a.getLooper());
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
        Looper looper = this.f291a.getLooper();
        if (looper != null) {
            looper.quit();
        }
        try {
            this.f291a.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized void m480a(SurfaceHolder surfaceHolder) {
        this.f290a.post(new fw(this, surfaceHolder));
        fz fzVar = new fz(this);
        this.f290a.post(fzVar);
        try {
            fzVar.m859a();
        } catch (InterruptedException e) {
        }
    }

    public synchronized void m481a(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
        this.f290a.post(new fx(this, surfaceHolder, i2, i3));
        fz fzVar = new fz(this);
        this.f290a.post(fzVar);
        try {
            fzVar.m859a();
        } catch (InterruptedException e) {
        }
    }

    public synchronized boolean m482a() {
        boolean z = true;
        synchronized (this) {
            if (this.f297b) {
                this.f296a = true;
                this.f290a.post(this.f294a);
            } else {
                z = false;
            }
        }
        return z;
    }

    public synchronized void m483b() {
        this.f290a.removeCallbacksAndMessages(null);
        this.f290a.post(new fy(this));
        fz fzVar = new fz(this);
        this.f290a.post(fzVar);
        try {
            fzVar.m859a();
        } catch (InterruptedException e) {
        }
        this.f296a = false;
        this.f290a.removeCallbacksAndMessages(null);
    }

    public boolean m484b() {
        return this.f297b;
    }

    public synchronized void m485c() {
        this.f296a = false;
        this.f290a.removeCallbacksAndMessages(null);
    }

    public class fv implements Runnable {
        final /* synthetic */ GLESThread f428a;

        public fv(GLESThread gLESThread) {
            this.f428a = gLESThread;
        }

        public void run() {
            if (this.f428a.m482a()) {
                this.f428a.f293a.mo59b(this.f428a.f295a);
                if (this.f428a.f296a) {
                    this.f428a.f290a.removeCallbacks(this.f428a.f294a);
                    this.f428a.f290a.postDelayed(this.f428a.f294a, 10);
                }
                this.f428a.f292a.onDrawFrame();
            }
        }
    }

    public class fw implements Runnable {
        private final /* synthetic */ SurfaceHolder f429a;
        final /* synthetic */ GLESThread f430a;

        public fw(GLESThread gLESThread, SurfaceHolder surfaceHolder) {
            this.f430a = gLESThread;
            this.f429a = surfaceHolder;
        }

        public void run() {
            try {
                this.f430a.f292a.m856a(8, 8, 8, 8, 16, 0);
                this.f430a.f295a = this.f430a.f292a.m855a(this.f429a);
                this.f430a.f293a.mo58a(this.f430a.f295a, this.f430a.f292a.f424a);
                this.f430a.f297b = true;
            } catch (Exception e) {
                this.f430a.f297b = false;
                this.f430a.f296a = false;
            }
        }
    }


    public class fx implements Runnable {
        private final /* synthetic */ int f431a;
        private final /* synthetic */ SurfaceHolder f432a;
        final /* synthetic */ GLESThread f433a;
        private final /* synthetic */ int f434b;

        public fx(GLESThread gLESThread, SurfaceHolder surfaceHolder, int i, int i2) {
            this.f433a = gLESThread;
            this.f432a = surfaceHolder;
            this.f431a = i;
            this.f434b = i2;
        }

        public void run() {
            try {
                this.f433a.f292a.m855a(this.f432a);
                this.f433a.f293a.mo57a(this.f433a.f295a, this.f431a, this.f434b);
                this.f433a.f297b = true;
                this.f433a.f296a = true;
            } catch (Exception e) {
                this.f433a.f297b = false;
                this.f433a.f296a = false;
            }
        }
    }

    public class fy implements Runnable {
        final /* synthetic */ GLESThread f435a;

        public fy(GLESThread gLESThread) {
            this.f435a = gLESThread;
        }

        public void run() {
            this.f435a.f293a.mo56a(this.f435a.f295a);
            this.f435a.f292a.m857a(this.f435a.f292a.f423a, this.f435a.f292a.f426a, this.f435a.f292a.f427a);
            this.f435a.f297b = false;
        }
    }


    public class fz implements Runnable {
        final /* synthetic */ GLESThread f436a;
        public boolean f437a = false;

        public fz(GLESThread gLESThread) {
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
