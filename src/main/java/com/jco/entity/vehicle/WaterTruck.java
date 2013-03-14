package com.jco.entity.vehicle;

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
        setVehicleColor(EntitiesUtility.DEFAULT_COLOR);
    }

    public WaterTruck(Color color) {
        this();
        setVehicleColor(color);
    }

    @Override
    public VehicleTypeEnum getVehicleType() {
        return VehicleTypeEnum.WATER_TRUCK;
    }
}
