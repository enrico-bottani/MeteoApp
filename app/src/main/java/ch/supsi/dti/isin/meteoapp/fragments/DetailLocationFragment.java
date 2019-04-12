package ch.supsi.dti.isin.meteoapp.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.UUID;

import ch.supsi.dti.isin.meteoapp.R;
import ch.supsi.dti.isin.meteoapp.model.LocationsHolder;
import ch.supsi.dti.isin.meteoapp.model.Location;
import ch.supsi.dti.isin.meteoapp.network.FetchItemsTask;
import ch.supsi.dti.isin.meteoapp.network.OnTaskCompleted;
import ch.supsi.dti.isin.meteoapp.network.Weather;


public class DetailLocationFragment extends Fragment implements OnTaskCompleted {
    private static final String ARG_LOCATION_ID = "location_id";

    private Location mLocation;
    private TextView mIdTextView;

    private TextView meanTemp;
    private TextView minTemp;
    private TextView maxTemp;

    public static DetailLocationFragment newInstance(UUID locationId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_LOCATION_ID, locationId);

        DetailLocationFragment fragment = new DetailLocationFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID locationId = (UUID) getArguments().getSerializable(ARG_LOCATION_ID);
        mLocation = LocationsHolder.get(getActivity()).getLocation(locationId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_detail_location, container, false);

        mIdTextView = v.findViewById(R.id.id_textView);
        mIdTextView.setText(mLocation.getName());
        meanTemp = v.findViewById(R.id.meanTemp);
        minTemp = v.findViewById(R.id.minTemp);
        maxTemp = v.findViewById(R.id.maxTemp);

        // request to get the values
        FetchItemsTask t = new FetchItemsTask(this);
        t.execute(mLocation);

        return v;
    }

    @Override
    public void onTaskCompleted(Weather item) {
        NumberFormat formatter = new DecimalFormat("#0.00");
        meanTemp.setText(formatter.format(item.getMain().getTemp()-273.15) + " °C");
        minTemp.setText(formatter.format(item.getMain().getTemp_min()-273.15) + " °C");
        maxTemp.setText(formatter.format(item.getMain().getTemp_max()-273.15) + " °C");

    }
}

