package com.ist.lwp.koipond.natives;

public class NativeKoi3DRenderer {
    public NativeKoi3DRenderer() {
        NativeLibraryMethods.koi3drenderer_init();
    }

    public void addKoi(int id, String species, int size) {
        NativeLibraryMethods.koi3drenderer_addKoi(id, species, size);
    }

    public void updateKoi(int id, String species, int size) {
        NativeLibraryMethods.koi3drenderer_updateKoi(id, species, size);
    }

    public void removeKoi(int instanceId) {
        NativeLibraryMethods.koi3drenderer_removeKoi(instanceId);
    }
}
