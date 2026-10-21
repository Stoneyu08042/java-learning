package org.example.day7;

public class Rectangle extends Shape implements Describable{
    private  double width;
    private  double height;
    public Rectangle(double width,double height){
        super("长方形");
        if (width<=0 || height<=0){
            throw new IllegalArgumentException("宽和高必须大于0");
        }
        this.width=width;
        this.height=height;
    }
    @Override
    public double getArea(){
        return width*height;
    }
    @Override
    public String describe(){
        return getName()+"的面积是："+getArea();
    }
}
