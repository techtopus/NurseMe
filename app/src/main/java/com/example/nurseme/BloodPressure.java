package com.example.nurseme;

public class BloodPressure {
    int systolic,diastolic;
    String condition,date;

    public int getSystolic() {
        return systolic;
    }

    public void setSystolic(int systolic) {
        this.systolic = systolic;
    }

    public int getDiastolic() {
        return diastolic;
    }

    public void setDiastolic(int diastolic) {
        this.diastolic = diastolic;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public BloodPressure(int systolic, int diastolic, String condition, String date) {
        this.systolic = systolic;
        this.diastolic = diastolic;
        this.condition = condition;
        this.date = date;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public BloodPressure() {

    }
}
