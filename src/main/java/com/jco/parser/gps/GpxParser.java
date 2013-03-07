package com.jco.parser.gps;

import com.jco.database.table.LocationTable;
import com.jco.entity.database.Location;
import com.jco.parser.AbstractParser;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * .GPX parser implementation
 *
 * @author Bondoronok_P
 *         Date: 26.02.13
 */
public class GpxParser extends AbstractParser {

    private static final SimpleDateFormat gpxDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

    // TAGS
    private static final String EMPTY_NODE_NAME = "";
    private static final String NAME_TAG = "name";
    private static final String TIME_TAG = "time";
    private static final String TREK_POINT_TAG = "trkpt";
    private static final String LAT_TAG = "lat";
    private static final String LON_TAG = "lon";


    @Override
    public void parse(File file) {
        if (file == null) {
            throw new IllegalArgumentException("File path for parsing is null");
        }
        for (Location location : findPoints(file)) {
            LocationTable.insert(location);
        }
    }

    private List<Location> findPoints(File file) {
        List<Location> points = null;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();

            FileInputStream fis = new FileInputStream(file);
            Document dom = builder.parse(fis);
            Element root = dom.getDocumentElement();
            NodeList items = root.getElementsByTagName(TREK_POINT_TAG);

            points = new ArrayList<Location>();

            for(int j = 0; j < items.getLength(); j++) {
                Node item = items.item(j);
                NamedNodeMap attrs = item.getAttributes();

                Location location = new Location();

                Node parentNode;
                if ((parentNode = item.getParentNode()) != null) {
                    if ((parentNode = parentNode.getParentNode()) != null) {
                        location.setTrkName(getValueByTagName(parentNode.getChildNodes(), NAME_TAG));
                    }
                }
                location.setLatitude(Double.parseDouble(attrs.getNamedItem(LAT_TAG).getTextContent()));
                location.setLongitude(Double.parseDouble(attrs.getNamedItem(LON_TAG).getTextContent()));

                points.add(location);
            }
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch(ParserConfigurationException ex) {
        } catch (SAXException ex) {
        }
        return points;
    }

    private String getValueByTagName(NodeList nodeList, String tagName) {
        if (nodeList != null) {
            for (int index = 0; index < nodeList.getLength(); index++) {
                Node currentChild = nodeList.item(index);
                if (!tagName.equals(currentChild.getNodeName())) continue;
                return currentChild.getFirstChild().getNodeValue();
            }                
        }
        return EMPTY_NODE_NAME;
    }

}
