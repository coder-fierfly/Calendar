package com.company;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FirstChoice {
    public RadioButton de, be, et, cs, sv;
    public Label makeChoice;
    public Button save;
    public ToggleGroup answers;

    public FirstChoice() throws IOException {
        System.out.println("ЗАХОДИМ НЕ ТОЛПИМСЯ");
        File file = new File("lang.txt");
        if (!file.exists()) {
            try {
                boolean bool = file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        radioLangButton();
    }

    //кнопки выбора языка
    public void radioLangButton() {
        // TODO написать другие языки на радиобаттонах после полного внесения
        if (be.isSelected()) {
            addLang("be");
            System.out.println("be");
            saveLang();
        } else if (de.isSelected()) {
            addLang("de");
            System.out.println("de");
            saveLang();
        } else if (cs.isSelected()) {
            addLang("cs");
            System.out.println("cs");
            saveLang();
        } else if (et.isSelected()) {
            addLang("et");
            System.out.println("et");
            saveLang();
        } else if (et.isSelected()) {
            addLang("sv");
            System.out.println("sv");
            saveLang();
        } else {
            makeChoice.setText("Вы не сделали выбор.");
        }
    }

    public void addLang(String lang) {
        String fileName = "lang.txt";
        try {
            FileWriter writer = new FileWriter(fileName, false);
            writer.write(lang);
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
