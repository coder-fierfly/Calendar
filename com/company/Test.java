package com.company;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class Test implements Initializable {
    public Label resultLabel;
    public Button reButton;
    public String id;

    /* public Test(String id) {
        this.id = id;
    } */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //resultLabel.setText("Тест был пройден с результатом %s баллов.\nПерепройти?");
        reButton.setOnAction(event -> {
            TestController tc = new TestController();
            tc.setId(this.getId());
            System.out.println(this.id);
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("sample.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Stage primaryStage = new Stage();
            primaryStage.setTitle("Тесты");
            if (root != null) {
                primaryStage.setScene(new Scene(root, 747, 460));
            }
            primaryStage.show();
        });
    }

    @FXML //загрузка окна тестов (менять его дизайн в fxml)
    public void testOpen(String id) throws IOException {
        FXMLLoader loader = new FXMLLoader(Test.class.getResource("Test.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("тест");
        stage.show();
        TestController tc = new TestController();
        tc.setId(id);
        //this.makeTest(id);
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
