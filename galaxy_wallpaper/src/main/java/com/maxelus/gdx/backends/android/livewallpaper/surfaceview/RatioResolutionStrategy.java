package com.maxelus.gdx.backends.android.livewallpaper.surfaceview;

import android.view.View.MeasureSpec;

import com.mybadlogic.gdx.backends.android.surfaceview.ResolutionStrategy;

public class RatioResolutionStrategy implements ResolutionStrategy {
    private final float ratio;

    public RatioResolutionStrategy(float ratio) {
        this.ratio = ratio;
    }

    public RatioResolutionStrategy(float width, float height) {
        this.ratio = width / height;
    }

    public MeasuredDimension calcMeasures(int widthMeasureSpec, int heightMeasureSpec) {
        int width;
        int height;
        int specWidth = MeasureSpec.getSize(widthMeasureSpec);
        int specHeight = MeasureSpec.getSize(heightMeasureSpec);
        float desiredRatio = this.ratio;
        if (((float) specWidth) / ((float) specHeight) < desiredRatio) {
            width = specWidth;
            height = Math.round(((float) width) / desiredRatio);
        } else {
            height = specHeight;
            width = Math.round(((float) height) * desiredRatio);
        }
        return new MeasuredDimension(width, height);
    }
}
