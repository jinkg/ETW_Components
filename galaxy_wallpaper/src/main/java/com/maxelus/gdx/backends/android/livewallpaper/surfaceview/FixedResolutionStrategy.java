package com.maxelus.gdx.backends.android.livewallpaper.surfaceview;

import com.mybadlogic.gdx.backends.android.surfaceview.ResolutionStrategy;

public class FixedResolutionStrategy implements ResolutionStrategy {
    private final int height;
    private final int width;

    public FixedResolutionStrategy(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public MeasuredDimension calcMeasures(int widthMeasureSpec, int heightMeasureSpec) {
        return new MeasuredDimension(this.width, this.height);
    }
}
