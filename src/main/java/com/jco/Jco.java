package com.jco;

import com.jco.database.table.LocationTable;
import com.jco.map.JcoMap;
import org.openstreetmap.gui.jmapviewer.*;
import org.openstreetmap.gui.jmapviewer.interfaces.MapRectangle;

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
        JcoMap map = JcoMap.loadMapByLatLon(QATAR_LAT, QATAR_LON, 10);
        List<Coordinate> coordinates = LocationTable.getRouteByTrackName("Doha1.gpx");
        drawTrackRoute(map, coordinates);
        map.setVisible(Boolean.TRUE);
//        emulateMarkerMoving(map, coordinates);
    }

    private static void emulateMarkerMoving(JcoMap map, List<Coordinate> coordinates) {
        try {
            JMapViewer mapViewer = map.getjMapViewerInstance();
            MapMarkerDot previousMapMarker = null;
            for (Coordinate coordinate : coordinates) {
                Thread.sleep(1000);
                if (previousMapMarker != null) {
                    mapViewer.removeMapMarker(previousMapMarker);
                }
                mapViewer.addMapMarker(previousMapMarker = new MapMarkerDot(coordinate.getLat(), coordinate.getLon()));
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void drawTrackRoute(JcoMap map, List<Coordinate> coordinates) {
        try {
            JMapViewer mapViewer = map.getjMapViewerInstance();
            mapViewer.addMapPolygon(new LinePolygon(coordinates, Color.RED, new BasicStroke(2)));
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

}
