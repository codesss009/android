package com.example.bac;

public class DrinkDetails {
    double size, percentage;

    public DrinkDetails(double size, double percentage) {
        this.size = size;
        this.percentage = percentage;
    }

    public DrinkDetails() {
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage(double percentage) {
        this.percentage = percentage;
    }
}
