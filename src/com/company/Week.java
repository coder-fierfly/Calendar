package com.company;

import com.company.Day;
import java.util.ArrayList;

public class Week extends Day {

    private Day[] days;

    public Week(Day[] days) {
        this.days = days;
    }

    public Week() {
    }

    /* public ArrayList getDays() {
        return days;
    }

    public void setDays(ArrayList days) {
        this.days = days;
    }

    public int getDay(int number) {
        return days.get(number).getNum();
    } */
}