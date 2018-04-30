package com.maxelus.gdx.backends.android.livewallpaper;

import android.view.MotionEvent;

public class AndroidSingleTouchHandlerLW implements AndroidTouchHandlerLW {
    private static long timeStamp;
    private static int f104x;
    private static int f105y;

    public void onTap(int pX, int pY, AndroidInputLW input) {
    }

    public void onDrop(int pX, int pY, AndroidInputLW input) {
    }

    public void onTouch(MotionEvent event, AndroidInputLW input) {
        f104x = (int) event.getX();
        f105y = (int) event.getY();
        input.touchX[0] = f104x;
        input.touchY[0] = f105y;
        if (event.getAction() == 0) {
            postTouchEvent(input, 0, f104x, f105y, 0);
            input.touched[0] = true;
        } else if (event.getAction() == 2) {
            postTouchEvent(input, 2, f104x, f105y, 0);
            input.touched[0] = true;
        } else if (event.getAction() == 1) {
            postTouchEvent(input, 1, f104x, f105y, 0);
            input.touched[0] = false;
        } else if (event.getAction() == 3) {
            postTouchEvent(input, 1, f104x, f105y, 0);
            input.touched[0] = false;
        }
    }

    private void postTouchEvent(AndroidInputLW input, int type, int x, int y, int pointer) {
        long timeStamp = System.nanoTime();
        synchronized (input) {
            AndroidInputLW.TouchEvent event = input.usedTouchEvents.obtain();
            event.timeStamp = timeStamp;
            event.pointer = 0;
            event.f87x = x;
            event.f88y = y;
            event.type = type;
            input.touchEvents.add(event);
        }
    }
}
