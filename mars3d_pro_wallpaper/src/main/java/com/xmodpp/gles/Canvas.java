package com.xmodpp.gles;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Paint;
import android.graphics.Paint.Join;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Rect;
import java.io.InputStream;

public class Canvas {
    public Bitmap jni_bitmap = null;
    public android.graphics.Canvas jni_canvas = null;
    public Paint jni_paint = new Paint();

    public Canvas() {
        this.jni_paint.setFlags(this.jni_paint.getFlags() | 1);
        this.jni_paint.setStrokeWidth(0.0f);
        this.jni_paint.setStrokeJoin(Join.ROUND);
    }

    public void jni_init(int width, int height, boolean monochrome) {
        Config config;
        if (this.jni_bitmap != null) {
            this.jni_bitmap.recycle();
        }
        if (monochrome) {
            config = Config.ALPHA_8;
        } else {
            config = Config.ARGB_8888;
        }
        this.jni_bitmap = Bitmap.createBitmap(width, height, config);
        this.jni_canvas = new android.graphics.Canvas(this.jni_bitmap);
    }

    public boolean jni_init(InputStream stream, int downscale) {
        if (this.jni_bitmap != null) {
            this.jni_bitmap.recycle();
        }
        Options options = new Options();
        options.inMutable = true;
        options.inScaled = false;
        options.inDither = false;
        options.inSampleSize = downscale;
        this.jni_bitmap = BitmapFactory.decodeStream(stream, null, options);
        this.jni_canvas = new android.graphics.Canvas(this.jni_bitmap);
        if (this.jni_bitmap != null) {
            return true;
        }
        return false;
    }

    public void jni_destroy() {
        if (this.jni_bitmap != null) {
            this.jni_bitmap.recycle();
        }
    }

    public void jni_setColor(int color) {
        this.jni_paint.setColor(color);
    }

    public void jni_setTextSize(float size) {
        this.jni_paint.setTextSize(size);
    }

    public void jni_setLineWidth(float size) {
        this.jni_paint.setStrokeWidth(size);
    }

    public float jni_getFontSpacing() {
        return this.jni_paint.getFontSpacing();
    }

    public void jni_drawGlyph(int codePoint, int x, int y) {
        char[] chars = Character.toChars(codePoint);
        Path path = new Path();
        this.jni_paint.getTextPath(chars, 0, chars.length, (float) x, (float) y, path);
        this.jni_paint.setStyle(Style.STROKE);
        this.jni_canvas.drawPath(path, this.jni_paint);
        this.jni_paint.setStyle(Style.FILL);
        this.jni_canvas.drawText(chars, 0, chars.length, (float) x, (float) y, this.jni_paint);
    }

    public Rect jni_getGlyphBounds(int codePoint) {
        Rect glyphBounds = new Rect();
        char[] chars = Character.toChars(codePoint);
        this.jni_paint.getTextBounds(chars, 0, chars.length, glyphBounds);
        return glyphBounds;
    }

    public int jni_getGlyphKerning(int codePoint1, int codePoint2) {
        char[] chars = new char[4];
        int l1 = Character.toChars(codePoint2, chars, 0);
        float[] widths = new float[4];
        this.jni_paint.getTextWidths(chars, 0, l1 + Character.toChars(codePoint1, chars, l1), widths);
        return (int) widths[0];
    }
}
