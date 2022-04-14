package com.company;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class Stat implements Initializable {
    public ListView<String> statList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        statList.setItems(getAllWords());
    }

    @FXML // загрузка окна словаря (менять его дизайн в fxml)
    public void statOpen() throws IOException {
        FXMLLoader loader = new FXMLLoader(Vocab.class.getResource("Stat.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("словарь");
        stage.show();
    }

    private ObservableList<String> getAllWords() {
        ObservableList<String> vocab = FXCollections.observableArrayList();

        StringBuilder sb = new StringBuilder();
        File file = new File("testData.txt");
        String line;
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            try {
                while((line = br.readLine()) != null) {
                    System.out.println(line);
                    sb.append(Logic.getMonthName(Integer.parseInt(line.substring(0, 2)) - 1))
                            .append(", ").append(line, 6, 10);
                    if (!vocab.contains(String.valueOf(sb))) {
                        vocab.add(String.valueOf(sb));
                    }
                    if (line.length() < 16) {
                        sb.setLength(0);
                        sb.append(line, 3, 5).append(": ").append(line.substring(11));
                        vocab.add(String.valueOf(sb));
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
