package com.company;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.UnknownHostException;

public class PartyParser {
    public String parse(String id) {
        Connection connect = findPage(id);
        try {
            Document document;
            try {
                document = connect.get();
            } catch (UnknownHostException e) {
                return null;
            }
            String word = findWord(document);
            return word;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    private Connection findPage(String id) {
        String[] str = id.split("/");
        id = String.join("-", str[2], str[0], str[1]);
        StringBuilder sb = new StringBuilder();
        sb.append("https://www.calend.ru/day/").append(id);
        return Jsoup.connect(String.valueOf(sb)).userAgent("Mozilla");
    }

    private String findWord(Document document) {
        StringBuilder sb = new StringBuilder();
        sb.append("body > div.wrapper > div.block.main > div.index_page.content > div:nth-child(1) > ul > li:nth-child(1) > div.caption > span > a");
        return (document.select(String.valueOf(sb)).text());
    }
}

