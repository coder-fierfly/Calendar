package com.company;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ChangeLangController {

    public RadioButton de, be, et, cs, sv, en;
    public Label makeChoice, makeChoice2;
    public Button save;
    public ToggleGroup answers;
    public javafx.scene.text.Text title;
    public boolean warning = false;

    //кнопки выбора языка
    public void radioLangButton() {
        Path path = Paths.get("lang.txt");
        // если файл не существуем, то создаем его и записываем в него язык для изучения
        if (Files.exists(path)) {
            File langFile = new File("lang.txt");
            try {
                boolean bool = langFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (be.isSelected()) {
                saveLang("be");
            } else if (en.isSelected()) {
                saveLang("en");
            } else if (de.isSelected()) {
                saveLang("de");
            } else if (cs.isSelected()) {
                saveLang("cs");
            } else if (et.isSelected()) {
                saveLang("et");
            } else if (sv.isSelected()) {
                saveLang("sv");
            } else {
                makeChoice.setText("Вы не сделали выбор.");
            }
        } else {
            title.setText("Выберите нужный язык:");
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
        Stage stage = (Stage) save.getScene().getWindow();
        stage.close();
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

    //кнопочка сохранения языка закрывает окно.
    public void saveLang(String word) {
        makeChoice2.setVisible(true);
        makeChoice2.setText("Старый язык будет удален. Нажмите 'Сохранить', если уверены.");
        makeChoice.setVisible(false);
        save.setOnAction(e -> {
            makeChoice2.setVisible(false);
            selectedLang(word);
        });
    }
}
