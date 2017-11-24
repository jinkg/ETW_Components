package com.xmodpp.application;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;
import com.xmodpp.application.Signals.SignalHandlerString;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class Application {

  static Context jni_context;
  private static boolean libraryLoaded = false;
  static Toast toast;

  static class C06561 implements SignalHandlerString {

    C06561() {
    }

    public void onSignal(String str, String str2) {
      Log.d("XMOD++", "toast: " + str2);
      if (Application.toast == null) {
        Application.toast = Toast.makeText(Application.jni_context, str2, 1);
      } else {
        Application.toast.setText(str2);
      }
      Application.toast.setDuration(1);
      Application.toast.show();
    }
  }

  public static synchronized void Init(Context context) {
    synchronized (Application.class) {
      if (jni_context == null) {
        jni_context = context.getApplicationContext();
      }
      Signals.RegisterHandler("toast", new C06561());
    }
  }

  @SuppressLint({"UnsafeDynamicallyLoadedCode"})
  public static boolean LoadLibrary(Context context, String str) {
    InputStream bufferedInputStream;
    ZipFile zipFile;
    InputStream inputStream;
    Throwable th;
    BufferedOutputStream bufferedOutputStream = null;
    Init(context);
    if (libraryLoaded) {
      return true;
    }
    try {
      System.loadLibrary(str);
      libraryLoaded = true;
      return true;
    } catch (UnsatisfiedLinkError e) {
      String str2 = context.getApplicationInfo().sourceDir;
      File file = new File(context.getFilesDir(), str + ".so");
      InputStream inputStream2 = null;
      BufferedOutputStream bufferedOutputStream2 = null;
      ZipFile zipFile2;
      try {
        zipFile2 = new ZipFile(str2);
        try {
          ZipEntry zipEntry = null;
          for (String str3 : new String[]{"arm64-v8a", "armeabi-v7a", "armeabi", "x86_64", "x86",
              "mips64", "mips"}) {
            if (Build.CPU_ABI.equals(str3)) {
              zipEntry = zipFile2.getEntry("lib/" + str3 + "/" + str + ".so");
              if (zipEntry != null) {
                Log.i("LibraryLoader", "Best ABI: " + str3);
                break;
              }
            }
          }
          if (zipEntry == null) {
            Log.e("LibraryLoader", "ARRG! No suitable library found!");
            if (null != null) {
              try {
                bufferedOutputStream2.close();
              } catch (Exception e2) {
              }
            }
            if (null != null) {
              try {
                inputStream2.close();
              } catch (Exception e3) {
              }
            }
            if (zipFile2 != null) {
              try {
                zipFile2.close();
              } catch (IOException e4) {
              }
            }
            return false;
          }
          BufferedOutputStream bufferedOutputStream3;
          bufferedInputStream = new BufferedInputStream(zipFile2.getInputStream(zipEntry));
          try {
            bufferedOutputStream3 = new BufferedOutputStream(new FileOutputStream(file));
          } catch (Exception e5) {
            zipFile = zipFile2;
            inputStream = bufferedInputStream;
            if (bufferedOutputStream != null) {
              try {
                bufferedOutputStream.close();
              } catch (Exception e6) {
              }
            }
            if (inputStream != null) {
              try {
                inputStream.close();
              } catch (Exception e7) {
              }
            }
            if (zipFile != null) {
              try {
                zipFile.close();
              } catch (IOException e8) {
              }
            }
            return false;
          } catch (Throwable th2) {
            th = th2;
            if (bufferedOutputStream != null) {
              try {
                bufferedOutputStream.close();
              } catch (Exception e9) {
              }
            }
            if (bufferedInputStream != null) {
              try {
                bufferedInputStream.close();
              } catch (Exception e10) {
              }
            }
            if (zipFile2 != null) {
              try {
                zipFile2.close();
              } catch (IOException e11) {
              }
            }
            throw th;
          }
          try {
            byte[] bArr = new byte[4096];
            for (int read = bufferedInputStream.read(bArr); read != -1;
                read = bufferedInputStream.read(bArr)) {
              bufferedOutputStream3.write(bArr, 0, read);
            }
            bufferedOutputStream3.close();
            bufferedInputStream.close();
            System.load(file.getAbsolutePath());
            libraryLoaded = true;
            if (file.delete()) {
              if (bufferedOutputStream3 != null) {
                bufferedOutputStream3.close();
              }
              if (bufferedInputStream != null) {
                bufferedInputStream.close();
              }
              if (zipFile2 != null) {
                zipFile2.close();
              }
              return true;
            }
            if (bufferedOutputStream3 != null) {
              try {
                bufferedOutputStream3.close();
              } catch (Exception e12) {
              }
            }
            if (bufferedInputStream != null) {
              try {
                bufferedInputStream.close();
              } catch (Exception e13) {
              }
            }
            if (zipFile2 != null) {
              try {
                zipFile2.close();
              } catch (IOException e14) {
              }
            }
            return true;
          } catch (Exception e15) {
            bufferedOutputStream = bufferedOutputStream3;
            inputStream = bufferedInputStream;
            zipFile = zipFile2;
            if (bufferedOutputStream != null) {
              bufferedOutputStream.close();
            }
            if (inputStream != null) {
              inputStream.close();
            }
            if (zipFile != null) {
              zipFile.close();
            }
            return false;
          } catch (Throwable th3) {
            bufferedOutputStream = bufferedOutputStream3;
            th = th3;
            if (bufferedOutputStream != null) {
              bufferedOutputStream.close();
            }
            if (bufferedInputStream != null) {
              bufferedInputStream.close();
            }
            if (zipFile2 != null) {
              zipFile2.close();
            }
            throw th;
          }
        } catch (Exception e16) {
          zipFile = zipFile2;
          inputStream = null;
          if (bufferedOutputStream != null) {
            bufferedOutputStream.close();
          }
          if (inputStream != null) {
            inputStream.close();
          }
          if (zipFile != null) {
            zipFile.close();
          }
          return false;
        } catch (Throwable th4) {
          th = th4;
          bufferedInputStream = null;
          if (bufferedOutputStream != null) {
            bufferedOutputStream.close();
          }
          if (bufferedInputStream != null) {
            bufferedInputStream.close();
          }
          if (zipFile2 != null) {
            zipFile2.close();
          }
          throw th;
        }
      } catch (Exception e17) {
        zipFile = null;
        inputStream = null;
        try {
          if (bufferedOutputStream != null) {
            bufferedOutputStream.close();
          }
          if (inputStream != null) {
            inputStream.close();
          }
          if (zipFile != null) {
            zipFile.close();
          }
        } catch (Exception ignore) {
        }
        return false;
      } catch (Throwable th5) {
        th = th5;
        zipFile2 = null;
        bufferedInputStream = null;
        try {
          if (bufferedOutputStream != null) {
            bufferedOutputStream.close();
          }
          if (bufferedInputStream != null) {
            bufferedInputStream.close();
          }
          if (zipFile2 != null) {
            zipFile2.close();
          }
        } catch (Exception ignore) {

        }
        return false;
      }
    }
  }

  public static synchronized Context jni_getContext() {
    Context context;
    synchronized (Application.class) {
      context = jni_context;
    }
    return context;
  }
}
