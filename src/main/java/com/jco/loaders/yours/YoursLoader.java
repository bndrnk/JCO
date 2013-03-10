package com.jco.loaders.yours;

import com.jco.entity.database.Location;
import com.jco.loaders.Loader;
import com.jco.parser.AbstractParser;
import com.jco.parser.gps.GpxParser;
import org.openstreetmap.gui.jmapviewer.JMapViewer;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

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

    public YoursLoader(JMapViewer mapViewer) {
        this.viewer = mapViewer;
    }

    @Override
    public void loadData(Location location) {
        try {
            AbstractParser parser = new GpxParser();
            parser.parse(new URL(prepareRequestUrl(location)).openStream());

            List<Location> foundedLocations = parser.getFoundedData();
            for (Location foundedLocation : foundedLocations) {
                foundedLocation.setRouteId(location.getRouteId());
            }
            setLoadedData(foundedLocations);
            routesTime.put(location.getRouteId(), parser.getParsedTime());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Prepare request url by locations
     * @param selectedLocation {@link Location}
     * @return request url
     */
    private String prepareRequestUrl(Location selectedLocation) {
        StringBuilder builder = new StringBuilder();
        Location baseLocation = getBaseLocation();
        if (baseLocation != null && selectedLocation != null) {
            builder.append("http://www.yournavigation.org/api/1.0/gosmore.php?format=kml&flat=")
            .append(baseLocation.getLatitude())
            .append("&flon=")
            .append(baseLocation.getLongitude())
            .append("&tlat=")
            .append(selectedLocation.getLatitude())
            .append("&tlon=")
            .append(selectedLocation.getLongitude())
            .append("&v=motorcar&fast=0");
        } else {
            throw new IllegalArgumentException("Coordinates is null!");
        }
        return builder.toString();
    }
}
