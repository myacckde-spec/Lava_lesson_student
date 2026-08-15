package org.itproger.project9windowadmin1.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB {


    private static final String HOST = "localhost";
    private static final String PORT = "3306";
    private static final String DB_NAME = "hibernate";
    private static final String LOGIN = "root";
    private static final String PASS = "root";

    public static Connection dbConn = null;
    public static Connection getDbConnection() throws ClassNotFoundException, SQLException {
        String connStr = "JDBC:mysql://"+HOST +":"+PORT+"/"+DB_NAME;
        Class.forName("com.mysql.cj.jdbc.Driver");

        DB.dbConn = DriverManager.getConnection(connStr, LOGIN, PASS);
        return dbConn;
    }
    public void isConnected() throws SQLException, ClassNotFoundException {
        dbConn = getDbConnection();
        System.out.println(dbConn.isValid(1000));
    }
   /* public boolean isExistsUser(String login){

        return false;
    };
*/

  }
