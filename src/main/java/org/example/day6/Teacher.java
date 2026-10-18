package org.example.day6;

public class Teacher extends Person{
    private String subject;
    public Teacher(String name,String subject){
        super(name);
        this.subject=subject;
    }
    @Override
    public String getRole(){
        return "教师";
    }
    @Override
    public void printInfo() {
        System.out.println("姓名：" + getName());
        System.out.println("身份：" + getRole());
        System.out.println("学科：" + subject);
    }
}
