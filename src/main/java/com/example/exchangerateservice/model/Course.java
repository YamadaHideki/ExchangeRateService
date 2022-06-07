package com.example.exchangerateservice.model;

public class Course {

    private String name;
    private double count;

    public Course(String name, Double count) {
        this.name = name;
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCount() {
        return count;
    }

    public void setCount(double count) {
        this.count = count;
    }
}
