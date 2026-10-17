package org.example.day5;

public class Day5 {
    public static void main(String[] args){
        Student student1=new Student("张三",new double[]{92,88,90,78,67});
        Student student2=new Student("李四",new double[]{90,98,70,78,67});
        student1.printReport();
        student2.printReport();

    }
}
