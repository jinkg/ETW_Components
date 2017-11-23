package com.xmodpp.ipc;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;

public class NativeServiceConnection implements ServiceConnection {
    public native void onServiceConnected(ComponentName componentName, IBinder iBinder);

    public native void onServiceDisconnected(ComponentName componentName);
}
