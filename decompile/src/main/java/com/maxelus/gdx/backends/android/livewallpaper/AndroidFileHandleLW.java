package com.maxelus.gdx.backends.android.livewallpaper;

import android.content.res.AssetManager;
import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.GdxRuntimeException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class AndroidFileHandleLW extends FileHandle {
    final AssetManager assets;

    AndroidFileHandleLW(AssetManager assets, String fileName, FileType type) {
        super(fileName, type);
        this.assets = assets;
    }

    AndroidFileHandleLW(AssetManager assets, File file, FileType type) {
        super(file, type);
        this.assets = assets;
    }

    public FileHandle child(String name) {
        if (this.file.getPath().length() == 0) {
            return new AndroidFileHandleLW(this.assets, new File(name), this.type);
        }
        return new AndroidFileHandleLW(this.assets, new File(this.file, name), this.type);
    }

    public FileHandle parent() {
        File parent = this.file.getParentFile();
        if (parent == null) {
            if (this.type == FileType.Absolute) {
                parent = new File("/");
            } else {
                parent = new File("");
            }
        }
        return new AndroidFileHandleLW(this.assets, parent, this.type);
    }

    public InputStream read() {
        if (this.type != FileType.Internal) {
            return super.read();
        }
        try {
            return this.assets.open(this.file.getPath());
        } catch (IOException ex) {
            throw new GdxRuntimeException("Error reading file: " + this.file + " (" + this.type + ")", ex);
        }
    }

    public FileHandle[] list() {
        if (this.type != FileType.Internal) {
            return super.list();
        }
        try {
            String[] relativePaths = this.assets.list(this.file.getPath());
            FileHandle[] handles = new FileHandle[relativePaths.length];
            int n = handles.length;
            for (int i = 0; i < n; i++) {
                handles[i] = new AndroidFileHandleLW(this.assets, new File(this.file, relativePaths[i]), this.type);
            }
            return handles;
        } catch (Exception ex) {
            throw new GdxRuntimeException("Error listing children: " + this.file + " (" + this.type + ")", ex);
        }
    }

    public boolean isDirectory() {
        if (this.type != FileType.Internal) {
            return super.isDirectory();
        }
        try {
            if (this.assets.list(this.file.getPath()).length > 0) {
                return true;
            }
            return false;
        } catch (IOException e) {
            return false;
        }
    }

    public boolean exists() {
        if (this.type != FileType.Internal) {
            return super.exists();
        }
        String fileName = this.file.getPath();
        try {
            this.assets.open(fileName).close();
            return true;
        } catch (Exception e) {
            try {
                if (this.assets.list(fileName).length <= 0) {
                    return false;
                }
                return true;
            } catch (Exception e2) {
                return false;
            }
        }
    }
}
