package ch.supsi.dti.isin.meteoapp.services;

import android.os.AsyncTask;

import java.util.List;

public interface OnTaskCompleted {
    void onTaskCompleted(List<String> items);
}
