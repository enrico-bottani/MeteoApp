package ch.supsi.dti.isin.meteoapp.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;


import ch.supsi.dti.isin.meteoapp.fragments.ListFragment;
import ch.supsi.dti.isin.meteoapp.network.Weather;

public class MainActivity extends SingleFragmentActivity {

    public static MainActivity instance;

    static final int GEOLOCATION_PERMISSION = 0;

    @Override
    protected Fragment createFragment() {
        instance = this;
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, GEOLOCATION_PERMISSION);
        }
        return new ListFragment();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case GEOLOCATION_PERMISSION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // ho ottenuto i permessi
                }
                return;
            }
        }
    }

    @Override
    public void onTaskCompleted(Weather item) {

    }
}
