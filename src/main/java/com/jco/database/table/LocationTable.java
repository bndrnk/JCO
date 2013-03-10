package com.jco.database.table;

import com.jco.database.DatabaseManager;
import com.jco.database.queries.QueriesUtility;
import com.jco.entity.database.Location;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Class discription
 *
 * @author Bondoronok_P
 *         Date: 28.02.13
 */                        // TODO handle try/catch blocks
public class LocationTable {

    private static final String LATITUDE_FIELD = "LATITUDE";
    private static final String LONGITUDE_FIELD = "LONGITUDE";
    private static final String ROUTE_ID_FIELD = "ROUTE_ID";
    private static final String BASE = "BASE";

    /**
     * Insert new location instance into database table LOCATION
     * @param location {@link Location}
     *
     * @return count of updated rows
     */
    public static int insert(Location location) {
        PreparedStatement statement = null;
        try {
            statement = DatabaseManager.getConnection().prepareStatement(QueriesUtility.INSERT_NEW_LOCATION);
            int index = 1;
            statement.setString(index++, location.getLocationName());
            statement.setDouble(index++, location.getLatitude());
            statement.setDouble(index++, location.getLongitude());
            statement.setLong(index++, location.getRouteId());
            return statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                closeConnections(statement);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    public static Location selectBaseLocation() {
        Location baseLocation = null;
        Statement statement = null;
        try {
            statement = DatabaseManager.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(QueriesUtility.BASE_LOCATION_QUERY);
            if (resultSet.next()) {
                baseLocation = new Location();
                baseLocation.setLocationName(BASE);
                baseLocation.setLatitude(resultSet.getDouble(LATITUDE_FIELD));
                baseLocation.setLongitude(resultSet.getDouble(LONGITUDE_FIELD));
            } else {
                throw new SQLException("Cannot find base location in database!");
            }
        } catch (SQLException e) {
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return baseLocation;
    }

    /**
     * Get initialized {@link List} with {@link Location}
     * @return {@link List} with {@link Location} or Empty;
     */
    public static List<Location> selectLocations(long routeId) {
        List<Location> resultList = new ArrayList<Location>();
        PreparedStatement statement = null;
        try {
            statement = DatabaseManager.getConnection().prepareStatement(QueriesUtility.SELECT_LOCATIONS_BY_ROUTE_ID);
            statement.setLong(1, routeId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                double latitude = resultSet.getDouble(LATITUDE_FIELD);
                double longitude = resultSet.getDouble(LONGITUDE_FIELD);
                resultList.add(new Location(latitude, longitude));
            }
            resultSet.close();
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

    public static int insertNewBaseLocation(Location location) {
        PreparedStatement statement = null;
        try {
            statement = DatabaseManager.getConnection().prepareStatement(QueriesUtility.INSERT_NEW_BASE_LOCATION);
            int index = 1;
            statement.setLong(index++, 1);
            statement.setString(index++, BASE);
            statement.setDouble(index++, location.getLatitude());
            statement.setDouble(index++, location.getLongitude());
            statement.setLong(index++, 0);
            return statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                closeConnections(statement);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    public static void closeConnections(PreparedStatement statement) throws SQLException {
        if (statement != null) {
            statement.close();
        }
    }

    public static void clean() {
        Statement statement = null;
        try {
            statement = DatabaseManager.getConnection().createStatement();
            statement.executeUpdate(QueriesUtility.DELETE_LOCATION);
        } catch (SQLException e) {
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void deleteBaseLocation() {
        Statement statement = null;
        try {
            statement = DatabaseManager.getConnection().createStatement();
            statement.executeUpdate(QueriesUtility.DELETE_BASE_LOCATION);
        } catch (SQLException e) {
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}