package com.andela.checkpoint.onestep.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.andela.checkpoint.onestep.database.LocationBaseHelper;
import com.andela.checkpoint.onestep.database.LocationCursorWrapper;
import com.andela.checkpoint.onestep.database.LocationDbSchema;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.andela.checkpoint.onestep.database.LocationDbSchema.*;

/**
 * Created by andela-jugba on 11/2/15.
 */
public class LocationHelper {
    private static LocationHelper sLocationHelper;
    private List<Location> mLocations;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static LocationHelper get(Context context) {
        if (sLocationHelper == null) {
            sLocationHelper = new LocationHelper(context);
        }
        return sLocationHelper;
    }
    public LocationHelper(Context context) {
        mContext = context;
        mDatabase = new LocationBaseHelper(mContext).getWritableDatabase();
    }

    public Location getLocation(UUID id) {
        LocationCursorWrapper cursor = queryLocation(
                LocationTable.Cols.UUID+ " = ?",
                new String[]{id.toString()}
        );

        try {
            if(cursor.getCount() == 0){
                return null;
            }
            cursor.moveToFirst();
            return cursor.getLocation();
        }finally {
            cursor.close();
        }

    }

    private static ContentValues getContentValues(Location location) {
        ContentValues values = new ContentValues();
        values.put(LocationTable.Cols.UUID, location.getID().toString());
        values.put(LocationTable.Cols.NAME, location.getName());
        values.put(LocationTable.Cols.DATE, location.getDate().getTime());
        values.put(LocationTable.Cols.LONGITUDE, location.getLongitude());
        values.put(LocationTable.Cols.LATITUDE, location.getLatitude());
        values.put(LocationTable.Cols.TIMES_VISITED, location.getTimesVisted());
        return values;
    }

    public void addLocation(Location location){
        ContentValues values = getContentValues(location);
        mDatabase.insert(LocationTable.NAME, null, values);
    }

    public void updateLocation(Location location) {
        String uuidString = location.getID().toString();

        ContentValues values = getContentValues(location);

        mDatabase.update(LocationTable.NAME, values,
                LocationTable.Cols.UUID + " = ?",
                new String[]{uuidString});
    }


    private LocationCursorWrapper queryLocation(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                LocationTable.NAME,
                null,// returns all columns
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                null  // orderBy
        );
        return new LocationCursorWrapper(cursor);
    }

    public List<Location> getLocations() {
        List<Location> locations = new ArrayList<>();

        LocationCursorWrapper cursor = queryLocation(null, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                locations.add(cursor.getLocation());
                cursor.moveToNext();
            }
        }finally {
            cursor.close();
        }
        return locations;

    }

}
