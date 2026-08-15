package org.itproger.project9windowadmin1.controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {


        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("main-admin.fxml"));
        //Scene.getStylesheets().add(HelloApplication.class.getResource("styles/main.css").toExternalForm());

        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Личный кабинет");
        stage.setScene(scene);
        stage.show();
    }
   public static void main(String[] args) {
       launch();
   }
}
