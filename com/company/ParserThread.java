package com.company;

import java.io.*;

public class ParserThread extends Thread {
    private String id;

    ParserThread(String id) {
        this.id = id;
    }

    @Override
    public void run() {  // этот метод будет вызван при старте потока
        Parser parser = new Parser();
        String[] w = getWords(id);
        if (w == null) {
            do {
                //TODO здесь была ошибка aConnection timed out: no further information  Cannot invoke "java.lang.CharSequence.toString()" because "s" is null
                w = parser.parse(getLang());
            } while (checkWord(w[0]));
            StringBuilder sb = new StringBuilder();
            sb.append(id).append(" ").append(w[0]).append("/").append(w[1]);
            if (!checkWord(String.valueOf(sb))) {
                Logic.addWords(String.valueOf(sb), "data.txt");
            }
        }

        String p = getParty(id);
        if (p == null) {
            //TODO ERROR Connection timed out: no further information
            PartyParser pp = new PartyParser();
            p = pp.parse(id);
            StringBuilder sb = new StringBuilder();
            sb.append(id).append(" ").append(p);
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
        //Сначала идет путь к каталогу
        File file = new File("data.txt");

        try {
            boolean bool = file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(file.exists());

        String line;
        String[] w = null;
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            try {
                while ((line = br.readLine()) != null) {
                    if (line.startsWith(id)) {
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

    private boolean checkWord(String w) {
        File file = new File("data.txt");

        try {
            boolean bool = file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String line;
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            //TODO ERROR at com.company.ParserThread.checkWord Cannot invoke "java.lang.CharSequence.toString()" because "s" is null
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

