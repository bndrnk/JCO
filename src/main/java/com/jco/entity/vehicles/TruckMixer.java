package com.jco.entity.vehicles;

import com.jco.entity.EntitiesUtility;

import java.awt.*;


/**
 * Truck mixer instance
 *
 * @author Bondoronok_P
 *         Date: 04.03.13
 */
public class TruckMixer extends Vehicle {

    public TruckMixer() {
        super();
        setVehicleType(EntitiesUtility.TRUCK_MIXER_TYPE);
        setVehicleColor(EntitiesUtility.TRUCK_MIXER_COLOR);
        setVehicleRouteColor(EntitiesUtility.TRUCK_MIXER_ROUTE_COLOR);
    }

    public TruckMixer(Color color) {
        this();
        setVehicleColor(color);
        setVehicleRouteColor(color);
    }

}
