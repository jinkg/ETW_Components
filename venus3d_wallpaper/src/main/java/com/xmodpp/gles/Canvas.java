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

    public void jni_destroy() {
        if (this.jni_bitmap != null) {
            this.jni_bitmap.recycle();
        }
    }

    public void jni_drawGlyph(int i, int i2, int i3) {
        char[] toChars = Character.toChars(i);
        Path path = new Path();
        this.jni_paint.getTextPath(toChars, 0, toChars.length, (float) i2, (float) i3, path);
        this.jni_paint.setStyle(Style.STROKE);
        this.jni_canvas.drawPath(path, this.jni_paint);
        this.jni_paint.setStyle(Style.FILL);
        this.jni_canvas.drawText(toChars, 0, toChars.length, (float) i2, (float) i3, this.jni_paint);
    }

    public float jni_getFontSpacing() {
        return this.jni_paint.getFontSpacing();
    }

    public Rect jni_getGlyphBounds(int i) {
        Rect rect = new Rect();
        char[] toChars = Character.toChars(i);
        this.jni_paint.getTextBounds(toChars, 0, toChars.length, rect);
        return rect;
    }

    public int jni_getGlyphKerning(int i, int i2) {
        char[] cArr = new char[4];
        int toChars = Character.toChars(i2, cArr, 0);
        float[] fArr = new float[4];
        this.jni_paint.getTextWidths(cArr, 0, toChars + Character.toChars(i, cArr, toChars), fArr);
        return (int) fArr[0];
    }

    public void jni_init(int i, int i2, boolean z) {
        if (this.jni_bitmap != null) {
            this.jni_bitmap.recycle();
        }
        this.jni_bitmap = Bitmap.createBitmap(i, i2, z ? Config.ALPHA_8 : Config.ARGB_8888);
        this.jni_canvas = new android.graphics.Canvas(this.jni_bitmap);
    }

    public boolean jni_init(InputStream inputStream, int i) {
        if (this.jni_bitmap != null) {
            this.jni_bitmap.recycle();
        }
        Options options = new Options();
        options.inMutable = true;
        options.inScaled = false;
        options.inDither = false;
        options.inSampleSize = i;
        this.jni_bitmap = BitmapFactory.decodeStream(inputStream, null, options);
        this.jni_canvas = new android.graphics.Canvas(this.jni_bitmap);
        return this.jni_bitmap != null;
    }

    public boolean jni_init(InputStream inputStream, int i, int i2) {
        Options options = new Options();
        options.inMutable = true;
        options.inScaled = false;
        options.inDither = false;
        options.inSampleSize = 1;
        Bitmap decodeStream = BitmapFactory.decodeStream(inputStream, null, options);
        int max = Math.max(decodeStream.getWidth(), decodeStream.getHeight());
        this.jni_bitmap = Bitmap.createScaledBitmap(decodeStream, (decodeStream.getWidth() * 1024) / max, (decodeStream.getHeight() * 1024) / max, true);
        this.jni_canvas = new android.graphics.Canvas(this.jni_bitmap);
        return this.jni_bitmap != null;
    }

    public void jni_setColor(int i) {
        this.jni_paint.setColor(i);
    }

    public void jni_setLineWidth(float f) {
        this.jni_paint.setStrokeWidth(f);
    }

    public void jni_setTextSize(float f) {
        this.jni_paint.setTextSize(f);
    }
}
