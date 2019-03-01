package ch.supsi.dti.isin.meteoapp.model;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import ch.supsi.dti.isin.meteoapp.activities.MainActivity;
import ch.supsi.dti.isin.meteoapp.fragments.ListFragment;
import io.nlopez.smartlocation.OnLocationUpdatedListener;

public class LocationsHolder implements OnLocationUpdatedListener {

    private static LocationsHolder sLocationsHolder;
    private List<Location> mLocations;
    private List<ListFragment> listFragment;

    public static LocationsHolder get(Context context) {
        if (sLocationsHolder == null)
            sLocationsHolder = new LocationsHolder(context);

        return sLocationsHolder;
    }

    private LocationsHolder(Context context) {
        mLocations = new ArrayList<>();
        mLocations.add(new Location("Getting GPS location"));
        for (int i = 1; i < 10; i++) {
            Location location = new Location("Location # " + i);
            mLocations.add(location);
        }
        listFragment = new ArrayList<>();
    }

    public List<Location> getLocations() {
        return mLocations;
    }

    public Location getLocation(UUID id) {
        for (Location location : mLocations) {
            if (location.getId().equals(id))
                return location;
        }

        return null;
    }

    @Override
    public void onLocationUpdated(android.location.Location location) {
        mLocations.set(0, new Location(location));
        for (ListFragment listFragment : listFragment) listFragment.updateAdapter();
    }

    public void addListener(ListFragment listFragment) {
        this.listFragment.add(listFragment);
    }
}
