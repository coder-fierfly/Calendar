package com.company;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

public class Logic extends BorderPane implements Initializable {
    public Calendar currentMonth;
    public AnchorPane firstColor;
    public AnchorPane secondColor;
    public AnchorPane thirdColor;
    public AnchorPane fourthColor;
    Boolean isDark = false;

    public void initialize(URL location, ResourceBundle resources) {
       ObservableList<String> months = FXCollections.observableArrayList("Январь", "Февраль", "Март", "Апрель", "Май", "Июнь", "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь");
        currentMonth = new GregorianCalendar();
        currentMonth.set(Calendar.DAY_OF_MONTH, 1);
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
        //drawHeader();
        drawBody();
        drawFooter();
    }

//    @FXML private Label monthName;
//    private void drawHeader() {
//        // рисуем заголовок: месяц и год
//        String monthString = getMonthName(currentMonth.get(Calendar.MONTH));
//        String yearString = String.valueOf(currentMonth.get(Calendar.YEAR));
//        String tHeader = monthString + ", " + yearString;
//        monthName.setText(tHeader);
//    }

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

            // делаем дни кликабельными
            addButtons(String.valueOf(sb), dayOfWeek - 1, row);

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
    }


    public String[] getWords(String id) {
        StringBuilder sb = new StringBuilder();
        if ((currentMonth.get(Calendar.MONTH) + 1) < 10) {
            sb.append("0");
        }
        sb.append(currentMonth.get(Calendar.MONTH) + 1).append("/").append(currentMonth.get(Calendar.DAY_OF_MONTH)).append("/").append(currentMonth.get(Calendar.YEAR));
        System.out.println(sb);
        File file = new File("data.txt");

        String line;
        //TODO what is w??? and why w?
        String[] w = null;
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            try {
                while((line = br.readLine()) != null) {
                    //if(line.startsWith("03/25/2022")) {//(String.valueOf(sb))) {
                    if(line.startsWith(id)) {
                        System.out.println(line);
                        line = line.substring(11);
                        w = line.split("/");
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

        return w;
    }

    public void addWords(String[] w, String id) {
        try {
            FileWriter writer = new FileWriter("data.txt", true);
            String lineSeparator = System.getProperty("line.separator");
            String line = String.format("%s %s/%s", id, w[0], w[1]);
            writer.write(line + lineSeparator);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addButtons(String id, int dayOfWeek, int row) {
        Button toggleButton11 = new Button();
        toggleButton11.setMaxWidth(Double.MAX_VALUE);
        toggleButton11.setMaxHeight(Double.MAX_VALUE);
        toggleButton11.setStyle("-fx-background-color: transparent;");

        toggleButton11.setId(String.valueOf(id));
        GridPane.setConstraints(toggleButton11, dayOfWeek, row);
        gpBody.setVgap(5);
        gpBody.setHgap(5);
        gpBody.getChildren().add(toggleButton11);
        toggleButton11.setOnAction(
                event -> {
                    makeButton(toggleButton11);
                });
    }

    public void makeButton(Button button) {  // TODO ehehehhh
        String[] w = getWords(button.getId());
        if (w == null) {
            w = Parser.parse();
        }
        Label label = new Label();
        if (w != null) {
            label.setText(String.format("да вы прям полиглот.\nслова за выбранный день:\n%s - %s", w[0], w[1]));
            addWords(w, button.getId());
        } else {
            label.setText("слов на этот день нет, не было и не будет.");
        }
        StackPane secondaryLayout = new StackPane();
        secondaryLayout.getChildren().add(label);
        Scene secondScene = new Scene(secondaryLayout, 300, 100);
        Stage newWindow = new Stage();
        newWindow.setTitle("крутые календарно-языковые штуки");
        newWindow.setScene(secondScene);
        newWindow.initModality(Modality.WINDOW_MODAL);
        newWindow.show();
    }

    @FXML private Button nextMonth, prevMonth;
    @FXML private MenuItem topic, changeLang, info;
    @FXML ComboBox<String> mComboBox;
    @FXML ComboBox<Integer> yComboBox;
    private void drawFooter() {  // вы бы знали, как долго я с этим емучилась
        prevMonth.setOnAction(e -> previous());
        nextMonth.setOnAction(e -> next());
        mComboBox.setOnAction(e -> {
            currentMonth = new GregorianCalendar(currentMonth.get(Calendar.YEAR), getMonthNum(mComboBox.getValue()), 1);
            mComboBox.setValue(mComboBox.getValue());  // устанавливаем выбранный элемент по умолчанию
            drawCalendar();
        });

        yComboBox.setOnAction(e -> {
            currentMonth = new GregorianCalendar(yComboBox.getValue(), currentMonth.get(Calendar.MONTH), 1);
            yComboBox.setValue(yComboBox.getValue());
            drawCalendar();
        });

        //кнопка информации
        info.setOnAction(event -> {
            try {
                infoOpen();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        //кнопка смены языка
        changeLang.setOnAction(event -> {
            try {
                changeLangOpen();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        //кнопка темы
        topic.setOnAction(event -> changeColor());

        //HBox hbFooter = new HBox(10);
        //hbFooter.getChildren().addAll(prevMonth, nextMonth);
        //hbFooter.setAlignment(Pos.CENTER);
        //setBottom(hbFooter);
        //setMargin(hbFooter, new Insets(15));
    }


    //загрузка окна информации(менять его дизайн в fxml)
    @FXML
    private void infoOpen() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("info.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

    //загрузка окна смены языка(менять его дизайн в fxml)
    @FXML
    private void changeLangOpen() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ChangeLang.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }

    // Функция смены цвета
    //TODO номальный дизайн
    private void changeColor() {
        if (!isDark) {
            firstColor.setBackground(Background.fill(Color.DARKCYAN));
            secondColor.setBackground(Background.fill(Color.DARKBLUE));
            thirdColor.setBackground(Background.fill(Color.BLACK));
            fourthColor.setBackground(Background.fill(Color.DARKBLUE));
            isDark = true;
        } else {
            firstColor.setBackground(Background.fill(Paint.valueOf("#38A3A5")));
            secondColor.setBackground(Background.fill(Paint.valueOf("#C7F9CC")));
            thirdColor.setBackground(Background.fill(Paint.valueOf("#80ED99")));
            fourthColor.setBackground(Background.fill(Paint.valueOf("#C7F9CC")));
            isDark = false;
        }
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
        int i;
        for (i = 0; i < 12; i++) {
            if (monthNames[i].equals(name)) {
                break;
            }
        }
        return i;
    }
}