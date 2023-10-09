package com.example.myapplication;

import java.io.Serializable;

public class Drinks implements Serializable {
    Integer drink_size;
    Integer alcohol_per;

    public Drinks(Integer drink_size, Integer alcohol_per) {
        this.drink_size = drink_size;
        this.alcohol_per = alcohol_per;
    }

    public Drinks() {
    }

    public int getDrink_size() {
        return drink_size;
    }

    public void setDrink_size(Integer drink_size) {
        this.drink_size = drink_size;
    }

    public Integer getAlcohol_per() {
        return alcohol_per;
    }

    public void setAlcohol_per(Integer alcohol_per) {
        this.alcohol_per = alcohol_per;
    }
}
