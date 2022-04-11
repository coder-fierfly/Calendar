package com.company;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ChangeLang {

    public RadioButton de;
    public RadioButton en;
    public RadioButton et;
    public Label makeChoice;
    public Button save;

    //кнопки выбора языка
    //TODO по какой-то причине RadioButton выбирается не только 1 а можно выбрать все.
    // возможно это по тому, что я пока что не подключила функцию
    public void radioLangButton() {
        if (de.isSelected()) {
            save.setOnAction(e -> saveLang());
        } else if (en.isSelected()) {
            save.setOnAction(e -> saveLang());
        } else if (et.isSelected()) {
            save.setOnAction(e -> saveLang());
            //TODO здесь должно вылезать сообщение если язык не выбран, но чел пытается что-то сохранить
        } else {
            save.setOnAction(e -> makeChoice.setText("Вы не сделали выбор."));
        }
    }

    public AnchorPane stageLang;

    //кнопочка сохранения языка. в идеале должна закрывать окно.
    public void saveLang() {
        //TODO сделать в парсере смену языка
        save.setOnAction(e -> stageLang.getScene().getWindow());
//        stageLang.close();
    }
}
