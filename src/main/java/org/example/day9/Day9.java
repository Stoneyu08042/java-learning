package org.example.day9;

import org.example.day5.Student;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day9 {
    public static void main(String[] args){
        List<Student> students=new ArrayList<>();
        students.add(new Student("王五",new double[]{84,98,92,86,77}));
        students.add(new Student("石头", new double[]{70, 65, 80}));
        students.add(new Student("花花", new double[]{95, 92, 96}));
//        List：规定能做什么，接口
//        ArrayList：具体怎么做，实现类

        System.out.println(students.size());
        System.out.println(students.isEmpty());
        students.remove(0);
        System.out.print(new Student("花花", new double[]{95, 92, 96}));
        for (Student student:students){
            student.printReport();
        }


        Map<String,Student>studentMap=new HashMap<>();
        studentMap.put("煤球",new Student("煤球",new double[]{66,77,88,99}));
        studentMap.put("mimi",new Student("mimi",new double[]{66,78,88,99}));
        Student student=studentMap.get("mimi");
        student.printReport();
//        if (studentMap.containsKey("煤球")) {
//            Student studentTest = studentMap.get("煤球");
//            studentTest.printReport();
//        }
//        else {
//            System.out.println("没有找到该学生");
//        }
        Student missing = studentMap.get("不存在");
        if (missing == null) {
            System.out.println("没有找到该学生");
        } else {
            missing.printReport();
        }
        System.out.println(studentMap.keySet());
    }

}
