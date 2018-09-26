package com.ist.lwp.koipond.waterpond;

import com.ist.lwp.koipond.waterpond.PreferencesManager.OnPreferenceChangedListener;
import com.ist.lwp.koipond.waterpond.PreferencesManager.PreferenceChangedType;

public class PowerSaver implements OnPreferenceChangedListener {
    private long deltaNanoTime;
    private long nanoTime;
    private long nanoTimeOneFrame;
    private long savedNanoTime;

    public PowerSaver() {
        PreferencesManager.getInstance().addPreferenceChangedListener(this);
        updateNanoTimeOneFrame();
        reset();
    }

    public void reset() {
        this.nanoTime = System.nanoTime();
        this.savedNanoTime = this.nanoTime;
    }

    private void updateNanoTimeOneFrame() {
        this.nanoTimeOneFrame = (long) (1.0E9d / ((double) PreferencesManager.getInstance().fps));
    }

    public void sleep() {
        this.nanoTime = System.nanoTime();
        this.deltaNanoTime = this.nanoTime - this.savedNanoTime;
        long sleepedNonoTime = this.nanoTimeOneFrame - this.deltaNanoTime;
        if (sleepedNonoTime > 0) {
            try {
                Thread.sleep(sleepedNonoTime / 1000000);
            } catch (InterruptedException e) {
            }
            this.nanoTime = System.nanoTime();
            this.deltaNanoTime = this.nanoTime - this.savedNanoTime;
        }
        this.savedNanoTime += this.deltaNanoTime;
    }

    public void onPreferenceChanged(PreferenceChangedType type) {
        switch (type) {
            case POWERSAVER:
                updateNanoTimeOneFrame();
                return;
            default:
                return;
        }
    }

    public void dispose() {
        PreferencesManager.getInstance().removePreferenceChangedListener(this);
    }
}
