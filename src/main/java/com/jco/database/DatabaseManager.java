package com.jco.database;

import javax.sql.StatementEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Class discription
 *
 * @author Bondoronok_P
 *         Date: 28.02.13
 */
public class DatabaseManager {

    private static final ResourceBundle db = ResourceBundle.getBundle("connection");;
    private static final String URL;
    private static final String UID;
    private static final String PASSWORD;
    private static final String DRIVER;
    private static Connection connection;

    static {
        URL = db.getString("url");
        UID = db.getString("uid");
        PASSWORD = db.getString("password");
        DRIVER = db.getString("driver");
        connection = null;
    }

    private DatabaseManager() {
    }

    public static Connection getConnection() {
        if (connection == null) {
            try {
                Class.forName(DRIVER);
                connection = DriverManager.getConnection(URL, UID, PASSWORD);
            } catch (ClassNotFoundException e) {
                System.out.println("Driver not Found!");
            } catch (SQLException e) {
                System.out.println("Can't notify connection");
            }
        }
        return connection;
    }

    public static void close() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }
}
