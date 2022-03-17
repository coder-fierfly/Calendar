package com.company;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.scene.control.TableColumn;

public class Logic {

    public Logic() {
    }

    public boolean isVis(int year) {
        if ((year % 4 == 0) && (year % 100 != 0) || (year % 400 == 0)) {
            return true;
        } else {
            return false;
        }
    }

    public int daysNum(int year, int month) {
        boolean years = isVis(year);
        switch(month) {
            case 1: case 3: case 5: case 7: case 8: case 10: case 12:
                return 31;
            case 2:
                if (years) {
                    return 29;
                } else {
                    return 28;
                }
            default:
                return 30;
        }
    }



    private TableView<String> tableUsers;

    private ObservableList<String> usersData = FXCollections.observableArrayList();

    //@FXML
    private TableView<String> Month;

    //@FXML
    private TableColumn<String, String> Mon;

    private TableColumn<String, String> Tue;

    //@FXML
    private TableColumn<String, String> Wed;

    //@FXML
    private TableColumn<String, String> Thu;

    //@FXML
    private TableColumn<String, String> Fri;

    private TableColumn<String, String> Sut;

    private TableColumn<String, String> Sun;

    //@FXML
    private void initialize() {
        initData();

        // устанавливаем тип и значение которое должно хранится в колонке
        Mon.setCellValueFactory(new PropertyValueFactory<String, String>("day1"));
        Tue.setCellValueFactory(new PropertyValueFactory<String, String>("day2"));
        Wed.setCellValueFactory(new PropertyValueFactory<String, String>("day3"));
        Thu.setCellValueFactory(new PropertyValueFactory<String, String>("day4"));
        Fri.setCellValueFactory(new PropertyValueFactory<String, String>("day5"));
        Sut.setCellValueFactory(new PropertyValueFactory<String, String>("day6"));
        Sun.setCellValueFactory(new PropertyValueFactory<String, String>("day7"));

        // заполняем таблицу данными
        tableUsers.setItems(usersData);
    }

    // подготавливаем данные для таблицы
    // вы можете получать их с базы данных
    private void initData() {
        usersData.add(new String("ff"));
        //usersData.add(new String(2, "Bob", "dsfsdfw", "bob@mail.com"));
        //usersData.add(new String(3, "Jeck", "dsfdsfwe", "Jeck@mail.com"));
        //usersData.add(new String(4, "Mike", "iueern", "mike@mail.com"));
        //usersData.add(new String(5, "colin", "woeirn", "colin@mail.com"));
    }

    //initialize();

}
