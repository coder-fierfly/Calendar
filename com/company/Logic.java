package com.company;
import java.net.URL;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Logic extends BorderPane implements Initializable {
    public Calendar currentMonth;

    public void initialize(URL location, ResourceBundle resources) {
        currentMonth = new GregorianCalendar();
        currentMonth.set(Calendar.DAY_OF_MONTH, 1);

        drawCalendar();
        //Parser.parse();
    }

    private void drawCalendar() {
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
            gpBody.add(tDayName, day - 1, 0);
        }

        // рисуем сами числа в неделе
        int currentDay = currentMonth.get(Calendar.DAY_OF_MONTH);
        int daysInMonth = currentMonth.getActualMaximum(Calendar.DAY_OF_MONTH);
        int dayOfWeek;
        if ((currentMonth.get(Calendar.DAY_OF_WEEK) - 1) != 0) {
            dayOfWeek = currentMonth.get(Calendar.DAY_OF_WEEK) - 1;
        } else {
            dayOfWeek = 7;
        }
        int row = 1;
        for (int i = currentDay; i <= daysInMonth; i++) {
            if (dayOfWeek == 8) {
                dayOfWeek = 1;
                row++;
            }
            Text tDate = new Text(String.valueOf(currentDay));
            gpBody.add(tDate, dayOfWeek - 1, row);
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
                 day++;
             }
             row++;
             dayOfWeek = 0;
        }
        //setCenter(gpBody);
        //setMargin(gpBody, new Insets(30));
    }

    @FXML private Button nextMonth;
    @FXML private Button prevMonth;
    private void drawFooter() {
        // вы бы знали, как долго я с этим емучилась
        prevMonth.setOnAction(e -> {
            previous(); });
        nextMonth.setOnAction(e -> {
            next(); });

        //HBox hbFooter = new HBox(10);
        //hbFooter.getChildren().addAll(prevMonth, nextMonth);
        //hbFooter.setAlignment(Pos.CENTER);
        //setBottom(hbFooter);
        //setMargin(hbFooter, new Insets(15));
    }

    private void previous() {
        gpBody.setGridLinesVisible(false);
        gpBody.getChildren().clear();
        gpBody.setGridLinesVisible(true);
        currentMonth = getPreviousMonth(currentMonth);
        drawCalendar();
    }

    private void next() {
        gpBody.setGridLinesVisible(false);
        gpBody.getChildren().clear();
        gpBody.setGridLinesVisible(true);
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
            case 1:
                sb.append("Понедельник");
                break;
            case 2:
                sb.append("Вторник");
                break;
            case 3:
                sb.append("Среда");
                break;
            case 4:
                sb.append("Четверг");
                break;
            case 5:
                sb.append("Пятница");
                break;
            case 6:
                sb.append("Суббота");
                break;
            case 7:
                sb.append("Воскресенье");
        }
        return sb.toString();
    }

    private String getMonthName(int n) {
        String[] monthNames = {"Январь", "Февраль", "Март", "Апрель", "Май", "Июнь", "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь"};
        return monthNames[n];
    }
}
