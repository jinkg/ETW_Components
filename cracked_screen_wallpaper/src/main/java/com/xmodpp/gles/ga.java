package com.xmodpp.gles;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public interface ga {
    void onDestroy(GL10 gl10);

    void onResize(GL10 gl10, int i, int i2);

    void mo58a(GL10 gl10, EGLConfig eGLConfig);

    void mo59b(GL10 gl10);
}
