package com.maxelus.gdx.backends.android.livewallpaper.surfaceview;

public interface ResolutionStrategy {

    public static class MeasuredDimension {
        public final int height;
        public final int width;

        public MeasuredDimension(int width, int height) {
            this.width = width;
            this.height = height;
        }
    }

    MeasuredDimension calcMeasures(int i, int i2);
}
