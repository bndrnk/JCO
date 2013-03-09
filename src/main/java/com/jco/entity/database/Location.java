package com.jco.entity.database;

import java.io.Serializable;

/**
 * Class discription
 *
 * @author Bondoronok_P
 *         Date: 27.02.13
 */
public class Location implements Serializable {

    private long locationId;
    private String locationName;
    private double latitude;
    private double longitude;
    private long routeId;

    public Location(){};

    public Location(double lat, double lon) {
        this.latitude = lat;
        this.longitude = lon;
    }

    public long getRouteId() {
        return routeId;
    }

    public void setRouteId(long routeId) {
        this.routeId = routeId;
    }

    public long getLocationId() {
        return locationId;
    }

    public void setLocationId(long locationId) {
        this.locationId = locationId;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
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
}
