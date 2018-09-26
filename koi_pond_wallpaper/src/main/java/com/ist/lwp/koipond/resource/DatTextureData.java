package com.ist.lwp.koipond.resource;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.utils.GdxRuntimeException;

public class DatTextureData implements TextureData {
    public TextureDataType getType() {
        return TextureDataType.Custom;
    }

    public boolean isPrepared() {
        return true;
    }

    public void prepare() {
    }

    public void consumeCustomData(int target) {
    }

    public Pixmap consumePixmap() {
        throw new GdxRuntimeException("This TextureData implementation does not return a Pixmap");
    }

    public boolean disposePixmap() {
        throw new GdxRuntimeException("This TextureData implementation does not return a Pixmap");
    }

    public int getWidth() {
        return 256;
    }

    public int getHeight() {
        return 256;
    }

    public Format getFormat() {
        return Format.RGBA8888;
    }

    public boolean useMipMaps() {
        return true;
    }

    public boolean isManaged() {
        return true;
    }
}
