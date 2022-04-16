package com.company;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.*;
import java.net.UnknownHostException;

public class Parser {
    public int[] pages = new int[0];
    //public String pages = null;

    public String[] parse(String lang) {
        String[] w = new String[2];
        Connection connect = findPage(lang);
        try {
            Document document;
            try {
                document = connect.get();
            } catch (UnknownHostException e) {
                return null;
            }
            String word = findWord(document);
            if (word.charAt(word.length() - 1) == '-') {
                word = word.replaceAll("-", "");
            }
            System.out.println(word);
            w[0] = word;
            word = word.replaceAll(" ", "_");
            StringBuilder sb = new StringBuilder();
            sb.append("https://gufo.me/dict/").append(lang).append("ru/").append(word);
            //connect = Jsoup.connect("https://gufo.me/dict/" + lang + "ru/" + word).userAgent("Mozilla");
            connect = Jsoup.connect(String.valueOf(sb)).userAgent("Mozilla");

            String trans = connect.get().select("#dictionary-acticle > article > p > span").text();
            System.out.println(trans);
            w[1] = trans;
            //Dictionary dictionary = new Dictionary();
            //HashMap map = new HashMap();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return w;
    }

    private Connection findPage(String lang) {
        /* URL u = new URL ( "https://gufo.me/dict/etru?page=234&letter=%C5%A1");
        HttpURLConnection huc = ( HttpURLConnection )  u.openConnection();
        huc.setRequestMethod ("HEAD");
        huc.connect();
        int code = huc.getResponseCode(); */
        /* char[] letters = new char[40]; //{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'r', 's', 'š', 't', 'u', 'v', 'w', 'õ', 'ä', 'ö', 'ü'};


        sb.append("https://gufo.me/dict/").append(lang).append("ru");
        Connection connect1 = Jsoup.connect(String.valueOf(sb)).userAgent("Mozilla");
        Document document = null;
        try {
            document = connect1.get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        sb.append("#abc > table > tbody > tr > td");
        int count = 0;
        while (true) {
            System.out.println(document.select(String.valueOf(sb)).text());
            if (document.select(String.valueOf(sb)).text().equals("")) {
                break;
            }
            letters[count] = document.select(String.valueOf(sb)).text().charAt(0);
            System.out.println(letters[count]);
            ++count;
        }
        sb.setLength(0);
        System.out.println(count); */
        char[] letters = new char[0];
        switch (lang) {
            case "et":
                letters = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'r', 's', 'š', 't', 'u', 'v', 'w', 'õ', 'ä', 'ö', 'ü'};
                if (pages.length != letters.length) {
                    pages = new int[letters.length];
                }
                break;
            case "de":
                letters = new char[]{'a', 'ä', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'ö', 'p', 'q', 'r', 's', 't', 'u', 'ü', 'v', 'w', 'x', 'y', 'z'};
                if (pages.length != letters.length) {
                    pages = new int[letters.length];
                }
                break;
            default:
                break;
        }

        int page = (int) ((Math.random()*((26 - 1) + 1)) + 1);
        char let = letters[page];
        int count = 0;
        StringBuilder sb = new StringBuilder();
        if (pages[page] == 0) {
            while (true) {  // TODO придумать что-нибудь для оптимизации чтения страницы (f.e. читать заголовок, но без использования get(), однако для этого нужно обходить защиту сайта (ошибку 403) - я еще не разобралась, как)
                sb.append("https://gufo.me/dict/").append(lang).append("ru?page=").append(count + 1).append("&letter=").append(let);
                //Connection connect = Jsoup.connect("https://gufo.me/dict/" + lang + "ru?page=" + (count + 1) + "&letter=" + let).userAgent("Mozilla");
                Connection connect = Jsoup.connect(String.valueOf(sb)).userAgent("Mozilla");
                sb.setLength(0);
                try {
                    //connect.get().select("head > title").text(); //!= ("Not Found (#404)")) {  Not Found (#404)
                    if (connect.get().title().equals("Not Found (#404)")) {
                        break;
                    }
                    count++;
                    pages[page] = count;
                } catch (IOException e) {
                    break;
                }
            }
        } else {
            count = pages[page];
        }

        System.out.println(count);
        count = (int) ((Math.random()*((count - 1) + 1)) + 1);
        //int ddd = sb.charAt(1);
        sb.append("https://gufo.me/dict/").append(lang).append("ru?page=").append(count).append("&letter=").append(let);
        //return Jsoup.connect("https://gufo.me/dict/etru?page=" + count + "&letter=" + let).userAgent("Mozilla");
        return Jsoup.connect(String.valueOf(sb)).userAgent("Mozilla");
    }

    private String findWord(Document document) {
        int count = 0;
        StringBuilder sb = new StringBuilder();
        while (true) {
            sb.append("#all_words > div > div:nth-child(1) > ul > li:nth-child(").append(count + 1).append(") > a");
            //if (document.select("#all_words > div > div:nth-child(1) > ul > li:nth-child(" + (count + 1) + ") > a").text() == "") {
            if (document.select(String.valueOf(sb)).text().equals("")) {
                sb.setLength(0);
                break;
            } else {
                sb.setLength(0);
                count++;
            }
        }
        System.out.println(count);
        int let = ((int) ((Math.random()*((count - 1) + 1)) + 1));
        sb.append("#all_words > div > div:nth-child(1) > ul > li:nth-child(").append(let).append(") > a");
        //return (document.select("#all_words > div > div:nth-child(1) > ul > li:nth-child(" + let + ") > a").text());
        return (document.select(String.valueOf(sb)).text());
    }
}
