package com.xmodpp.core;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import java.io.IOException;

public class Assets {
    private static AssetManager assetManager;

    private static native void Init(AssetManager assetManager);

    public static void Init(Context context) {
        assetManager = context.getAssets();
        Init(assetManager);
    }

    public static AssetManager getAssetManager() {
        return assetManager;
    }

    public static Bitmap getBitmapFromAsset(String assetName) {
        try {
            return BitmapFactory.decodeStream(assetManager.open(assetName));
        } catch (IOException e) {
            return null;
        }
    }
}
