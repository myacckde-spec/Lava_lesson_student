package org.itproger.newspage10;

//import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.itproger.newspage10.Database;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class HelloController {
    @FXML
    private Button But_Add;

    @FXML
    private Button But_Exit;

    @FXML
    private TextField FirstText;

    @FXML
    private TextField InputText;

    @FXML
    private Label welcomeText;

    private Database database = new Database();

    @FXML
        void initialize() {
        System.out.println("init");
    }
    public HelloController(){

        System.out.println("HelloController");

//        try {
//            Connection connection = Database.getConnection();
//
//            System.out.println("Database connected!");
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
    }



}










/*
    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }*/


//public class PleaseProvideControllerClassName {


