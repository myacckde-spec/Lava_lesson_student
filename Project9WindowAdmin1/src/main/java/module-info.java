module org.itproger.project9windowadmin1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;


    opens org.itproger.project9windowadmin1 to javafx.fxml;

    exports org.itproger.project9windowadmin1.controller;
    opens org.itproger.project9windowadmin1.controller to javafx.fxml;
}