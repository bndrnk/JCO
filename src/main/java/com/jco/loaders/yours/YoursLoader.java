package com.jco.loaders.yours;

import com.jco.database.table.LocationTable;
import com.jco.entity.database.Location;
import com.jco.loaders.Loader;
import com.jco.parser.AbstractParser;
import com.jco.parser.gps.GpxParser;
import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.JMapViewer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Using http://www.yournavigation.org route service
 * for gpx data loading
 *
 * Given parameters list from OSM Wiki.
 *
 * Available parameters are described below. Only the location
 * parameters are required. All other parameters are optional,
 * they will use the default value when omitted.
 *
 * flat = latitude of the starting location.
 * flon = longitude of the starting location.
 * tlat = latitude of the end location.
 * tlon = longitude of the end location.
 * v = the type of transport, possible options are: motorcar, bicycle or foot. Default is: motorcar.
 * fast = 1 selects the fastest route, 0 the shortest route. Default is: 1.
 * layer = determines which Gosmore instance is used to calculate the route.
 *  - Provide mapnik for normal routing using car, bicycle or foot.
 *  - Provide cn for using bicycle routing using cycle route networks only. Default is: mapnik.
 * format = specifies the format (KML or geoJSON) in which the route result is being sent back to the client.
 *    This can either be kml or geojson. Default is: kml.
 * geometry = enables/disables adding the route geometry in the output.
 *    Options are 1 to include the route geometry in the output or 0 to exclude it. Default is: 1.
 * distance = specifies which algorithm is used to calculate the distance.
 *    Options are v for Vicenty, gc for simplified Great Circle,
 *    h for Haversine Law, cs for Cosine Law. Default is: v.
 *    Implemented using the geography class from Simon Holywell.
 * instructions = enbles/disables adding driving instructions in the output.
 *    Options are 1 to include driving directions, 0 to disable driving directions. Default is 0.
 * lang = specifies the language code in which the routing directions are returned.
 *    Default is en (English).
 * @author Bondoronok_P
 *         Date: 08.03.13
 */
public class YoursLoader extends Loader {

    public YoursLoader(JMapViewer mapViewer, Coordinate coordinate) {
        this.viewer = mapViewer;
        this.selectedCoordinate = coordinate;
    }

    @Override
    public void loadData(Coordinate coordinate) {
        // todo select base location in another place
        Location baseLocation = LocationTable.selectBaseCoordinate();
        try {
            AbstractParser parser = new GpxParser();
            parser.parse(new URL(prepareRequestUrl(baseLocation, coordinate)).openStream());
            setLoadedData(parser.getFoundedData());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String prepareRequestUrl(Location startCoordinate, Coordinate finishCoordinate) {
        StringBuilder builder = new StringBuilder();
        if (startCoordinate != null && finishCoordinate != null) {
            builder.append("http://www.yournavigation.org/api/1.0/gosmore.php?format=kml&flat=")
            .append(startCoordinate.getLatitude())
            .append("&flon=")
            .append(startCoordinate.getLongitude())
            .append("&tlat=")
            .append(finishCoordinate.getLat())
            .append("&tlon=")
            .append(finishCoordinate.getLon())
            .append("&v=motorcar&fast=0");
        } else {
            throw new IllegalArgumentException("Coordinates is null!");
        }
        return builder.toString();
    }
}
