package ch.supsi.dti.isin.meteoapp.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import ch.supsi.dti.isin.meteoapp.model.Location;

public class LocationCursor extends CursorWrapper {

    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public LocationCursor(Cursor cursor) {
        super(cursor);
    }

    public Location getEntry() {
        String name = getString(getColumnIndex(Schema.Location.Columns.NAME));
        return new Location(name);
    }
}