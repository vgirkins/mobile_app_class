package com.csci448.vgirkins.vgirkins_A3;

import com.google.android.gms.maps.model.LatLng;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Tori on 4/6/2018.
 */

public class CheckInPoint {

    private UUID mId;
    private LatLng mLatLng;
    private Date mDate;
    private float mTemp;
    private String mWeather;    // TODO check if this is correct data type

    // Do we even need this constructor?
    public CheckInPoint() {
        this(UUID.randomUUID(), new LatLng(0, 0), new Date(), 0, "");
    }

    public CheckInPoint(UUID id) {
        mId = id;
    }

    public CheckInPoint(UUID id, LatLng latLng, Date date, float temp, String weather) {
        mId = id;
        mLatLng = latLng;
        mDate = date;
        mTemp = temp;
        mWeather = weather;
    }

    public UUID getId() {
        return mId;
    }

    public void setId(UUID id) {
        this.mId = id;
    }

    public LatLng getLatLng() {
        return mLatLng;
    }

    public void setLatLng(LatLng latLng) {
        this.mLatLng = latLng;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        this.mDate = date;
    }

    public float getTemp() {
        return mTemp;
    }

    public void setTemp(float temp) {
        this.mTemp = temp;
    }

    public String getWeather() {
        return mWeather;
    }

    public void setWeather(String weather) {
        this.mWeather = weather;
    }
}
