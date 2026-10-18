package org.example.day6;

public class Day6 {
    public static void main(String[] args){
        Person[] people={new Student("张三",new double[]{90,89,88,87,78,66}),
        new Teacher("李老师","java")};
        for(Person person:people){
            person.printInfo();
            System.out.println();

        }
    }
}
//子类继承父类的方法，但不继承构造方法
//super(...) 初始化父类那部分数据
//重写时方法名和参数必须相同，权限不能变小
