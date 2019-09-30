package com.example.nurseme;

public class NursePersonalInfo {
    String Uid;
String name;
String age;
String phoneno;
String locality;
String district;
String gender;
String profilepic;

    public NursePersonalInfo(String uid, String name, String age, String phoneno, String locality, String district, String gender, String profilepic) {
       this. Uid = uid;
        this.name = name;
        this.age = age;
        this.phoneno = phoneno;
        this.locality = locality;
        this.district = district;
        this.gender = gender;
        this.profilepic = profilepic;
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

    public String getProfilepic() {
        return profilepic;
    }

    public void setProfilepic(String profilepic) {
        this.profilepic = profilepic;
    }
}
