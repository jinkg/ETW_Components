package com.maxelus.gdx.backends.android.livewallpaper;

import android.media.AudioTrack;
import com.badlogic.gdx.audio.AudioDevice;

class AndroidAudioDeviceLW implements AudioDevice {
    private short[] buffer = new short[1024];
    private final boolean isMono;
    private final AudioTrack track;

    AndroidAudioDeviceLW(boolean isMono) {
        int i;
        int i2;
        this.isMono = isMono;
        if (isMono) {
            i = 2;
        } else {
            i = 3;
        }
        int minSize = AudioTrack.getMinBufferSize(44100, i, 2);
        if (isMono) {
            i2 = 2;
        } else {
            i2 = 3;
        }
        this.track = new AudioTrack(3, 44100, i2, 2, minSize, 1);
        this.track.play();
    }

    public void dispose() {
        this.track.stop();
        this.track.release();
    }

    public boolean isMono() {
        return this.isMono;
    }

    public void writeSamples(short[] samples, int offset, int numSamples) {
        int writtenSamples = this.track.write(samples, offset, numSamples);
        while (writtenSamples != numSamples) {
            writtenSamples += this.track.write(samples, offset + writtenSamples, numSamples - writtenSamples);
        }
    }

    public void writeSamples(float[] samples, int offset, int numSamples) {
        if (this.buffer.length < samples.length) {
            this.buffer = new short[samples.length];
        }
        int bound = offset + numSamples;
        int i = offset;
        int j = 0;
        while (i < bound) {
            float fValue = samples[i];
            if (fValue > 1.0f) {
                fValue = 1.0f;
            }
            if (fValue < -1.0f) {
                fValue = -1.0f;
            }
            this.buffer[j] = (short) ((int) (32767.0f * fValue));
            i++;
            j++;
        }
        int writtenSamples = this.track.write(this.buffer, 0, numSamples);
        while (writtenSamples != numSamples) {
            writtenSamples += this.track.write(this.buffer, writtenSamples, numSamples - writtenSamples);
        }
    }

    public int getLatency() {
        return 0;
    }

    public void setVolume(float volume) {
    }
}
