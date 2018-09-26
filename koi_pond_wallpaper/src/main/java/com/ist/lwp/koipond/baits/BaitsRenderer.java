package com.ist.lwp.koipond.baits;

import com.ist.lwp.koipond.natives.NativeBaitsRenderer;

public class BaitsRenderer {
    private NativeBaitsRenderer nativeBaitsRenderer = new NativeBaitsRenderer();

    public void addBait(int screenX, int screenY, int pointer) {
        this.nativeBaitsRenderer.addBait(screenX, screenY, pointer);
    }
}
