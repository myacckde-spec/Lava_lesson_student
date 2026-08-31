package org.itproger.newspage10;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {



    private static final String URL = "JDBC:mysql://localhost:3306/hibernate";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    public static Connection getConnection() throws SQLException {
        System.out.println("123");
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}

