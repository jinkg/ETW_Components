package com.maxelus.gdx.backends.android.livewallpaper;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Vibrator;
import android.view.MotionEvent;
import android.view.WindowManager;

import com.mybadlogic.gdx.Input;
import com.mybadlogic.gdx.InputProcessor;
import com.mybadlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.mybadlogic.gdx.utils.Pool;

import java.util.ArrayList;

public final class AndroidInputLW implements Input, SensorEventListener {
    final float[] f103R = new float[9];
    public boolean accelerometerAvailable = false;
    private final float[] accelerometerValues = new float[3];
    final AndroidApplicationLW app;
    private float azimuth = 0.0f;
    private boolean catchBack = false;
    private boolean compassAvailable = false;
    public final AndroidApplicationConfiguration config;
    private boolean justTouched = false;
    boolean keyboardAvailable;
    private final float[] magneticFieldValues = new float[3];
    private SensorManager manager;
    final float[] orientation = new float[3];
    private float pitch = 0.0f;
    private InputProcessor processor;
    int[] realId = new int[0];
    boolean requestFocus = true;
    private float roll = 0.0f;
    private int sleepTime = 0;
    ArrayList<TouchEvent> touchEvents = new ArrayList();
    private final AndroidTouchHandlerLW touchHandler;
    int[] touchX = new int[20];
    int[] touchY = new int[20];
    boolean[] touched = new boolean[20];
    Pool<TouchEvent> usedTouchEvents = new Pool<TouchEvent>(16, 1000) {
        protected TouchEvent newObject() {
            return new TouchEvent();
        }
    };
    private final Vibrator vibrator = null;

    class TouchEvent {
        static final int TOUCH_DOWN = 0;
        static final int TOUCH_DRAGGED = 2;
        static final int TOUCH_DROP = 4;
        static final int TOUCH_TAP = 3;
        static final int TOUCH_UP = 1;
        int pointer;
        long timeStamp;
        int type;
        int f87x;
        int f88y;

        TouchEvent() {
        }
    }

    public AndroidInputLW(AndroidApplicationLW activity, AndroidApplicationConfiguration config) {
        this.config = config;
        for (int i = 0; i < this.realId.length; i++) {
            this.realId[i] = -1;
        }
        this.app = activity;
        this.sleepTime = config.touchSleepTime;
        this.touchHandler = new AndroidSingleTouchHandlerLW();
    }

    public float getAccelerometerX() {
        return this.accelerometerValues[0];
    }

    public float getAccelerometerY() {
        return this.accelerometerValues[1];
    }

    public float getAccelerometerZ() {
        return this.accelerometerValues[2];
    }

    public int getX() {
        int i;
        synchronized (this) {
            i = this.touchX[0];
        }
        return i;
    }

    public int getY() {
        int i;
        synchronized (this) {
            i = this.touchY[0];
        }
        return i;
    }

    public int getX(int pointer) {
        int i;
        synchronized (this) {
            i = this.touchX[pointer];
        }
        return i;
    }

    public int getY(int pointer) {
        int i;
        synchronized (this) {
            i = this.touchY[pointer];
        }
        return i;
    }

    public boolean isTouched(int pointer) {
        boolean z;
        synchronized (this) {
            z = this.touched[pointer];
        }
        return z;
    }

    public boolean isTouched() {
        boolean z;
        synchronized (this) {
            z = this.touched[0];
        }
        return z;
    }

    public void setInputProcessor(InputProcessor processor) {
        synchronized (this) {
            this.processor = processor;
        }
    }

    void processEvents() {
        synchronized (this) {
            this.justTouched = false;
            int len;
            int i;
            TouchEvent e;
            if (this.processor == null) {
                len = this.touchEvents.size();
                for (i = 0; i < len; i++) {
                    e = this.touchEvents.get(i);
                    if (e.type == 3) {
                        this.justTouched = true;
                    }
                    this.usedTouchEvents.free(e);
                }
            } else if (this.processor instanceof InputProcessorLW) {
                InputProcessorLW processor = (InputProcessorLW) this.processor;
                len = this.touchEvents.size();
                for (i = 0; i < len; i++) {
                    e = this.touchEvents.get(i);
                    switch (e.type) {
                        case 0:
                            processor.touchDown(e.f87x, e.f88y, e.pointer, 0);
                            this.justTouched = true;
                            break;
                        case 1:
                            processor.touchUp(e.f87x, e.f88y, e.pointer, 0);
                            this.justTouched = true;
                            break;
                        case 2:
                            processor.touchDragged(e.f87x, e.f88y, e.pointer);
                            break;
                        default:
                            break;
                    }
                    this.usedTouchEvents.free(e);
                }
            }
            this.touchEvents.clear();
        }
    }

    public boolean onTouch(MotionEvent event) {
        this.touchHandler.onTouch(event, this);
        if (this.sleepTime != 0) {
            try {
                Thread.sleep((long) this.sleepTime);
            } catch (InterruptedException e) {
            }
        }
        return true;
    }

    public void onAccuracyChanged(Sensor arg0, int arg1) {
    }

    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == 1) {
            System.arraycopy(event.values, 0, this.accelerometerValues, 0, this.accelerometerValues.length);
        } else if (event.sensor.getType() == 2) {
            System.arraycopy(event.values, 0, this.magneticFieldValues, 0, this.magneticFieldValues.length);
        }
    }

    public void setCatchBackKey(boolean catchBack) {
        this.catchBack = catchBack;
    }

    public void vibrate(int milliseconds) {
    }

    public void vibrate(long[] pattern, int repeat) {
    }

    public void cancelVibrate() {
    }

    public boolean justTouched() {
        return this.justTouched;
    }

    public boolean isButtonPressed(int button) {
        if (button == 0) {
            return isTouched();
        }
        return false;
    }

    private void updateOrientation() {
        if (SensorManager.getRotationMatrix(this.f103R, null, this.accelerometerValues, this.magneticFieldValues)) {
            SensorManager.getOrientation(this.f103R, this.orientation);
            this.azimuth = (float) Math.toDegrees((double) this.orientation[0]);
            this.pitch = (float) Math.toDegrees((double) this.orientation[1]);
            this.roll = (float) Math.toDegrees((double) this.orientation[2]);
        }
    }

    public float getAzimuth() {
        if (!this.compassAvailable) {
            return 0.0f;
        }
        updateOrientation();
        return this.azimuth;
    }

    public float getPitch() {
        if (!this.compassAvailable) {
            return 0.0f;
        }
        updateOrientation();
        return this.pitch;
    }

    public float getRoll() {
        if (!this.compassAvailable) {
            return 0.0f;
        }
        updateOrientation();
        return this.roll;
    }

    public void registerSensorListeners() {
        if (this.config.useAccelerometer) {
            this.manager = (SensorManager) this.app.getService().getSystemService("sensor");
            if (this.manager.getSensorList(1).size() == 0) {
                this.accelerometerAvailable = false;
            } else {
                this.accelerometerAvailable = this.manager.registerListener(this, (Sensor) this.manager.getSensorList(1).get(0), 2);
                this.accelerometerAvailable = true;
            }
        } else {
            this.accelerometerAvailable = false;
        }
        if (this.config.useCompass) {
            if (this.manager == null) {
                this.manager = (SensorManager) this.app.getService().getSystemService("sensor");
            }
            Sensor sensor = this.manager.getDefaultSensor(2);
            if (sensor != null) {
                this.compassAvailable = this.accelerometerAvailable;
                if (this.compassAvailable) {
                    this.compassAvailable = this.manager.registerListener(this, sensor, 2);
                    return;
                }
                return;
            }
            this.compassAvailable = false;
            return;
        }
        this.compassAvailable = false;
    }

    public void unregisterSensorListeners() {
        if (this.manager != null) {
            this.manager.unregisterListener(this);
            this.manager = null;
        }
    }

    public InputProcessor getInputProcessor() {
        return this.processor;
    }

    public boolean isPeripheralAvailable(Peripheral peripheral) {
        if (peripheral == Peripheral.Accelerometer) {
            return this.accelerometerAvailable;
        }
        if (peripheral == Peripheral.Compass) {
            return this.compassAvailable;
        }
        if (peripheral == Peripheral.HardwareKeyboard) {
            return this.keyboardAvailable;
        }
        if (peripheral == Peripheral.OnscreenKeyboard) {
            return true;
        }
        if (peripheral != Peripheral.Vibrator) {
            return false;
        }
        if (this.vibrator == null) {
            return false;
        }
        return true;
    }

    public int getFreePointerIndex() {
        int len = this.realId.length;
        for (int i = 0; i < len; i++) {
            if (this.realId[i] == -1) {
                return i;
            }
        }
        int[] tmp = new int[(this.realId.length + 1)];
        System.arraycopy(this.realId, 0, tmp, 0, this.realId.length);
        this.realId = tmp;
        return tmp.length - 1;
    }

    public int lookUpPointerIndex(int pointerId) {
        int len = this.realId.length;
        for (int i = 0; i < len; i++) {
            if (this.realId[i] == pointerId) {
                return i;
            }
        }
        return -1;
    }

    public void getTextInput(TextInputListener arg0, String arg1, String arg2) {
    }

    public boolean isKeyPressed(int arg0) {
        return false;
    }

    public void setOnscreenKeyboardVisible(boolean arg0) {
    }

    public int getDeltaX() {
        return 0;
    }

    public int getDeltaX(int pointer) {
        return 0;
    }

    public int getDeltaY() {
        return 0;
    }

    public int getDeltaY(int pointer) {
        return 0;
    }

    public void getRotationMatrix(float[] matrix) {
    }

    public long getCurrentEventTime() {
        return 0;
    }

    public void setCatchMenuKey(boolean catchMenu) {
    }

    public int getRotation() {
        return 0;
    }

    public Orientation getNativeOrientation() {
        return null;
    }

    public int getOrientation() {
        return ((WindowManager) this.app.getService().getSystemService("window")).getDefaultDisplay().getOrientation();
    }

    public void setCursorCatched(boolean catched) {
    }

    public boolean isCursorCatched() {
        return false;
    }

    public void setCursorPosition(int x, int y) {
    }
}
