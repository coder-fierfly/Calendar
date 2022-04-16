package com.company;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.text.Text;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TestController {
    @FXML private ToggleGroup answers;
    @FXML private Text question_text;
    @FXML private RadioButton radio_btn_1, radio_btn_2, radio_btn_3, radio_btn_4;
    @FXML private Button answerBtn;
    public String id;

    // переменные для установки текущего номера вопроса и для подсчета количества верных ответов
    private int nowQuestion = 0, correctAnswers;
    // в эту переменную будет устанавливаться корректный ответ текущего вопроса
    private String nowCorrectAnswer;

    @FXML
    public void initialize() {
        String id = this.getId();
        System.out.println(this.getId());
        Questions[] questions = doTest(this.getId());
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
                    sb.append(getId()).append(" ").append(correctAnswers);
                    //Logic.addWords(String.valueOf(sb), "data.txt");
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

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Questions[] doTest(String id) {
        File file = new File("data.txt");
        String line;
        ArrayList<String[]> words = new ArrayList<>(7);
        String[] newWords;

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            try {
                /* boolean lineFound = false;
                while (!lineFound) {
                    while ((line = br.readLine()) != null) {
                        if (line.startsWith(id)) {
                            lineFound = true;
                            break;
                        }
                    }
                    // StringBuilder myString = new StringBuilder(id);
                   // myString.setCharAt(id.charAt(), ch);
                    // TODO чтобы можно было брать по неделям слова для тестов
                } */
                System.out.println(getId());
                int count = 0;
                while (((line = br.readLine()) != null) && (count < 6)) {
                    line = line.substring(11);
                    System.out.println(line);
                    newWords = line.split("/");
                    words.add(newWords);
                    ++count;
                    System.out.println(count);
                }
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Questions[] questions = new Questions[] {
            new Questions("Как правильно переводится слово " + words.get(0)[0] + "?", new String[]{words.get(1)[1], words.get(5)[1], words.get(3)[1], words.get(0)[1]}),
            new Questions("Как правильно переводится слово " + words.get(1)[0] + "?", new String[]{words.get(3)[1], words.get(2)[1], words.get(0)[1], words.get(1)[1]}),
            new Questions("Как правильно переводится слово " + words.get(2)[0] + "?", new String[]{words.get(4)[1], words.get(1)[1], words.get(0)[1], words.get(2)[1]}),
            new Questions("Как правильно переводится слово " + words.get(3)[0] + "?", new String[]{words.get(5)[1], words.get(1)[1], words.get(2)[1], words.get(3)[1]}),
            new Questions("Как правильно переводится слово " + words.get(4)[0] + "?", new String[]{words.get(1)[1], words.get(0)[1], words.get(5)[1], words.get(4)[1]}),
            new Questions("Как правильно переводится слово " + words.get(5)[0] + "?", new String[]{words.get(2)[1], words.get(3)[1], words.get(4)[1], words.get(5)[1]}),
        };

        return questions;
    }
}