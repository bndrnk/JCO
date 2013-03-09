package com.jco.database.queries;

/**
 * Class discription
 *
 * @author Bondoronok_P
 *         Date: 28.02.13
 */
public final class QueriesUtility {

    public static final String INSERT_NEW_LOCATION = "INSERT INTO `JCO`.LOCATION (LOCATION_NAME, LATITUDE, LONGITUDE, ROUTE_ID) VALUES(?,?,?,?)";
    public static final String INSERT_NEW_ROUTE = "INSERT INTO `JCO`.ROUTE (ROUTE_ID, ROUTE_NAME, ROUTE_TIME) VALUES(?,?,?)";
    public static final String INSERT_NEW_VEHICLE = "INSERT INTO `JCO`.VEHICLE (VEHICLE_NAME, VEHICLE_TYPE, ROUTE_ID) VALUES(?,?,?)";
    public static final String SELECT_LOCATIONS_BY_NAME =  new StringBuilder().append("SELECT LATITUDE, LONGITUDE FROM LOCATION ")
              .append(" WHERE LOCATION_NAME = ? ")
              .append(" AND (LATITUDE IS NOT NULL ")
              .append("OR LONGITUDE IS NOT NULL) ").toString();

    public static final String BASE_LOCATION_QUERY = "SELECT * FROM LOCATION WHERE LOCATION_NAME = 'BASE'";
    public static final String ROUTE_ID_VALUE = "SELECT CASE WHEN(MAX(ROUTE_ID) IS NULL) THEN 0 ELSE (MAX(ROUTE_ID) + 1) END AS NEXT_ROUTE_ID FROM ROUTE;";

}
