package com.company;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ResourceBundle;

public class ChangeLang implements Initializable {

    @FXML
    public RadioButton de, be, et, cs, sv, en;
    public Label makeChoice;
    @FXML
    public Button save;
    public ToggleGroup answers;
    public javafx.scene.text.Text title;
    private Logic logic;

    public void initialize(URL location, ResourceBundle resources) {
        getButton(Logic.getLang()).setSelected(true);
    }

    public void setParent(Logic logic) {
        this.logic = logic;
    }

    public ChangeLang getThis() {
        return this;
    }

    public RadioButton getButton(String lang) {
        if (lang.equals("be")) {
            return be;
        } else if (lang.equals("en")) {
            return en;
        } else if (lang.equals("de")) {
            return de;
        } else if (lang.equals("cs")) {
            cs.requestFocus();
        } else if (lang.equals("et")) {
            return et;
        } else if (lang.equals("sv")) {
            return sv;
        }
        return null;
    }

    //кнопки выбора языка
    public void radioLangButton() {
        File langFile = new File("lang.txt");

        // если файл не существует, то создаем его и записываем в него язык для изучения
        if (!langFile.exists()) {
            try {
                boolean bool = langFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            title.setText("Выберите язык для изучения:");
            if (be.isSelected()) {
                addLang("be");
                saveLang();
            } else if (en.isSelected()) {
                addLang("en");
                saveLang();
            } else if (de.isSelected()) {
                addLang("de");
                saveLang();
            } else if (cs.isSelected()) {
                addLang("cs");
                saveLang();
            } else if (et.isSelected()) {
                addLang("et");
                saveLang();
            } else if (et.isSelected()) {
                addLang("sv");
                saveLang();
            } else {
                makeChoice.setText("Вы не сделали выбор.");
            }
        } else {
            title.setText("Выберите новый язык для изучения:(Старый язык при этом удалится)");
            if (be.isSelected()) {
                selectedLang("be");
            } else if (en.isSelected()) {
                selectedLang("en");
            } else if (de.isSelected()) {
                selectedLang("de");
            } else if (cs.isSelected()) {
                selectedLang("cs");
            } else if (et.isSelected()) {
                selectedLang("et");
            } else if (sv.isSelected()) {
                selectedLang("sv");
            } else {
                makeChoice.setText("Вы не сделали выбор.");
            }
        }
    }

    public void selectedLang(String word) {
        File numFile = new File("numFile.txt");
        File dataFile = new File("data.txt");
        addLang(word);
        if (numFile.exists() || dataFile.exists()) {
            cleanFiles();
        }
        saveLang();
    }

    // Запись в файл после выбора язка
    public void addLang(String word) {
        String fileName = "lang.txt";
        try {
            FileWriter writer = new FileWriter(fileName, false);
            writer.write(word);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Удаление старых файлов, после обновления языка
    public void cleanFiles() {
        BufferedWriter numFile = null;
        BufferedWriter data = null;
        try {
            numFile = Files.newBufferedWriter(Paths.get("numFile.txt"));
            data = Files.newBufferedWriter(Paths.get("data.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            if (data != null) {
                data.write("");
                numFile.write("");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            assert numFile != null;
            numFile.flush();
            assert data != null;
            data.flush();
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
