package com.example.nurseme;

class BloodPressure2 {
  String email;
  float savg,davg;


    public float getSavg() {
        return savg;
    }

    public void setSavg(float savg) {
        this.savg = savg;
    }

    public float getDavg() {
        return davg;
    }

    public void setDavg(float davg) {
        this.davg = davg;
    }

    public BloodPressure2(String email, float savg, float davg) {
        this.email = email;
        this.savg = savg;
        this.davg = davg;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }




    public BloodPressure2() {
    }
}
