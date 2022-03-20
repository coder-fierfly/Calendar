package com.company;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class BaseController implements Initializable {

    private Main appFX;


    /*private void initialize() {
        nameList.setCellValueFactory(
                cellData -> cellData.getValue().getName());
        dogs.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> showDogsInformation(newValue));
    }*/

    @FXML
    private Button answer2;

    @FXML
    private GridPane gpBody;

    @FXML
    public void initialize(URL location, ResourceBundle resources) {
        answer2.setText("--");
        Text text1 = new Text("adcfvtgbynhj");
        gpBody.add(text1, 0, 1);
    }

    public void setAppFX(Main appFX) {
        this.appFX = appFX;
    }





    public BaseController() {
        //GridPane gpBody = new GridPane();

        //gpBody.setHgap(10);
        //gpBody.setVgap(10);
        //gpBody.setAlignment(Pos.CENTER);
        //gpBody.setMinHeight(300);
        Text text1 = new Text("aaaaaaaaaaa");
        //Text tDayName = new Text("a");
       //gpBody.add(text1, 0, 1);
    }

    public void layout() {

    }
}