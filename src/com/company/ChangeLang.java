package com.company;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;

public class ChangeLang {

    public RadioButton de, en, et;
    public Label makeChoice;
    public Button save;
    public ToggleGroup answers;

    //кнопки выбора языка
    //TODO по какой-то причине RadioButton выбирается не только 1 а можно выбрать все. возможно это по тому, что я пока что не подключила функцию
    public void radioLangButton() {
        if (de.isSelected()) {
            //Logic.setLang("de");
            addLang("de");
            //save.setOnAction(e -> saveLang());
            System.out.println("de");
        } else if (en.isSelected()) {
            //Logic.setLang("en");
            addLang("en");
            //save.setOnAction(e -> saveLang());
            System.out.println("en");
        } else if (et.isSelected()) {
            //Logic.setLang("et");
            addLang("et");
            //save.setOnAction(e -> saveLang());
            System.out.println("et");
            //TODO здесь должно вылезать сообщение если язык не выбран, но чел пытается что-то сохранить
        } else {
            save.setOnAction(e -> makeChoice.setText("Вы не сделали выбор."));
        }
    }

    public void addLang(String w) {
        String fineName = "lang.txt";
        try {
            FileWriter writer = new FileWriter(fineName, false);
            writer.write(w);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
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
