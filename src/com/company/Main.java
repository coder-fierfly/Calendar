package com.company;

import java.util.Locale;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class Main extends Application {

    public static void main (String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Control.fxml"));
        Scene scene = new Scene(root);

        stage.setTitle("Календарь)))))))))))))");
        //stage.setWidth(1920);
        //stage.setHeight(1080);
        stage.setScene(scene);
        stage.show();




    }
}