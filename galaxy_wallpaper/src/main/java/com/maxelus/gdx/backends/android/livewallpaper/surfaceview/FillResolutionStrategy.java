package com.maxelus.gdx.backends.android.livewallpaper.surfaceview;

import android.view.View.MeasureSpec;

import com.mybadlogic.gdx.backends.android.surfaceview.ResolutionStrategy;

public class FillResolutionStrategy implements ResolutionStrategy {
    public MeasuredDimension calcMeasures(int widthMeasureSpec, int heightMeasureSpec) {
        return new MeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), MeasureSpec.getSize(heightMeasureSpec));
    }
}
