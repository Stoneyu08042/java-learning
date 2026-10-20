package org.example.day7;

public class Day7 {
    public static  void main(String[] args){
        Shape[] shapes={new Rectangle(4,5),
                new Circle(3) };
        for (Shape shape:shapes){
            System.out.println(shape.describe());
        }
    }
}
