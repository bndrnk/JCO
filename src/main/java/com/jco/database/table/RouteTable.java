package com.jco.database.table;

import com.jco.database.DatabaseManager;
import com.jco.database.queries.QueriesUtility;
import com.jco.entity.database.Location;
import com.jco.entity.database.Route;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Class discription
 *
 * @author Bondoronok_P
 *         Date: 09.03.13
 */
public class RouteTable {

    private static final String ROUTE_ID_FIELD = "ROUTE_ID";
    private static final String ROUTE_NAME_FIELD = "ROUTE_NAME";
    private static final String ROUTE_ROUTE_TIME = "ROUTE_TIME";
    private static final String NEXT_ROUTE_ID = "NEXT_ROUTE_ID";


    public static int insert(Route route) {
        PreparedStatement statement = null;
        try {
            statement = DatabaseManager.getConnection().prepareStatement(QueriesUtility.INSERT_NEW_ROUTE);
            int index = 1;
            statement.setLong(index++, route.getRouteId());
            statement.setString(index++, route.getRouteName());
            statement.setLong(index++, route.getRouteTime());
            return statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null)
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }
        return 0;
    }

    public static long selectMaxRouteId() {
        Statement statement = null;
        try {
            statement = DatabaseManager.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(QueriesUtility.ROUTE_ID_VALUE);
            if (resultSet.next()) {
                return resultSet.getLong(NEXT_ROUTE_ID);
            } else {
                throw new SQLException("Cannot generate route id");
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

        return 0;
    }

}
