package com.example.nurseme;

public class Patient {
    String relativename;
    String name;
    int age;
    String gender;
    String nursingtype;
    String servicestype;
    String desc;

    public Patient(String relativename, String name, int age, String gender, String nursingtype, String servicestype, String desc) {
        this.relativename = relativename;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.nursingtype = nursingtype;
        this.servicestype = servicestype;
        this.desc = desc;
    }

    public Patient() {
    }

    public String getName() {
        return name;
    }

    public String getRelativename() {
        return relativename;
    }

    public void setRelativename(String relativename) {
        this.relativename = relativename;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getNursingtype() {
        return nursingtype;
    }

    public void setNursingtype(String nursingtype) {
        this.nursingtype = nursingtype;
    }

    public String getServicestype() {
        return servicestype;
    }

    public void setServicestype(String servicestype) {
        this.servicestype = servicestype;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
