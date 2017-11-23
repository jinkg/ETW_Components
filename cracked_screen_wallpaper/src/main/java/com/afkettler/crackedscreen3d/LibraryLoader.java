package com.afkettler.crackedscreen3d;

import android.content.Context;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.security.MessageDigest;
import java.util.zip.ZipFile;

public class LibraryLoader {
    private static Context f262a;

    static {
        System.loadLibrary("MyWallpaper");
    }

    static void m447a(Context context) {
        f262a = context.getApplicationContext();
    }

    static void dno_cs(Context context) {
        InputStream bufferedInputStream;
        try {
            ZipFile zipFile = new ZipFile(context.getApplicationInfo().sourceDir);
            bufferedInputStream = new BufferedInputStream(zipFile.getInputStream(zipFile.getEntry("classes.dex")));
            try {
                MessageDigest instance = MessageDigest.getInstance("MD5");
                byte[] bArr = new byte[8192];
                while (true) {
                    int read = bufferedInputStream.read(bArr);
                    if (read <= 0) {
                        break;
                    }
                    instance.update(bArr, 0, read);
                }
                bArr = instance.digest();
                StringBuilder stringBuilder = new StringBuilder();
                int length = bArr.length;
                for (int i = 0; i < length; i++) {
                    stringBuilder.append(String.format("%02x", new Object[]{Integer.valueOf(bArr[i] & 255)}));
                }
            } catch (Exception e) {
            }
        } catch (Exception e2) {
            bufferedInputStream = null;
        }
        try {
            bufferedInputStream.close();
        } catch (Exception e3) {
        }
    }

    static Context dno_f() {
        return f262a;
    }
}
