package org.itproger.newspage10;

import javafx.scene.control.Label;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.geometry.Insets;
import org.itproger.newspage10.Database;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

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

    @FXML
    private VBox newsContainer;

    private Database database = new Database();

    @FXML
    void initialize() {
        System.out.println("init");
        loadAllNews();
    }

    public HelloController() {
        System.out.println("HelloController");
    }

    private void loadAllNews() {
        newsContainer.getChildren().clear();

        List<News> newsList = Database.getAllNews();

        if (newsList.isEmpty()) {
            Label noNewsLabel = new Label("Новостей нет");
            newsContainer.getChildren().add(noNewsLabel);
        } else {
            for (News news : newsList) {
                HBox newsItem = createNewsItem(news);
                newsContainer.getChildren().add(newsItem);
            }
        }
    }

    private HBox createNewsItem(News news) {
        HBox hbox = new HBox();
        hbox.setSpacing(10);
        hbox.setPadding(new Insets(10));
        hbox.setStyle("-fx-border-color: #cccccc; -fx-border-radius: 5; -fx-background-color: #f9f9f9;");

        Label titleLabel = new Label("📰 " + news.getTitle());
        titleLabel.setStyle("-fx-font-size: 14; -fx-font-weight: bold;");

        Label contentLabel = new Label(news.getContent());
        contentLabel.setStyle("-fx-font-size: 12;");
        contentLabel.setWrapText(true);

        VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.getChildren().addAll(titleLabel, contentLabel);

        hbox.getChildren().add(vbox);
        HBox.setHgrow(vbox, javafx.scene.layout.Priority.ALWAYS);

        return hbox;
    }

}
