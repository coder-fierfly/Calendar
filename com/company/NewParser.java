package com.company;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;

import java.io.*;
import java.nio.CharBuffer;
import java.util.Objects;

public class NewParser {

    public String[] getPage() throws IOException {
        //Запихнуть это в свич
//        FileReader fileReader = new FileReader("lang.txt");
//        fileReader.close();
        //char[] ch = new char[2];

        // считаем сначала первую строку
        //System.out.println("ASD:ASD:KLAS:DLKAS:LDA" + String.valueOf(fi.read(CharBuffer.allocate(1))));
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
            default -> throw new IllegalStateException("Unexpected value: " + fileName);
        }
        // TODO при выборе другого свича делать полное стирание всех файлов

        FileInputStream fis = new FileInputStream(vocFile);
        //TODO переделать идентификатор
        Document doc = Jsoup.parse(fis, null, "BE", Parser.xmlParser());


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
        try {
            File file = new File("numFile.txt");
            if (!file.exists()) {
                try {
                    boolean bool = file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            //TODO нормальная запись в файл
            FileWriter fw = new FileWriter("numFile.txt");
            fw.write(id);
            fw.write(lineSeparator);
            //fw.flush();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return id;
    }

    private boolean checkWord(String w) {
        File numFile = new File("numFile.txt");
        if (!numFile.exists()) {
            try {
                boolean bool = numFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
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
