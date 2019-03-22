package ch.supsi.dti.isin.meteoapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import ch.supsi.dti.isin.meteoapp.model.Location;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "locations.db";
    private static final int VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+Schema.Location.NAME+"(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                Schema.Location.Columns.NAME+ ");"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Brutal delete
        db.delete(Schema.Location.NAME, null, null);

        // Recreate
        db.execSQL("CREATE TABLE "+Schema.Location.NAME+"(" + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + Schema.Location.Columns.NAME+ ");");
    }

    public void insertLocation(String name){
        SQLiteDatabase db = this.getReadableDatabase();

        ContentValues values=new ContentValues();
        values.put(Schema.Location.Columns.NAME, name);

        db.insert(Schema.Location.NAME, null, values);
    }

    public List<Location> selectLocation(){
        SQLiteDatabase db = this.getReadableDatabase();
        List<Location> locations=new ArrayList<>();
        //Cursor c = db.query(Schema.Location.NAME, null, null, null, null, null, null);
        String query="SELECT * FROM "+Schema.Location.NAME+";";
        Cursor c = db.rawQuery(query,null);
        LocationCursor cursor = new LocationCursor(c);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Location entry = cursor.getEntry();

                System.out.println(entry.getName());
                locations.add(entry);

                cursor.moveToNext();
            }

            return locations;
        } finally {
            cursor.close();
        }
    }
}