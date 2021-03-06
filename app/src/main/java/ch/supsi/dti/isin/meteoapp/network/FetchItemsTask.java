package ch.supsi.dti.isin.meteoapp.network;

import android.os.AsyncTask;


import ch.supsi.dti.isin.meteoapp.model.Datafetcher;
import ch.supsi.dti.isin.meteoapp.model.Location;

public class FetchItemsTask extends AsyncTask<Location,Void, JSONRoot> {
    private OnTaskCompleted listener;

    public FetchItemsTask(OnTaskCompleted listener) {
        this.listener = listener;
    }

    @Override
    protected JSONRoot doInBackground(Location... locationName) {

        return new Datafetcher().fetchItem(locationName[0]);
    }
    @Override
    protected void onPostExecute(JSONRoot item) {
        listener.onTaskCompleted(item);
    }
}
