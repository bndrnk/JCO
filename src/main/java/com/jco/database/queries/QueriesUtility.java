package com.jco.database.queries;

/**
 * Class discription
 *
 * @author Bondoronok_P
 *         Date: 28.02.13
 */
public final class QueriesUtility {

    public static final String INSERT_NEW_LOCATION = "INSERT INTO `JCO`.LOCATION (LOCATION_NAME, LATITUDE, LONGITUDE, TIME) VALUES(?,?,?,?)";
    public static final String SELECT_LOCATIONS_BY_NAME =  new StringBuilder().append("SELECT LATITUDE, LONGITUDE FROM LOCATION ")
              .append(" WHERE LOCATION_NAME = ? ")
              .append(" AND (LATITUDE IS NOT NULL ")
              .append("OR LONGITUDE IS NOT NULL) ").toString();


}
