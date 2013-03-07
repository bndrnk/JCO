package com.jco.animation;

import com.jco.entity.vehicles.TruckMixer;
import com.jco.entity.vehicles.Vehicle;
import com.jco.entity.vehicles.WaterTruck;
import org.openstreetmap.gui.jmapviewer.JMapViewer;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Instance for run animation process
 *
 * @author Bondoronok_P
 *         Date: 07.03.13
 */
// TODO should be runnable?
public final class AnimationRunner {

    private static AnimationRunner instance = null;

    private AnimationRunner() {
    };

    /**
     *
     * @param viewer {@link JMapViewer} instance
     */
    public static void runAnimation(JMapViewer viewer) {
        List<Vehicle> vehicles = new ArrayList<Vehicle>(Arrays.asList(new TruckMixer("Doha1.gpx"),
                new WaterTruck("doha_Ras_Laffan_port.gpx"), new WaterTruck(Color.MAGENTA, "17_JAN_12_09.42.24.gpx"),
                new TruckMixer(Color.RED, "Nepal_Bhutan.gpx")));
        for (Vehicle vehicle: vehicles) {
            vehicle.drawRoute(viewer, new BasicStroke(2));
            vehicle.showAnimation(viewer);
        }
    }

}
