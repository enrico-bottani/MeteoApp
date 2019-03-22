package ch.supsi.dti.isin.meteoapp.activities;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import ch.supsi.dti.isin.meteoapp.R;
import ch.supsi.dti.isin.meteoapp.database.DatabaseHelper;

public abstract class SingleFragmentActivity extends AppCompatActivity {

    SQLiteDatabase db = null;
    public static DatabaseHelper helper = null;

    protected abstract Fragment createFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_single_fragment);
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container);
        if (fragment == null) {
            fragment = createFragment();
            fm.beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }

        // create database
        helper = new DatabaseHelper(this);
        db = helper.getWritableDatabase();

        //helper.insertLocation("FakeLocation");
        //helper.selectLocation("Manno");
    }

    @Override
    protected void onStop() {
        super.onStop();

        if(db!=null){
            db.close();
        }
    }
}