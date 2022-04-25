package com.company;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;

import java.io.*;
import java.util.Objects;

/*
 * есть мысль записывать в файл дату, строку с которой взяли из arrayList<>, и сами слова,
 * потом передавать в str[] для проверки в getNumEl все занятые строки оттуда(надеюсь можно это как-то максимально оптимизировать)
 * Все это нужно чтобы все у нас сохранялось и в словаре и по 2 раза слова не повторялись
 * */
public class newParser {
    private String id;
    newParser(String id) {
        this.id = id;
    }

    public String[] getPage() throws IOException {
        File numFile = new File("numFile");
        /*	appendChild(Node child) Вставьте узел в конец дочерних элементов этого элемента.
         appendElement(String tagName) Создайте новый элемент по имени тега и добавьте его в качестве последнего дочернего элемента.
         appendTo(Element parent) Добавьте этот элемент к предоставленному родительскому элементу в качестве его следующего дочернего элемента.
        	attr(String attributeKey, String attributeValue) Установите значение атрибута для этого элемента.
        	attr(String attributeKey, boolean attributeValue) Установите логическое значение атрибута для этого элемента.
        	prependChild(Node child) Добавьте узел в начало дочерних элементов этого элемента.
          val() Получить значение элемента формы (ввод, текстовое поле и т. д.).

         attributes() Получить все атрибуты элемента.
         	className() Получает буквальное значение атрибута class этого элемента, которое может включать несколько имен классов, разделенных пробелом.
        	getElementsByAttribute(String key) Найдите элементы, у которых есть именованный набор атрибутов.
         getElementsByAttributeValueStarting(String key, String valuePrefix) Найдите элементы, атрибуты которых начинаются с префикса значения.
         getElementsByAttributeValueNot(String key, String value) Найдите элементы, которые либо не имеют этого атрибута, либо имеют его с другим значением.
         id() Получить idатрибут этого элемента.
         id(String id) Установите idатрибут этого элемента.*/

        File fileBe = new File("be.xml");
        File infoFile = new File("infoFile.txt");
        FileInputStream fis = new FileInputStream(fileBe);
        //TODO переделать идентификатор
        Document doc = Jsoup.parse(fis, null, "BE", Parser.xmlParser());
        int sizeBe = 43509;
//        for (Element e : doc.select("ar")) {
//            //e.child(0).text(e.child(0).text() + "?");
//            //System.out.println(e.text());
//            size = e.childNodeSize();
//            // int s = words.size();
//            //words.add(e.child(1).text());
//
//        }

        // TODO если все так и останется, то переделать сразу в стрингу
        // перед этим делать проверку в getNumEl на совпадение с имеющимся файлом
        String allWord = Objects.requireNonNull(doc.getElementById(String.valueOf(getNumEl(sizeBe)))).after(Objects.requireNonNull(doc.getElementById(String.valueOf(getNumEl(sizeBe))))).text();
        String[] wordMass = allWord.split(" ", 2);
        System.out.println(wordMass.length);
        return wordMass;
        // пока достается вот так
        // <k>бамбавік</k>


//        int df = 3;
//        String sd = String.valueOf(df);

//        System.out.println("НАША ПРОБА СЛАЩЕ МЕДА");
//        System.out.println("НАША ПРОБА СЛАЩЕ МЕДА");

    }

    private int getNumEl(int size) {
        String line;
        String lineSeparator = System.getProperty("line.separator");
        String[] checkStr = new String[size];
        //наше будующее id. По сути просто является местом элемента в файле.
        int id;
        do {
            id = (int) (Math.random() * size);
        } while (checkWord(String.valueOf(id)));
        try {
            FileWriter fw = new FileWriter("numFile");
            fw.write(id);
            fw.write(lineSeparator);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return id;
    }

    private boolean checkWord(String w) {
        File numFile = new File("numFile");
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
