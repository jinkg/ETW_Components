package com.ist.lwp.koipond.utils;

import java.util.Locale;

public class LocaleUtils {
    public static boolean isViLan() {
        if (Locale.getDefault().getLanguage().equalsIgnoreCase("vi")) {
            return true;
        }
        return false;
    }
}
