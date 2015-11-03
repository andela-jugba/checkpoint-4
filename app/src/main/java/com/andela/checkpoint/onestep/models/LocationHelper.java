package com.andela.checkpoint.onestep.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.andela.checkpoint.onestep.database.LocationBaseHelper;
import com.andela.checkpoint.onestep.database.LocationCursorWrapper;
import com.andela.checkpoint.onestep.database.LocationDbSchema;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static com.andela.checkpoint.onestep.database.LocationDbSchema.*;

/**
 * Created by andela-jugba on 11/2/15.
 */
public class LocationHelper {
    private static LocationHelper sLocationHelper;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static LocationHelper get(Context context) {
        if (sLocationHelper == null) {
            sLocationHelper = new LocationHelper(context);
        }
        return sLocationHelper;
    }

    public LocationHelper(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new LocationBaseHelper(mContext).getWritableDatabase();

//        Location testLocationOne = new Location();
//        testLocationOne.setName("M55");
//        testLocationOne.setLatitude(6.777);
//        testLocationOne.setLongitude(5.66);
//        testLocationOne.setTimesVisited(1);
//        this.addLocation(testLocationOne);
//
//        Location testLocationTwo = new Location();
//        testLocationTwo.setName("Yaba");
//        testLocationTwo.setLatitude(6.567);
//        testLocationTwo.setLongitude(5.645);
//        testLocationTwo.setTimesVisited(1);
//        this.addLocation(testLocationTwo);
//
//        Location testLocationThree = new Location();
//        testLocationThree.setName("Amity");
//        testLocationThree.setLatitude(7.8);
//        testLocationThree.setLongitude(7.88);
//        testLocationThree.setTimesVisited(1);
//        this.addLocation(testLocationThree);
    }

    public Location getLocation(UUID id) {
        LocationCursorWrapper cursor = queryLocation(
                LocationTable.Cols.UUID + " = ?",
                new String[]{id.toString()}
        );

        try {
            if (cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();
            return cursor.getLocation();
        } finally {
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

    public void addLocation(Location location) {
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

    /**
     * Queries for all locations in the Db
     * @return a List of locations
     */
    public List<Location> getLocations() {
        List<Location> locations = new ArrayList<>();

        LocationCursorWrapper cursor = queryLocation(null, null);
        try {
            if (cursor.getCount() == 0) return null;
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                locations.add(cursor.getLocation());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return locations;

    }

    /**
     * Queries for unique date
     * @return a set of dates
     */
    public HashMap<String, List<Location>> getLocationByDate(){
        DateCriterion dateCriteria = new DateCriterion();
        Set<String> dates = new HashSet<>();
        List<Location> locations = new ArrayList<>();

        HashMap<String, List<Location>> locationByDate = new HashMap<>();

        DateFormat dateFormat = new SimpleDateFormat("EEEE dd,MMM,yyyy");
        LocationCursorWrapper cursor = queryLocation(null, null);
        try {
            if (cursor.getCount() == 0) return null;
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Location temp = cursor.getLocation();
                dates.add(dateFormat.format(temp.getDate()));
                locations.add(temp);
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        for (String date: dates){
            List<Location> tempLocations = dateCriteria.meetCriteria(locations,date);
            locationByDate.put(date,tempLocations);
        }

        return locationByDate;
    }

    /**
     * deletes a location from the DB
     * @param location
     */
    public void deleteLocation(Location location) {
        String uuidString = location.getID().toString();

        ContentValues values = getContentValues(location);
        mDatabase.delete(LocationTable.NAME, LocationTable.Cols.UUID + " = ?",
                new String[]{uuidString});
    }



}
