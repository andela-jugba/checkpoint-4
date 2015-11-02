package com.andela.checkpoint.onestep.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.andela.checkpoint.onestep.models.Location;

import java.util.Date;
import java.util.UUID;

import static com.andela.checkpoint.onestep.database.LocationDbSchema.*;

/**
 * Created by andela-jugba on 11/2/15.
 */
public class LocationCursorWrapper extends CursorWrapper {
    public LocationCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Location getLocation() {
        String uuidString = getString(getColumnIndex(LocationTable.Cols.UUID));
        String name = getString(getColumnIndex(LocationTable.Cols.NAME));
        long date = getLong(getColumnIndex(LocationTable.Cols.DATE));
        double longitude = getDouble(getColumnIndex(LocationTable.Cols.LONGITUDE));
        double latitude = getDouble(getColumnIndex(LocationTable.Cols.LATITUDE));
        int times_visited =  getInt(getColumnIndex(LocationTable.Cols.TIMES_VISITED));

        Location location = new Location(UUID.fromString(uuidString));
        location.setName(name);
        location.setDate(new Date(date));
        location.setLongitude(longitude);
        location.setLatitude(latitude);
        location.setTimesVisited(times_visited);
        return location;
    }
}
