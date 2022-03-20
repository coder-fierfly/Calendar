package com.company;

import java.io.IOException;
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

    /* public static void main (String[] args) {
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

    } */


    private static Stage primaryStage;

    @Override
    public void start(Stage stage)
    {
        primaryStage = stage;
        openWindow(this.getClass(), "Control.fxml");
    }

    public static void openWindow(Class<?> c, String fxml)
    {
        Scene scene = null;
        try
        {
            Parent root = FXMLLoader.load(c.getResource(fxml));
            scene = new Scene(root);
        }
        catch (IOException ex)
        {
            System.out.println(ex.getMessage());

            return;
        }

        primaryStage.setResizable(false);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}