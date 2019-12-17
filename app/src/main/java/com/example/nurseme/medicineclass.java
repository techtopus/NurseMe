package com.example.nurseme;

public class medicineclass {
    String uid,morning,morning2,noon,noon2,night,night2;

    public String getMorning() {
        return morning;
    }

    public void setMorning(String morning) {
        this.morning = morning;
    }

    public String getMorning2() {
        return morning2;
    }

    public void setMorning2(String morning2) {
        this.morning2 = morning2;
    }

    public String getNoon() {
        return noon;
    }

    public void setNoon(String noon) {
        this.noon = noon;
    }

    public String getNoon2() {
        return noon2;
    }

    public void setNoon2(String noon2) {
        this.noon2 = noon2;
    }

    public String getNight() {
        return night;
    }

    public void setNight(String night) {
        this.night = night;
    }

    public String getNight2() {
        return night2;
    }

    public void setNight2(String night2) {
        this.night2 = night2;
    }

    public medicineclass() {
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public medicineclass(String uid, String morning, String morning2, String noon, String noon2, String night, String night2) {
        this.uid = uid;
        this.morning = morning;
        this.morning2 = morning2;
        this.noon = noon;
        this.noon2 = noon2;
        this.night = night;
        this.night2 = night2;
    }
}
