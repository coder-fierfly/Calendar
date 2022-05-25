package com.company;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Main extends Application {

    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fx\\Cont2.fxml")));
        Scene scene = new Scene(root);

        primaryStage.setScene(scene);
        primaryStage.toBack();
        primaryStage.setTitle("Календарь");
        primaryStage.setResizable(false);
        primaryStage.getIcons().add(new Image("file:com/company/pictures/Calendar.png"));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}