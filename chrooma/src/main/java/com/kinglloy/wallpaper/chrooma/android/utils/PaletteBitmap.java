package com.kinglloy.wallpaper.chrooma.android.utils;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import com.badlogic.gdx.graphics.Color;

public class PaletteBitmap {
    private int[] colors;
    private boolean deletable;

    public PaletteBitmap(int[] palettes, boolean deletable) {
        this.colors = palettes;
        this.deletable = deletable;
    }

    public Bitmap getBitmap(int x, int y) {
        Bitmap bmp = Bitmap.createBitmap(x, y, Config.ARGB_8888);
        Canvas canvas = new Canvas(bmp);
        Paint p = new Paint(1);
        for (int i = 0; i < this.colors.length; i++) {
            float w = (float) x;
            p.setColor(this.colors[i]);
            canvas.drawRect((w / ((float) this.colors.length)) * ((float) i), 0.0f, (w / ((float) this.colors.length)) * ((float) (i + 1)), w, p);
        }
        return bmp;
    }

    public static int[] colorToInt(Color[] colors) {
        int[] temp = new int[colors.length];
        for (int i = 0; i < colors.length; i++) {
            temp[i] = android.graphics.Color.argb(255, (int) (colors[i].r * 255.0f), (int) (colors[i].g * 255.0f), (int) (colors[i].b * 255.0f));
        }
        return temp;
    }

    public boolean isDeletable() {
        return this.deletable;
    }
}
