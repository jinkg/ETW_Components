package com.xmodpp.location;

import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.GpsStatus.Listener;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import com.xmodpp.core.App;

public class XModLocationListener implements LocationListener, Listener {
    GpsStatus gpsStatus;

    public static native void nativeOnGpsStatusUpdate(int i);

    public static native void nativeOnLocationUpdate(int i, double d, double d2, double d3, double d4);

    public void onLocationChanged(Location location) {
        double altitude;
        double d = Double.NaN;
        int provider = -1;
        if (location.getProvider().equals("gps")) {
            provider = 0;
        } else if (location.getProvider().equals("network")) {
            provider = 1;
        } else if (location.getProvider().equals("passive")) {
            provider = 2;
        }
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        if (location.hasAltitude()) {
            altitude = location.getAltitude();
        } else {
            altitude = Double.NaN;
        }
        if (location.hasAccuracy()) {
            d = (double) location.getAccuracy();
        }
        nativeOnLocationUpdate(provider, latitude, longitude, altitude, d);
    }

    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    public void onProviderEnabled(String provider) {
    }

    public void onProviderDisabled(String provider) {
    }

    public void onGpsStatusChanged(int event) {
        LocationManager locationManager = (LocationManager) App.jni_getApplicationContext().getSystemService("location");
        if (event == 4) {
            this.gpsStatus = locationManager.getGpsStatus(this.gpsStatus);
            int nSatellites = 0;
            for (GpsSatellite satellite : this.gpsStatus.getSatellites()) {
                nSatellites++;
            }
            nativeOnGpsStatusUpdate(nSatellites);
        }
    }
}
