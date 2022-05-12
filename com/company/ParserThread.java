package com.company;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ParserThread {
    private String id;

    ParserThread(String id) {
        this.id = id;
    }

    public void run() {
        NewParser pvf = new NewParser();
        //TODO: многа words
        String[] words = getWords(id);

        if (words == null) {
            try {
                words = pvf.getPage();
            } catch (IOException e) {
                e.printStackTrace();
            }

            StringBuilder sb = new StringBuilder();
            sb.append(id).append(" ").append(words[0]).append("/").append(words[1]);
            if (!checkWord(String.valueOf(sb))) {
                Logic.addWords(String.valueOf(sb), "data.txt");
            }
        }

        String part = getParty(id);
        if (part == null) {
            PartyParser pp = new PartyParser();
            part = pp.parse(id);
            StringBuilder sb = new StringBuilder();
            sb.append(id).append(" ").append(part);
            Logic.addWords(String.valueOf(sb), "parties.txt");
        }
    }

    public String getLang() {
        File file = new File("lang.txt");
        String line = null;
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            try {
                line = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            return "et";
        }
        return line;
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

        String line = null;
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            try {
                while ((line = br.readLine()) != null) {
                    if (line.startsWith(id)) {
                        System.out.println(line);
                        line = line.substring(11);
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
        return line;
    }

    private String[] getWords(String id) {
        File file = new File("data.txt");
        if (!file.exists()) {
            try {
                boolean bool = file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

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


    private boolean checkWord(String word) {
        File file = new File("data.txt");
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
                    if (line.contains(word)) {
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

