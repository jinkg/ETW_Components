package com.ist.lwp.koipond.school;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.ist.lwp.koipond.natives.NativeSchoolRenderer;
import com.ist.lwp.koipond.resource.TextureMananger;
import com.ist.lwp.koipond.waterpond.PreferencesManager;
import com.ist.lwp.koipond.waterpond.PreferencesManager.OnPreferenceChangedListener;
import com.ist.lwp.koipond.waterpond.PreferencesManager.PreferenceChangedType;

public final class SchoolRenderer implements OnPreferenceChangedListener {
    private static final String TEXTUREKEY = "FISHSCHOOL";
    public final int fishPopulation;
    private NativeSchoolRenderer nativeSchoolRenderer;
    public final float refFishLength;
    private Texture texture = TextureMananger.getInstance().getTexture(TEXTUREKEY);

    public SchoolRenderer() {
        PreferencesManager preferencesManager = PreferencesManager.getInstance();
        preferencesManager.addPreferenceChangedListener(this);
        this.fishPopulation = preferencesManager.fishPopulationInt;
        this.refFishLength = preferencesManager.fishSizeIndex;
        this.texture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        this.nativeSchoolRenderer = new NativeSchoolRenderer(this.fishPopulation, this.refFishLength);
        this.nativeSchoolRenderer.setFps(preferencesManager.fps);
        onThemeTextureUpdated();
    }

    public final void dispose() {
        PreferencesManager.getInstance().removePreferenceChangedListener(this);
    }

    public void onThemeTextureUpdated() {
        PreferencesManager preferencesManager = PreferencesManager.getInstance();
        this.nativeSchoolRenderer.setFogDensity(preferencesManager.fishFogDensity);
        this.nativeSchoolRenderer.setFogColor(preferencesManager.waterColor);
        this.nativeSchoolRenderer.setFishScalesColor(preferencesManager.fishScalesColor);
        this.nativeSchoolRenderer.setFishFinsColor(preferencesManager.fishFinsColor);
    }

    public void onPreferenceChanged(PreferenceChangedType type) {
        switch (type) {
            case POWERSAVER:
                this.nativeSchoolRenderer.setFps(PreferencesManager.getInstance().fps);
                return;
            default:
                return;
        }
    }
}
