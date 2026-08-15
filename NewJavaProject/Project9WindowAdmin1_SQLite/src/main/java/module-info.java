module org.itproger.project9windowadmin1_sqlite {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.itproger.project9windowadmin1_sqlite to javafx.fxml;
    exports org.itproger.project9windowadmin1_sqlite;
}