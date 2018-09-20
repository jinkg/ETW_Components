package com.kinglloy.wallpaper.chrooma;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.utils.ScreenUtils;
import java.nio.ByteBuffer;

public class ScreenshotFactory {
    private static int counter = 1;

    public static String saveScreenshot() {
        FileHandle fh;
        do {
            Files files = Gdx.files;
            StringBuilder append = new StringBuilder().append("Chrooma/screenshot");
            int i = counter;
            counter = i + 1;
            fh = files.getFileHandle(append.append(i).append(".png").toString(), FileType.External);
        } while (fh.exists());
        try {
            Pixmap pixmap = getScreenshot(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);
            PixmapIO.writePNG(fh, pixmap);
            pixmap.dispose();
        } catch (Exception e) {
        }
        Gdx.app.debug("screen", "" + fh.path());
        return Gdx.files.getExternalStoragePath() + fh.path();
    }

    private static Pixmap getScreenshot(int x, int y, int w, int h, boolean yDown) {
        Pixmap pixmap = ScreenUtils.getFrameBufferPixmap(x, y, w, h);
        if (yDown) {
            ByteBuffer pixels = pixmap.getPixels();
            byte[] lines = new byte[((w * h) * 4)];
            int numBytesPerLine = w * 4;
            for (int i = 0; i < h; i++) {
                pixels.position(((h - i) - 1) * numBytesPerLine);
                pixels.get(lines, i * numBytesPerLine, numBytesPerLine);
            }
            pixels.clear();
            pixels.put(lines);
            pixels.clear();
        }
        return pixmap;
    }
}
