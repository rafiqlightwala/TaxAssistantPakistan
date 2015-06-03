package com.blueinklabs.taxassistantpakistan;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class Group {

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

}
