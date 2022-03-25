package com.company;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Logic extends BorderPane implements Initializable {
    public Calendar currentMonth;

    public void initialize(URL location, ResourceBundle resources) {
        currentMonth = new GregorianCalendar();
        currentMonth.set(Calendar.DAY_OF_MONTH, 1);
        ObservableList<String> months = FXCollections.observableArrayList("Январь", "Февраль", "Март", "Апрель", "Май", "Июнь", "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь");
        mComboBox.setItems(months);
        ObservableList<Integer> years = FXCollections.observableArrayList();
        for (int i = 1900; i <= 2100; i++) {
            years.add(i);
        }
        yComboBox.setItems(years);
        drawCalendar();
        //Parser.parse();
    }

    private void drawCalendar() {
        // меняем подписи у выпадающих списков выбора года и месяца
        mComboBox.setPromptText(getMonthName(currentMonth.get(Calendar.MONTH)));  // setValue(getMonthName(currentMonth.get(Calendar.MONTH)));
        yComboBox.setPromptText(String.valueOf(currentMonth.get(Calendar.YEAR)));
        gpBody.setGridLinesVisible(false);
        gpBody.getChildren().clear();
        gpBody.setGridLinesVisible(true);
        drawHeader();
        drawBody();
        drawFooter();

    }

    @FXML private Label monthName;
    private void drawHeader() {
        // рисуем заголовок: месяц и год
        String monthString = getMonthName(currentMonth.get(Calendar.MONTH));
        String yearString = String.valueOf(currentMonth.get(Calendar.YEAR));
        String tHeader = monthString + ", " + yearString;
        monthName.setText(tHeader);
    }

    @FXML private GridPane gpBody;
    private void drawBody() {
        //gpBody.setHgap(10);
        //gpBody.setVgap(10);
        //gpBody.setAlignment(Pos.CENTER);
        //gpBody.setMinHeight(300);

        // рисуем дни недели
        for (int day = 1; day <= 7; day++) {
            Text tDayName = new Text(getDayName(day));
            //tDayName.setStyle("-fx-text-fill: #8B008B;");
            gpBody.add(tDayName, day - 1, 0);
            GridPane.setHalignment(tDayName, HPos.CENTER);
        }

        // делаем дни кликабельными
        addButtons();

        // рисуем сами числа в неделе
        int currentDay = currentMonth.get(Calendar.DAY_OF_MONTH);
        int daysInMonth = currentMonth.getActualMaximum(Calendar.DAY_OF_MONTH);
        int dayOfWeek;
        if ((currentMonth.get(Calendar.DAY_OF_WEEK) - 1) != 0) {
            dayOfWeek = currentMonth.get(Calendar.DAY_OF_WEEK) - 1;
        } else {
            dayOfWeek = 7;
        }
        String dateNow = new SimpleDateFormat("MM/dd/yyyy").format(Calendar.getInstance().getTime());
        int row = 1;
        for (int i = currentDay; i <= daysInMonth; i++) {
            if (dayOfWeek == 8) {
                dayOfWeek = 1;
                row++;
            }
            Text tDate = new Text(String.valueOf(currentDay));
            StringBuilder sb = new StringBuilder();
            if ((currentMonth.get(Calendar.MONTH) + 1) < 10) {
                sb.append("0");
            }
            sb.append(currentMonth.get(Calendar.MONTH) + 1).append("/").append(currentDay).append("/").append(currentMonth.get(Calendar.YEAR));
            if (dateNow.equals(String.valueOf(sb))) {
               tDate.setFill(Color.BLUE);
            }
            gpBody.add(tDate, dayOfWeek - 1, row);
            GridPane.setHalignment(tDate, HPos.CENTER);
            currentDay++;
            dayOfWeek++;
        }

        // рисуем дни предыдущего месяца там, где остались пустые ячейки
        if ((currentMonth.get(Calendar.DAY_OF_WEEK) - 1) != 0) {
            dayOfWeek = currentMonth.get(Calendar.DAY_OF_WEEK) - 1;
        } else {
            dayOfWeek = 7;
        }
        if (currentDay != 1) {
            Calendar prevMonth = getPreviousMonth(currentMonth);
            int daysInPrevMonth = prevMonth.getActualMaximum(Calendar.DAY_OF_MONTH);
            for (int i = dayOfWeek - 2; i >= 0; i--) {
                Text tDate = new Text(String.valueOf(daysInPrevMonth));
                tDate.setFill(Color.GRAY);
                gpBody.add(tDate, i, 1);
                GridPane.setHalignment(tDate, HPos.CENTER);
                daysInPrevMonth--;
            }
        }

        // рисуем дни следующего месяца там, где остались пустые ячейки
        currentMonth.set(Calendar.DAY_OF_MONTH, currentMonth.getActualMaximum(Calendar.DAY_OF_MONTH));
        if ((currentMonth.get(Calendar.DAY_OF_WEEK) - 1) != 0) {
            dayOfWeek = currentMonth.get(Calendar.DAY_OF_WEEK) - 1;
        } else {
            dayOfWeek = 7;
        }

        int day = 1;
        while (row < 7) {
             for (int i = dayOfWeek; i < 7; i++) {
                 Text tDate = new Text(String.valueOf(day));
                 tDate.setFill(Color.GRAY);
                 gpBody.add(tDate, i, row);
                 GridPane.setHalignment(tDate, HPos.CENTER);
                 day++;
             }
             row++;
             dayOfWeek = 0;
        }
        //setCenter(gpBody);
        //setMargin(gpBody, new Insets(30));

        File file = new File("data.txt");
        try {
            FileWriter writer = new FileWriter("data.txt", true);
            String lineSeparator = System.getProperty("line.separator");
            writer.write("хм??&" + lineSeparator);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addButtons() {
        for (int i = 0; i < 7; i++) {
            for (int i1 = 1; i1 < 7; i1++) {
                Button toggleButton11 = new Button();
                toggleButton11.setMaxWidth(Double.MAX_VALUE);
                toggleButton11.setMaxHeight(Double.MAX_VALUE);
                toggleButton11.setStyle("-fx-background-color: #38A3A5;");
                //toggleButton11.();//setBackground(new Color(0000));
                StringBuilder sb = new StringBuilder(3);
                sb.append(i).append(i1);
                toggleButton11.setId(String.valueOf(sb));
                //toggleButton11.setId(String.valueOf(i) + i1);
                GridPane.setConstraints(toggleButton11, i, i1);
                gpBody.setVgap(5);
                gpBody.setHgap(5);
                gpBody.getChildren().addAll(toggleButton11);
                List<Button> toggleButtonList = new ArrayList();
                toggleButtonList.add(toggleButton11);

                for (Button tempToggleButton : toggleButtonList) {
                    tempToggleButton.setOnAction(
                            event -> {
                        makeButton(tempToggleButton);
                    });
                }
            }
        }
    }

    public void makeButton(Button tempToggleButton) {  // TODO ehehehhh
        Label secondLabel = new Label("да вы прям полиглот");
        StackPane secondaryLayout = new StackPane();
        secondaryLayout.getChildren().add(secondLabel);
        Scene secondScene = new Scene(secondaryLayout, 230, 100);
        Stage newWindow = new Stage();
        newWindow.setTitle("крутые календарно-языковые штуки");
        newWindow.setScene(secondScene);
        newWindow.initModality(Modality.WINDOW_MODAL);
        newWindow.show();
    }

    @FXML private Button nextMonth;
    @FXML private Button prevMonth;
    @FXML ComboBox mComboBox;
    @FXML ComboBox yComboBox;
    private void drawFooter() {  // вы бы знали, как долго я с этим емучилась
        prevMonth.setOnAction(e -> previous());
        nextMonth.setOnAction(e -> next());
        mComboBox.setOnAction(e -> {
            currentMonth = new GregorianCalendar(currentMonth.get(Calendar.YEAR), getMonthNum((String) mComboBox.getValue()), 1);
            mComboBox.setValue(mComboBox.getValue());  // устанавливаем выбранный элемент по умолчанию
            drawCalendar();
        });

        yComboBox.setOnAction(e -> {
            currentMonth = new GregorianCalendar((Integer) yComboBox.getValue(), currentMonth.get(Calendar.MONTH), 1);
            yComboBox.setValue(yComboBox.getValue());
            drawCalendar();
        });
        //HBox hbFooter = new HBox(10);
        //hbFooter.getChildren().addAll(prevMonth, nextMonth);
        //hbFooter.setAlignment(Pos.CENTER);
        //setBottom(hbFooter);
        //setMargin(hbFooter, new Insets(15));
    }

    private void previous() {
        currentMonth = getPreviousMonth(currentMonth);
        drawCalendar();
    }

    private void next() {
        currentMonth = getNextMonth(currentMonth);
        drawCalendar();
    }

    private GregorianCalendar getPreviousMonth(Calendar cal) {
        int cMonth = cal.get(Calendar.MONTH);
        int pMonth = cMonth == 0 ? 11 : cMonth - 1;
        int pYear = cMonth == 0 ? cal.get(Calendar.YEAR) - 1 : cal.get(Calendar.YEAR);
        return new GregorianCalendar(pYear, pMonth, 1);
    }

    private GregorianCalendar getNextMonth(Calendar cal) {
        int cMonth = cal.get(Calendar.MONTH);
        int pMonth = cMonth == 11 ? 0 : cMonth + 1;
        int pYear = cMonth == 11 ? cal.get(Calendar.YEAR) + 1 : cal.get(Calendar.YEAR);
        return new GregorianCalendar(pYear, pMonth, 1);
    }

    private String getDayName(int n) {
        StringBuilder sb = new StringBuilder();
        switch (n) {
            case 1 -> sb.append("Понедельник");
            case 2 -> sb.append("Вторник");
            case 3 -> sb.append("Среда");
            case 4 -> sb.append("Четверг");
            case 5 -> sb.append("Пятница");
            case 6 -> sb.append("Суббота");
            case 7 -> sb.append("Воскресенье");
        }
        return sb.toString();
    }

    private String getMonthName(int n) {
        String[] monthNames = {"Январь", "Февраль", "Март", "Апрель", "Май", "Июнь", "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь"};
        return monthNames[n];
    }

    private int getMonthNum(String name) {
        String[] monthNames = {"Январь", "Февраль", "Март", "Апрель", "Май", "Июнь", "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь"};
        for (int i = 0; i < 12; i++) {
            if (monthNames[i].equals(name)) {
                return i;
            }
        }
        return 0;
    }
}
