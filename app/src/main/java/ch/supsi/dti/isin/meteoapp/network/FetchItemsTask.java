package ch.supsi.dti.isin.meteoapp.network;

import android.os.AsyncTask;


import ch.supsi.dti.isin.meteoapp.model.Datafetcher;

public class FetchItemsTask extends AsyncTask<String,Void, Weather> {
    private OnTaskCompleted listener;

    public FetchItemsTask(OnTaskCompleted listener) {
        this.listener = listener;
    }

    @Override
    protected Weather doInBackground(String... locationName) {

        return new Datafetcher().fetchItem(locationName);
    }
    @Override
    protected void onPostExecute(Weather item) {
        listener.onTaskCompleted(item);
    }
}
