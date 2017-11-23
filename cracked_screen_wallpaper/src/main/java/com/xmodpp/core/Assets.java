package com.xmodpp.core;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import java.io.IOException;

public class Assets {
    private static AssetManager f285a;

    private static native void Init(AssetManager assetManager);

    public static AssetManager m465a() {
        return f285a;
    }

    public static Bitmap m466a(String str) {
        try {
            return BitmapFactory.decodeStream(f285a.open(str));
        } catch (IOException e) {
            return null;
        }
    }

    public static void m467a(Context context) {
        f285a = context.getAssets();
        Init(f285a);
    }
}
