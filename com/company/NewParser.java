package com.company;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;

//TODO: кста нам вроде говорили, что лучше не делать такие импорты со звездочкой, так что если получится исправить, будет славно
import java.io.*;
import java.util.Objects;

public class NewParser {

    public String[] getPage() throws IOException {
        File fileBe = new File("be.xml");
        FileInputStream fis = new FileInputStream(fileBe);
        //TODO переделать идентификатор
        Document doc = Jsoup.parse(fis, null, "BE", Parser.xmlParser());
        int sizeBe = 43509;

        String allWord = Objects.requireNonNull(doc.getElementById(String.valueOf(getNumEl(sizeBe)))).after(Objects.requireNonNull(doc.getElementById(String.valueOf(getNumEl(sizeBe))))).text();
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

            FileWriter fw = new FileWriter("numFile.txt");
            fw.write(id);
            fw.write(lineSeparator);
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
