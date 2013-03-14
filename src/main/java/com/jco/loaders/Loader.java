package com.jco.loaders;

import com.jco.database.table.LocationTable;
import com.jco.database.table.RouteTable;
import com.jco.database.table.VehicleTable;
import com.jco.entity.database.Location;
import com.jco.entity.database.Route;
import com.jco.entity.database.Vehicle;
import com.jco.entity.vehicle.VehicleTypeEnum;
import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.LinePolygon;
import org.openstreetmap.gui.jmapviewer.MapMarkerDot;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Loader is object which load gpx data from
 * services by user selected coordinates
 *
 * @author Bondoronok_P
 *         Date: 08.03.13
 */
public abstract class Loader implements Runnable {

    private volatile List<Location> loadedData = new CopyOnWriteArrayList<Location>();
    private volatile List<Location> dataForSaving = new CopyOnWriteArrayList<Location>();
    private Location selectedLocation;
    private Location baseLocation;
    protected JMapViewer viewer;
    protected volatile Map<Long, Long> routesTime = new HashMap<Long, Long>();

    @Override
    synchronized
    public void run() {
        loadData(selectedLocation);
        Color color = new Color(getRandomValue(), getRandomValue(), getRandomValue());
        viewer.addMapMarker(new MapMarkerDot(color, selectedLocation.getLatitude(), selectedLocation.getLongitude()));
        viewer.addMapPolygon(new LinePolygon(getCoordinates(), color, new BasicStroke(4)));
    }

    /**
     * Save founded routes and locations into database
     *
     * @param routeList {@link List} of {@link Route}
     * @param vehicleType type of selected vehicle
     */
    public boolean saveRoutesToDatabase(List<Route> routeList, String vehicleType) {
        cleanTables();
        for (Route route : routeList) {
            route.setRouteTime(routesTime.get(route.getRouteId()));
            RouteTable.insert(route);
            VehicleTable.insert(new Vehicle("", vehicleType, route.getRouteId()));
        }
        for (Location location : dataForSaving) {
            LocationTable.insert(location);
        }
        return Boolean.TRUE;
    }

    /**
     * Save base location
     *
     * @param newBaseLocation {@link Location}
     * @return count of saved records
     */
    public int saveBaseLocation(Location newBaseLocation) {
        LocationTable.deleteBaseLocation();
        cleanTables();
        return LocationTable.insertNewBaseLocation(newBaseLocation);
    }

    /**
     * Returns next route id value which saved as new route in database
     * @return route id
     */
    public long getNextRouteId() {
        return RouteTable.selectMaxRouteId();
    }

    /**
     * Clear previously loaded data;
     */
    public void clearData() {
        loadedData.clear();
        dataForSaving.clear();
        routesTime.clear();
    }

    /**
     * Set new selected by user location on {@link JMapViewer}
     * This location uses in loading data process:
     * founding route between base location and selected
     * @param selectedLocation {@link Location} selected on map
     */
    public void setSelectedLocation(Location selectedLocation) {
        this.selectedLocation = selectedLocation;
    }

    /**
     * Set new base location selected by user on {@link JMapViewer}
     * This location uses in loading data process:
     * founding route between base location and selected
     * @param baseLocation {@link Location}
     */
    public void setBaseLocation(Location baseLocation) {
        this.baseLocation = baseLocation;
    }

    /**
     * Load data from service
     *
     * @param location
     */
    protected abstract void loadData(Location location);

    /**
     * Returns loaded coordinates
     *
     * @return {@link List} of {@link Coordinate} or empty list
     */
    protected List<Coordinate> getCoordinates() {
        List<Coordinate> result = new ArrayList<Coordinate>();
        for (Location currentLocation : loadedData) {
            result.add(new Coordinate(currentLocation.getLatitude(), currentLocation.getLongitude()));
        }
        return result;
    }

    /**
     * Set new list which used for drawing route on map
     * Added all elements to list for saving data
     * @param data
     */
    protected void setLoadedData(List<Location> data) {
        this.loadedData = data;
        for (Location location : data) {
            this.dataForSaving.add(location);
        }

    }

    /**
     * Returns base location instance
     *
     * @return {@link Location}
     */
    protected Location getBaseLocation() {
        return baseLocation;
    }

    /**
     * Returns random integer
     * value for built color;
     *
     * @return integer
     */
    private static int getRandomValue() {
        return (int)(255*Math.random());
    }

    /**
     * Remove data from tables before add new records
     */
    private static void cleanTables() {
        RouteTable.clean();
        LocationTable.clean();
        VehicleTable.clean();
    }
}
