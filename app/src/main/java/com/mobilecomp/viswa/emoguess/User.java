package com.mobilecomp.viswa.emoguess;

import android.provider.ContactsContract;

public class User {
    String name;
    String email;
    String phone;
    String childage;

    public User(String name, String email, String phone, String childage) {
        this.name=name;
        this.email=email;
        this.phone=phone;
        this.childage=childage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email=email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone=phone;
    }

    public String getAge() {
        return childage;
    }

    public void setAge(String age) {
        this.childage = age;
    }
}
