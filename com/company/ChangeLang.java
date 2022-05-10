package com.company;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ChangeLang {

    public RadioButton de, be, et, cs, sv;
    public Label makeChoice;
    public Button save;
    public ToggleGroup answers;

    //кнопки выбора языка
    public void radioLangButton() {
        // TODO написать другие языки на радиобаттонах после полного внесения
        if (be.isSelected()) {
            addLang("be");
            System.out.println("be");
            cleanFiles();
            saveLang();
        } else if (de.isSelected()) {
            addLang("de");
            System.out.println("de");
            cleanFiles();
            saveLang();
        } else if (cs.isSelected()) {
            addLang("cs");
            System.out.println("cs");
            cleanFiles();
            saveLang();
        } else if (et.isSelected()) {
            addLang("et");
            System.out.println("et");
            cleanFiles();
            saveLang();
        } else if (et.isSelected()) {
            addLang("sv");
            System.out.println("sv");
            cleanFiles();
            saveLang();
        } else {
            makeChoice.setText("Вы не сделали выбор.");
        }
    }

    public void addLang(String w) {
        String fileName = "lang.txt";
        try {
            FileWriter writer = new FileWriter(fileName, false);
            writer.write(w);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cleanFiles() {
        BufferedWriter numFile = null;
        BufferedWriter parties = null;
        BufferedWriter data = null;
        try {
            numFile = Files.newBufferedWriter(Paths.get("numFile.txt"));
            parties = Files.newBufferedWriter(Paths.get("parties.txt"));
            data = Files.newBufferedWriter(Paths.get("data.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            if (data != null) {
                data.write("");
                parties.write("");
                numFile.write("");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            assert numFile != null;
            numFile.flush();
            assert parties != null;
            parties.flush();
            //  assert numFile != null;
            numFile.flush();
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
