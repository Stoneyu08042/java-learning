package org.example.day7;

public class Circle extends Shape implements Describable{
    private double radius;
    public Circle(double radius){
        super("圆形");
        this.radius=radius;
    }
    @Override
    public  double getArea(){
        return Math.PI*radius*radius;
    }
    @Override
    public String describe(){
        return getName()+"的面积是："+getArea();
    }
}
