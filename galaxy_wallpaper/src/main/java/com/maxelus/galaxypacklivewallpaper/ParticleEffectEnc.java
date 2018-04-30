package com.maxelus.galaxypacklivewallpaper;

import com.mybadlogic.gdx.files.FileHandle;
import com.mybadlogic.gdx.graphics.Texture;
import com.mybadlogic.gdx.graphics.g2d.ParticleEffect;
import com.mybadlogic.gdx.graphics.g2d.ParticleEmitter;
import com.mybadlogic.gdx.graphics.g2d.Sprite;
import com.mybadlogic.gdx.utils.Array;
import com.mybadlogic.gdx.utils.GdxRuntimeException;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;

public class ParticleEffectEnc extends ParticleEffect {
    public void loadEmitters(String path) {
        IOException ex;
        Throwable th;
        String text = "";
        try {
            text = EncryptFile.load(ParticleEffectEnc.class.getResourceAsStream(path), "", Boolean.TRUE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        StringReader sr = new StringReader(text);
        BufferedReader reader = null;
        getEmitters().clear();
        try {
            BufferedReader reader2 = new BufferedReader(sr, 512);
            do {
                try {
                    ParticleEmitter emitter = new ParticleEmitter(reader2);
                    reader2.readLine();
                    emitter.setImagePath(reader2.readLine());
                    getEmitters().add(emitter);
                    if (reader2.readLine() == null) {
                        break;
                    }
                } catch (IOException e2) {
                    ex = e2;
                    reader = reader2;
                } catch (Throwable th2) {
                    th = th2;
                    reader = reader2;
                }
            } while (reader2.readLine() != null);
            if (reader2 != null) {
                try {
                    reader2.close();
                } catch (IOException e3) {
                }
            }
        } catch (IOException e4) {
            ex = e4;
            try {
                throw new GdxRuntimeException("Error loading effect: ... ", ex);
            } catch (Throwable th3) {
                th = th3;
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e5) {
                    }
                }
            }
        }
    }

    public void loadEmittersImages(FileHandle imagesDir, String... images) {
        Array<ParticleEmitter> emitters = getEmitters();
        for (int i = 0; i < images.length; i++) {
            try {
                if (images[i] != "") {
                    String imagePath = images[i];
                    ParticleEmitter emitter = (ParticleEmitter) emitters.get(i);
                    String imageName = new File(imagePath.replace('\\', '/')).getName();
                    Sprite sprite = emitter.getSprite();
                    if (sprite != null) {
                        Texture texture = sprite.getTexture();
                        if (texture != null) {
                            texture.dispose();
                        }
                    }
                    emitter.setImagePath(imagePath);
                    emitter.setSprite(new Sprite(loadTexture(imagesDir.child(imageName))));
                }
            } catch (Exception e) {
            }
        }
    }
}
