package com.ist.lwp.koipond.plants;

import com.ist.lwp.koipond.natives.NativePlantsRenderer;
import com.ist.lwp.koipond.waterpond.PreferencesManager;

public class PlantsRenderer {
    private NativePlantsRenderer nativePlantsRenderer = new NativePlantsRenderer();

    public PlantsRenderer() {
        onThemeTextureUpdated();
    }

    public void onThemeTextureUpdated() {
        this.nativePlantsRenderer.setPlantsSizeScale(PreferencesManager.getInstance().plantSizeScale);
    }
}
