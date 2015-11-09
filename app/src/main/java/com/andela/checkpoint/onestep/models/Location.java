package com.andela.checkpoint.onestep.models;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

/**
 * Created by andela-jugba on 11/2/15.
 */
public class Location extends Object{
    private String nName;
    private double nLongitude;
    private double nLatitude;
    private Date mDate;
    private UUID mID;
    private int mTimesVisited;

    public Location() {
        mID = UUID.randomUUID();
        mDate = new Date();
    }

    public Location(UUID id){
        mID = id;
        mDate = new Date();
    }

    public Location(android.location.Location location, String name, Integer mTimesVisited){
        mID = UUID.randomUUID();
        mDate = new Date();
        nLatitude = location.getLatitude();
        nLongitude = location.getLongitude();
        nName = name;
        this.mTimesVisited = mTimesVisited;
    }

    public String getName() {
        return nName;
    }

    public void setName(String nName) {
        this.nName = nName;
    }

    public double getLongitude() {
        return nLongitude;
    }

    public void setLongitude(double nLongitude) {
        this.nLongitude = nLongitude;
    }

    public double getLatitude() {
        return nLatitude;
    }

    public void setLatitude(double nLatitude) {
        this.nLatitude = nLatitude;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date mDate) {
        this.mDate = mDate;
    }

    public UUID getID() {
        return mID;
    }


    public int getTimesVisited() {
        return mTimesVisited;
    }

    public void setTimesVisited(int mTimesVisited) {
        this.mTimesVisited = mTimesVisited;
    }
}
