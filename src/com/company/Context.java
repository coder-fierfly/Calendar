package com.company;

public class Context {
    private final static Context instance = new Context();

    public static Context getInstance() {
        return instance;
    }

    private String string = new String();

    public String getString() {
        return string;
    }
}
