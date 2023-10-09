package com.example.assessment2;

import java.io.Serializable;

public class Profile implements Serializable {
    String Weight;
    public static final String Profile_Key = "profile_key";
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
