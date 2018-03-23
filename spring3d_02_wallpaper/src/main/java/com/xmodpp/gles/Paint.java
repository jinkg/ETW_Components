package com.xmodpp.gles;

import android.graphics.Rect;

public class Paint extends android.graphics.Paint {
    public Paint() {
        setFlags(getFlags() | 1);
    }

    public Rect jni_getGlyphBounds(int i) {
        Rect rect = new Rect();
        char[] toChars = Character.toChars(i);
        getTextBounds(toChars, 0, toChars.length, rect);
        return rect;
    }

    public int jni_getGlyphKerning(int i, int i2) {
        char[] cArr = new char[4];
        int toChars = Character.toChars(i2, cArr, 0);
        float[] fArr = new float[4];
        getTextWidths(cArr, 0, toChars + Character.toChars(i, cArr, toChars), fArr);
        return (int) fArr[0];
    }
}
