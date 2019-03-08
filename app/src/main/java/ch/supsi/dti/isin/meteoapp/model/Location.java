package ch.supsi.dti.isin.meteoapp.model;

import android.location.Geocoder;

import java.io.IOException;
import java.util.Locale;
import java.util.UUID;

import ch.supsi.dti.isin.meteoapp.activities.MainActivity;

public class Location {
    private UUID Id;
    private String mName;
    private android.location.Location location;

    public Location(String name) {
        mName = name;
    }

    public UUID getId() {
        return Id;
    }

    public void setId(UUID id) {
        Id = id;
    }

    public String getName() {
        return mName;
    }

    public Location(android.location.Location location) {
        Id = UUID.randomUUID();
        Geocoder geocoder = new Geocoder(MainActivity.instance, Locale.ITALIAN);
        try {
            mName = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1).get(0).getLocality();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.location = location;
    }

    public android.location.Location getActualLocation() {
        return location;
    }
}