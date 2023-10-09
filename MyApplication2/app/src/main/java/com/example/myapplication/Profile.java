package com.example.myapplication;

import java.io.Serializable;

public class Profile implements Serializable {
    String Weight;
    String Gender;

    public Profile(String weight, String gender) {
        Weight = weight;
        Gender = gender;
    }

    public Profile() {
    }

    public String getWeight() {
        return Weight;
    }

    public void setWeight(String weight) {
        Weight = weight;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }
}
