package com.xmodpp.location;

import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.GpsStatus.Listener;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import com.xmodpp.core.App;

public class XModLocationListener implements Listener, LocationListener {
    GpsStatus f10216a;

    public static native void nativeOnGpsStatusUpdate(int i);

    public static native void nativeOnLocationUpdate(int i, double d, double d2, double d3, double d4);

    public void onGpsStatusChanged(int i) {
        LocationManager locationManager = (LocationManager) App.jni_getApplicationContext().getSystemService("location");
        if (i == 4) {
            this.f10216a = locationManager.getGpsStatus(this.f10216a);
            int i2 = 0;
            for (GpsSatellite gpsSatellite : this.f10216a.getSatellites()) {
                i2++;
            }
            nativeOnGpsStatusUpdate(i2);
        }
    }

    public void onLocationChanged(Location location) {
        double d = Double.NaN;
        int i = -1;
        if (location.getProvider().equals("gps")) {
            i = 0;
        } else if (location.getProvider().equals("network")) {
            i = 1;
        } else if (location.getProvider().equals("passive")) {
            i = 2;
        }
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        double altitude = location.hasAltitude() ? location.getAltitude() : Double.NaN;
        if (location.hasAccuracy()) {
            d = (double) location.getAccuracy();
        }
        nativeOnLocationUpdate(i, latitude, longitude, altitude, d);
    }

    public void onProviderDisabled(String str) {
    }

    public void onProviderEnabled(String str) {
    }

    public void onStatusChanged(String str, int i, Bundle bundle) {
    }
}
