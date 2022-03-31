package com.company;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    /* private Stage primaryStage;
    private AnchorPane rootLayout;
    public static void main(String[] args) {
        Application.launch();
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        //GridPane root = FXMLLoader.load(getClass().getResource("Cont.fxml"));
        //primaryStage.setScene(new Scene(root, 800, 800));
        //primaryStage.show();
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Dogs application");
        showBaseWindow();
    }
    public void showBaseWindow() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("/com/company/Cont.fxml"));
            rootLayout = loader.load();
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            BaseController controller = loader.getController();
            controller.setAppFX(this);
            primaryStage.show();
            BaseController gc = new BaseController();
            gc.layout();
        } catch (IOException e) {
            e.printStackTrace();
        }
    } */

    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Cont2.fxml"));
        Scene scene = new Scene(root);

        primaryStage.setScene(scene);
        primaryStage.setTitle("stuDying");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}