package com.blueinklabs.taxassistantpakistan;


import java.text.DecimalFormat;

public class Child {

    private String Name;
    private int Image;
    private double enteredAmount;

    public Child() {
        enteredAmount = 0;
    }

    public String getEnteredAmount() {
        DecimalFormat formatter = new DecimalFormat("#,###");
        return "PKR " + formatter.format(enteredAmount);
    }

    public void setEnteredAmount(double eAmount) {
        this.enteredAmount = eAmount;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public int getImage() {
        return Image;
    }

    public void setImage(int Image) {
        this.Image = Image;
    }
}