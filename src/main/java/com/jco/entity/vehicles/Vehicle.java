package com.jco.entity.vehicles;

import org.openstreetmap.gui.jmapviewer.JMapViewer;

import java.awt.*;
import java.io.Serializable;

/**
 * Abstract Vehicle which contains
 * base information about all vehicles in project
 *
 * @author Bondoronok_P
 *         Date: 02.03.13
 */
public abstract class Vehicle implements Serializable {

    /**
     * Current vehicle implementation must contain it own color
     */
    protected Color vehicleColor;
    /**
     * Current vehicle implementation must contain it own route color
     */
    protected Color vehicleRouteColor;

    /**
     * Vehicle characteristics
     */
    protected String vehicleName;
    protected String vehicleType;

    /**
     * Drawing vehicle route on {@link JMapViewer} map
     * @param viewer {@link JMapViewer} map
     */
    public abstract void drawRoute(JMapViewer viewer);

    /**
     * Moving on current route emulation for current {@link Vehicle}
     *
     * @param viewer {@link JMapViewer} map
     */
    public abstract void movingEmulation(JMapViewer viewer);

    /**
     *  You should set {@link Vehicle} color
     */
    protected abstract void setVehicleColor();

    /**
     * You should set {@link Vehicle} route color
     */
    protected abstract void setVehicleRouteColor();

    public Color getVehicleColor() {
        return vehicleColor;
    }

    public Color getVehicleRouteColor() {
        return vehicleRouteColor;
    }

    public String getVehicleName() {
        return vehicleName;
    }

    public String getVehicleType() {
        return vehicleType;
    }
}
