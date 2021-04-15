package com.piyushmaheswari.firebasedemo;

public class Info {

    private String email,name;

    public Info(String email, String name) {
        this.email = email;
        this.name = name;
    }

    public Info() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
