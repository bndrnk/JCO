package com.jco.entity.vehicle;

import com.jco.entity.database.Location;

import java.awt.*;
import java.io.Serializable;
import java.util.List;

/**
 * Abstract AbstractVehicle which contains
 * base information about all vehicle in project
 *
 * @author Bondoronok_P
 *         Date: 02.03.13
 */
public abstract class AbstractVehicle implements Serializable {

    /**
     * Current vehicle implementation must contain it own color
     */
    protected Color vehicleColor;

    /**
     * AbstractVehicle characteristics
     */
    protected String vehicleName;
    protected List<Location> vehicleRoute;

    public abstract VehicleTypeEnum getVehicleType();

    public List<Location> getVehicleRoute() {
        return this.vehicleRoute;
    }

    public void setVehicleRoute(List<Location> vehicleRoute) {
        this.vehicleRoute = vehicleRoute;
    }

    public Color getVehicleColor() {
        return vehicleColor;
    }

    public void setVehicleColor(Color vehicleColor) {
        this.vehicleColor = vehicleColor;
    }


    public String getVehicleName() {
        return vehicleName;
    }

    @Override
    public String toString() {
        return getVehicleType().getName();
    }
}
