package com.company;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class VocabController implements Initializable {
    public ListView<String> vocabList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        vocabList.setItems(getAllWords());
    }

    @FXML // загрузка окна словаря
    public void vocabOpen() throws IOException {
        FXMLLoader loader = new FXMLLoader(VocabController.class.getResource("fx/Vocab.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Словарь");
        stage.getIcons().add(new Image("file:com/company/pictures/Calendar.png"));
        stage.show();
    }

    private ObservableList<String> getAllWords() {
        ObservableList<String> vocab = FXCollections.observableArrayList();
        StringBuilder sb = new StringBuilder();
        File file = new File("data.txt");
        String line;

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            try {
                while ((line = br.readLine()) != null) {
                    sb.append(Logic.getMonthName(Integer.parseInt(line.substring(0, 2)) - 1))
                            .append(", ").append(line, 6, 10);
                    if (!vocab.contains(String.valueOf(sb)) && (!vocab.isEmpty())) {
                        vocab.add("");
                        vocab.add(String.valueOf(sb));
                    }
                    if (!vocab.contains(String.valueOf(sb))) {
                        vocab.add(String.valueOf(sb));
                    }
                    if (line.length() > 13) {
                        vocab.add(line.substring(11).replace("/", " - "));
                    }
                    sb.setLength(0);
                }
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return vocab;
    }
}