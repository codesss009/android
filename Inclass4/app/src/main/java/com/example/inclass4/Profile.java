package com.example.inclass4;

import java.io.Serializable;

public class Profile implements Serializable {
    String name, email, id, dept;

    public Profile(String name, String email, String id, String dept) {
        this.name = name;
        this.email = email;
        this.id = id;
        this.dept = dept;
    }

    public Profile() {
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
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }
}
