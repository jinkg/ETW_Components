package org.androidworks.livewallpaperrose;

import android.content.Context;
import android.content.SharedPreferences;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import org.androidworks.livewallpaperrose.Wallpaper.ConfigChooser;
import org.androidworks.livewallpaperrose.Wallpaper.ContextFactory;

public class WallpaperGLSurfaceView extends GLSurfaceView {
    private long lastTouchTime;
    private Context mContext;
    protected SharedPreferences mPreferences;
    private float prevX;
    private float prevY;
    private RoseRenderer renderer;
    protected int surfaceHeight;
    protected int surfaceWidth;

    public WallpaperGLSurfaceView(Context context) {
        super(context);
        this.mContext = context;
        Wallpaper.mContext = context;
        setEGLContextFactory(new ContextFactory());
        setEGLConfigChooser(new ConfigChooser(5, 6, 5, 0, 16, 0));
        this.renderer = new RoseRenderer(context);
        this.mPreferences = context.getSharedPreferences(context.getPackageName(), 0);
        this.renderer.setResources(getResources());
        this.renderer.setRotate(Prefs.getRotate());
        this.renderer.setAutoRotate(true);
        this.renderer.setTilt(Prefs.getTilt());
        try {
            this.renderer.setBackground(Integer.parseInt(Prefs.getBackground()));
        } catch (NumberFormatException e) {
        }
        try {
            this.renderer.setPetal(Integer.parseInt(Prefs.getPetal()));
        } catch (NumberFormatException e2) {
        }
        this.renderer.setVignette(Prefs.getVignette());
        this.renderer.setRandomize(Prefs.getRandomize());
        try {
            this.renderer.setRotationDirection(Integer.parseInt(Prefs.getRotateDir()));
        } catch (NumberFormatException e3) {
        }
        this.renderer.setPreferences();
        setRenderer(this.renderer);
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        super.surfaceChanged(holder, format, w, h);
        this.surfaceHeight = h;
        this.surfaceWidth = w;
    }

    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        if (event.getAction() == 0) {
            this.prevX = event.getX();
            this.prevY = event.getY();
            this.lastTouchTime = System.currentTimeMillis();
        }
        if (event.getAction() == 2) {
            float deltaY = this.prevY - event.getY();
            if (Math.abs(deltaY) >= 10.0f) {
                this.renderer.setYOffset(deltaY);
            }
        }
        if (event.getAction() == 1) {
            float deltaX = event.getX() - this.prevX;
            float deltaY = event.getY() - this.prevY;
            if (Math.abs(deltaX) > 30.0f && Math.abs(deltaX / deltaY) > 0.5f
                    && System.currentTimeMillis() - this.lastTouchTime < 700) {
                this.renderer.changeSpeed(deltaX);
            }
        }
        return true;
    }
}
