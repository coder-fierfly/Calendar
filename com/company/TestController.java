package com.company;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.net.URL;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;

public class TestController implements Initializable {
    @FXML
    private ToggleGroup answers;
    @FXML
    private Text question_text;
    @FXML
    private RadioButton radio_btn_1, radio_btn_2, radio_btn_3, radio_btn_4;
    @FXML
    private Button answerBtn;


    @FXML
    private int nowQuestion = 0, correctAnswers;  // переменные для установки текущего номера вопроса и для подсчета количества верных ответов

    private String nowCorrectAnswer;  // в эту переменную будет устанавливаться корректный ответ текущего вопроса

    public Label resultLabel;
    public Button reButton;
    public Text infoTest;
    public ImageView imTest;
    public Rectangle recTest;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String id = getTestId();
        System.out.println(id);

        reButton.setOnAction(event -> {
            radio_btn_1.setVisible(true);
            radio_btn_2.setVisible(true);
            radio_btn_3.setVisible(true);
            radio_btn_4.setVisible(true);
            question_text.setVisible(true);
            answerBtn.setVisible(true);
            reButton.setVisible(false);
            infoTest.setVisible(false);
            imTest.setVisible(false);
            recTest.setVisible(false);
        });
        Questions[] questions = doTest(id);
        if (questions.length < 5) {
            return;
        }
        // берем корректный ответ для текущего вопроса
        nowCorrectAnswer = questions[nowQuestion].correctAns();
        question_text.setText(questions[nowQuestion].getQuestion());
        String[] answers1 = questions[nowQuestion].getAnswers();

        List<String> intList1 = Arrays.asList(answers1);
        Collections.shuffle(intList1);

        radio_btn_1.setText(intList1.get(0));
        radio_btn_2.setText(intList1.get(1));
        radio_btn_3.setText(intList1.get(2));
        radio_btn_4.setText(intList1.get(3));

        answerBtn.setOnAction(e -> {
            // получаем выбранную кнопку пользователем
            RadioButton selectedRadioButton = (RadioButton) answers.getSelectedToggle();
            // код будет выполняться только если пользователь выбрал ответ
            if (selectedRadioButton != null) {
                // получаем текст ответа
                String toogleGroupValue = selectedRadioButton.getText();

                if (toogleGroupValue.equals(nowCorrectAnswer)) {
                    System.out.println("Верный ответ");
                    correctAnswers++;
                } else {
                    System.out.println("Неверный ответ");
                }

                if (nowQuestion + 1 == questions.length) {
                    radio_btn_1.setVisible(false);
                    radio_btn_2.setVisible(false);
                    radio_btn_3.setVisible(false);
                    radio_btn_4.setVisible(false);
                    answerBtn.setVisible(false);

                    question_text.setText("Вы ответили верно на " + correctAnswers + " из " + questions.length + " вопросов!");
                    StringBuilder sb = new StringBuilder();
                    sb.append(id).append(" ").append(correctAnswers);
                    if (!checkWord(String.valueOf(sb))) {
                        Logic.addWords(String.valueOf(sb), "testData.txt");
                    }

                } else {
                    nowQuestion++;
                    nowCorrectAnswer = questions[nowQuestion].correctAns();

                    question_text.setText(questions[nowQuestion].getQuestion());
                    String[] answers = questions[nowQuestion].getAnswers();

                    List<String> intList = Arrays.asList(answers);

                    radio_btn_1.setText(intList.get(0));
                    radio_btn_2.setText(intList.get(1));
                    radio_btn_3.setText(intList.get(2));
                    radio_btn_4.setText(intList.get(3));

                    selectedRadioButton.setSelected(false);
                }
            }
        });
    }

    public String getTestId() {
        File file = new File("testId.txt");
        if (!file.exists()) {
            try {
                boolean bool = file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String line = null;
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            try {
                line = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return line;
    }

    public Questions[] doTest(String id) {
        File file = new File("data.txt");
        String line;
        ArrayList<String[]> words = new ArrayList<>(7);
        String[] newWords;
        StringBuilder sb = new StringBuilder();

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, Integer.parseInt(id.substring(0, 2)) - 1);
        cal.set(Calendar.DATE, Integer.parseInt(id.substring(3, 5)));
        cal.set(Calendar.YEAR, Integer.parseInt(id.substring(6)));
        System.out.println(cal.getTime());

        for (int i = 0; i < 6; i++) {
            sb.setLength(0);
            cal.add(Calendar.DAY_OF_MONTH, -1);
            System.out.println(cal.getTime());
            if ((cal.get(Calendar.MONTH) + 1) < 10) {
                sb.append("0");
            }
            sb.append(cal.get(Calendar.MONTH) + 1).append("/");
            if (cal.get(Calendar.DAY_OF_MONTH) < 10) {
                sb.append("0");
            }
            sb.append(cal.get(Calendar.DAY_OF_MONTH)).append("/").append(cal.get(Calendar.YEAR));
            //System.out.println(sb);
            if ((cal.get(Calendar.DAY_OF_MONTH) == 1) && (i < 5)) {
                infoTest.setText("Вы еще не изучили все слова, необходимые для прохождения недельного теста.\nВы можете посмотреть их, обратившись к станице предыдущего месяца.");
                reButton.setDisable(true);
                Questions[] q = new Questions[]{};
                return q;
            }

            try {
                BufferedReader br = new BufferedReader(new FileReader(file));
                try {
                    line = br.readLine();
                    System.out.println(sb);
                    while (line != null) {
                        if (line.startsWith(String.valueOf(sb))) {
                            line = line.substring(11);
                            System.out.println(line);
                            newWords = line.split("/");
                            words.add(newWords);
                            break;
                        }
                        line = br.readLine();
                    }
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        Questions[] questions = new Questions[]{
                new Questions("Как правильно переводится слово " + words.get(0)[0] + "?", new String[]{words.get(1)[1], words.get(5)[1], words.get(3)[1], words.get(0)[1]}),
                new Questions("Как правильно переводится слово " + words.get(1)[0] + "?", new String[]{words.get(3)[1], words.get(2)[1], words.get(0)[1], words.get(1)[1]}),
                new Questions("Как правильно переводится слово " + words.get(2)[0] + "?", new String[]{words.get(4)[1], words.get(1)[1], words.get(0)[1], words.get(2)[1]}),
                new Questions("Как правильно переводится слово " + words.get(3)[0] + "?", new String[]{words.get(5)[1], words.get(1)[1], words.get(2)[1], words.get(3)[1]}),
                new Questions("Как правильно переводится слово " + words.get(4)[0] + "?", new String[]{words.get(1)[1], words.get(0)[1], words.get(5)[1], words.get(4)[1]}),
                new Questions("Как правильно переводится слово " + words.get(5)[0] + "?", new String[]{words.get(2)[1], words.get(3)[1], words.get(4)[1], words.get(5)[1]}),
        };

        return questions;
    }

    @FXML //загрузка окна тестов
    public void testOpen() throws IOException {
        FXMLLoader loader = new FXMLLoader(TestController.class.getResource("Sample.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Тест");
        stage.getIcons().add(new Image("file:Calendar.png"));
        stage.setResizable(false);
        stage.show();
    }

    private boolean checkWord(String w) {
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
                    if (line.contains(w)) {
                        return true;
                    }
                }
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }
}
