package com.jco.entity.vehicles;

import com.jco.entity.EntitiesUtility;

import java.awt.*;


/**
 * Class discription
 *
 * @author Bondoronok_P
 *         Date: 04.03.13
 */
public class WaterTruck extends Vehicle {

    public WaterTruck() {
        super();
        setVehicleType(EntitiesUtility.WATER_TRUCK_TYPE);
        setVehicleColor(EntitiesUtility.WATER_TRUCK_COLOR);
        setVehicleRouteColor(EntitiesUtility.WATER_TRUCK_ROUTE_COLOR);
    }

    public WaterTruck(Color color) {
        this();
        setVehicleColor(color);
        setVehicleRouteColor(color);
    }
}
