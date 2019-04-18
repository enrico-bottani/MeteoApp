package ch.supsi.dti.isin.meteoapp.model;

import android.location.Address;
import android.location.Geocoder;
import android.util.Log;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import ch.supsi.dti.isin.meteoapp.activities.MainActivity;

public class Location {
    private UUID Id;
    private String mName;
    private android.location.Location location;
    private double lat;
    private double lon;

    public Location(String name) {
        Id = UUID.randomUUID();
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

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public Location(android.location.Location location) {
        Id = UUID.randomUUID();
        Geocoder geocoder = new Geocoder(MainActivity.instance, Locale.ITALIAN);
        try {
           // mName = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1).get(0).getLocality();
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

            this.lat=location.getLatitude();
            this.lon=location.getLongitude();
            String address = addresses.get(0).getAddressLine(0);
            String city = addresses.get(0).getAddressLine(1);
            mName=address;
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.location = location;
    }

    public android.location.Location getActualLocation() {
        return location;
    }
}