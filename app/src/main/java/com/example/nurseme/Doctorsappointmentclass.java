package com.example.nurseme;

public class Doctorsappointmentclass {
    String name,hospname,cause,time,date;

    public Doctorsappointmentclass(String name, String hospname, String cause, String time, String date) {
        this.name = name;
        this.hospname = hospname;
        this.cause = cause;
        this.time = time;
        this.date = date;
    }

    public Doctorsappointmentclass() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHospname() {
        return hospname;
    }

    public void setHospname(String hospname) {
        this.hospname = hospname;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
