package com.ist.lwp.koipond.natives;

import com.ist.lwp.koipond.KoiPondApplication;

public class NativeTextureManager {
    public NativeTextureManager() {
        NativeLibraryMethods.texturemanager_init(KoiPondApplication.getAssetManager(), KoiPondApplication.getContext());
    }

    public void putTexture(String key, int handle, int width, int height) {
        NativeLibraryMethods.texturemanager_putTexture(key, handle, width, height);
    }

    public void removeTexture(String key) {
        NativeLibraryMethods.texturemanager_removeTexture(key);
    }
}
