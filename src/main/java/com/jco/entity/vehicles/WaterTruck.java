package com.jco.entity.vehicles;

import com.jco.entity.EntitiesUtility;

import java.awt.*;


/**
 * Class discription
 *
 * @author Bondoronok_P
 *         Date: 04.03.13
 */
public class WaterTruck extends AbstractVehicle {

    public WaterTruck() {
        super();
        setVehicleColor(EntitiesUtility.WATER_TRUCK_COLOR);
    }

    public WaterTruck(Color color) {
        this();
        setVehicleColor(color);
    }

    @Override
    public String getVehicleType() {
        return EntitiesUtility.WATER_TRUCK_TYPE;
    }
}
