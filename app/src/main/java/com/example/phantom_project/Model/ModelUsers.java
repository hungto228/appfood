package com.example.phantom_project.Model;

public class ModelUsers {
    private String name, password, phone;

    public ModelUsers() {

    }

    public ModelUsers(String name, String password) {
        this.name = name;
        this.password = password;

    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
