package com.example.nurseme;

public class Relative {
    String name;
    String contactno;
    String alternateno;
    String emailid;
    String uid;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Relative(String name, String contactno, String alternateno, String emailid, String uid) {
        this.name = name;
        this.contactno = contactno;
        this.alternateno = alternateno;
        this.emailid = emailid;
        this.uid = uid;
    }

    public Relative() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactno() {
        return contactno;
    }

    public void setContactno(String contactno) {
        this.contactno = contactno;
    }

    public String getAlternateno() {
        return alternateno;
    }

    public void setAlternateno(String alternateno) {
        this.alternateno = alternateno;
    }

    public String getEmailid() {
        return emailid;
    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
    }
}
