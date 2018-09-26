package com.ist.lwp.koipond.resource;

import android.util.Log;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.utils.Disposable;
import com.ist.lwp.koipond.KoiPondApplication;
import com.ist.lwp.koipond.models.KoiModel;
import com.ist.lwp.koipond.models.PondsManager;
import com.ist.lwp.koipond.natives.NativeTextureManager;
import com.ist.lwp.koipond.resource.TexFactory.TextureUrl;
import com.ist.lwp.koipond.resource.TexFactory.UrlType;
import com.ist.lwp.koipond.utils.GLLimit;
import com.ist.lwp.koipond.waterpond.PreferencesManager;
import com.ist.lwp.koipond.waterpond.PreferencesManager.OnPreferenceChangedListener;
import com.ist.lwp.koipond.waterpond.PreferencesManager.PreferenceChangedType;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class TextureMananger implements Disposable, OnPreferenceChangedListener {
  public static final String CUSTOMBGNAME = "koipond_custom_bg.png";
  private static TextureMananger sInstance = null;
  private final HashSet<String> filteredTexs = new HashSet<String>() {
    private static final long serialVersionUID = 1;

    {
      add(KoiModel.SPECIES_KOIB5);
    }
  };
  private boolean isGCSeries;
  private List<OnThemeTextureUpdated> listeners;
  private NativeTextureManager nativeTextureManager;
  private Map<String, TextureUrl> textureFileUrls = new HashMap<String, TextureUrl>() {
    private static final long serialVersionUID = 1;

    {
      put("BG", new TextureUrl("textures/muddy/bg.etc1", UrlType.ASSETS));
      put("ENV", new TextureUrl("textures/muddy/env.etc1", UrlType.ASSETS));
      put("FISHSCHOOL", new TextureUrl("textures/school/fish_0.cim", UrlType.ASSETS));
      put("BAIT", new TextureUrl("textures/bait/bait.png", UrlType.ASSETS));
      put("PLANT01", new TextureUrl("textures/floatage/green-01.png", UrlType.ASSETS));
      put("PLANT02", new TextureUrl("textures/floatage/green-02.png", UrlType.ASSETS));
      put("PLANT03", new TextureUrl("textures/floatage/green-03.png", UrlType.ASSETS));
      put("PLANT04", new TextureUrl("textures/floatage/green-04.png", UrlType.ASSETS));
      put(KoiModel.SPECIES_KOIA1, new TextureUrl("textures/koi3d/koi-01.png", UrlType.ASSETS));
      put(KoiModel.SPECIES_KOIB4, new TextureUrl("textures/koi3d/koi-02.png", UrlType.ASSETS));
      put(KoiModel.SPECIES_KOIB3, new TextureUrl("textures/koi3d/koi-03.png", UrlType.ASSETS));
      put(KoiModel.SPECIES_KOIB7, new TextureUrl("textures/koi3d/koi-04.png", UrlType.ASSETS));
      put(KoiModel.SPECIES_KOIB6, new TextureUrl("textures/koi3d/koi-05.png", UrlType.ASSETS));
      put(KoiModel.SPECIES_KOIA6, new TextureUrl("textures/koi3d/koi-06.png", UrlType.ASSETS));
      put(KoiModel.SPECIES_KOIB5, new TextureUrl("textures/koi3d/koi-07.png", UrlType.ASSETS));
      put(KoiModel.SPECIES_KOIB1, new TextureUrl("textures/koi3d/koi-08.png", UrlType.ASSETS));
      put(KoiModel.SPECIES_KOIB2, new TextureUrl("textures/koi3d/koi-09.png", UrlType.ASSETS));
      put(KoiModel.SPECIES_KOIA8, new TextureUrl("textures/koi3d/koi-10.png", UrlType.ASSETS));
      put(KoiModel.SPECIES_KOID01, new TextureUrl("textures/koi3d/koid01.dat", UrlType.DAT));
      put(KoiModel.SPECIES_KOID02, new TextureUrl("textures/koi3d/koid02.dat", UrlType.DAT));
      put(KoiModel.SPECIES_KOID03, new TextureUrl("textures/koi3d/koid03.dat", UrlType.DAT));
      put(KoiModel.SPECIES_KOID04, new TextureUrl("textures/koi3d/koid04.dat", UrlType.DAT));
      put(KoiModel.SPECIES_KOID05, new TextureUrl("textures/koi3d/koid05.dat", UrlType.DAT));
      put(KoiModel.SPECIES_KOID06, new TextureUrl("textures/koi3d/koid06.dat", UrlType.DAT));
      put(KoiModel.SPECIES_KOID07, new TextureUrl("textures/koi3d/koid07.dat", UrlType.DAT));
      put(KoiModel.SPECIES_KOID08, new TextureUrl("textures/koi3d/koid08.dat", UrlType.DAT));
      put(KoiModel.SPECIES_KOID09, new TextureUrl("textures/koi3d/koid09.dat", UrlType.DAT));
      put(KoiModel.SPECIES_KOID10, new TextureUrl("textures/koi3d/koid10.dat", UrlType.DAT));
      put(KoiModel.SPECIES_KOID11, new TextureUrl("textures/koi3d/koid11.dat", UrlType.DAT));
      put(KoiModel.SPECIES_KOID12, new TextureUrl("textures/koi3d/koid12.dat", UrlType.DAT));
      put(KoiModel.SPECIES_KOID13, new TextureUrl("textures/koi3d/koid13.dat", UrlType.DAT));
      put(KoiModel.SPECIES_KOID14, new TextureUrl("textures/koi3d/koid14.dat", UrlType.DAT));
      put(KoiModel.SPECIES_KOID15, new TextureUrl("textures/koi3d/koid15.dat", UrlType.DAT));
      put(KoiModel.SPECIES_KOID16, new TextureUrl("textures/koi3d/koid16.dat", UrlType.DAT));
    }
  };
  private Map<String, Texture> textures = null;
  private boolean themeTexturesDirty;

  public interface OnThemeTextureUpdated {
    void onThemeTextureUpdated();
  }

  private TextureMananger() {
    PreferencesManager.getInstance().addPreferenceChangedListener(this);
    this.textures = new HashMap();
    this.nativeTextureManager = new NativeTextureManager();
    this.listeners = new ArrayList();
    this.isGCSeries = GLLimit.instance().isGCSeries();
    updateThemeUrls();
    initTextures();
    this.themeTexturesDirty = false;
  }

  public static TextureMananger getInstance() {
    if (sInstance == null) {
      sInstance = new TextureMananger();
    }
    return sInstance;
  }

  private void notifyThemeTextureUpdated() {
    for (OnThemeTextureUpdated listener : this.listeners) {
      listener.onThemeTextureUpdated();
    }
  }

  private void initTextures() {
    putTexture("BG", TexFactory.createTexture(this.textureFileUrls.get("BG")));
    putTexture("ENV", TexFactory.createTexture(this.textureFileUrls.get("ENV")));
    putTexture("FISHSCHOOL", TexFactory.createTexture(this.textureFileUrls.get("FISHSCHOOL")));
    putTexture("BAIT", TexFactory.createTexture(this.textureFileUrls.get("BAIT")));
    putTexture("PLANT01", TexFactory.createTexture(this.textureFileUrls.get("PLANT01")));
    putTexture("PLANT02", TexFactory.createTexture(this.textureFileUrls.get("PLANT02")));
    putTexture("PLANT03", TexFactory.createTexture(this.textureFileUrls.get("PLANT03")));
    putTexture("PLANT04", TexFactory.createTexture(this.textureFileUrls.get("PLANT04")));
    putTexture(KoiModel.SPECIES_KOID01, TexFactory.createTexture(
        this.textureFileUrls.get(KoiModel.SPECIES_KOID01)));
    putTexture(KoiModel.SPECIES_KOID02, TexFactory.createTexture(
        this.textureFileUrls.get(KoiModel.SPECIES_KOID02)));
    updateKoiTextures();
  }

  private void updateThemeUrls() {
    this.themeTexturesDirty = true;
    String currentTheme = PreferencesManager.getInstance().theme;
    if (currentTheme.equals("Muddy")) {
      this.textureFileUrls.put("BG", new TextureUrl("textures/muddy/bg.etc1", UrlType.ASSETS));
      this.textureFileUrls.put("ENV", new TextureUrl("textures/muddy/env.etc1", UrlType.ASSETS));
      this.textureFileUrls.put("PLANT01",
          new TextureUrl("textures/floatage/green-01.png", UrlType.ASSETS));
      this.textureFileUrls.put("PLANT02",
          new TextureUrl("textures/floatage/green-02.png", UrlType.ASSETS));
      this.textureFileUrls.put("PLANT03",
          new TextureUrl("textures/floatage/green-03.png", UrlType.ASSETS));
      this.textureFileUrls.put("PLANT04",
          new TextureUrl("textures/floatage/green-04.png", UrlType.ASSETS));
    }
    if (currentTheme.equals("Pebble")) {
      this.textureFileUrls.put("BG", new TextureUrl("textures/pebble/bg.etc1", UrlType.ASSETS));
      this.textureFileUrls.put("ENV", new TextureUrl("textures/pebble/env.etc1", UrlType.ASSETS));
      this.textureFileUrls.put("PLANT01",
          new TextureUrl("textures/floatage/leaf-01.png", UrlType.ASSETS));
      this.textureFileUrls.put("PLANT02",
          new TextureUrl("textures/floatage/leaf-02.png", UrlType.ASSETS));
      this.textureFileUrls.put("PLANT03",
          new TextureUrl("textures/floatage/leaf-03.png", UrlType.ASSETS));
      this.textureFileUrls.put("PLANT04",
          new TextureUrl("textures/floatage/leaf-04.png", UrlType.ASSETS));
    }
    if (currentTheme.equals("Yellow")) {
      this.textureFileUrls.put("BG", new TextureUrl("textures/yellow/bg.etc1", UrlType.ASSETS));
      this.textureFileUrls.put("ENV", new TextureUrl("textures/forest/env.etc1", UrlType.ASSETS));
      this.textureFileUrls.put("PLANT01",
          new TextureUrl("textures/floatage/lilypad-01.png", UrlType.ASSETS));
      this.textureFileUrls.put("PLANT02",
          new TextureUrl("textures/floatage/lilypad-02.png", UrlType.ASSETS));
      this.textureFileUrls.put("PLANT03",
          new TextureUrl("textures/floatage/lilypad-03.png", UrlType.ASSETS));
      this.textureFileUrls.put("PLANT04",
          new TextureUrl("textures/floatage/lilypad-04.png", UrlType.ASSETS));
    }
    if (currentTheme.equals("Pavement")) {
      this.textureFileUrls.put("BG", new TextureUrl("textures/pavement/bg.etc1", UrlType.ASSETS));
      this.textureFileUrls.put("ENV", new TextureUrl("textures/pavement/env.etc1", UrlType.ASSETS));
      this.textureFileUrls.put("PLANT01",
          new TextureUrl("textures/floatage/ginkgo-01.png", UrlType.ASSETS));
      this.textureFileUrls.put("PLANT02",
          new TextureUrl("textures/floatage/ginkgo-02.png", UrlType.ASSETS));
      this.textureFileUrls.put("PLANT03",
          new TextureUrl("textures/floatage/ginkgo-03.png", UrlType.ASSETS));
      this.textureFileUrls.put("PLANT04",
          new TextureUrl("textures/floatage/ginkgo-04.png", UrlType.ASSETS));
    }
    if (currentTheme.equals("Forest")) {
      this.textureFileUrls.put("BG", new TextureUrl("textures/forest/bg.etc1", UrlType.ASSETS));
      this.textureFileUrls.put("ENV", new TextureUrl("textures/forest/env.etc1", UrlType.ASSETS));
      this.textureFileUrls.put("PLANT01",
          new TextureUrl("textures/floatage/aspen-01.png", UrlType.ASSETS));
      this.textureFileUrls.put("PLANT02",
          new TextureUrl("textures/floatage/aspen-02.png", UrlType.ASSETS));
      this.textureFileUrls.put("PLANT03",
          new TextureUrl("textures/floatage/aspen-03.png", UrlType.ASSETS));
      this.textureFileUrls.put("PLANT04",
          new TextureUrl("textures/floatage/aspen-04.png", UrlType.ASSETS));
    }
    if (currentTheme.equals("Sandy")) {
      this.textureFileUrls.put("BG", new TextureUrl("textures/sandy/bg.etc1", UrlType.ASSETS));
      this.textureFileUrls.put("ENV", new TextureUrl("textures/forest/env.etc1", UrlType.ASSETS));
      this.textureFileUrls.put("PLANT01",
          new TextureUrl("textures/floatage/birch-01.png", UrlType.ASSETS));
      this.textureFileUrls.put("PLANT02",
          new TextureUrl("textures/floatage/birch-02.png", UrlType.ASSETS));
      this.textureFileUrls.put("PLANT03",
          new TextureUrl("textures/floatage/birch-03.png", UrlType.ASSETS));
      this.textureFileUrls.put("PLANT04",
          new TextureUrl("textures/floatage/birch-04.png", UrlType.ASSETS));
    }
    if (PreferencesManager.getInstance().bgUseCustomBoolean
        && PreferencesManager.getInstance().customBgLoaded
        && new File(KoiPondApplication.getContext().getFilesDir(), CUSTOMBGNAME).exists()) {
      this.textureFileUrls.put("BG", new TextureUrl(CUSTOMBGNAME, UrlType.ONDISK));
    }
    if (this.isGCSeries) {
      Log.i("AndroidGraphics", "Mipmap disabled for the ENV texture");
      ((TextureUrl) this.textureFileUrls.get("ENV")).mipmap = false;
    }
  }

  public boolean isThemeTexturesDirty() {
    return this.themeTexturesDirty;
  }

  private void putTexture(String key, Texture tex) {
    this.textures.put(key, tex);
    this.nativeTextureManager.putTexture(key, tex.getTextureObjectHandle(), tex.getWidth(), tex.getHeight());
  }

  private void removeTexture(String key) {
    if (this.textures.containsKey(key)) {
      ((Texture) this.textures.get(key)).dispose();
      this.textures.remove(key);
      this.nativeTextureManager.removeTexture(key);
    }
  }

  public void updateThemeTextures() {
    if (this.themeTexturesDirty) {
      ((Texture) this.textures.get("ENV")).dispose();
      putTexture("ENV", TexFactory.createTexture((TextureUrl) this.textureFileUrls.get("ENV")));
      ((Texture) this.textures.get("BG")).dispose();
      putTexture("BG", TexFactory.createTexture((TextureUrl) this.textureFileUrls.get("BG")));
      ((Texture) this.textures.get("PLANT01")).dispose();
      putTexture("PLANT01",
          TexFactory.createTexture((TextureUrl) this.textureFileUrls.get("PLANT01")));
      ((Texture) this.textures.get("PLANT02")).dispose();
      putTexture("PLANT02",
          TexFactory.createTexture((TextureUrl) this.textureFileUrls.get("PLANT02")));
      ((Texture) this.textures.get("PLANT03")).dispose();
      putTexture("PLANT03",
          TexFactory.createTexture((TextureUrl) this.textureFileUrls.get("PLANT03")));
      ((Texture) this.textures.get("PLANT04")).dispose();
      putTexture("PLANT04",
          TexFactory.createTexture((TextureUrl) this.textureFileUrls.get("PLANT04")));
      this.themeTexturesDirty = false;
      notifyThemeTextureUpdated();
    }
  }

  public void addThemeChangedListener(OnThemeTextureUpdated listener) {
    if (!this.listeners.contains(listener)) {
      this.listeners.add(listener);
    }
  }

  public void removeThemeChangedListener(OnThemeTextureUpdated listener) {
    if (this.listeners.contains(listener)) {
      this.listeners.remove(listener);
    }
  }

  public Texture getTexture(String textureKey) {
    return (Texture) this.textures.get(textureKey);
  }

  public void dispose() {
    for (Entry<String, Texture> entry : this.textures.entrySet()) {
      ((Texture) entry.getValue()).dispose();
    }
    Texture.clearAllTextures(Gdx.app);
    this.textures = null;
    sInstance = null;
    this.listeners = null;
    this.textureFileUrls = null;
    PreferencesManager.getInstance().removePreferenceChangedListener(this);
  }

  public void onPreferenceChanged(PreferenceChangedType type) {
    switch (type) {
      case CURRENTTHEME:
      case CUSTOMBGENABLE:
      case CUSTOMBGLOADED:
        updateThemeUrls();
        return;
      default:
        return;
    }
  }

  public void updateKoiTextures() {
    HashSet<String> pondSpecies = PondsManager.getInstance().getCurrentPond().getKoiSpecies();
    Iterator it = pondSpecies.iterator();
    while (it.hasNext()) {
      String koiSpecies = (String) it.next();
      if (!this.textures.containsKey(koiSpecies)) {
        Texture koiTex =
            TexFactory.createTexture(this.textureFileUrls.get(koiSpecies));
        putTexture(koiSpecies, koiTex);
        if (this.filteredTexs.contains(koiSpecies)) {
          koiTex.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        }
      }
    }
    Iterator it2 = KoiModel.validSpecies.iterator();
    while (it2.hasNext()) {
      String koiSpecies = (String) it2.next();
      if (!pondSpecies.contains(koiSpecies)) {
        removeTexture(koiSpecies);
      }
    }
  }

  public void invalidateAllTextures() {
    for (Entry<String, Texture> entry : this.textures.entrySet()) {
      putTexture(entry.getKey(), entry.getValue());
    }
  }
}
