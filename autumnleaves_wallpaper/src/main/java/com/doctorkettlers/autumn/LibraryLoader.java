package com.doctorkettlers.autumn;

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
        return false;
    }
}
