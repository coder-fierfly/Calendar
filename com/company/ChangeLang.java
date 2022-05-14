package com.company;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import org.w3c.dom.Text;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ChangeLang {

    public RadioButton de, be, et, cs, sv, en;
    public Label makeChoice;
    public Button save;
    public ToggleGroup answers;
    public Text tit;
    public javafx.scene.text.Text title;

    //кнопки выбора языка
    public void radioLangButton() {
        File langFile = new File("lang.txt");
        // если файл не существуем, то создаем его и записываем в него язык для изучения
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
        File partiesFile = new File("parties.txt");
        File dataFile = new File("data.txt");
        addLang(word);
//        System.out.println(word);
        // TODO проверить что && это или
        if(numFile.exists() && partiesFile.exists() && dataFile.exists()) {
            cleanFiles();
        }
        saveLang();
    }

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

        //if(numFile.)
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
