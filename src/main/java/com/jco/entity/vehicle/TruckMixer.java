package com.jco.entity.vehicle;

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
        setVehicleColor(EntitiesUtility.DEFAULT_COLOR);
    }

    public TruckMixer(Color color) {
        this();
        setVehicleColor(color);
    }

    @Override
    public VehicleTypeEnum getVehicleType() {
        return VehicleTypeEnum.TRUCK_MIXER;
    }
}
