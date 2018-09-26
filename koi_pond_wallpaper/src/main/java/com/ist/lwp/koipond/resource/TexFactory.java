package com.ist.lwp.koipond.resource;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.ist.lwp.koipond.KoiPondApplication;
import java.io.File;

public class TexFactory {

  public static class TextureUrl {
    public boolean mipmap = true;
    public UrlType type;
    public String url;

    public TextureUrl(String url, UrlType type) {
      this.url = url;
      this.type = type;
    }
  }

  public enum UrlType {
    ASSETS,
    ONDISK,
    DAT
  }

  public static Texture createTexture(TextureUrl textureUrl) {
    switch (textureUrl.type) {
      case ONDISK:
        return new Texture(
            new FileHandle(new File(KoiPondApplication.getContext().getFilesDir(), textureUrl.url)),
            textureUrl.mipmap);
      case ASSETS:
        return new Texture(Gdx.files.internal(textureUrl.url), textureUrl.mipmap);
      case DAT:
        return new Texture(new DatTextureData());
      default:
        return new Texture(Gdx.files.internal(textureUrl.url), textureUrl.mipmap);
    }
  }
}
