package com.blueinklabs.taxassistantpakistan;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class Group implements Serializable {

    private String Name;
    private ArrayList<Child> Items;
    private double headingResult;

    public Group() {
        headingResult = 0;
    }

    public String getHeadingResult() {
        if (headingResult < 0) {
            DecimalFormat formatter = new DecimalFormat("#,###");
            formatter.setNegativePrefix("(");
            formatter.setNegativeSuffix(")");
            return "PKR " + formatter.format(headingResult);
        }
        DecimalFormat formatter = new DecimalFormat("#,###");
        return "PKR " + formatter.format(headingResult);

    }

    public void setHeadingResult(double hResult) {
        this.headingResult = hResult;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public ArrayList<Child> getItems() {
        return Items;
    }

    public void setItems(ArrayList<Child> Items) {
        this.Items = Items;
    }

    public Child getChild(int cInt) {
        return this.Items.get(cInt);
    }

    public int getChildCount() {
        return Items.size();
    }

    public boolean isGroupEmpty() {
        Double tempVal = Double.valueOf(0);
        for (int i = 0; i < getChildCount(); i++) {
            tempVal = tempVal + Items.get(i).getComponentsSum();
        }
        if (tempVal.intValue() == 0) {
            return Boolean.TRUE;
        } else
            return Boolean.FALSE;
    }
}
