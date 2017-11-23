package com.xmodpp;

import android.view.WindowManager;
import com.xmodpp.core.App;

public class Utils {
    public static final int getDisplayRotation() {
        switch (((WindowManager) App.jni_getApplicationContext().getSystemService("window")).getDefaultDisplay().getRotation()) {
            case 0:
                return 0;
            case 1:
                return 90;
            case 2:
                return 180;
            case 3:
                return 270;
            default:
                return 0;
        }
    }
}
