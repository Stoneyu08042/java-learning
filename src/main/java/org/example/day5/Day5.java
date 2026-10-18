package org.example.day5;

public class Day5 {
    public static void main(String[] args){
        Student student1=new Student("张三",new double[]{92,88,90,78,67});
        Student student2=new Student("李四",new double[]{90,98,70,78,67});
        student1.printReport();
        student2.printReport();

    }
}
//类：模板
//对象：模板创建出来的具体实例
//new：创建对象并调用构造方法
//构造方法：初始化对象
//this：当前对象
//private：保护对象内部数据
//静态方法：属于类
//实例方法：属于对象
//实例方法里的字段：属于当前对象，也就是 this 字段