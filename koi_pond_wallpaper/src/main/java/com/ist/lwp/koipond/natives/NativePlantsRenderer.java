package com.ist.lwp.koipond.natives;

public class NativePlantsRenderer {
    public NativePlantsRenderer() {
        NativeLibraryMethods.plantsrenderer_init();
    }

    public void setPlantsSizeScale(float sizeScale) {
        NativeLibraryMethods.plantsrenderer_setPlantsSizeScale(sizeScale);
    }
}
