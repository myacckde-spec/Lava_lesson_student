package org.itproger.newspage10;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Database {

    private static final String URL = "JDBC:mysql://localhost:3306/hibernate";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    public static Connection getConnection() throws SQLException {
        System.out.println("123");
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static List<News> getAllNews() {
        List<News> newsList = new ArrayList<>();
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {

            String sql = "SELECT id, title, content FROM news";
            ResultSet resultSet = statement.executeQuery(sql);

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                String content = resultSet.getString("content");

                News news = new News(id, title, content);
                newsList.add(news);
            }

            resultSet.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return newsList;
    }
}
