package com.assessment.pojo;

public class Box {

    private double length;
    private double breadth;
    private double height;
    private double weight;

    public Box(String[] box) {
        this.length = Integer.parseInt(box[0]);
        this.breadth = Integer.parseInt(box[1]);
        this.height = Integer.parseInt(box[2]);
        this.weight = Integer.parseInt(box[3]);
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

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}
