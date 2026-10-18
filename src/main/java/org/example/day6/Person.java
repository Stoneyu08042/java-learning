package org.example.day6;

public class Person {
    private String name;
    public Person(String name){
        this.name=name;
    }
    public String getName(){
        return name;
    }
    public String getRole(){
        return "人员";
    }
    public void printInfo(){
        System.out.println("姓名："+name);
        System.out.println("身份："+getRole());
    }
}
