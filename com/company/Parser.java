package com.company;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Parser {

    public String[] getPage() throws IOException {
        //беру файл
        File file = new File("lang.txt");
        //создаем объект FileReader для объекта File
        FileReader fr = new FileReader(file);
        //создаем BufferedReader с существующего FileReader для построчного считывания
        BufferedReader reader = new BufferedReader(fr);
        // считаем сначала первую строку
        String fileName = reader.readLine();
        int vocSize;
        File vocFile;
        Logic.setLang(fileName);
        //по слову в файле выбираю какой язык парсить
        switch (fileName) {
            case "en" -> {
                vocFile = new File("com/company/xml/en.xml");
                vocSize = 17007;
            }
            case "be" -> {
                vocFile = new File("com/company/xml/be.xml");
                vocSize = 43509;
            }
            case "cs" -> {
                vocFile = new File("com/company/xml/cs.xml");
                vocSize = 9655;
            }
            case "de" -> {
                vocFile = new File("com/company/xml/de.xml");
                vocSize = 13001;
            }
            case "sv" -> {
                vocFile = new File("com/company/xml/sv.xml");
                vocSize = 10403;
            }
            case "et" -> {
                vocFile = new File("com/company/xml/et.xml");
                vocSize = 58652;
            }
            default -> throw new IllegalStateException("Unexpected value: " + fileName);
        }

        FileInputStream fis = new FileInputStream (vocFile);
        Document doc = Jsoup.parse(fis, null, "VOC", org.jsoup.parser.Parser.xmlParser());
        Elements foreignLang = doc.getElementsByTag("k");
        Elements ourLang = doc.getElementsByTag("ar");
        StringBuilder sb = new StringBuilder();
        int num = getNumEl(vocSize);
        sb.append(foreignLang.get(num).ownText());
        sb.append("%").append(ourLang.get(num).ownText());
        return sb.toString().split("%");
    }

    // Выбираю из файла еще не занятые слова
    private int getNumEl(int size) {
        String lineSeparator = System.getProperty("line.separator");
        // Будущий id. По сути просто является местом элемента в файле.
        int id;
        do {
            id = (int) (Math.random() * size);
        } while (checkWord(String.valueOf(id)));

        FileWriter fw = null;
        try {
            fw = new FileWriter("numFile.txt", true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String idStr = String.valueOf(id);
        try {
            fw.write(idStr);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fw.write(lineSeparator);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return id;
    }

    //проверяю не занято ли место в файле
    private boolean checkWord(String str) {
        File numFile = new File("numFile.txt");
        try {
            boolean bool = numFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String line;
        try {
            BufferedReader br = new BufferedReader(new FileReader(numFile));
            try {
                while ((line = br.readLine()) != null) {
                    if (line.equals(str)) {
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