package com.jco.entity.database;

import java.io.Serializable;
import java.util.Date;

/**
 * Class discription
 *
 * @author Bondoronok_P
 *         Date: 27.02.13
 */
public class Location implements Serializable {

    private String trkName;
    private double latitude;
    private double longitude;
    private long date;


    public String getTrkName() {
        return trkName;
    }

    public void setTrkName(String trkName) {
        this.trkName = trkName;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Location{ TREK_NAME=" + trkName +
                "longitude=" + longitude +
                ", latitude=" + latitude +
                ", time=" + date +'}';
    }
}
