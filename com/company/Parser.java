package com.company;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.*;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;

public class Parser {
    public static void parse() {//main(String[] args) {
        Connection connect = findPage();
        try {
            Document document = connect.get();
            /* go(document);
            Elements elements1 = document.select("#page-wrapper > div.wrapper.wrapper-content.animated.article.article-page > div > div:nth-child(1) > div > div > div.text-center.article-title");
            Elements elements2 = document.select("#page-wrapper > div.wrapper.wrapper-content.animated.article.article-page > div > div:nth-child(1) > div > div > div.blockquote > div:nth-child(1) > span");
            System.out.println(elements1.get(0).text());
            System.out.println(elements2.get(0).text());
    }

    public static void go(Document document) {
        Elements o = document.getElementsByTag("#all_words > div > div:nth-child(1) > ul > li:nth-child(1)");
        o = o.getElementsByTag("li").get(0);
            var o1 = document.getElementsByTag("ul").get(0);
            System.out.println(document.select("#all_words > div > div:nth-child(1) > ul > li:nth-child(" + let + ") > a").text()); //o.first().getElementsByTag("a"));
            do {
                o = o.nextSibling();
                if (o.tagName == "LI") break;
            } while (true);
        } */
            String word = findWord(document);
            System.out.println(word);
            if (word.charAt(word.length() - 1) == '-') {
                word = word.replaceAll("-", "");
            }
            System.out.println(word);
            word = word.replaceAll(" ", "_");
            System.out.println(word);
            connect = Jsoup.connect("https://gufo.me/dict/etru/" + word).userAgent("Mozilla");
            word = connect.get().select("#dictionary-acticle > article > h1").text();
            String trans = connect.get().select("#dictionary-acticle > article > p > span").text();
            System.out.println(word);
            System.out.println(trans);
            //Dictionary dictionary = new Dictionary();
            //HashMap map = new HashMap();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Connection findPage() {
        /* URL u = new URL ( "https://gufo.me/dict/etru?page=234&letter=%C5%A1");
        HttpURLConnection huc = ( HttpURLConnection )  u.openConnection();
        huc.setRequestMethod ("HEAD");
        huc.connect();
        int code = huc.getResponseCode(); */

        char[] letters = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'r', 's', 'š', 't', 'u', 'v', 'w', 'õ', 'ä', 'ö', 'ü'};
        char let = letters[(int) ((Math.random()*((26 - 1) + 1)) + 1)];
        int count = 0;
        while (true) {
            Connection connect = Jsoup.connect("https://gufo.me/dict/etru?page=" + (count + 1) + "&letter=" + let).userAgent("Mozilla");
            try {
                connect.get().select("head > title").text(); //!= ("Not Found (#404)")) {
                count++;
                //}
            } catch (IOException e) {
                break;
            }
        }
        System.out.println(count);
        count = (int) ((Math.random()*((count - 1) + 1)) + 1);
        return Jsoup.connect("https://gufo.me/dict/etru?page=" + count + "&letter=" + let).userAgent("Mozilla");
    }

    public static String findWord(Document document) {
        int count = 0;
        while (true) {
            if (document.select("#all_words > div > div:nth-child(1) > ul > li:nth-child(" + (count + 1) + ") > a").text() == "") {
                break;
            } else {
                count++;
            }
        }
        System.out.println(count);
        int let = ((int) ((Math.random()*((count - 1) + 1)) + 1));
        return (document.select("#all_words > div > div:nth-child(1) > ul > li:nth-child(" + let + ") > a").text());
    }
}
