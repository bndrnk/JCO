package com.jco.entity.vehicles;

import com.jco.entity.EntitiesUtility;

import java.awt.*;


/**
 * Truck mixer instance
 *
 * @author Bondoronok_P
 *         Date: 04.03.13
 */
public class TruckMixer extends AbstractVehicle {

    public TruckMixer() {
        super();
        setVehicleColor(EntitiesUtility.TRUCK_MIXER_COLOR);
    }

    public TruckMixer(Color color) {
        this();
        setVehicleColor(color);
    }

    @Override
    public String getVehicleType() {
        return EntitiesUtility.TRUCK_MIXER_TYPE;
    }
}
