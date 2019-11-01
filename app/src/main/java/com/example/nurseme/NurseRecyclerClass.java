package com.example.nurseme;

public class NurseRecyclerClass {
    String username;
    String location,email;

    public NurseRecyclerClass(String username, String location,String email) {
        this.username = username;
        this.location = location;
        this.email=email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public NurseRecyclerClass() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}