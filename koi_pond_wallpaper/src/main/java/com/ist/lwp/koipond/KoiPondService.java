package com.ist.lwp.koipond;

import android.content.Context;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.badlogic.gdx.backends.android.AndroidWallpaperListener;
import com.ist.lwp.koipond.datareader.SharedPreferenceHelper;
import com.ist.lwp.koipond.models.BaitsManager;
import com.ist.lwp.koipond.models.KoiManager;
import com.ist.lwp.koipond.models.PondsManager;
import com.ist.lwp.koipond.resource.ShaderManager;
import com.ist.lwp.koipond.resource.TextureMananger;
import com.ist.lwp.koipond.waterpond.MainRenderer;
import com.ist.lwp.koipond.waterpond.PreferencesManager;
import com.yalin.style.engine.GDXWallpaperServiceProxy;

public class KoiPondService extends GDXWallpaperServiceProxy {
    private ApplicationListener listener;

    public KoiPondService(Context host) {
        super(host);
    }

    private class KoiPondApplicationInternal
        implements AndroidWallpaperListener, ApplicationListener {
        private boolean created;
        private boolean isPreview;
        private boolean isPreviewNotified;
        private MainRenderer mMainRenderer;
        private ShaderManager shaderManager;
        private TextureMananger textureManager;
        private int viewportHeight;
        private int viewportWidth;

        private KoiPondApplicationInternal() {
            this.isPreview = true;
            this.isPreviewNotified = false;
        }

        public void offsetChange(float xOffsetPercent, float yOffsetPercent, float xOffsetStep, float yOffsetStep, int xPixelOffset, int yPixelOffset) {
            this.mMainRenderer.updateSurfaceCameraDestin(xOffsetPercent, yOffsetPercent);
        }

        public void previewStateChange(boolean isPreview) {
            this.isPreview = isPreview;
            if (!this.isPreviewNotified) {
                this.isPreviewNotified = true;
            }
            this.mMainRenderer.onPreviewStateChanged(isPreview);
        }

        public void create() {
            this.shaderManager = ShaderManager.getInstance();
            this.textureManager = TextureMananger.getInstance();
            this.viewportWidth = Gdx.graphics.getWidth();
            this.viewportHeight = Gdx.graphics.getHeight();
            this.mMainRenderer = createMainRenderer();
            this.mMainRenderer.show();
            this.created = true;
        }

        public void dispose() {
            if (this.created) {
                if (this.mMainRenderer != null) {
                    this.mMainRenderer.dispose();
                }
                this.shaderManager.dispose();
                this.textureManager.dispose();
                SharedPreferenceHelper.getInstance().dispose();
                PondsManager.getInstance().dispose();
                PreferencesManager.getInstance().dispose();
                KoiManager.getInstance().dispose();
                BaitsManager.getInstance().dispose();
            }
        }

        public void pause() {
            this.mMainRenderer.pause();
        }

        public void render() {
            this.mMainRenderer.render(Gdx.graphics.getDeltaTime());
        }

        public void resize(int width, int height) {
            if (this.viewportWidth != width || this.viewportHeight != height) {
                this.viewportHeight = height;
                this.viewportWidth = width;
                if (this.mMainRenderer != null) {
                    this.mMainRenderer.dispose();
                }
                this.mMainRenderer = createMainRenderer();
                this.mMainRenderer.resize(width, height);
            }
        }

        public void resume() {
            this.mMainRenderer.resume();
        }

        public void onSurfaceCreated() {
            if (this.created) {
                loseGLContext();
            }
        }

        private void loseGLContext() {
            if (this.mMainRenderer != null) {
                this.mMainRenderer.dispose();
            }
            this.shaderManager.dispose();
            this.shaderManager = ShaderManager.getInstance();
            this.textureManager.invalidateAllTextures();
            this.mMainRenderer = createMainRenderer();
        }

        private MainRenderer createMainRenderer() {
            MainRenderer renderer = new MainRenderer();
            if (this.isPreviewNotified) {
                renderer.onPreviewStateChanged(this.isPreview);
            }
            return renderer;
        }
    }

    public void onCreateApplication() {
        super.onCreateApplication();
        KoiPondApplication.onCreate(getApplicationContext());
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        config.getTouchEventsForLiveWallpaper = true;
        this.listener = new KoiPondApplicationInternal();
        initialize(this.listener, config);
    }

    public void onDestroy() {
        super.onDestroy();
        if (this.listener != null) {
            this.listener.dispose();
        }
    }
}
