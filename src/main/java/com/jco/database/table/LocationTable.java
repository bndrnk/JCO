package com.jco.database.table;

import com.jco.database.DatabaseManager;
import com.jco.database.queries.QueriesUtility;
import com.jco.entity.database.Location;
import org.openstreetmap.gui.jmapviewer.Coordinate;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class discription
 *
 * @author Bondoronok_P
 *         Date: 28.02.13
 */
public class LocationTable {

    private static final String LOCATION_NAME_FIELD = "LOCATION_NAME";
    private static final String LATITUDE_FIELD = "LATITUDE";
    private static final String LONGITUDE_FIELD = "LONGITUDE";
    private static final String TIME_FIELD = "TIME";

    /**
     * Insert new location instance into database table LOCATION
     * @param location {@link Location}
     */
    public static void insert(Location location) {
        PreparedStatement statement = null;
        try {
            statement = DatabaseManager.getConnection().prepareStatement(QueriesUtility.INSERT_NEW_LOCATION);
            int index = 1;
            statement.setString(index++, location.getTrkName());
            statement.setDouble(index++, location.getLatitude());
            statement.setDouble(index++, location.getLongitude());
            statement.setLong(index++, location.getDate());

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                closeConnections(statement);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Get initialized {@link List} with {@link Coordinate}
     *
     * @param name Track name
     * @return {@link List} with {@link Coordinate} or Empty;
     */
    public static List<Coordinate> getRouteByName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("Route name is null");
        }
        List<Coordinate> resultList = new ArrayList<Coordinate>();
        PreparedStatement statement = null;
        try {
            statement = DatabaseManager.getConnection().prepareStatement(QueriesUtility.SELECT_LOCATIONS_BY_NAME);
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                double latitude = resultSet.getDouble(LATITUDE_FIELD);
                double longitude = resultSet.getDouble(LONGITUDE_FIELD);
                resultList.add(new Coordinate(latitude, longitude));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                closeConnections(statement);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return resultList;
    }

    public static void closeConnections(PreparedStatement statement) throws SQLException {
        if (statement != null) {
            statement.close();
        }
//        DatabaseManager.close();
    }

}
