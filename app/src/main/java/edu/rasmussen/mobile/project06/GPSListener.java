package edu.rasmussen.mobile.project06;

import android.location.LocationListener;
import android.location.Location;
import android.os.Bundle;
import android.widget.Toast;

/**
 * Created by nicho_000 on 9/5/2015.
 */
public class GPSListener extends LocationListener {
    public static double latitude;
    public static double longitude;

    @Override
    public void onLocationChange (Location loc) {
        loc.getLatitude();
        loc.getLongitude();
        latitude=loc.getLatitude();
        longitude=loc.getLongitude();
    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(getBaseContext(), "GPS is disabled.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProviderEnabled(String provider) {
        Toast.makeText(getBaseContext(), "GPS is enabled.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }
}
