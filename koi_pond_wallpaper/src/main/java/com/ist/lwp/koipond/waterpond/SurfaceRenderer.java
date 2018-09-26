package com.ist.lwp.koipond.waterpond;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.Texture.TextureWrap;
import com.ist.lwp.koipond.natives.NativeSurfaceRenderer;
import com.ist.lwp.koipond.resource.TextureMananger;
import com.ist.lwp.koipond.waterpond.PreferencesManager.OnPreferenceChangedListener;
import com.ist.lwp.koipond.waterpond.PreferencesManager.PreferenceChangedType;

public final class SurfaceRenderer implements OnPreferenceChangedListener {
    private MainRenderer mMainRenderer;
    private final NativeSurfaceRenderer nativeSurfaceRenderer = new NativeSurfaceRenderer();
    private Texture texture;

    public SurfaceRenderer(MainRenderer mainRenderer) {
        this.mMainRenderer = mainRenderer;
        PreferencesManager.getInstance().addPreferenceChangedListener(this);
        onThemeTextureUpdated();
    }

    public final void render() {
        this.mMainRenderer.sceneFBORenderer.getColorBufferTexture().bind(0);
        this.nativeSurfaceRenderer.render();
    }

    public void onThemeTextureUpdated() {
        PreferencesManager preferencesManager = PreferencesManager.getInstance();
        this.texture = TextureMananger.getInstance().getTexture("ENV");
        this.texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        this.texture.setWrap(TextureWrap.MirroredRepeat, TextureWrap.MirroredRepeat);
        this.nativeSurfaceRenderer.setFogDensity(preferencesManager.fogDensity);
        this.nativeSurfaceRenderer.setEnvReflectionsPercent(preferencesManager.envReflectionsPercent);
        this.nativeSurfaceRenderer.setFogColor(preferencesManager.waterColor);
        this.nativeSurfaceRenderer.setLightAmbient(preferencesManager.lightInfo.lightAmbient);
        this.nativeSurfaceRenderer.setLightDiffuse(preferencesManager.lightInfo.lightDiffuse);
        this.nativeSurfaceRenderer.setEsToLightDir(preferencesManager.lightInfo.esToLightDir);
    }

    public void dispose() {
        PreferencesManager.getInstance().removePreferenceChangedListener(this);
    }

    public void onPreferenceChanged(PreferenceChangedType type) {
        switch (type) {
            case SHOWREFLECTION:
                this.nativeSurfaceRenderer.setEnvReflectionsPercent(PreferencesManager.getInstance().envReflectionsPercent);
                return;
            default:
                return;
        }
    }
}
