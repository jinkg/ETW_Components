package com.kinglloy.wallpaper.chrooma;

import com.badlogic.gdx.utils.TimeUtils;

public class FPSLimiter {
    private long currentTime = TimeUtils.nanoTime();
    private long deltaTime = 0;
    private float fps = 60.0f;
    private long previousTime = TimeUtils.nanoTime();

    public void setFps(int fps) {
        this.fps = (float) fps;
    }

    public void delay() {
        this.currentTime = TimeUtils.nanoTime();
        this.deltaTime += this.currentTime - this.previousTime;
        while (((float) this.deltaTime) < 1.0E9f / this.fps) {
            this.previousTime = this.currentTime;
            long diff = (long) ((1.0E9f / this.fps) - ((float) this.deltaTime));
            if (diff / 1000000 > 1) {
                try {
                    Thread.currentThread();
                    Thread.sleep((diff / 1000000) - 1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            this.currentTime = TimeUtils.nanoTime();
            this.deltaTime += this.currentTime - this.previousTime;
            this.previousTime = this.currentTime;
        }
        this.deltaTime = (long) (((float) this.deltaTime) - (1.0E9f / this.fps));
    }
}
