package com.csci448.vgirkins.criminalintent;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Tori on 2/4/2018.
 */

public class Crime {

    private UUID mId;
    private String mTitle;
    private Date mDate;
    private boolean mSolved;

    private String mSuspect;
    private String mPhoneNumber;

    public Crime() {
        mId = UUID.randomUUID();
        mDate = new Date();
    }

    public UUID getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date mDate) {
        this.mDate = mDate;
    }

    public boolean isSolved() {
        return mSolved;
    }

    public void setSolved(boolean mSolved) {
        this.mSolved = mSolved;
    }

    public String getSuspect() {
        return mSuspect;
    }

    public void setSuspect(String suspect) {
        mSuspect = suspect;
    }

    public String getPhoneNumber() { return mPhoneNumber; }

    public void setPhoneNumber(String PhoneNumber) { this.mPhoneNumber = PhoneNumber; }
}
