package com.xmodpp.location;

import android.location.LocationManager;

import com.xmodpp.core.App;

public class XModLocationManager {
    static XModLocationListener f10217a = new XModLocationListener();

    public static void jni_Start(int i, int i2, int i3) {
        LocationManager locationManager = (LocationManager) App.jni_getApplicationContext().getSystemService("location");
        if (i == 0) {
            locationManager.requestLocationUpdates("gps", (long) i2, (float) i3, f10217a);
            locationManager.addGpsStatusListener(f10217a);
        } else if (i == 1) {
            locationManager.requestLocationUpdates("network", (long) i2, (float) i3, f10217a);
        } else {
            locationManager.requestLocationUpdates("passive", (long) i2, (float) i3, f10217a);
        }
    }

    public static void jni_Stop() {
        LocationManager locationManager = (LocationManager) App.jni_getApplicationContext().getSystemService("location");
        locationManager.removeUpdates(f10217a);
        locationManager.removeGpsStatusListener(f10217a);
    }
}
