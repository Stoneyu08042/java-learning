package org.example.day6;

public class  Student extends Person {
    private double[] scores;
    public Student(String name,double[] scores){
        super(name);
        this.scores=scores;
    }
    @Override
    public String getRole(){
        return "学生";
    }
    @Override
    public void printInfo(){
        System.out.println("姓名："+getName());
        System.out.println("身份："+getRole());
        System.out.println("平均分："+getAverage());
    }
    public double getAverage(){
        double sum=0;
        for (double score:scores){
            sum+=score;
        }
        return sum/scores.length;
    }
}
