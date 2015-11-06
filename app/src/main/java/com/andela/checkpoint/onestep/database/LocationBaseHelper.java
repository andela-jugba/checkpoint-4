package com.andela.checkpoint.onestep.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.andela.checkpoint.onestep.models.Location;

import static com.andela.checkpoint.onestep.database.LocationDbSchema.*;

/**
 * Created by andela-jugba on 11/2/15.
 */
public class LocationBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "locationBase.db";

    public LocationBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + LocationTable.NAME +
                        "(" + " _id integer primary key autoincrement, "
                        + LocationTable.Cols.UUID + ", "
                        + LocationTable.Cols.NAME + ", "
                        + LocationTable.Cols.DATE + ", "
                        + LocationTable.Cols.LONGITUDE + ", "
                        + LocationTable.Cols.LATITUDE + ", "
                        + LocationTable.Cols.TIMES_VISITED
                        + ")"

        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }
}
