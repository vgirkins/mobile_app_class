package com.csci448.vgirkins.vgirkins_A3.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.csci448.vgirkins.vgirkins_A3.CheckInPoint;
import com.csci448.vgirkins.vgirkins_A3.database.CheckInPointDbSchema.LocationTable;

import com.google.android.gms.maps.model.LatLng;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Tori on 4/6/2018.
 */

public class CheckInPointCursorWrapper extends CursorWrapper {
    public CheckInPointCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public CheckInPoint getLocation() {
        String uuidString = getString(getColumnIndex(LocationTable.Cols.UUID));
        double lat = getDouble(getColumnIndex(LocationTable.Cols.LAT));
        double lon = getDouble(getColumnIndex(LocationTable.Cols.LONG));
        long date = getLong(getColumnIndex(LocationTable.Cols.DATETIME));
        float temp = getFloat(getColumnIndex(LocationTable.Cols.TEMPERATURE));
        String weather = getString(getColumnIndex(LocationTable.Cols.WEATHER));

        CheckInPoint checkInPoint = new CheckInPoint(UUID.fromString(uuidString));
        checkInPoint.setLatLng(new LatLng(lat, lon));
        checkInPoint.setDate(new Date(date));
        checkInPoint.setTemp(temp);
        checkInPoint.setWeather(weather);

        return checkInPoint;
    }
}
