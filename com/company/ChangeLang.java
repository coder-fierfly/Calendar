package com.company;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;

public class ChangeLang {

    public RadioButton de, en, et;
    public Label makeChoice;
    public Button save;
    public ToggleGroup answers;

    //кнопки выбора языка
    public void radioLangButton() {
        if (de.isSelected()) {
            addLang("de");
            System.out.println("de");
            saveLang();
        } else if (en.isSelected()) {
            addLang("en");
            System.out.println("de");
            saveLang();
        } else if (et.isSelected()) {
            addLang("et");
            System.out.println("et");
            saveLang();
        } else {
            makeChoice.setText("Вы не сделали выбор.");
        }
    }

    //TODO: переменная w из одной буковки ;((
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

    //кнопочка сохранения языка. закрывает окно.
    public void saveLang() {
        Stage stage = (Stage) save.getScene().getWindow();
        stage.close();
    }
}
