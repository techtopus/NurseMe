package com.example.nurseme;

public class hospital {
    String relativeid;
    String no;

    public String getRelativeid() {
        return relativeid;
    }

    public void setRelativeid(String relativeid) {
        this.relativeid = relativeid;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public hospital() {
    }

    public hospital(String relativeid, String no) {
        this.relativeid = relativeid;
        this.no = no;
    }
}
