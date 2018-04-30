package com.maxelus.gdx.backends.android.livewallpaper;

import android.media.MediaPlayer;
import com.badlogic.gdx.audio.Music;
import java.io.IOException;

public class AndroidMusicLW implements Music {
    private final AndroidAudioLW audio;
    private boolean isPrepared = true;
    private final MediaPlayer player;

    AndroidMusicLW(AndroidAudioLW audio, MediaPlayer player) {
        this.audio = audio;
        this.player = player;
    }

    public void dispose() {
        if (this.player.isPlaying()) {
            this.player.stop();
        }
        this.player.release();
        this.audio.musics.remove(this);
    }

    public boolean isLooping() {
        return this.player.isLooping();
    }

    public boolean isPlaying() {
        return this.player.isPlaying();
    }

    public void pause() {
        if (this.player.isPlaying()) {
            this.player.pause();
        }
    }

    public void play() {
        if (!this.player.isPlaying()) {
            try {
                if (!this.isPrepared) {
                    this.player.prepare();
                }
                this.player.start();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
    }

    public void setLooping(boolean isLooping) {
        this.player.setLooping(isLooping);
    }

    public void setVolume(float volume) {
        this.player.setVolume(volume, volume);
    }

    public void stop() {
        this.player.stop();
        this.isPrepared = false;
    }

    public float getPosition() {
        return 0.0f;
    }
}
