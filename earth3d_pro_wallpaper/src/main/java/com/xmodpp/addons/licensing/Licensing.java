package com.xmodpp.addons.licensing;

import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.vending.licensing.C0649a.C0651a;
import com.google.android.vending.licensing.ILicensingService;
import com.google.android.vending.licensing.ILicensingService.C0648a;

public class Licensing {

  private static Connection connection;

  private static class Connection implements ServiceConnection {

    private Context context;
    private ILicensingService mService = null;
    private int nonce = -1;

    class C06531 extends C0651a {

      C06531() {
      }

      public void verifyLicense(int i, String str, String str2) {
        Licensing.verify(i, str, str2);
        try {
          Connection.this.context.unbindService(Connection.this);
        } catch (IllegalArgumentException e) {
        }
        Connection.this.mService = null;
      }
    }

    Connection(Context context) {
      this.context = context;
    }

    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
      try {
        this.mService = C0648a.m2401a(iBinder);
        this.mService.mo903a((long) this.nonce, this.context.getPackageName(), new C06531());
      } catch (RemoteException e) {
        Licensing.verify(4096, "", "");
      }
    }

    public void onServiceDisconnected(ComponentName componentName) {
      Licensing.verify(1, "", "");
      this.mService = null;
    }
  }

  public static int check(Context context) {

    if (connection == null) {
      connection = new Connection(context);
    }
    connection.nonce = nonce();
    Log.d("licensing", "checking...");
    return start(null);

////    verify(0, "", "");
//    return 0;
  }

  private static native int nonce();

  private static native int start(ServiceConnection serviceConnection);

  private static native void verify(int i, String str, String str2);
}
