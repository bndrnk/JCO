package com.jco;

import com.jco.entity.vehicles.TruckMixer;
import com.jco.entity.vehicles.Vehicle;
import com.jco.entity.vehicles.WaterTruck;
import com.jco.map.JcoMap;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        List<Vehicle> vehicles = new ArrayList<Vehicle>(Arrays.asList(new TruckMixer("Doha1.gpx"),
                new WaterTruck("doha_Ras_Laffan_port.gpx"), new WaterTruck(Color.MAGENTA, "17_JAN_12_09.42.24.gpx"),
                new TruckMixer(Color.RED, "Nepal_Bhutan.gpx"),
                new WaterTruck(Color.CYAN, "62.gpx"),
                new TruckMixer(Color.GRAY,"75.gpx"),
                new TruckMixer(Color.YELLOW, "69.gpx"),
                new TruckMixer(Color.BLACK, "66.gpx")));
        map.setVisible(Boolean.TRUE);

        for (Vehicle vehicle: vehicles) {
            vehicle.drawRoute(map.getjMapViewerInstance(), new BasicStroke(2));
            vehicle.showAnimation(map.getjMapViewerInstance());
        }

    }

}
