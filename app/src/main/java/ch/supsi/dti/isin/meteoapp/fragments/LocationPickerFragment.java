package ch.supsi.dti.isin.meteoapp.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.SearchView;

import ch.supsi.dti.isin.meteoapp.R;
import ch.supsi.dti.isin.meteoapp.activities.MainActivity;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class LocationPickerFragment extends DialogFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_date, null);

        final SearchView searchView = v.findViewById(R.id.search_location);

        if (searchView != null) {
            searchView.requestFocus();
        }

        AlertDialog rtnDialog = new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle("Show").setPositiveButton("Add location", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (searchView != null)
                            sendResultBack(Activity.RESULT_OK, searchView.getQuery().toString());
                    }
                }).setNegativeButton("Annulla", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sendResultBack(Activity.RESULT_CANCELED, "");
                    }
                }).create();
        rtnDialog.show();
        rtnDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.GRAY);
        rtnDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.rgb(135, 206, 235));
        return rtnDialog;
    }


    private void sendResultBack(int resultCode, String place) {
        if (getTargetFragment() == null) {
            return;
        }
        Intent intent = new Intent();
        intent.putExtra("place", place);
        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
    }
}
