package edu.rasmussen.mobile.project06;

import android.content.Context;
import android.location.LocationListener;
import android.location.Location;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Created by nicho_000 on 9/5/2015.
 */

public class GPSListener implements LocationListener {
    public static double latitude;
    public static double longitude;
    Context tContext = getApplicationContext();
    String tDisabledText = "GPS is Disabled";
    String tEnabledText = "GPS is Enabled";
    int tDuration = Toast.LENGTH_SHORT;


    @Override
    public void onProviderDisabled(String provider) {
        Toast tToast = Toast.makeText(tContext, tDisabledText, tDuration);
        tToast.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL, 0, 0);
        tToast.show();
    }

    @Override
    public void onProviderEnabled(String provider) {
        Toast tToast = Toast.makeText(tContext, tEnabledText, tDuration);
        tToast.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL, 0, 0);
        tToast.show();
    }

    @Override
    public void onLocationChanged(Location location) {
        location.getLatitude();
        location.getLongitude();
        latitude=location.getLatitude();
        longitude=location.getLongitude();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }
}
