package com.jco.animation;

import com.jco.database.table.LocationTable;
import com.jco.database.table.RouteTable;
import com.jco.database.table.VehicleTable;
import com.jco.entity.EntitiesUtility;
import com.jco.entity.database.Location;
import com.jco.entity.database.Route;
import com.jco.entity.database.Vehicle;
import com.jco.entity.vehicles.AbstractVehicle;
import com.jco.entity.vehicles.TruckMixer;
import com.jco.entity.vehicles.WaterTruck;
import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.LinePolygon;
import org.openstreetmap.gui.jmapviewer.MapMarkerDot;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Instance for run animation process
 *
 * @author Bondoronok_P
 *         Date: 07.03.13
 */
public final class AnimationRunner {

    private static AnimationRunner instance = null;
    private static JMapViewer viewer;

    private AnimationRunner() {
    };

    public static AnimationRunner getInstance(JMapViewer mapViewer) {
        if (instance == null) {
            viewer = mapViewer;
            instance = new AnimationRunner();
        }
        return instance;
    }

    public void startAnimation() {
        List<Route> routes = RouteTable.selectRoutes();
        for (Route route : routes) {
            Vehicle foundedVehicle = VehicleTable.selectVehicle(route.getRouteId());
            Color color = new Color(getRandomValue(),getRandomValue(),getRandomValue());
            if (EntitiesUtility.WATER_TRUCK_TYPE.equals(foundedVehicle.getVehicleType())) {
                initializeVehicle(new WaterTruck(color), route.getRouteId());
            } else if (EntitiesUtility.TRUCK_MIXER_TYPE.equals(foundedVehicle.getVehicleType())) {
                initializeVehicle(new TruckMixer(color), route.getRouteId());
            }
        }
    }

    private static void startVehicle(final AbstractVehicle vehicle) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ThreadLocal<MapMarkerDot> threadLocal = new ThreadLocal<MapMarkerDot>();
                    for (Location location : Collections.synchronizedList(vehicle.getVehicleRoute())) {
                        if (threadLocal.get() != null && viewer.getMapMarkerList().contains(threadLocal.get())) {
                            viewer.removeMapMarker(threadLocal.get());
                            threadLocal.remove();
                        }
                        threadLocal.set(new MapMarkerDot(vehicle.getVehicleColor(),
                                location.getLatitude(), location.getLongitude()));
                        viewer.addMapMarker(threadLocal.get());
                        Thread.sleep(EntitiesUtility.DELAY);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.setPriority(Thread.MIN_PRIORITY);
        thread.start();
    }
    private static void initializeVehicle(AbstractVehicle vehicle, long routeId) {
        vehicle.setVehicleRoute(LocationTable.selectLocations(routeId));
        drawRoute(vehicle);
        startVehicle(vehicle);
    }

    private static int getRandomValue() {
        return (int)(255*Math.random());
    }

    private static void drawRoute(AbstractVehicle vehicle) {
        List<Coordinate> coordinates = new ArrayList<Coordinate>();
        for (Location location : vehicle.getVehicleRoute()) {
            coordinates.add(new Coordinate(location.getLatitude(), location.getLongitude()));
        }
        viewer.addMapPolygon(new LinePolygon(coordinates, vehicle.getVehicleColor(), new BasicStroke(2)));
    }
}
