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
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class TextureUtils {
    public static boolean ToETC1(InputStream input, OutputStream output, int downscale) {
        try {
            Options options = new Options();
            options.inSampleSize = downscale;
            options.inScaled = false;
            options.inPreferredConfig = Config.RGB_565;
            ETC1Util.writeTexture(ToETC1(BitmapFactory.decodeStream(input, null, options)), output);
            output.flush();
            return true;
        } catch (Exception e) {
            Log.d("XMOD++", "ETC1 Conversion failed: " + e.toString());
            return false;
        }
    }

    public static ETC1Texture ToETC1(Bitmap bitmap) throws Exception {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int rowBytes = bitmap.getRowBytes();
        if (bitmap.getConfig() != Config.RGB_565) {
            bitmap.recycle();
            throw new Exception("ToETC1: Wrong Bitmap.Config (need RGB_565)");
        }
        ByteBuffer uncomressedBuffer = ByteBuffer.allocateDirect(rowBytes * height).order(ByteOrder.nativeOrder());
        bitmap.copyPixelsToBuffer(uncomressedBuffer);
        uncomressedBuffer.position(0);
        bitmap.recycle();
        ByteBuffer compressedBuffer = ByteBuffer.allocateDirect(ETC1.getEncodedDataSize(width, height)).order(ByteOrder.nativeOrder());
        ETC1.encodeImage(uncomressedBuffer, width, height, 2, rowBytes, compressedBuffer);
        return new ETC1Texture(width, height, compressedBuffer);
    }

    static Bitmap LoadScaledBitmap(InputStream input, int width, int height, int rotate) {
        try {
            Options options = new Options();
            options.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(input, null, options);
            if (rotate == 90 || rotate == 270) {
                int temp = options.outWidth;
                options.outWidth = options.outHeight;
                options.outHeight = temp;
            }
            options = new Options();
            options.inSampleSize = 1;
            while ((options.outWidth / options.inSampleSize) / 2 >= width && (options.outHeight / options.inSampleSize) / 2 >= height) {
                options.inSampleSize *= 2;
            }
            Bitmap bitmap = BitmapFactory.decodeStream(input, null, options);
            int rotatedWidth = (rotate == 0 || rotate == 180) ? bitmap.getWidth() : bitmap.getHeight();
            int rotatedHeight = (rotate == 0 || rotate == 180) ? bitmap.getHeight() : bitmap.getWidth();
            Matrix matrix = new Matrix();
            matrix.postTranslate(((float) (-bitmap.getWidth())) / 2.0f, ((float) (-bitmap.getHeight())) / 2.0f);
            matrix.postRotate((float) rotate);
            matrix.postTranslate(((float) rotatedWidth) / 2.0f, ((float) rotatedHeight) / 2.0f);
            matrix.postScale(((float) width) / ((float) rotatedWidth), ((float) height) / ((float) rotatedHeight));
            Bitmap scaledAndRotatedBitmap = Bitmap.createBitmap(width, height, Config.RGB_565);
            Canvas canvas = new Canvas(scaledAndRotatedBitmap);
            Paint paint = new Paint();
            paint.setFilterBitmap(true);
            canvas.drawBitmap(bitmap, matrix, paint);
            bitmap.recycle();
            return scaledAndRotatedBitmap;
        } catch (Exception e) {
            Log.e("XMOD++", e.getMessage());
            for (StackTraceElement ste : e.getStackTrace()) {
                Log.e("XMOD++", ste.getFileName() + ":" + ste.getLineNumber() + " " + ste.getMethodName());
            }
            return null;
        }
    }
}
