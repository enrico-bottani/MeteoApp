package ch.supsi.dti.isin.meteoapp.activities;

import android.location.Geocoder;
import android.support.v4.app.Fragment;

import java.util.Locale;

import ch.supsi.dti.isin.meteoapp.fragments.ListFragment;

public class MainActivity extends SingleFragmentActivity {

    public static MainActivity instance;
    @Override
    protected Fragment createFragment() {
        instance = this;
        return new ListFragment();
    }
}
