module org.itproger.newspage10 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens org.itproger.newspage10 to javafx.fxml;
    exports org.itproger.newspage10;
}