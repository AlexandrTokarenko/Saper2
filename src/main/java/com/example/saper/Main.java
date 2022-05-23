package com.example.saper;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("saper.fxml")); // загружается fxml файл
        Scene scene = new Scene(fxmlLoader.load(), 332, 380); // загружается fxml файл в сцену, устанавливается размер окна
        stage.setTitle("Сапер"); // заголовок окна
        stage.setScene(scene);
        stage.setResizable(false); // таким образом запрещаеться изменение размера окна(нельзя растянуть окно)
        stage.show(); // показывается окно
    }

    public static void main(String[] args) {
        launch();
    }
}