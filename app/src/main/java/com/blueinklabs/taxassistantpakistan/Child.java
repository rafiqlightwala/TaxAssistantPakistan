package com.blueinklabs.taxassistantpakistan;


import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Child implements Serializable {

    private String Name;
    private int Image;
    private Double displayAmount;
    private List<Double> childComponents;
    private List<Boolean> childPeriod;

    public Child() {
        displayAmount = (double) 0;
        childComponents = new ArrayList<Double>();
        childPeriod = new ArrayList<Boolean>();
        //this.Image = R.drawable.edit_icon;
    }

    public boolean isChildEmpty() {
        if (displayAmount == 0) return true;
        else {
            return false;
        }
    }

    public int getChildPeriodSize() {
        return childPeriod.size();
    }

    public int getChildComponentSize() {
        return childComponents.size();
    }

    public void addChildComponent(int index, Double cComponent) {
        if (getChildComponentSize() <= index) {
            childComponents.add(cComponent);
        } else {
            childComponents.set(index, cComponent);
        }
    }

    public Double getChildComponent(int index) {
        if (getChildComponentSize() > index)
            return childComponents.get(index);
        else
            return Double.valueOf(0);

    }

    public void addChildPeriod(int index, Boolean cPeriod) {
        if (getChildPeriodSize() <= index) {
            childPeriod.add(cPeriod);
        } else {
            childPeriod.set(index, cPeriod);
        }
    }

    public Boolean getChildPeriod(int index) {
        if (childPeriod.size() > 0) {
            return childPeriod.get(index);
        } else {
            return Boolean.FALSE;
        }
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

    public void setDisplayAmount() {
        Double tempDouble = Double.valueOf(0);
        for (int i = 0; i < getChildComponentSize(); i++) {
            tempDouble = tempDouble + childComponents.get(i);
        }
        this.displayAmount = tempDouble;
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