package com.ist.lwp.koipond.natives;

public class NativeSceneFBORenderer {
    public NativeSceneFBORenderer() {
        NativeLibraryMethods.scenefborenderer_init();
    }

    public void render(float deltaTime) {
        NativeLibraryMethods.scenefborenderer_render(deltaTime);
    }

    public void setSchoolVisible(boolean visible) {
        NativeLibraryMethods.scenefborenderer_setSchoolVisible(visible);
    }

    public void setPlantsVisible(boolean visible) {
        NativeLibraryMethods.scenefborenderer_setPlantsVisible(visible);
    }
}
