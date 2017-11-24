package com.xmodpp.ipc;

import android.content.Intent;
import android.net.Uri;
import com.xmodpp.application.Application;

public class IPC {
    static void jni_OpenURL(String str) {
        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(str));
        intent.addFlags(268435456);
        Application.jni_getContext().startActivity(intent);
    }
}
