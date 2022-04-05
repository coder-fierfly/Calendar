package com.company;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

public class Test implements Initializable {
    public Label resultLabel;
    public Button reButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //resultLabel.setText("Тест был пройден с результатом %s баллов.\nПерепройти?");
    }

    @FXML //загрузка окна тестов (менять его дизайн в fxml)
    public void testOpen(String id) throws IOException {
        FXMLLoader loader = new FXMLLoader(Test.class.getResource("Test.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("тест");
        stage.show();
        this.makeTest(id);
    }

    public void makeTest(String id) {
        //this.resultLabel = new Label();
        File file = new File("data.txt");
        String line;

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            try {
                while((line = br.readLine()) != null) {
                    if(line.startsWith(id)) {
                        System.out.println(line);
                        line = line.substring(11);
                        //this.resultLabel.setText(String.format("Тест был пройден с результатом %s баллов.\nПерепройти?", line));
                        break;
                    }
                }
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        /*
        ArrayList<String[]> words = new ArrayList<>(7);
        String[] newWords;
        for (int i = 0; i < 7; i++) {
            try {
                BufferedReader br = new BufferedReader(new FileReader(file));
                try {
                    LineNumberReader lnr = new LineNumberReader(br);
                    int count = 0;
                    while ((line = br.readLine()) != null) {
                        if (count == (lnr.getLineNumber() - 1)) {
                            line = line.substring(11);
                            System.out.println(line);
                            newWords = line.split("/");
                            words.add(newWords);
                            break;
                        }
                    }
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        Collections.shuffle(words);
        int score = 0;
        StringBuilder sb = new StringBuilder();
        sb.append(id).append(" ").append(score);
        Logic.addWords(String.valueOf(sb)); */
    }
}
