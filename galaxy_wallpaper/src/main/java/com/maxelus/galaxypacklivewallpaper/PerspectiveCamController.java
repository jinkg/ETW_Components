package com.maxelus.galaxypacklivewallpaper;

import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mybadlogic.gdx.Gdx;
import com.mybadlogic.gdx.graphics.PerspectiveCamera;
import com.mybadlogic.gdx.math.Plane;
import com.maxelus.gdx.backends.android.livewallpaper.InputProcessorLW;

public class PerspectiveCamController implements InputProcessorLW {
    public PerspectiveCamera cam;
    Vector3 curr3 = new Vector3();
    Vector2 currWindow = new Vector2();
    Vector2 delta = new Vector2();
    Vector3 delta3 = new Vector3();
    Vector2 last = new Vector2();
    Vector2 lastWindow = new Vector2();
    Vector3 lookAt = new Vector3();
    Plane lookAtPlane = new Plane(new Vector3(0.0f, 1.0f, 0.0f), 0.0f);
    TransformMode mode = TransformMode.Translate;
    Vector3 point = new Vector3();
    Matrix4 rotMatrix = new Matrix4();
    Vector2 tCurr = new Vector2();
    boolean translated = false;
    Vector3 xAxis = new Vector3(1.0f, 0.0f, 0.0f);
    Vector3 yAxis = new Vector3(0.0f, 1.0f, 0.0f);

    enum TransformMode {
        Rotate,
        Translate,
        Zoom,
        None
    }

    public PerspectiveCamController(PerspectiveCamera cam) {
        this.cam = cam;
    }

    public boolean touchDown(int x, int y, int pointer, int button) {
        this.mode = TransformMode.Rotate;
        this.last.set((float) x, (float) y);
        this.tCurr.set((float) x, (float) y);
        return true;
    }

    public boolean touchUp(int x, int y, int pointer, int button) {
        this.mode = TransformMode.None;
        return true;
    }

    public boolean touchDragged(int x, int y, int pointer) {
        if (pointer != 0) {
            return false;
        }
        this.delta.set((float) x, (float) y).sub(this.last);
        if (this.mode == TransformMode.Rotate) {
            this.point.set(this.cam.position).sub(this.lookAt);
            this.rotMatrix.setToRotation(this.xAxis, this.delta.y / 15.0f);
            this.point.mul(this.rotMatrix);
            this.rotMatrix.setToRotation(this.yAxis, (-this.delta.x) / 15.0f);
            this.point.mul(this.rotMatrix);
            this.cam.position.set(this.point.add(this.lookAt));
            this.cam.lookAt(this.lookAt.x, this.lookAt.y, this.lookAt.z);
        }
        if (this.mode == TransformMode.Zoom) {
            PerspectiveCamera perspectiveCamera = this.cam;
            perspectiveCamera.fieldOfView -= (-this.delta.y) / 10.0f;
        }
        if (this.mode == TransformMode.Translate) {
            this.tCurr.set((float) x, (float) y);
            this.translated = true;
        }
        this.last.set((float) x, (float) y);
        return true;
    }

    public boolean accel(float x, float y) {
        this.point.set(this.cam.position).sub(this.lookAt);
        this.rotMatrix.setToRotation(this.xAxis, y / 15.0f);
        this.point.mul(this.rotMatrix);
        this.rotMatrix.setToRotation(this.yAxis, x / 15.0f);
        this.point.mul(this.rotMatrix);
        this.cam.position.set(this.point.add(this.lookAt));
        this.cam.lookAt(this.lookAt.x, this.lookAt.y, this.lookAt.z);
        return true;
    }

    public boolean scrolled(int amount) {
        PerspectiveCamera perspectiveCamera = this.cam;
        perspectiveCamera.fieldOfView -= (((float) (-amount)) * Gdx.graphics.getDeltaTime()) * 100.0f;
        this.cam.update();
        return true;
    }

    public boolean keyDown(int keycode) {
        return false;
    }

    public boolean keyTyped(char character) {
        return false;
    }

    public boolean keyUp(int keycode) {
        return false;
    }

    public boolean touchMoved(int x, int y) {
        return false;
    }

    public void touchDrop(int x, int y) {
    }

    public void touchTap(int x, int y) {
    }
}
