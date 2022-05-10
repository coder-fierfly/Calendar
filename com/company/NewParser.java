package com.company;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;

import java.io.*;
import java.nio.CharBuffer;
import java.util.Objects;

public class NewParser {

    public String[] getPage() throws IOException {
        File file = new File("lang.txt");
        //создаем объект FileReader для объекта File
        FileReader fr = new FileReader(file);
        //создаем BufferedReader с существующего FileReader для построчного считывания
        BufferedReader reader = new BufferedReader(fr);
        // считаем сначала первую строку
        String fileName = reader.readLine();
        int vocSize;
        File vocFile;
        switch (fileName) {
            case "be" -> {
                vocFile = new File("be.xml");
                vocSize = 43509;
            }
            case "cs" -> {
                vocFile = new File("cs.xml");
                vocSize = 9655;
            }
            case "de" -> {
                vocFile = new File("de.xml");
                vocSize = 13001;
            }
            case "sv" -> {
                vocFile = new File("sv.xml");
                vocSize = 10403;
            }
            case "et" -> {
                vocFile = new File("et.xml");
                vocSize = 58652;
            }
            default -> throw new IllegalStateException("Unexpected value: " + fileName);
        }
        // TODO при выборе другого свича делать полное стирание всех файлов

        FileInputStream fis = new FileInputStream(vocFile);
        Document doc = Jsoup.parse(fis, null, "VOC", Parser.xmlParser());


        String allWord = Objects.requireNonNull(doc.getElementById(String.valueOf(getNumEl(vocSize)))).after(Objects.requireNonNull(doc.getElementById(String.valueOf(getNumEl(vocSize))))).text();
        String[] wordMass = allWord.split(" ", 2);
        return wordMass;
    }

    private int getNumEl(int size) {
        String lineSeparator = System.getProperty("line.separator");
        //наше будующее id. По сути просто является местом элемента в файле.
        int id;
        do {
            id = (int) (Math.random() * size);
        } while (checkWord(String.valueOf(id)));

        //TODO нормальная запись в файл, почему-то все стирается..
        FileWriter fw = null;
        try {
            fw = new FileWriter("numFile.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        String idStr = String.valueOf(id);
        try {
            assert fw != null;
            fw.write(idStr);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(idStr);
        try {
            fw.write(lineSeparator);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //fw.flush();
        try {
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return id;
    }

    private boolean checkWord(String w) {
        File numFile = new File("numFile.txt");
        try {
            boolean bool = numFile.createNewFile();
            if (bool) {
                System.out.println("2 СООООООООООООООООООООООООООООООООООООООООООООЗДАЛСЯЯЯЯЯЯЯЯЯЯЯЯЯЯЯЯЯЯ");
            } else {
                System.out.println("\n" + " 2 уже существует numFile.txt");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        String line;
        try {
            BufferedReader br = new BufferedReader(new FileReader(numFile));
            try {
                while ((line = br.readLine()) != null) {
                    if (line.equals(w)) {
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
