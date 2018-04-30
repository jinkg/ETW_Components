package com.maxelus.gdx.backends.android.livewallpaper;

import com.badlogic.gdx.InputProcessor;

public interface InputProcessorLW extends InputProcessor {
    void touchDrop(int i, int i2);

    void touchTap(int i, int i2);
}
