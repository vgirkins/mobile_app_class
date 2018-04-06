package com.csci448.vgirkins.vgirkins_A3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.csci448.vgirkins.vgirkins_A3.database.CheckInPointBaseHelper;
import com.csci448.vgirkins.vgirkins_A3.database.CheckInPointCursorWrapper;
import com.csci448.vgirkins.vgirkins_A3.database.CheckInPointDbSchema.LocationTable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Tori on 4/6/2018.
 */

public class LocationLab {
    private static LocationLab sLocationLab;

    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static LocationLab get(Context context) {
        if (sLocationLab == null) {
            sLocationLab = new LocationLab(context);
        }
        return sLocationLab;
    }

    private LocationLab(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new CheckInPointBaseHelper(mContext).getWritableDatabase();
    }

    public void addLocation(CheckInPoint l) {
        ContentValues values = getContentValues(l);

        mDatabase.insert(LocationTable.NAME, null, values);
    }

    public List<CheckInPoint> getLocations() {
        List<CheckInPoint> locations = new ArrayList<>();

        CheckInPointCursorWrapper cursor = queryLocations(null, null);

        try {
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

    public CheckInPoint getLocation(UUID id) {
        CheckInPointCursorWrapper cursor = queryLocations(
                LocationTable.Cols.UUID + " = ?",
                new String[] {id.toString()}
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

    private CheckInPointCursorWrapper queryLocations(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                LocationTable.NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );

        return new CheckInPointCursorWrapper(cursor);
    }

    private static ContentValues getContentValues(CheckInPoint location /*location*/) {
        ContentValues values = new ContentValues();
        values.put(LocationTable.Cols.UUID, location.getId().toString());
        values.put(LocationTable.Cols.LAT, location.getLatLng().latitude);
        values.put(LocationTable.Cols.LONG, location.getLatLng().longitude);
        values.put(LocationTable.Cols.DATETIME, location.getDate().getTime());
        values.put(LocationTable.Cols.TEMPERATURE, location.getTemp());
        values.put(LocationTable.Cols.WEATHER, location.getWeather());

        return values;
    }
}
