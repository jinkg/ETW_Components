package com.kinglloy.wallpaper.chrooma;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

public class ChroomaLWP extends Game {
    private static final String TAG = "CHROOMALWP";
    private ActionResolver actionResolver;

    public ChroomaLWP(ActionResolver actionResolver) {
        this.actionResolver = actionResolver;
    }

    public void create() {
        Gdx.app.setLogLevel(3);
        Gdx.app.debug(TAG, "create");
        setScreen(new ChroomaLWPScreen(this));
    }

    public ActionResolver getActionResolver() {
        return this.actionResolver;
    }

    public String takeScreenshot() {
        return ScreenshotFactory.saveScreenshot();
    }

    public void takeScreenshotAndShare() {
        this.actionResolver.shareImage(ScreenshotFactory.saveScreenshot());
    }
}
