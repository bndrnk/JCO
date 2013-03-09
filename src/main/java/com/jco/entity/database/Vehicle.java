package com.jco.entity.database;

/**
 * Class discription
 *
 * @author Bondoronok_P
 *         Date: 09.03.13
 */
public class Vehicle {

    private long vehicleId;
    private String vehicleName;
    private String vehicleType;
    private long routeId;

    public Vehicle() {

    }

    public Vehicle(String vehicleName, String vehicleType, long routeId) {
        this.vehicleName = vehicleName;
        this.vehicleType = vehicleType;
        this.routeId = routeId;
    }

    public long getRouteId() {
        return routeId;
    }

    public void setRouteId(long routeId) {
        this.routeId = routeId;
    }

    public long getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(long vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getVehicleName() {
        return vehicleName;
    }

    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }
}
