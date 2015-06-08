package com.blueinklabs.taxassistantpakistan;


import java.text.DecimalFormat;
import java.util.ArrayList;

public class Child {

    private String Name;
    private int Image;
    private Double displayAmount;
    private ArrayList<Double> childComponents;

    public Child() {
        displayAmount = (double) 0;
        childComponents = new ArrayList<Double>();
        //this.Image = R.drawable.edit_icon;
    }

    public boolean isChildEmpty() {
        if (displayAmount == 0) return true;
        else {
            return false;
        }
    }

    public int getChildSize() {
        return childComponents.size();
    }

    public void addChildComponent(int index, Double cComponent) {
        childComponents.add(index, cComponent);
    }

    public Double getChildComponent(int index) {
        return childComponents.get(index);
    }

    public Double getComponentsSum() {
        int size = childComponents.size();
        Double SumComp = (double) 0;
        for (int i = 0; i < size; i++) {
            SumComp = SumComp + childComponents.get(i);
        }
        return SumComp;
    }

    public String getDisplayAmount() {
        if (displayAmount < 0) {
            DecimalFormat formatter = new DecimalFormat("#,###");
            formatter.setNegativePrefix("(");
            formatter.setNegativeSuffix(")");
            return "PKR " + formatter.format(displayAmount);
        } else if (displayAmount == 0) {
            return "";
        } else {
            DecimalFormat formatter = new DecimalFormat("#,###");
            return "PKR " + formatter.format(displayAmount);
        }
    }

    public void setDisplayAmount(double eAmount) {
        this.displayAmount = eAmount;
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