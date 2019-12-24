package com.example.nurseme;

public class NursePersonalInfo {
    String Uid;
String name;
String age;
String phoneno;
String locality;
String district;
String gender;
String email;
String status;

    public NursePersonalInfo(String uid, String name, String age, String phoneno, String locality, String district, String gender, String email, String status, int daycare, int nightcare, int stryathome) {
        Uid = uid;
        this.name = name;
        this.age = age;
        this.phoneno = phoneno;
        this.locality = locality;
        this.district = district;
        this.gender = gender;
        this.email = email;
        this.status = status;
        this.daycare = daycare;
        this.nightcare = nightcare;
        this.stryathome = stryathome;
    }

    int daycare,nightcare,stryathome;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getDaycare() {
        return daycare;
    }

    public void setDaycare(int daycare) {
        this.daycare = daycare;
    }

    public int getNightcare() {
        return nightcare;
    }

    public void setNightcare(int nightcare) {
        this.nightcare = nightcare;
    }

    public int getStryathome() {
        return stryathome;
    }

    public void setStryathome(int stryathome) {
        this.stryathome = stryathome;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public NursePersonalInfo() {
    }


}
