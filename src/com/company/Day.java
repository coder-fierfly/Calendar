package com.company;

public class Day {
    private int num;
    private int weekNum;
    private String[] vocab;
    private String holiday;

    public Day(int num, int weekNum) { //, String[] vocab) {
        this.num = num;
        this.weekNum = weekNum;
        //this.vocab = vocab;
    }

    public Day() {
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getWeekNum() {
        return weekNum;
    }

    public void setWeekNum(int weekNum) {
        this.weekNum = weekNum;
    }

    public String[] getVocab() {
        return vocab;
    }

    public void setVocab(String[] vocab) {
        this.vocab = vocab;
    }

    public String getHoliday() {
        return holiday;
    }

    public void setHoliday(String holiday) {
        this.holiday = holiday;
    }

}
