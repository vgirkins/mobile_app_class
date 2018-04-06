package com.csci448.vgirkins.vgirkins_A3.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.csci448.vgirkins.vgirkins_A3.database.CheckInPointDbSchema.LocationTable;

/**
 * Created by Tori on 4/6/2018.
 */

public class CheckInPointBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "locationBase.db";

    public CheckInPointBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + LocationTable.NAME + "(" +
            " _id integer primary key autoincrement, " +
            LocationTable.Cols.UUID + ", " +
            LocationTable.Cols.LAT + ", " +
            LocationTable.Cols.LONG + ", " +
            LocationTable.Cols.DATETIME + ", " +
            LocationTable.Cols.TEMPERATURE + ", " +
            LocationTable.Cols.WEATHER +
            ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
