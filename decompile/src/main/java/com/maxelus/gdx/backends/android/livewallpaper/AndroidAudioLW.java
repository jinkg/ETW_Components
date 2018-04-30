package com.maxelus.gdx.backends.android.livewallpaper;

import android.app.Service;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;

import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.audio.AudioDevice;
import com.badlogic.gdx.audio.AudioRecorder;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.GdxRuntimeException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class AndroidAudioLW implements Audio {
    private final AudioManager manager;
    protected final List<AndroidMusicLW> musics = new ArrayList();
    private SoundPool soundPool = new SoundPool(16, 3, 100);
    protected final List<Boolean> wasPlaying = new ArrayList();

    public AndroidAudioLW(Service context) {
        this.manager = (AudioManager) context.getSystemService("audio");
    }

    protected void pause() {
        this.wasPlaying.clear();
        for (AndroidMusicLW music : this.musics) {
            if (music.isPlaying()) {
                music.pause();
                this.wasPlaying.add(Boolean.valueOf(true));
            } else {
                this.wasPlaying.add(Boolean.valueOf(false));
            }
        }
    }

    protected void resume() {
        for (int i = 0; i < this.musics.size(); i++) {
            if (((Boolean) this.wasPlaying.get(i)).booleanValue()) {
                ((AndroidMusicLW) this.musics.get(i)).play();
            }
        }
    }

    public Music newMusic(FileHandle file) {
        AndroidFileHandleLW aHandle = (AndroidFileHandleLW) file;
        MediaPlayer mediaPlayer = new MediaPlayer();
        if (aHandle.type() == FileType.Internal) {
            try {
                AssetFileDescriptor descriptor = aHandle.assets.openFd(aHandle.path());
                mediaPlayer.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());
                descriptor.close();
                mediaPlayer.prepare();
                AndroidMusicLW music = new AndroidMusicLW(this, mediaPlayer);
                this.musics.add(music);
                return music;
            } catch (Exception ex) {
                throw new GdxRuntimeException("Error loading audio file: " + file + "\nNote: Internal audio files must be placed in the assets directory.", ex);
            }
        }
        try {
            mediaPlayer.setDataSource(aHandle.path());
            mediaPlayer.prepare();
            AndroidMusicLW music = new AndroidMusicLW(this, mediaPlayer);
            this.musics.add(music);
            return music;
        } catch (Exception ex2) {
            throw new GdxRuntimeException("Error loading audio file: " + file, ex2);
        }
    }

    public Sound newSound(FileHandle file) {
        AndroidFileHandleLW aHandle = (AndroidFileHandleLW) file;
        if (aHandle.type() == FileType.Internal) {
            try {
                AssetFileDescriptor descriptor = aHandle.assets.openFd(aHandle.path());
                AndroidSoundLW sound = new AndroidSoundLW(this.soundPool, this.manager, this.soundPool.load(descriptor, 1));
                descriptor.close();
                return sound;
            } catch (IOException ex) {
                throw new GdxRuntimeException("Error loading audio file: " + file + "\nNote: Internal audio files must be placed in the assets directory.", ex);
            }
        }
        try {
            return new AndroidSoundLW(this.soundPool, this.manager, this.soundPool.load(aHandle.path(), 1));
        } catch (Exception ex2) {
            throw new GdxRuntimeException("Error loading audio file: " + file, ex2);
        }
    }

    public void dispose() {
        this.soundPool.release();
    }

    public AudioDevice newAudioDevice(int arg0, boolean arg1) {
        return null;
    }

    @Override
    public AudioRecorder newAudioRecoder(int samplingRate, boolean isMono) {
        return null;
    }

    public AudioRecorder newAudioRecorder(int samplingRate, boolean isMono) {
        return null;
    }
}
