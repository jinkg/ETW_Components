package com.ist.lwp.koipond;

import android.content.Context;
import android.content.res.AssetManager;

public class KoiPondApplication {
  private static AssetManager assetManager;
  private static Context sContext;

  public static void onCreate(Context context) {
    sContext = context;
    assetManager = context.getAssets();
  }

  public static Context getContext() {
    return sContext;
  }

  public static AssetManager getAssetManager() {
    return assetManager;
  }
}
