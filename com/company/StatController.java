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

public class StatController implements Initializable {
    public ListView<String> statList;

    //TODO так можно сделать красивый график информация взята с
    // https://www.youtube.com/watch?v=0_TeHv2Q1PI&t=648s&ab_channel=CoolITHelp
//    @FXML
//    private LineChart lineChart;
//    @FXML
//    private void handleButtonAction(ActionEvent event) {
//        //XYChart.Series series = (XYChart.Series)lineChart.getData().get(0);
//            //new animetefx.animation.Pulse (series.getNode()).play;
//    }
//    @Override
//    public void initialize(URL url, ResourceBundle resourceBundle) {
//        statList.setItems(getAllWords());
//        XYChart.Series series = new XYChart.Series();
//
//        series.setName("KFKFK");
//        series.getData().add(new XYChart.Data<String, Integer>("asd", 2));
//        series.getData().add(new XYChart.Data<String, Integer>("add", 3));
//        series.getData().add(new XYChart.Data<String, Integer>("as", 10));
//    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        statList.setItems(getAllWords());
    }

    @FXML // загрузка окна статистики
    public void statOpen() throws IOException {
        FXMLLoader loader = new FXMLLoader(StatController.class.getResource("fx/Stat.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Статистика");
        stage.getIcons().add(new Image("file:com/company/pictures/Calendar.png"));
        stage.show();
    }

    private ObservableList<String> getAllWords() {
        ObservableList<String> vocab = FXCollections.observableArrayList();
        StringBuilder sb = new StringBuilder();
        File file = new File("testData.txt");
        if (!file.exists()) {
            try {
                boolean bool = file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

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
                    if (line.length() < 16) {
                        sb.setLength(0);
                        sb.append("За ").append(line, 3, 5).append("-е число было набрано ").append(line.substring(11) + " из 6 баллов");
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
