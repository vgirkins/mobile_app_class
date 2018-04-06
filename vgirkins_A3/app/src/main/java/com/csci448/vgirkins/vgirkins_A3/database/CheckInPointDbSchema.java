package com.csci448.vgirkins.vgirkins_A3.database;

/**
 * Created by Tori on 4/6/2018.
 */

public class CheckInPointDbSchema {
    public static final class LocationTable {
        public static final String NAME = "CheckInPoint";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String LAT = "lat";
            public static final String LONG = "long";
            public static final String DATETIME = "datetime";
            public static final String TEMPERATURE = "temperature";
            public static final String WEATHER = "weather";
        }
    }
}
