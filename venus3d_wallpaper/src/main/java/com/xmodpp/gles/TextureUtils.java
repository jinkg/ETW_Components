package com.xmodpp.gles;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.opengl.ETC1;
import android.opengl.ETC1Util;
import android.opengl.ETC1Util.ETC1Texture;
import android.util.Log;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class TextureUtils {
    static Bitmap LoadScaledBitmap(InputStream inputStream, int i, int i2, int i3) {
        int height;
        try {
            int i4;
            Options options = new Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(inputStream, null, options);
            if (i3 == 90 || i3 == 270) {
                i4 = options.outWidth;
                options.outWidth = options.outHeight;
                options.outHeight = i4;
            }
            options = new Options();
            options.inSampleSize = 1;
            while ((options.outWidth / options.inSampleSize) / 2 >= i && (options.outHeight / options.inSampleSize) / 2 >= i2) {
                options.inSampleSize *= 2;
            }
            Bitmap decodeStream = BitmapFactory.decodeStream(inputStream, null, options);
            i4 = (i3 == 0 || i3 == 180) ? decodeStream.getWidth() : decodeStream.getHeight();
            height = (i3 == 0 || i3 == 180) ? decodeStream.getHeight() : decodeStream.getWidth();
            Matrix matrix = new Matrix();
            matrix.postTranslate(((float) (-decodeStream.getWidth())) / 2.0f, ((float) (-decodeStream.getHeight())) / 2.0f);
            matrix.postRotate((float) i3);
            matrix.postTranslate(((float) i4) / 2.0f, ((float) height) / 2.0f);
            matrix.postScale(((float) i) / ((float) i4), ((float) i2) / ((float) height));
            Bitmap createBitmap = Bitmap.createBitmap(i, i2, Config.RGB_565);
            Canvas canvas = new Canvas(createBitmap);
            Paint paint = new Paint();
            paint.setFilterBitmap(true);
            canvas.drawBitmap(decodeStream, matrix, paint);
            decodeStream.recycle();
            return createBitmap;
        } catch (Exception e) {
            for (StackTraceElement stackTraceElement : e.getStackTrace()) {
            }
            return null;
        }
    }

    public static ETC1Texture jni_ToETC1(Bitmap bitmap) throws Exception {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int rowBytes = bitmap.getRowBytes();
        if (bitmap.getConfig() != Config.RGB_565) {
            bitmap.recycle();
            throw new Exception("ToETC1: Wrong Bitmap.Config (need RGB_565)");
        }
        Buffer order = ByteBuffer.allocateDirect(rowBytes * height).order(ByteOrder.nativeOrder());
        bitmap.copyPixelsToBuffer(order);
        order.position(0);
        bitmap.recycle();
        ByteBuffer order2 = ByteBuffer.allocateDirect(ETC1.getEncodedDataSize(width, height)).order(ByteOrder.nativeOrder());
        ETC1.encodeImage(order, width, height, 2, rowBytes, order2);
        return new ETC1Texture(width, height, order2);
    }

    public static boolean jni_ToETC1(InputStream inputStream, OutputStream outputStream, int i) {
        try {
            Log.d("XMOD++", "ETC1 Conversion...");
            Options options = new Options();
            options.inSampleSize = i + 1;
            options.inScaled = false;
            options.inPreferredConfig = Config.RGB_565;
            ETC1Util.writeTexture(jni_ToETC1(BitmapFactory.decodeStream(inputStream, null, options)), outputStream);
            outputStream.flush();
            return true;
        } catch (Exception e) {
            Log.d("XMOD++", "ETC1 Conversion failed: " + e.toString());
            return false;
        }
    }
}
