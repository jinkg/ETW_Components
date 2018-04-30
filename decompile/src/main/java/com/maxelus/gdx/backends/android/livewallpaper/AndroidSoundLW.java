package com.maxelus.gdx.backends.android.livewallpaper;

import android.media.AudioManager;
import android.media.SoundPool;
import com.badlogic.gdx.audio.Sound;

final class AndroidSoundLW implements Sound {
    final AudioManager manager;
    final int soundId;
    final SoundPool soundPool;

    AndroidSoundLW(SoundPool pool, AudioManager manager, int soundId) {
        this.soundPool = pool;
        this.manager = manager;
        this.soundId = soundId;
    }

    public void dispose() {
        this.soundPool.unload(this.soundId);
    }

    public void stop() {
    }

    public long loop() {
        return 0;
    }

    public long loop(float volume) {
        return 0;
    }

    public void stop(long soundId) {
    }

    public void setLooping(long soundId, boolean looping) {
    }

    public void setPitch(long soundId, float pitch) {
    }

    public void setVolume(long soundId, float volume) {
    }

    public void setPan(long soundId, float pan, float volume) {
    }

    public void play() {
    }

    public void play(float volume) {
    }
}
