package com.company;

/* import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import com.company.Week;
import com.company.Day;
import javafx.scene.control.TableColumn;

import java.util.ArrayList;

public class Controller {

    private ObservableList<Week> usersData = FXCollections.observableArrayList();

    @FXML
    private TableView<Week> table;

    @FXML
    private TableColumn<Week, Integer> Mon;

    @FXML
    private TableColumn<Week, Integer> Tue;

    @FXML
    private TableColumn<Week, Integer> Wed;

    @FXML
    private TableColumn<Week, String> Thu;

    @FXML
    private TableColumn<Week, String> Fri;

    @FXML
    private TableColumn<Week, String> Sut;

    @FXML
    private TableColumn<Week, String> Sun;

    // инициализируем форму данными
    @FXML
    private void initialize() {
        initData();
        //String num = "ff";
        // устанавливаем тип и значение которое должно хранится в колонке
        Mon.setCellValueFactory(new PropertyValueFactory<Week, Integer>("num"));
        Tue.setCellValueFactory(new PropertyValueFactory<Week, Integer>("num"));
        Wed.setCellValueFactory(new PropertyValueFactory<Week, Integer>("num"));
        Thu.setCellValueFactory(new PropertyValueFactory<Week, String>("email"));
        Fri.setCellValueFactory(new PropertyValueFactory<Week, String>("email"));
        Sut.setCellValueFactory(new PropertyValueFactory<Week, String>("email"));
        Sun.setCellValueFactory(new PropertyValueFactory<Week, String>("email"));

        //System.out.println(usersData.get(0).getDay(1));
        // заполняем таблицу данными
        table.setItems(usersData);
    }

    private void initData() {
        //Day[] week1 = new Day[7];
        //week1[0] = new Day(1, 1);
        //week1[1] = new Day(2, 1);
        //week1[2] = new Day(3, 1);
        //week1[1].setNum("2");
         week1.add(new Day(1, 1));
        week1.add(new Day(2, 1));
        week1.add(new Day(3, 1));
        week1.add(new Day(4, 1));
        week1.add(new Day(5, 1));
        week1.add(new Day(6, 1));
        week1.add(new Day(7, 1));

        //Week week1 = new Week(new Day(1, 1), new Day(1, 2));
        //usersData.add(new Week(week1));
        //usersData.add(new Week(week1));
        int[] week1 = new int[3];
        week1[0] = 5;
        week1[1] = 6;
        week1[2] = 7;
        usersData.add(new Week(week1));
        //usersData.add(new Week([2]));
        //System.out.println(week1[0].getNum());
        //System.out.println(week1[2].getNum());
        //System.out.println((usersData.get(0)).getDay(0));
        //System.out.println((usersData.get(0)).getDay(2));
        //usersData.add(new Week(3, "Jeck", "dsfdsfwe", "Jeck@mail.com"));
        //usersData.add(new Week(4, "Mike", "iueern", "mike@mail.com"));
        //usersData.add(new Week(5, "colin", "woeirn", "colin@mail.com"));
    }

}


import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;

import java.awt.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

public class Controller {

    @FXML
    private GridPane gridPane;
    @FXML
    private HBox hbox;
    private int teamId;
    private LocalDateTime time;
    private LocalDate date;
    private ZonedDateTime gameTime;
    private StackPane days[];
    //private Month monthSelected;

    public void initialize() {

        date = LocalDate.of(2019, 03, 1);//
        //monthSelected = date.getMonth();
        //createMonthSpinner();
        createDates();//create lables for dates;
        createCalendar();
    }

    public void createCalendar() {

        gridPane.getChildren().clear();

        int firstDay = date.withDayOfMonth(1).getDayOfWeek().getValue();

        for (StackPane stackpane : days) {
            gridPane.add(stackpane, firstDay % 7, firstDay / 7);
            GridPane.setHgrow(stackpane, Priority.ALWAYS);
            GridPane.setVgrow(stackpane, Priority.ALWAYS);
            GridPane.setFillHeight(stackpane, Boolean.TRUE);
            GridPane.setFillWidth(stackpane, Boolean.TRUE);
            firstDay++;
        }
    }

    public void createDates() {

        days = new StackPane[date.lengthOfMonth()];
        for (int i = 0; i < days.length; i++) {
            days[i] = new StackPane();
            Label label = new Label("" + (i + 1));
            Label dateNum = (Label) days[i - 1].getChildren().get(0);
            label.setId("dateNum");
            days[i].getChildren().add(label);
            StackPane.setMargin(label, new Insets(5, 0, 0, 5));//Top, Right, Bottom, Left insets
        }
        createCalendar();
    }
}  */


import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller {
    @FXML
    private GridPane newTable;

    public void initialize(URL url, ResourceBundle rb) {

        Label test = new Label("Testtttttttttttt");
        newTable.add(test, 1, 1);
    }
}