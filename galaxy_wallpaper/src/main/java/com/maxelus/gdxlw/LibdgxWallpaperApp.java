package com.maxelus.gdxlw;

import com.mybadlogic.gdx.ApplicationListener;

public abstract class LibdgxWallpaperApp implements ApplicationListener, LibdgxWallpaperListener {
    protected boolean isPreview;

    public void setIsPreview(boolean isPreview) {
        this.isPreview = isPreview;
    }
}
