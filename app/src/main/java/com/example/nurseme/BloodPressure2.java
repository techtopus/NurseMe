package com.example.nurseme;

class BloodPressure2 {
  String email;
  int savg,davg;

    public int getSavg() {
        return savg;
    }

    public void setSavg(int savg) {
        this.savg = savg;
    }

    public int getDavg() {
        return davg;
    }

    public void setDavg(int davg) {
        this.davg = davg;
    }

    public BloodPressure2(String email, int savg, int davg) {
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
