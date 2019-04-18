package ch.supsi.dti.isin.meteoapp.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.UUID;

import ch.supsi.dti.isin.meteoapp.R;
import ch.supsi.dti.isin.meteoapp.model.LocationsHolder;
import ch.supsi.dti.isin.meteoapp.model.Location;
import ch.supsi.dti.isin.meteoapp.network.FetchItemsTask;
import ch.supsi.dti.isin.meteoapp.network.OnTaskCompleted;
import ch.supsi.dti.isin.meteoapp.network.JSONRoot;


public class DetailLocationFragment extends Fragment implements OnTaskCompleted {
    private static final String ARG_LOCATION_ID = "location_id";

    private Location mLocation;
    private TextView mIdTextView;

    private TextView meanTemp;
    private TextView minTemp;
    private TextView maxTemp;

    private ImageView weatherImage;

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

        weatherImage = v.findViewById(R.id.weather_image);


        // request to get the values
        FetchItemsTask t = new FetchItemsTask(this);
        t.execute(mLocation);

        return v;
    }

    @Override
    public void onTaskCompleted(JSONRoot item) {
        NumberFormat formatter = new DecimalFormat("#0.00");
        switch (item.getWeather().get(0).getMain().toLowerCase()) {
            case "clouds":
                weatherImage.setImageResource(R.drawable.cloudy);
                break;
            case "drizzle":
                Log.d("[WFETCH]","It's drizzle");
                break;
            case "rain":
                weatherImage.setImageResource(R.drawable.rainy);
                break;
            case "snow":
                weatherImage.setImageResource(R.drawable.snowy);
                break;
            case "tornado":
                Log.d("[WFETCH]","It's a tornado!");
                break;
            case "clear":
                weatherImage.setImageResource(R.drawable.sunny);
                break;
            case "thunderstorm":
                Log.d("[WFETCH]","It's a thunderstorm!");
                break;
            default:
                Log.d("[WFETCH]","[Default]" + item.getWeather().get(0).getMain());
        }
        meanTemp.setText(formatter.format(item.getMain().getTemp() - 273.15) + " °C");
        minTemp.setText(formatter.format(item.getMain().getTemp_min() - 273.15) + " °C");
        maxTemp.setText(formatter.format(item.getMain().getTemp_max() - 273.15) + " °C");

    }
}

