package com.xmodpp.core;

import android.content.Context;
import android.os.Build;

import com.xmodpp.core.App;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipFile;

public class LibraryLoader {
    public static boolean f10171a = false;

    public static boolean m20003a(Context context, String str) {
        ZipFile zipFile;
        InputStream bufferedInputStream;
        ZipFile zipFile2;
        InputStream inputStream;
        Throwable th;
        BufferedOutputStream bufferedOutputStream = null;
        App.m20002a(context);
        if (f10171a) {
            return true;
        }
        try {
            System.loadLibrary(str);
            f10171a = true;
            nativeSetApplicationContext(context.getApplicationContext());
            return true;
        } catch (UnsatisfiedLinkError e) {
            String str2 = context.getApplicationInfo().sourceDir;
            File file = new File(context.getFilesDir(), str + ".so");
            try {
                zipFile = new ZipFile(str2);
                try {
                    str2 = "lib/armeabi-v7a/";
                    if (Build.CPU_ABI.startsWith("x86")) {
                        str2 = "lib/x86/";
                    }
                    if (Build.CPU_ABI.startsWith("mips")) {
                        str2 = "lib/mips/";
                    }
                    bufferedInputStream = new BufferedInputStream(zipFile.getInputStream(zipFile.getEntry(str2 + str + ".so")));
                } catch (Exception e2) {
                    zipFile2 = zipFile;
                    inputStream = null;
                    if (bufferedOutputStream != null) {
                        try {
                            bufferedOutputStream.close();
                        } catch (Exception e3) {
                        }
                    }
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (Exception e4) {
                        }
                    }
                    if (zipFile2 != null) {
                        try {
                            zipFile2.close();
                        } catch (IOException e5) {
                        }
                    }
                    return false;
                } catch (Throwable th2) {
                    th = th2;
                    bufferedInputStream = null;
                    if (bufferedOutputStream != null) {
                        try {
                            bufferedOutputStream.close();
                        } catch (Exception e6) {
                        }
                    }
                    if (bufferedInputStream != null) {
                        try {
                            bufferedInputStream.close();
                        } catch (Exception e7) {
                        }
                    }
                    if (zipFile != null) {
                        try {
                            zipFile.close();
                        } catch (IOException e8) {
                        }
                    }
                    throw th;
                }
                try {
                    BufferedOutputStream bufferedOutputStream2 = new BufferedOutputStream(new FileOutputStream(file));
                    try {
                        byte[] bArr = new byte[4096];
                        for (int read = bufferedInputStream.read(bArr); read != -1; read = bufferedInputStream.read(bArr)) {
                            bufferedOutputStream2.write(bArr, 0, read);
                        }
                        bufferedOutputStream2.close();
                        bufferedInputStream.close();
                        System.load(file.getAbsolutePath());
                        nativeSetApplicationContext(context.getApplicationContext());
                        file.delete();
                        try {
                            bufferedOutputStream2.close();
                        } catch (Exception e9) {
                        }
                        try {
                            bufferedInputStream.close();
                        } catch (Exception e10) {
                        }
                        try {
                            zipFile.close();
                            return true;
                        } catch (IOException e11) {
                            return true;
                        }
                    } catch (Exception e12) {
                        zipFile2 = zipFile;
                        bufferedOutputStream = bufferedOutputStream2;
                        inputStream = bufferedInputStream;
                        if (bufferedOutputStream != null) {
                            bufferedOutputStream.close();
                        }
                        if (inputStream != null) {
                            inputStream.close();
                        }
                        if (zipFile2 != null) {
                            zipFile2.close();
                        }
                        return false;
                    } catch (Throwable th3) {
                        th = th3;
                        bufferedOutputStream = bufferedOutputStream2;
                        if (bufferedOutputStream != null) {
                            bufferedOutputStream.close();
                        }
                        if (bufferedInputStream != null) {
                            bufferedInputStream.close();
                        }
                        if (zipFile != null) {
                            zipFile.close();
                        }
                        throw th;
                    }
                } catch (Exception e13) {
                    zipFile2 = zipFile;
                    inputStream = bufferedInputStream;
                    if (bufferedOutputStream != null) {
                        bufferedOutputStream.close();
                    }
                    if (inputStream != null) {
                        inputStream.close();
                    }
                    if (zipFile2 != null) {
                        zipFile2.close();
                    }
                    return false;
                } catch (Throwable th4) {
                    th = th4;
                    if (bufferedOutputStream != null) {
                        bufferedOutputStream.close();
                    }
                    if (bufferedInputStream != null) {
                        bufferedInputStream.close();
                    }
                    if (zipFile != null) {
                        zipFile.close();
                    }
                    throw th;
                }
            } catch (Exception e14) {
                zipFile2 = null;
                inputStream = null;
                try {
                    if (bufferedOutputStream != null) {
                        bufferedOutputStream.close();
                    }
                    if (inputStream != null) {
                        inputStream.close();
                    }
                    if (zipFile2 != null) {
                        zipFile2.close();
                    }
                } catch (Exception ignore) {

                }
                return false;
            } catch (Throwable th5) {
                th = th5;
                zipFile = null;
                bufferedInputStream = null;
                try {
                    if (bufferedOutputStream != null) {
                        bufferedOutputStream.close();
                    }
                    if (bufferedInputStream != null) {
                        bufferedInputStream.close();
                    }
                    if (zipFile != null) {
                        zipFile.close();
                    }
                } catch (Exception ignore) {
                }
                return false;
            }
        }
    }

    public static native void nativeSetApplicationContext(Context context);
}
