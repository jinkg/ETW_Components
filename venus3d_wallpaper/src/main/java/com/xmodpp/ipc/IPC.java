package com.xmodpp.ipc;

import android.content.Intent;
import android.net.Uri;
import com.xmodpp.core.App;

public class IPC {
    static void jni_OpenURL(String str) {
        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(str));
        intent.addFlags(268435456);
        App.jni_getApplicationContext().startActivity(intent);
    }
}
