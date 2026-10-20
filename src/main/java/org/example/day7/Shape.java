package org.example.day7;

public abstract class Shape implements Describable {
    private String name;
    protected Shape(String name){
        this.name=name;
    }
    public  String getName(){
        return name;
    }
    public abstract double getArea();
}

