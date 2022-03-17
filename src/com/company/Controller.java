package com.company;

import javafx.collections.FXCollections;
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
    private TableColumn<Week, String> Wed;

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

        // устанавливаем тип и значение которое должно хранится в колонке
        Mon.setCellValueFactory(new PropertyValueFactory<Week, Integer>("num"));
        Tue.setCellValueFactory(new PropertyValueFactory<Week, Integer>("weekNum"));
        Wed.setCellValueFactory(new PropertyValueFactory<Week, String>("password"));
        Thu.setCellValueFactory(new PropertyValueFactory<Week, String>("email"));
        Fri.setCellValueFactory(new PropertyValueFactory<Week, String>("email"));
        Sut.setCellValueFactory(new PropertyValueFactory<Week, String>("email"));
        Sun.setCellValueFactory(new PropertyValueFactory<Week, String>("email"));

        // заполняем таблицу данными
        table.setItems(usersData);
    }

    private void initData() {
        Day[] week1 = new Day[7];
        week1[0] = new Day(1, 1);
        week1[1] = new Day(2, 1);
        /* week1.add(new Day(1, 1));
        week1.add(new Day(2, 1));
        week1.add(new Day(3, 1));
        week1.add(new Day(4, 1));
        week1.add(new Day(5, 1));
        week1.add(new Day(6, 1));
        week1.add(new Day(7, 1)); */

        //Week week1 = new Week(new Day(1, 1), new Day(1, 2));
        //usersData.add(new Week(week1));
        usersData.add(new Week(week1));
        //usersData.add(new Week(3, "Jeck", "dsfdsfwe", "Jeck@mail.com"));
        //usersData.add(new Week(4, "Mike", "iueern", "mike@mail.com"));
        //usersData.add(new Week(5, "colin", "woeirn", "colin@mail.com"));
    }

}
