package com.xmodpp.gles;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.opengl.GLUtils;

public class Canvas extends android.graphics.Canvas {
    Bitmap jni_bitmap = null;

    public void jni_drawGlyph(int i, int i2, int i3, Paint paint) {
        char[] toChars = Character.toChars(i);
        drawText(toChars, 0, toChars.length, (float) i2, (float) i3, paint);
    }

    public boolean jni_isMonochrome() {
        return this.jni_bitmap.getConfig() == Config.ALPHA_8;
    }

    public void jni_resize(int i, int i2, boolean z) {
        if (this.jni_bitmap != null) {
            this.jni_bitmap.recycle();
        }
        this.jni_bitmap = Bitmap.createBitmap(i, i2, z ? Config.ALPHA_8 : Config.ARGB_8888);
        setBitmap(this.jni_bitmap);
    }

    public void jni_upload(int i, int i2) {
        GLUtils.texSubImage2D(3553, 0, i, i2, this.jni_bitmap);
    }
}
