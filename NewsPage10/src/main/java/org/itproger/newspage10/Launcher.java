package org.itproger.newspage10;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.application.Application;

public class Launcher {
    @FXML
    private TextField InputText;
    FXMLLoader loader = new FXMLLoader(
            HelloApplication.class.getResource("HelloView.fxml")
    );

    Parent root = loader.load();

    HelloController controller = loader.getController();

    public static void main(String[] args) {
        System.out.println("123");
        String sql = "SELECT * FROM articles";
        try { Connection connection = Database.getConnection();
            Statement statement = connection.createStatement();

            ResultSet result = statement.executeQuery(sql);

            while (result.next()) {
                System.out.println(
                        result.getInt("id") + " " +
                                result.getString("title")

                );
                String title = result.getString("title");

                FirstText.setText(title);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        Application.launch(HelloApplication.class, args);

    }
}
