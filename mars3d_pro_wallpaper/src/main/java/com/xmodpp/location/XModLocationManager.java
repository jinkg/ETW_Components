package com.xmodpp.location;

import android.location.Location;
import android.location.LocationManager;
import com.xmodpp.core.App;

public class XModLocationManager {
    static XModLocationListener listener = new XModLocationListener();

    public static void jni_Start(int provider, int minTime, int minDistance) {
        LocationManager locationManager = (LocationManager) App.jni_getApplicationContext().getSystemService("location");
        if (provider == 0) {
            locationManager.requestLocationUpdates("gps", (long) minTime, (float) minDistance, listener);
            locationManager.addGpsStatusListener(listener);
        } else if (provider == 1) {
            locationManager.requestLocationUpdates("network", (long) minTime, (float) minDistance, listener);
        } else {
            locationManager.requestLocationUpdates("passive", (long) minTime, (float) minDistance, listener);
        }
    }

    public static void jni_Stop() {
        LocationManager locationManager = (LocationManager) App.jni_getApplicationContext().getSystemService("location");
        locationManager.removeUpdates(listener);
        locationManager.removeGpsStatusListener(listener);
    }

    public static float jni_GetBearing(double fromLat, double fromLon, double toLat, double toLon) {
        Location locFrom = new Location("");
        Location locTo = new Location("");
        locFrom.setLatitude(fromLat);
        locFrom.setLongitude(fromLon);
        locTo.setLatitude(toLat);
        locTo.setLongitude(toLon);
        return locFrom.bearingTo(locTo);
    }

    public static float jni_GetDistance(double fromLat, double fromLon, double toLat, double toLon) {
        float[] distance = new float[1];
        Location.distanceBetween(fromLat, fromLon, toLat, toLon, distance);
        return distance[0];
    }
}
