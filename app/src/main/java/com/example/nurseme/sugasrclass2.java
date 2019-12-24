package com.example.nurseme;

public class sugasrclass2 {
    int beforeavg,afteravg;
    String email;

    public int getBeforeavg() {
        return beforeavg;
    }

    public void setBeforeavg(int beforeavg) {
        this.beforeavg = beforeavg;
    }

    public int getAfteravg() {
        return afteravg;
    }

    public void setAfteravg(int afteravg) {
        this.afteravg = afteravg;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public sugasrclass2() {
    }

    public sugasrclass2(int beforeavg, int afteravg, String email) {
        this.beforeavg = beforeavg;
        this.afteravg = afteravg;
        this.email = email;
    }
}
