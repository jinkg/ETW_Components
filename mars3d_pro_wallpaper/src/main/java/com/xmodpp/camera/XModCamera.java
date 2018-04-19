package com.xmodpp.camera;

import android.annotation.TargetApi;
import android.graphics.SurfaceTexture;
import android.graphics.SurfaceTexture.OnFrameAvailableListener;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.Size;
import android.util.Log;
import java.io.IOException;

public class XModCamera implements OnFrameAvailableListener {
    public Camera camera;
    public int camera_id;
    private boolean hasNewFrame = false;
    public SurfaceTexture surfaceTexture;
    public int texture_id;

    public XModCamera(int camera_id_, int texture_id_) {
        this.camera_id = camera_id_;
        this.texture_id = texture_id_;
    }

    @TargetApi(14)
    public synchronized void start() throws IOException {
        this.camera = Camera.open(this.camera_id);
        this.surfaceTexture = new SurfaceTexture(this.texture_id);
        this.surfaceTexture.setOnFrameAvailableListener(this);
        this.camera.setPreviewTexture(this.surfaceTexture);
        Parameters params = this.camera.getParameters();
        for (Size size : params.getSupportedPreviewSizes()) {
            Log.d("XMOD++", "Supported size: " + size.width + "x" + size.height);
            if (size.width > params.getPreviewSize().width) {
                params.setPreviewSize(size.width, size.height);
            }
        }
        Log.d("XMOD++", "Max zoom: " + params.getMaxZoom());
        this.camera.setParameters(params);
        this.camera.startPreview();
    }

    public synchronized void stop() throws IOException {
        this.camera.stopPreview();
        this.camera.setPreviewTexture(null);
        this.camera.release();
    }

    public synchronized boolean captureFrame() {
        boolean z = false;
        synchronized (this) {
            if (this.hasNewFrame) {
                this.surfaceTexture.updateTexImage();
                this.hasNewFrame = false;
                z = true;
            }
        }
        return z;
    }

    public synchronized void onFrameAvailable(SurfaceTexture surfaceTexture) {
        this.hasNewFrame = true;
    }

    public synchronized Size getSize() {
        return this.camera.getParameters().getPreviewSize();
    }
}
