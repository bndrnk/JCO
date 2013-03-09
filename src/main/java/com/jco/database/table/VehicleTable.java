package com.jco.database.table;

import com.jco.database.DatabaseManager;
import com.jco.database.queries.QueriesUtility;
import com.jco.entity.database.Vehicle;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Class discription
 *
 * @author Bondoronok_P
 *         Date: 09.03.13
 */
public class VehicleTable {

    private static final String VEHICLE_ID_FILED = "VEHICLE_ID";
    private static final String VEHICLE_NAME_FIELD = "VEHICLE_NAME";
    private static final String VEHICLE_TYPE_FIELD = "VEHICLE_TYPE";
    private static final String ROUTE_ID_FIELD = "ROUTE_ID";

    public static int insert(Vehicle vehicle) {
        PreparedStatement statement = null;
        try {
            statement = DatabaseManager.getConnection().prepareStatement(QueriesUtility.INSERT_NEW_VEHICLE);
            int index = 1;
            statement.setString(index++, vehicle.getVehicleName());
            statement.setString(index++, vehicle.getVehicleType());
            statement.setLong(index++, vehicle.getRouteId());
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
}