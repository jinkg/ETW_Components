package com.xmodpp.gles;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.opengl.GLUtils;
import android.util.Log;
import java.io.File;

public class TextureUtils {
    public static Context f298a;

    static float dno_loadTexture(String str, int i, int i2) {
        int i3 = 1;
        Log.d("XMOD++", "Load Texture " + str + " into " + i + "x" + i2);
        try {
            Options options = new Options();
            options.inJustDecodeBounds = true;
            File file = new File(f298a.getFilesDir(), "background_cache");
            BitmapFactory.decodeFile(file.getPath(), options);
            Log.d("XMOD++", "Load Picture: " + options.outWidth + " " + options.outHeight);
            while (options.outWidth / (i3 + 1) > i && options.outHeight / (i3 + 1) > i2) {
                i3++;
            }
            Log.d("XMOD++", "Downscaling by " + i3);
            Options options2 = new Options();
            options2.inSampleSize = i3;
            options2.inPreferredConfig = Config.RGB_565;
            Bitmap decodeFile = BitmapFactory.decodeFile(file.getPath(), options2);
            Log.d("XMOD++", "pre-scaled bitmap dimensions: " + decodeFile.getWidth() + "x" + decodeFile.getHeight());
            while (i > 64 && i / 2 > decodeFile.getWidth()) {
                i /= 2;
            }
            while (i2 > 64 && i2 / 2 > decodeFile.getHeight()) {
                i2 /= 2;
            }
            float width = ((float) i) / ((float) decodeFile.getWidth());
            float height = ((float) i2) / ((float) decodeFile.getHeight());
            Matrix matrix = new Matrix();
            matrix.postScale(width, height);
            Bitmap createBitmap = Bitmap.createBitmap(i, i2, decodeFile.getConfig());
            Canvas canvas = new Canvas(createBitmap);
            Paint paint = new Paint();
            paint.setFilterBitmap(true);
            canvas.drawBitmap(decodeFile, matrix, paint);
            decodeFile.recycle();
            Log.d("XMOD++", "final bitmap dimensions: " + createBitmap.getWidth() + "x" + createBitmap.getHeight());
            GLUtils.texImage2D(3553, 0, createBitmap, 0);
            float f = ((float) options.outWidth) / ((float) options.outHeight);
            Log.d("XMOD++", "ratio " + f);
            createBitmap.recycle();
            return f;
        } catch (Exception e) {
            return -1.0f;
        }
    }
}
