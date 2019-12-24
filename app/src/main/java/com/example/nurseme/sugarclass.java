package com.example.nurseme;

public class sugarclass {
    int before,after;
    String condition,date;

    public int getBefore() {
        return before;
    }

    public void setBefore(int before) {
        this.before = before;
    }

    public int getAfter() {
        return after;
    }

    public void setAfter(int after) {
        this.after = after;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public sugarclass() {
    }

    public sugarclass(int before, int after, String condition, String date) {
        this.before = before;
        this.after = after;
        this.condition = condition;
        this.date = date;
    }
}
