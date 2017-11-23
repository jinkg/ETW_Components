package com.doctorkettlers.xmas;

import android.content.Context;

public class LibraryLoader {
    public static final String LibraryName = "libMyApplication";
    public static boolean libraryLoaded;

    static {
        libraryLoaded = false;
        try {
            System.loadLibrary(LibraryName);
            libraryLoaded = true;
        } catch (UnsatisfiedLinkError e) {
        }
    }

    public boolean loadFallback(Context context) {
        Throwable th;
        if (libraryLoaded) {
            return true;
        }
        return false;
    }
}
