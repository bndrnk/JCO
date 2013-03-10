package com.jco.database.queries;

/**
 * Class discription
 *
 * @author Bondoronok_P
 *         Date: 28.02.13
 */
public final class QueriesUtility {

    public static final String INSERT_NEW_LOCATION = "INSERT INTO `JCO`.LOCATION (LOCATION_NAME, LATITUDE, LONGITUDE, ROUTE_ID) VALUES(?,?,?,?)";
    public static final String INSERT_NEW_BASE_LOCATION = "INSERT INTO `JCO`.LOCATION VALUES(?,?,?,?,?)";
    public static final String INSERT_NEW_ROUTE = "INSERT INTO `JCO`.ROUTE (ROUTE_ID, ROUTE_TIME) VALUES(?,?)";
    public static final String INSERT_NEW_VEHICLE = "INSERT INTO `JCO`.VEHICLE (VEHICLE_NAME, VEHICLE_TYPE, ROUTE_ID) VALUES(?,?,?)";
    public static final String DELETE_ROUTES = "DELETE FROM ROUTE";
    public static final String DELETE_VEHICLES = "DELETE FROM VEHICLE";
    public static final String DELETE_LOCATION= "DELETE FROM LOCATION WHERE LOCATION_ID <> 1";
    public static final String DELETE_BASE_LOCATION= "DELETE FROM LOCATION WHERE LOCATION_NAME = 'BASE'";

    public static final String SELECT_LOCATIONS_BY_ROUTE_ID =  "SELECT * FROM LOCATION WHERE LOCATION_ID <> 1 AND ROUTE_ID = ? ORDER BY LOCATION_ID";
    public static final String BASE_LOCATION_QUERY = "SELECT * FROM LOCATION WHERE LOCATION_ID = 1";
    public static final String ROUTES_QUERY = "SELECT * FROM ROUTE";
    public static final String SELECT_VEHILE_BY_ROUTE = "SELECT * FROM VEHICLE WHERE ROUTE_ID = ?";
    public static final String ROUTE_ID_VALUE = "SELECT CASE WHEN(MAX(ROUTE_ID) IS NULL) THEN 0 ELSE (MAX(ROUTE_ID) + 1) END AS NEXT_ROUTE_ID FROM ROUTE;";

}
