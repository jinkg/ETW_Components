package com.maxelus.gdx.backends.android.livewallpaper;

import android.view.MotionEvent;

public interface AndroidTouchHandlerLW {
    void onDrop(int i, int i2, AndroidInputLW androidInputLW);

    void onTap(int i, int i2, AndroidInputLW androidInputLW);

    void onTouch(MotionEvent motionEvent, AndroidInputLW androidInputLW);
}
