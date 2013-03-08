package com.jco.entity.database;

import java.io.Serializable;

/**
 * Class discription
 *
 * @author Bondoronok_P
 *         Date: 27.02.13
 */
public class Location implements Serializable {

    private String routeName;
    private String vehicleType;
    private double latitude;
    private double longitude;
    private long time;

    public Location(){};

    public Location(double lat, double lon) {
        this.latitude = lat;
        this.longitude = lon;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
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

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Location{ TREK_NAME=" + routeName +
                "longitude=" + longitude +
                ", latitude=" + latitude +
                ", time=" + time +'}';
    }
}
