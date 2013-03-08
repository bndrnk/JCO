package com.jco.loaders;

import com.jco.database.table.LocationTable;
import com.jco.entity.database.Location;
import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.JMapViewer;
import org.openstreetmap.gui.jmapviewer.LinePolygon;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Loader is object which load gpx data from
 * services by user selected coordinates
 *
 * @author Bondoronok_P
 *         Date: 08.03.13
 */
public abstract class Loader implements Runnable {

    private Iterable<? extends Location> loadedData;
    protected Coordinate selectedCoordinate;
    protected JMapViewer viewer;

    @Override
    public void run() {
        loadData(selectedCoordinate);
        viewer.addMapPolygon(new LinePolygon(getCoordinates(), Color.RED, new BasicStroke(4)));
    }

    /**
     * Load data from service
     *
     * @param coordinate
     */
    protected abstract void loadData(Coordinate coordinate);

    /**
     * Save founded locations to database
     * @param name name of saving route
     */
    public void saveToDatabase(String name, String vehicleType) {
        for (Location location : loadedData) {
            location.setRouteName(name);
            location.setVehicleType(vehicleType);
            // TODO add vehicle type in query
            LocationTable.insert(location);
        }
    }

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

    protected void setLoadedData(Iterable<? extends Location> data) {
        this.loadedData = data;
    }
}
