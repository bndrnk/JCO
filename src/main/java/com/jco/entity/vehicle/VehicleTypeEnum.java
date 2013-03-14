package com.jco.entity.vehicle;

/**
 * Class discription
 *
 * @author Bondoronok_P
 *         Date: 12.03.13
 */
public enum VehicleTypeEnum {
    /**
     * Truck mixer vehicle type
     */
    TRUCK_MIXER("Truck Mixer"),

    /**
     * Water truck vehicle type
     */
    WATER_TRUCK("Water truck");

    private String name;


    VehicleTypeEnum(String name) {
        this.name = name;
    }


    /**
     * Returns vehicle type name
     * @return
     */
    public String getName() {
        return name;
    }
}
