package com.maxelus.gdx.backends.android.livewallpaper;

import android.opengl.GLSurfaceView.EGLConfigChooser;
import android.opengl.GLSurfaceView.Renderer;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.backends.android.AndroidGL20;
import com.badlogic.gdx.backends.android.AndroidGLU;
import com.badlogic.gdx.backends.android.surfaceview.ResolutionStrategy;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GL11;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.GLCommon;
import com.badlogic.gdx.graphics.GLU;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.WindowedMean;
import com.maxelus.gdx.backends.android.livewallpaper.surfaceview.DefaultGLSurfaceView;
import com.maxelus.gdx.backends.android.livewallpaper.surfaceview.GLBaseSurfaceView;
import com.maxelus.gdx.backends.android.livewallpaper.surfaceview.GLSurfaceView20;
import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;

public final class AndroidGraphicsLW implements Graphics, Renderer {
    AndroidApplicationLW app;
    volatile boolean created = false;
    private float deltaTime = 0.0f;
    volatile boolean destroy = false;
    private int fps;
    private long frameStart = System.nanoTime();
    private int frames = 0;
    GLCommon gl;
    GL10 gl10;
    GL11 gl11;
    GL20 gl20;
    GLU glu;
    int height;
    private long lastFrameTime = System.nanoTime();
    boolean ldestroy = false;
    boolean lpause = false;
    boolean lresume = false;
    boolean lrunning = false;
    private WindowedMean mean = new WindowedMean(5);
    volatile boolean pause = false;
    private float ppcX = 0.0f;
    private float ppcY = 0.0f;
    private float ppiX = 0.0f;
    private float ppiY = 0.0f;
    volatile boolean resume = false;
    volatile boolean running = false;
    Object synch = new Object();
    final GLBaseSurfaceView view;
    int width;

    class C01391 implements EGLConfigChooser {
        C01391() {
        }

        public EGLConfig chooseConfig(EGL10 egl, EGLDisplay display) {
            EGLConfig[] configs = new EGLConfig[1];
            egl.eglChooseConfig(display, new int[]{12325, 16, 12344}, configs, 1, new int[1]);
            return configs[0];
        }
    }

    public AndroidGraphicsLW(AndroidApplicationLW app, boolean useGL2IfAvailable, ResolutionStrategy resolutionStrategy) {
        this.view = createGLSurfaceView(app, useGL2IfAvailable, resolutionStrategy);
        this.app = app;
    }

    private GLBaseSurfaceView createGLSurfaceView(AndroidApplicationLW app, boolean useGL2, ResolutionStrategy resolutionStrategy) {
        DefaultGLSurfaceView view;
        EGLConfigChooser configChooser = getEglConfigChooser();
        if (useGL2 && checkGL20()) {
            view = new GLSurfaceView20(app.getEngine(), resolutionStrategy);
            if (configChooser != null) {
                view.setEGLConfigChooser(configChooser);
            }
            view.setRenderer(this);
        } else {
            view = new DefaultGLSurfaceView(app.getEngine(), resolutionStrategy);
            if (configChooser != null) {
                view.setEGLConfigChooser(configChooser);
            }
            view.setRenderer(this);
        }
        return view;
    }

    private EGLConfigChooser getEglConfigChooser() {
        if (Build.DEVICE.equalsIgnoreCase("GT-I7500")) {
            return new C01391();
        }
        return null;
    }

    private void updatePpi() {
        DisplayMetrics metrics = new DisplayMetrics();
        ((WindowManager) this.app.getService().getSystemService("window")).getDefaultDisplay().getMetrics(metrics);
        this.ppiX = metrics.xdpi;
        this.ppiY = metrics.ydpi;
        this.ppcX = metrics.xdpi / 2.54f;
        this.ppcY = metrics.ydpi / 2.54f;
    }

    private boolean checkGL20() {
        EGL10 egl = (EGL10) EGLContext.getEGL();
        EGLDisplay display = egl.eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY);
        egl.eglInitialize(display, new int[2]);
        int[] num_config = new int[1];
        egl.eglChooseConfig(display, new int[]{12324, 4, 12323, 4, 12322, 4, 12352, 4, 12344}, new EGLConfig[10], 10, num_config);
        egl.eglTerminate(display);
        return num_config[0] > 0;
    }

    public GL10 getGL10() {
        return this.gl10;
    }

    public GL11 getGL11() {
        return this.gl11;
    }

    public GL20 getGL20() {
        return this.gl20;
    }

    public int getHeight() {
        return this.height;
    }

    public int getWidth() {
        return this.width;
    }

    public boolean isGL11Available() {
        return this.gl11 != null;
    }

    public boolean isGL20Available() {
        return this.gl20 != null;
    }

    private void setupGL(javax.microedition.khronos.opengles.GL10 gl) {
        if (this.gl10 == null && this.gl20 == null) {
            if (this.view instanceof GLSurfaceView20) {
                this.gl20 = new AndroidGL20();
                this.gl = this.gl20;
            } else {
                this.gl10 = new AndroidGL10(gl);
                this.gl = this.gl10;
                if (gl instanceof javax.microedition.khronos.opengles.GL11) {
                    String renderer = gl.glGetString(7937);
                    if (!(renderer == null || renderer.toLowerCase().contains("pixelflinger") || Build.MODEL.equals("MB200") || Build.MODEL.equals("MB220") || Build.MODEL.contains("Behold"))) {
                        this.gl11 = new AndroidGL11((javax.microedition.khronos.opengles.GL11) gl);
                        this.gl10 = this.gl11;
                    }
                }
            }
            this.glu = new AndroidGLU();
            Gdx.gl = this.gl;
            Gdx.gl10 = this.gl10;
            Gdx.gl11 = this.gl11;
            Gdx.gl20 = this.gl20;
            Gdx.glu = this.glu;
        }
    }

    public void onSurfaceChanged(javax.microedition.khronos.opengles.GL10 gl, int width, int height) {
        this.width = width;
        this.height = height;
        updatePpi();
        gl.glViewport(0, 0, this.width, this.height);
        this.app.getListener().resize(width, height);
    }

    public void onSurfaceCreated(javax.microedition.khronos.opengles.GL10 gl, EGLConfig config) {
        setupGL(gl);
        updatePpi();
        Mesh.invalidateAllMeshes(this.app);
        Texture.invalidateAllTextures(this.app);
        ShaderProgram.invalidateAllShaderPrograms(this.app);
        FrameBuffer.invalidateAllFrameBuffers(this.app);
        try {
            Display display = ((WindowManager) this.app.getService().getSystemService("window")).getDefaultDisplay();
            this.width = display.getWidth();
            this.height = display.getHeight();
            this.mean = new WindowedMean(5);
            this.lastFrameTime = System.nanoTime();
        } catch (Exception e) {
            this.width = 0;
            this.height = 0;
        }
        gl.glViewport(0, 0, this.width, this.height);
        if (!this.created) {
            this.app.getListener().create();
            this.created = true;
            synchronized (this) {
                this.running = true;
            }
        }
    }

    public void resume() {
        synchronized (this.synch) {
            this.running = true;
            this.resume = true;
        }
    }

    public void pause() {
        synchronized (this.synch) {
            if (this.running) {
                this.running = false;
                this.pause = true;
                return;
            }
        }
    }

    public void destroy() {
        synchronized (this.synch) {
            this.running = false;
            this.destroy = true;
        }
    }

    public void setMSSleep(long ms) {
        synchronized (this.synch) {
            this.view.setMSSleep(ms);
        }
    }

    public long getMSSleep() {
        long mSSleep;
        synchronized (this.synch) {
            mSSleep = this.view.getMSSleep();
        }
        return mSSleep;
    }

    public void onDrawFrame(javax.microedition.khronos.opengles.GL10 gl) {
        if (gl == null) {
            this.running = false;
            this.destroy = true;
        }
        long time = System.nanoTime();
        this.deltaTime = ((float) (time - this.lastFrameTime)) / 1.0E9f;
        this.lastFrameTime = time;
        this.mean.addValue(this.deltaTime);
        this.lrunning = false;
        this.lpause = false;
        this.ldestroy = false;
        this.lresume = false;
        synchronized (this.synch) {
            this.lrunning = this.running;
            this.lpause = this.pause;
            this.ldestroy = this.destroy;
            this.lresume = this.resume;
            if (this.resume) {
                this.resume = false;
            }
            if (this.pause) {
                this.pause = false;
                this.synch.notifyAll();
            }
            if (this.destroy) {
                this.destroy = false;
                this.synch.notifyAll();
            }
        }
        if (this.lresume) {
            this.app.getListener().resume();
        }
        if (this.lrunning && !(Gdx.graphics.getGL10() == null && Gdx.graphics.getGL11() == null && Gdx.graphics.getGL20() == null)) {
            synchronized (this.app.runnables) {
                this.app.executedRunnables.clear();
                this.app.executedRunnables.addAll(this.app.runnables);
                this.app.runnables.clear();
                for (int i = 0; i < this.app.executedRunnables.size; i++) {
                    try {
                        ((Runnable) this.app.executedRunnables.get(i)).run();
                    } catch (Throwable t) {
                        t.printStackTrace();
                    }
                }
            }
            this.app.input.processEvents();
            this.app.getListener().render();
        }
        if (this.lpause) {
            this.app.getListener().pause();
        }
        if (this.ldestroy) {
            this.app.getListener().dispose();
        }
        if (time - this.frameStart > 1000000000) {
            this.fps = this.frames;
            this.frames = 0;
            this.frameStart = time;
        }
        this.frames++;
    }

    public float getDeltaTime() {
        return this.mean.getMean() == 0.0f ? this.deltaTime : this.mean.getMean();
    }

    public GraphicsType getType() {
        return GraphicsType.AndroidGL;
    }

    public int getFramesPerSecond() {
        return this.fps;
    }

    public void clearManagedCaches() {
        Mesh.clearAllMeshes(this.app);
        Texture.clearAllTextures(this.app);
        ShaderProgram.clearAllShaderPrograms(this.app);
        FrameBuffer.clearAllFrameBuffers(this.app);
    }

    public GLCommon getGLCommon() {
        return this.gl;
    }

    public float getPpiX() {
        return this.ppiX;
    }

    public float getPpiY() {
        return this.ppiY;
    }

    public float getPpcX() {
        return this.ppcX;
    }

    public float getPpcY() {
        return this.ppcY;
    }

    public GLBaseSurfaceView getView() {
        return this.view;
    }

    public DisplayMode[] getDisplayModes() {
        return new DisplayMode[0];
    }

    public GLU getGLU() {
        return this.glu;
    }

    public boolean setDisplayMode(DisplayMode arg0) {
        return false;
    }

    public void setTitle(String arg0) {
    }

    public boolean supportsDisplayModeChange() {
        return false;
    }

    public float getDensity() {
        return 0.0f;
    }

    public DisplayMode getDesktopDisplayMode() {
        return null;
    }

    public boolean setDisplayMode(int width, int height, boolean fullscreen) {
        return false;
    }

    public void setIcon(Pixmap[] pixmaps) {
    }

    public void setVSync(boolean vsync) {
    }

    public BufferFormat getBufferFormat() {
        return null;
    }

    public boolean supportsExtension(String extension) {
        return false;
    }
}
