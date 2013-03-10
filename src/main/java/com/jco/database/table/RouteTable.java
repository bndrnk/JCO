package com.jco.database.table;

import com.jco.database.DatabaseManager;
import com.jco.database.queries.QueriesUtility;
import com.jco.entity.database.Route;

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
 *         Date: 09.03.13
 */
public class RouteTable {

    private static final String ROUTE_ID_FIELD = "ROUTE_ID";
    private static final String ROUTE_TIME_FIELD = "ROUTE_TIME";
    private static final String NEXT_ROUTE_ID = "NEXT_ROUTE_ID";


    public static int insert(Route route) {
        PreparedStatement statement = null;
        try {
            statement = DatabaseManager.getConnection().prepareStatement(QueriesUtility.INSERT_NEW_ROUTE);
            int index = 1;
            statement.setLong(index++, route.getRouteId());
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

    public static void clean() {
        Statement statement = null;
        try {
            statement = DatabaseManager.getConnection().createStatement();
            statement.executeUpdate(QueriesUtility.DELETE_ROUTES);
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

    public static List<Route> selectRoutes() {
        List<Route> routeList = new ArrayList<Route>();
        Statement statement = null;
        try {
            statement = DatabaseManager.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(QueriesUtility.ROUTES_QUERY);
            Route currentRoute;
            while (resultSet.next()) {
                currentRoute = new Route();
                currentRoute.setRouteId(resultSet.getLong(ROUTE_ID_FIELD));
                currentRoute.setRouteTime(resultSet.getLong(ROUTE_TIME_FIELD));
                routeList.add(currentRoute);
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
        return routeList;
    }
}
