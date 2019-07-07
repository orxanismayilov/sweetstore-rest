package com.orxan.sweetstorerest.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {

    private static String DAO_PROPERTY_URL="C:\\Users\\Orxan\\Desktop\\projects\\sweetstore-rest\\src\\main\\resources\\properties\\dao.properties";
    private static Properties daoProperties;

    public static Connection getConnection() {
        daoProperties= LoadPropertyUtil.loadPropertiesFile(DAO_PROPERTY_URL);
        try {
            Class.forName (daoProperties.getProperty("driver")).newInstance ();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection conn = null;
        try {
            conn = DriverManager.getConnection (daoProperties.getProperty("url"), daoProperties.getProperty("user"), daoProperties.getProperty("password"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
}
