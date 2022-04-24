package com.company;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
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

    public void getPage() throws IOException {
        //	appendChild(Node child) Вставьте узел в конец дочерних элементов этого элемента.
        // appendElement(String tagName) Создайте новый элемент по имени тега и добавьте его в качестве последнего дочернего элемента.
        // appendTo(Element parent) Добавьте этот элемент к предоставленному родительскому элементу в качестве его следующего дочернего элемента.
        //	attr(String attributeKey, String attributeValue) Установите значение атрибута для этого элемента.
        //	attr(String attributeKey, boolean attributeValue) Установите логическое значение атрибута для этого элемента.
        //	prependChild(Node child) Добавьте узел в начало дочерних элементов этого элемента.
        //  val() Получить значение элемента формы (ввод, текстовое поле и т. д.).

        // attributes() Получить все атрибуты элемента.
        // 	className() Получает буквальное значение атрибута class этого элемента, которое может включать несколько имен классов, разделенных пробелом.
        //	getElementsByAttribute(String key) Найдите элементы, у которых есть именованный набор атрибутов.
        // getElementsByAttributeValueStarting(String key, String valuePrefix) Найдите элементы, атрибуты которых начинаются с префикса значения.
        // getElementsByAttributeValueNot(String key, String value) Найдите элементы, которые либо не имеют этого атрибута, либо имеют его с другим значением.
        // id() Получить idатрибут этого элемента.
        // id(String id) Установите idатрибут этого элемента.
        File file = new File("be.xml");
        FileInputStream fis = new FileInputStream(file);
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
        Element desktopOnly = Objects.requireNonNull(doc.getElementById(String.valueOf(getNumEl(sizeBe)))).child(0);
        // пока достается вот так
        // <k>бамбавік</k>
        int df = 3;
        String sd = String.valueOf(df);

//        System.out.println("НАША ПРОБА СЛАЩЕ МЕДА");
//        System.out.println("НАША ПРОБА СЛАЩЕ МЕДА");

    }

    private int getNumEl(int size) {
        //проверка должны быть с файлом
        String[] checkStr = new String[size];
        //наше будующее id. По сути просто является местом элемента в файле.
        int id;
        do {
            id = (int) (Math.random() * size);
        } while (checkStr[id - 1] != null);
        checkStr[id - 1] = "+";
        // TODO надо это бы записывать в файл после записи в str[],
        // чтобы все оставалось после закрытия приложения
        return id;
    }

}
