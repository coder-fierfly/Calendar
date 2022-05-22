package com.company;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Logic extends BorderPane implements Initializable {
    public Calendar currentMonth;
    public AnchorPane firstColor;
    public AnchorPane secondColor;
    public AnchorPane thirdColor;
    public MenuButton menuButton;
    Boolean isDark = false;

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
    }

    public void drawCalendar() {
        // меняем подписи у выпадающих списков выбора года и месяца
        mComboBox.setPromptText(getMonthName(currentMonth.get(Calendar.MONTH)));  // setValue(getMonthName(currentMonth.get(Calendar.MONTH)));
        yComboBox.setPromptText(String.valueOf(currentMonth.get(Calendar.YEAR)));
        gpBody.setGridLinesVisible(false);
        gpBody.getChildren().clear();
        gpBody.setGridLinesVisible(true);

        FadeAppController fadeApp = new FadeAppController();
        File langFile = new File("lang.txt");
        if (!langFile.exists()) {
            chooseLang(langFile);
        }
        fadeApp.init();
        fadeApp.startLoad();
        drawBody();
        drawFooter();
        fadeApp.endLoad();
    }

    @FXML
    private GridPane gpBody;

    public void chooseLang(File file) {
        //File langFile = new File("lang.txt");
        // если файл не существует записываю в него nый язык, чтобы дальше можно было выбрать любой другой
        try {
            boolean bool = file.createNewFile();
            FileWriter fr = new FileWriter("lang.txt", false);
            fr.write("en");
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            changeLangOpen();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //drawCalendar();
    }

    public void drawBody() {
        // рисуем дни недели
        for (int day = 1; day <= 7; day++) {
            Text tDayName = new Text(getDayName(day));
            gpBody.add(tDayName, day - 1, 0);
            GridPane.setHalignment(tDayName, HPos.CENTER);
            tDayName.setFont(Font.font("Segoe UI Semilight", 13));
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

        FadeAppController fadeApp = new FadeAppController();
        fadeApp.init();

        List<ParserThread> list = new ArrayList<>();
        for (int i = currentDay; i <= daysInMonth; i++) {
            if (dayOfWeek == 8) {
                dayOfWeek = 1;
                row++;
            }
            Text tDate = new Text(String.valueOf(currentDay));
            StringBuilder sb = new StringBuilder();
            //TODO переделать через time formatter
            if ((currentMonth.get(Calendar.MONTH) + 1) < 10) {
                sb.append("0");
            }
            sb.append(currentMonth.get(Calendar.MONTH) + 1).append("/");
            if (currentDay < 10) {
                sb.append("0");
            }
            sb.append(currentDay).append("/").append(currentMonth.get(Calendar.YEAR));
            if (dateNow.equals(String.valueOf(sb))) {
                tDate.setFill(Color.BLUE);
            }

            // делаем дни кликабельными
            list.add(addButtons(String.valueOf(sb), dayOfWeek - 1, row));

            gpBody.add(tDate, dayOfWeek - 1, row);
            GridPane.setHalignment(tDate, HPos.CENTER);
            currentDay++;
            dayOfWeek++;
        }

        fadeApp.startLoad();
        fadeApp.endLoad();

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
    }

    private String[] getWords(String id) {
        File file = new File("data.txt");
        String line;
        String[] words = null;
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            try {
                while ((line = br.readLine()) != null) {
                    if (line.startsWith(id)) {
                        System.out.println(line);
                        line = line.substring(11);
                        words = line.split("/");
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
        return words;
    }

    private String getParty(String id) {
        File file = new File("parties.txt");
        if (!file.exists()) {
            try {
                boolean bool = file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        String line;
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            try {
                while ((line = br.readLine()) != null) {
                    if (line.startsWith(id)) {
                        System.out.println(line);
                        line = line.substring(6);
                        sb.append(line).append("\n");
                    }
                }
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return String.valueOf(sb);
    }

    public static void addWords(String word, String fileName) {
        System.out.println(word);
        File file = new File(fileName);
        if (!file.exists()) {
            try {
                boolean bool = file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        List<String> list = new ArrayList<>();
        String line;
        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            try {
                while ((line = br.readLine()) != null) {
                    list.add(line);
                }
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            FileWriter writer = new FileWriter(fileName, false);
            String lineSeparator = System.getProperty("line.separator");
            writer.write(word + lineSeparator);
            writer.close();
            FileWriter writer2 = new FileWriter(fileName, true);
            for (String lines : list) {
                writer2.write(lines + lineSeparator);
            }
            writer2.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ParserThread addButtons(String id, int dayOfWeek, int row) {
        Button toggleButton11 = new Button();
        toggleButton11.setMaxWidth(Double.MAX_VALUE);
        toggleButton11.setMaxHeight(Double.MAX_VALUE);
        toggleButton11.setStyle("-fx-background-color: transparent;");

        toggleButton11.setId(String.valueOf(id));
        GridPane.setConstraints(toggleButton11, dayOfWeek, row);
        gpBody.setVgap(0);
        gpBody.setHgap(0);
        gpBody.getChildren().add(toggleButton11);

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, Integer.parseInt(id.substring(0, 2)) - 1);
        cal.set(Calendar.DATE, Integer.parseInt(id.substring(3, 5)));
        cal.set(Calendar.YEAR, Integer.parseInt(id.substring(6)));

        ParserThread pt = new ParserThread(id);
        if (cal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
            pt.run();
        }


        toggleButton11.setOnAction(event -> {
            System.out.println(cal.getTime());
            if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                addId(id);
                TestController test = new TestController();
                try {
                    test.testOpen();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                String[] words = getWords(id);
                String partyStr = getParty(id.substring(0, 5));
                if (partyStr == null || words[0] == null) {
                    showButton("Отсутствует подключение к интернету!", id);
                } else {
                    showButton(String.format("Да вы прям полиглот.\nСлово за выбранный день:\n%s - %s\n\nТакже сегодня отмечается следующее событие:\n%s", words[0], words[1], partyStr), id);
                }
            }
        });
        return pt;
    }

    public void addId(String word) {
        String fineName = "testId.txt";
        File file = new File(fineName);
        try {
            boolean bool = file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            FileWriter writer = new FileWriter(fineName, false);
            writer.write(word);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showButton(String text, String id) {

        VBox secondaryLayout = new VBox(10);
        Text textHead = new Text();
        textHead.setText("Информация о дне");
        Label label = new Label();
        label.setFont(Font.font("Segoe UI Semilight", 13));
        label.setText(text);
        secondaryLayout.getChildren().add(label);
        secondaryLayout.setSpacing(10);
        Label userName = new Label("Вы можете добавить новое событие на этот день: ");
        userName.setFont(Font.font("Segoe UI Semilight", 13));
        secondaryLayout.getChildren().add(userName);
        TextField userTextField = new TextField();
        userTextField.setFont(Font.font("Segoe UI Semilight", 13));
        secondaryLayout.getChildren().add(userTextField);
        Button btn = new Button("Добавить");
        btn.setFont(Font.font("Segoe UI Semilight", 13));
        secondaryLayout.getChildren().add(btn);

        btn.setOnAction(e -> {
            StringBuilder sb = new StringBuilder();
            sb.append(id.substring(0, 5)).append(" ").append(userTextField.getText());
            addWords(String.valueOf(sb), "parties.txt");
        });

        Scene secondScene = new Scene(secondaryLayout, 500, 300);
        Stage newWindow = new Stage();
        newWindow.setTitle("Информация о дне");
        newWindow.getIcons().add(new Image("file:Calendar.png"));
        newWindow.setScene(secondScene);
        newWindow.setResizable(false);
        newWindow.initModality(Modality.WINDOW_MODAL);
        newWindow.show();
    }

    @FXML
    private Button nextMonth, prevMonth, vocab, stat;
    @FXML
    private MenuItem topic, changeLang, info;
    @FXML
    private ComboBox<String> mComboBox;
    @FXML
    private ComboBox<Integer> yComboBox;

    private void drawFooter() {
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

        // кнопка информации
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
            currentMonth = new GregorianCalendar(currentMonth.get(Calendar.YEAR), currentMonth.get(Calendar.MONTH), 1);
            drawCalendar();
        });

        // кнопка словаря
        vocab.setOnAction(event -> {
            try {
                VocabController vocab = new VocabController();
                vocab.vocabOpen();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        // кнопка статистики
        stat.setOnAction(event -> {
            try {
                StatController stat = new StatController();
                stat.statOpen();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        // кнопка темы
        topic.setOnAction(event -> changeColor());
    }

    @FXML // загрузка окна информации
    private void infoOpen() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../fx/info.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Информация");
        stage.setResizable(false);
        stage.getIcons().add(new Image("file:Calendar.png"));
        stage.show();
    }

    @FXML // загрузка окна смены языка
    private void changeLangOpen() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../fx/changeLang.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Язык");
        stage.setResizable(false);
        stage.toFront();
        stage.getIcons().add(new Image("file:out/pictures/Calendar.png"));
        stage.showAndWait();
    }

    // функция смены цвета
    private void changeColor() {
        if (!isDark) {
            firstColor.setStyle("-fx-background-color: #a5a5a5;");
            secondColor.setStyle("-fx-background-color: #303131;");
            thirdColor.setStyle("-fx-background-color: #2a2a2a;");
            isDark = true;
            menuButton.getItems().remove(0);
            MenuItem menuItem = new MenuItem("Светлая тема");
            menuButton.getItems().add(0, menuItem);
            menuItem.setOnAction(event -> changeColor());
        } else {
            firstColor.setStyle("-fx-background-color: #d1f0f1;");
            secondColor.setStyle("-fx-background-color: #8ed8d8;");
            thirdColor.setStyle("-fx-background-color: #61c7c7;");
            isDark = false;
            menuButton.getItems().remove(0);
            MenuItem menuItem = new MenuItem("Темная тема");
            menuButton.getItems().add(0, menuItem);
            menuItem.setOnAction(event -> changeColor());
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

    private String getDayName(int num) {
        StringBuilder sb = new StringBuilder();
        switch (num) {
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

    public static String getMonthName(int num) {
        String[] monthNames = {"Январь", "Февраль", "Март", "Апрель", "Май", "Июнь", "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь"};
        return monthNames[num];
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