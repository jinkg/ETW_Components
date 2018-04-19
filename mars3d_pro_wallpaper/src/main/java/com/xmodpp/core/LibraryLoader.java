package com.xmodpp.core;

import android.content.Context;
import android.os.Build;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipFile;

public class LibraryLoader {
    public static boolean libraryLoaded = false;

    public static native void nativeSetApplicationContext(Context context);

    public static boolean Load(Context context, String libraryName) {
        Throwable th;
        App.Init(context);
        if (libraryLoaded) {
            return true;
        }
        try {
            System.loadLibrary(libraryName);
            libraryLoaded = true;
            nativeSetApplicationContext(context.getApplicationContext());
            return true;
        } catch (UnsatisfiedLinkError e) {
            String sourceDir = context.getApplicationInfo().sourceDir;
            File libFile = new File(context.getFilesDir(), new StringBuilder(String.valueOf(libraryName)).append(".so").toString());
            InputStream input = null;
            BufferedOutputStream output = null;
            ZipFile zipFile = null;
            try {
                ZipFile zipFile2 = new ZipFile(sourceDir);
                try {
                    String libdir = "lib/armeabi-v7a/";
                    if (Build.CPU_ABI.startsWith("x86")) {
                        libdir = "lib/x86/";
                    }
                    if (Build.CPU_ABI.startsWith("mips")) {
                        libdir = "lib/mips/";
                    }
                    InputStream input2 = new BufferedInputStream(zipFile2.getInputStream(zipFile2.getEntry(new StringBuilder(String.valueOf(libdir)).append(libraryName).append(".so").toString())));
                    try {
                        BufferedOutputStream output2 = new BufferedOutputStream(new FileOutputStream(libFile));
                        try {
                            byte[] buffer = new byte[4096];
                            for (int len = input2.read(buffer); len != -1; len = input2.read(buffer)) {
                                output2.write(buffer, 0, len);
                            }
                            output2.close();
                            input2.close();
                            System.load(libFile.getAbsolutePath());
                            nativeSetApplicationContext(context.getApplicationContext());
                            libFile.delete();
                            if (output2 != null) {
                                try {
                                    output2.close();
                                } catch (Exception e2) {
                                }
                            }
                            if (input2 != null) {
                                try {
                                    input2.close();
                                } catch (Exception e3) {
                                }
                            }
                            if (zipFile2 != null) {
                                try {
                                    zipFile2.close();
                                } catch (IOException e4) {
                                }
                            }
                            return true;
                        } catch (Exception e5) {
                            zipFile = zipFile2;
                            output = output2;
                            input = input2;
                        }
                    } catch (Exception e6) {
                        zipFile = zipFile2;
                        input = input2;

                        try {
                            if (output != null) {
                                output.close();
                            }
                            if (input != null) {
                                input.close();
                            }
                            if (zipFile != null) {
                                zipFile.close();
                            }
                        } catch (Exception e7) {
                        }
                        return false;
                    }
                } catch (Exception e13) {
                    zipFile = zipFile2;
                    if (output != null) {
                        output.close();
                    }
                    if (input != null) {
                        input.close();
                    }
                    if (zipFile != null) {
                        zipFile.close();
                    }
                    return false;
                }
            } catch (Exception e6) {
                try {
                    if (output != null) {
                        output.close();
                    }
                    if (input != null) {
                        input.close();
                    }
                    if (zipFile != null) {
                        zipFile.close();
                    }
                } catch (Exception e7) {
                }
                return false;
            }
        }
        return false;
    }
}
