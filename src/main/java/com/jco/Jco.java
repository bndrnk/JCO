package com.jco;

import com.jco.entity.vehicle.VehicleTypeEnum;
import com.jco.map.JcoMap;

/**
 * Open Street Maps (OSM) viewer.
 *
 * @author: Bondoronok_P
 * Date: 24.02.13
 */
public class Jco {

    private static final float QATAR_LAT = 25.35f;
    private static final float QATAR_LON = 51.25f;

    public static void main(String[] args) {
        final JcoMap map = JcoMap.loadMapByLatLon(QATAR_LAT, QATAR_LON, 10);
        map.setVisible(Boolean.TRUE);
    }

}
