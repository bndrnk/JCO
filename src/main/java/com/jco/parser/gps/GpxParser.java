package com.jco.parser.gps;

import com.jco.entity.database.Location;
import com.jco.parser.AbstractParser;
import com.jco.parser.ParserUtilities;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * .GPX parser implementation
 *
 * @author Bondoronok_P
 *         Date: 26.02.13
 */
public class GpxParser extends AbstractParser {

    @Override
    public void parse(InputStream stream) {
        if (stream == null) {
            throw new IllegalArgumentException("InputStream is null");
        }
        setFoundedData(findLocations(stream));
    }

    /**
     * Found locations in current <code>inputStream</code>
     * @param inputStream instantiated inputStream
     * @return {@link List} of {@link Location}
     */
    private List<Location> findLocations(InputStream inputStream) {
        List<Location> points = new ArrayList<Location>();;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            Document dom = builder.parse(inputStream);
            Element root = dom.getDocumentElement();

            NodeList items = root.getElementsByTagName(ParserUtilities.COORDINATES);

            String travelTime = root.getElementsByTagName(ParserUtilities.TIME_TAG)
                                    .item(ParserUtilities.FIRST_ITEM).getFirstChild().getNodeValue();
            long timeLong = (travelTime == null ||
                             travelTime.isEmpty()) ?
                    ParserUtilities.DEFAULT_TIME_TRAVEL : Long.valueOf(travelTime);
            Node coordinates = items.item(ParserUtilities.FIRST_ITEM);
            for (Location location : parseCoordinatesString(coordinates.getFirstChild().getNodeValue())) {
                location.setTime(timeLong);
                points.add(location);
            }
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch(ParserConfigurationException ex) {
        } catch (SAXException ex) {
        }
        return points;
    }

    /**
     * Parse string (20.124123, 51.102129 12.12314,51.123414) into Locations list
     * @param coordinatesString
     * @return {@link List} of {@link Location}
     */
    private Iterable<? extends Location> parseCoordinatesString(String coordinatesString) {
        if (coordinatesString == null || coordinatesString.isEmpty()) {
            throw new IllegalArgumentException("Cannot find any coordinate");
        }
        Pattern pattern = Pattern.compile(ParserUtilities.SPLIT_PATTERN);
        List<Location> result = new ArrayList<Location>();
        String[] valueReference;
        for(String currentValue : pattern.split(coordinatesString)) {
            if (currentValue.isEmpty()) continue;
            valueReference = currentValue.split(ParserUtilities.COMMA);
            if (valueReference.length != ParserUtilities.COORDINATE_VALUES_COUNT) continue;
            double lat = Double.valueOf(valueReference[ParserUtilities.LATITUDE_INDEX]);
            double lon = Double.valueOf(valueReference[ParserUtilities.LONGITUDE_INDEX]);
            result.add(new Location(lat, lon));
        }
        return result;
    }

}
