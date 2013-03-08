package com.jco.entity.vehicles;

import com.jco.database.table.LocationTable;
import com.jco.entity.EntitiesUtility;
import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.LinePolygon;
import org.openstreetmap.gui.jmapviewer.MapMarkerDot;

import java.awt.*;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * Abstract Vehicle which contains
 * base information about all vehicles in project
 *
 * @author Bondoronok_P
 *         Date: 02.03.13
 */
public abstract class Vehicle implements Serializable {

//    private static final byte ROUTE_TRANSPARENT = 50;
    /**
     * Current vehicle implementation must contain it own color
     */
    protected Color vehicleColor;
    /**
     * Current vehicle implementation must contain it own route color
     */
    protected Color vehicleRouteColor;

    /**
     * Vehicle characteristics
     */
    protected String vehicleName;
    protected String vehicleType;
    protected String routeName;
    protected List<Coordinate> vehicleRoute;


    /**
     * Drawing vehicle route on {@link JMapViewer} map
     * @param viewer {@link JMapViewer} map
     */
    public void drawRoute(JMapViewer viewer, Stroke stroke) {
        viewer.addMapPolygon(new LinePolygon(getVehicleRoute(), getVehicleRouteColor(), stroke));
    }

    /**
     * Moving on current route emulation for current {@link Vehicle}
     * @param viewer {@link JMapViewer} instance
     */
    synchronized
    public void showAnimation(final JMapViewer viewer) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ThreadLocal<MapMarkerDot> threadLocal = new ThreadLocal<MapMarkerDot>();
                    for (Coordinate coordinate : Collections.synchronizedList(getVehicleRoute())) {
                        if (threadLocal.get() != null && viewer.getMapMarkerList().contains(threadLocal.get())) {
                            viewer.removeMapMarker(threadLocal.get());
                            threadLocal.remove();
                        }
                        threadLocal.set(new MapMarkerDot(getVehicleColor(), coordinate.getLat(), coordinate.getLon()));
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
    };

    public List<Coordinate> getVehicleRoute() {
        return LocationTable.getRouteByName(routeName);
    }

    public void setVehicleRoute(List<Coordinate> vehicleRoute) {
        this.vehicleRoute = vehicleRoute;
    }

    public Color getVehicleColor() {
        return vehicleColor;
    }

    public void setVehicleColor(Color vehicleColor) {
        this.vehicleColor = vehicleColor;
    }

    public Color getVehicleRouteColor() {
        return vehicleRouteColor;
    }

    public void setVehicleRouteColor(Color vehicleRouteColor) {
        this.vehicleRouteColor = vehicleRouteColor;
//                new Color(vehicleRouteColor.getRed(),
//                                            vehicleRouteColor.getGreen(),
//                                            vehicleRouteColor.getBlue(), ROUTE_TRANSPARENT);
    }

    public String getVehicleName() {
        return vehicleName;
    }

    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    @Override
    public String toString() {
        return getVehicleType();
    }
}
