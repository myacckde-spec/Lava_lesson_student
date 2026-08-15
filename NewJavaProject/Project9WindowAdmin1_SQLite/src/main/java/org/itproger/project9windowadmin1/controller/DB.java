package org.itproger.project9windowadmin1.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DB {

    private static final String DB_FILE = "db/users.db";

    public static Connection dbConn = null;

    public static Connection getDbConnection() throws SQLException {
        try {
            // ensure directory exists
            Path dbDir = Paths.get("db");
            if (!Files.exists(dbDir)) {
                Files.createDirectories(dbDir);
            }
        } catch (Exception e) {
            // ignore directory creation failures; SQLite will try to create file
        }
        String connStr = "jdbc:sqlite:" + DB_FILE;
        DB.dbConn = DriverManager.getConnection(connStr);
        return dbConn;
    }

    public void isConnected() throws SQLException {
        dbConn = getDbConnection();
        System.out.println(dbConn != null && !dbConn.isClosed());
    }

}
