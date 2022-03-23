package com.company;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.*;

public class Parser {
    public static void parse() {
        String lang = "et";
        Connection connect = findPage(lang);
        try {
            Document document = connect.get();
            String word = findWord(document);
            if (word.charAt(word.length() - 1) == '-') {
                word = word.replaceAll("-", "");
            }
            System.out.println(word);
            word = word.replaceAll(" ", "_");
            StringBuilder sb = new StringBuilder();
            sb.append("https://gufo.me/dict/").append(lang).append("ru/").append(word);
            //connect = Jsoup.connect("https://gufo.me/dict/" + lang + "ru/" + word).userAgent("Mozilla");
            connect = Jsoup.connect(String.valueOf(sb)).userAgent("Mozilla");

            String trans = connect.get().select("#dictionary-acticle > article > p > span").text();
            System.out.println(trans);
            //Dictionary dictionary = new Dictionary();
            //HashMap map = new HashMap();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Connection findPage(String lang) {
        /* URL u = new URL ( "https://gufo.me/dict/etru?page=234&letter=%C5%A1");
        HttpURLConnection huc = ( HttpURLConnection )  u.openConnection();
        huc.setRequestMethod ("HEAD");
        huc.connect();
        int code = huc.getResponseCode(); */

        char[] letters = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'r', 's', 'š', 't', 'u', 'v', 'w', 'õ', 'ä', 'ö', 'ü'};
        char let = letters[(int) ((Math.random()*((26 - 1) + 1)) + 1)];
        int count = 0;

        StringBuilder sb = new StringBuilder();
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
            } catch (IOException e) {
                break;
            }
        }

        System.out.println(count);
        count = (int) ((Math.random()*((count - 1) + 1)) + 1);
        sb.append("https://gufo.me/dict/etru?page=").append(count).append("&letter=").append(let);
        //return Jsoup.connect("https://gufo.me/dict/etru?page=" + count + "&letter=" + let).userAgent("Mozilla");
        return Jsoup.connect(String.valueOf(sb)).userAgent("Mozilla");
    }

    public static String findWord(Document document) {
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
