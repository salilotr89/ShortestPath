package com.assessment.pojo;

public class Box {

    private double length;
    private double breadth;
    private double height;

    public Box(String[] box) {
        this.length = Integer.parseInt(box[0]);
        this.breadth = Integer.parseInt(box[1]);
        this.height = Integer.parseInt(box[2]);
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public double getBreadth() {
        return breadth;
    }

    public void setBreadth(double breadth) {
        this.breadth = breadth;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }
}
