package com.example.nurseme;

public class BloodPressure {
    int systolic,diastolic;
    String condition;

    public BloodPressure(int systolic, int diastolic, String condition) {
        this.systolic = systolic;
        this.diastolic = diastolic;
        this.condition = condition;
    }

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

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public BloodPressure() {
    }
}
