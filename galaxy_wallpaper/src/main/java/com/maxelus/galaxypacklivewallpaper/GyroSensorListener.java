package com.maxelus.galaxypacklivewallpaper;

import android.annotation.SuppressLint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;

@SuppressLint({"NewApi"})
public class GyroSensorListener implements SensorEventListener {
    public static final Matrix3 curentRotationMatrix = new Matrix3();
    public static final Matrix4 curentRotationMatrix4 = new Matrix4();
    public static final Matrix3 deltaRotationMatrix = new Matrix3();
    public static final Matrix4 deltaRotationMatrix4 = new Matrix4();
    private final float EPSILON = 0.1f;
    private final float NS2S = 1.0E-9f;
    private final float[] deltaRotationVector = new float[4];
    private float timestamp = 0.0f;

    GyroSensorListener() {
    }

    public void onAccuracyChanged(Sensor arg0, int arg1) {
    }

    public void onSensorChanged(SensorEvent event) {
        if (this.timestamp != 0.0f) {
            float dT = (((float) event.timestamp) - this.timestamp) * 1.0E-9f;
            float axisX = event.values[0];
            float axisY = event.values[1];
            float axisZ = event.values[2];
            float omegaMagnitude = (float) Math.sqrt((double) (((axisX * axisX) + (axisY * axisY)) + (axisZ * axisZ)));
            if (omegaMagnitude > 0.1f) {
                axisX /= omegaMagnitude;
                axisY /= omegaMagnitude;
                axisZ /= omegaMagnitude;
            }
            float thetaOverTwo = (omegaMagnitude * dT) / 2.0f;
            float sinThetaOverTwo = (float) Math.sin((double) thetaOverTwo);
            float cosThetaOverTwo = (float) Math.cos((double) thetaOverTwo);
            this.deltaRotationVector[0] = sinThetaOverTwo * axisX;
            this.deltaRotationVector[1] = sinThetaOverTwo * axisY;
            this.deltaRotationVector[2] = sinThetaOverTwo * axisZ;
            this.deltaRotationVector[3] = cosThetaOverTwo;
            SensorManager.getRotationMatrixFromVector(deltaRotationMatrix.val, this.deltaRotationVector);
            curentRotationMatrix.mul(deltaRotationMatrix);
            deltaRotationMatrix4.set(deltaRotationMatrix);
            curentRotationMatrix4.set(curentRotationMatrix);
        }
        this.timestamp = (float) event.timestamp;
    }
}
