package org.example.day2;

import java.util.Scanner;

public class day2 {
    public static void main(String[] args){
        Scanner scanner=new Scanner(System.in);
        System.out.print("请输入姓名：");
        String name=scanner.nextLine();
        System.out.print("请输入每天学习小时数：");
        double hoursPerDay=scanner.nextDouble();
        double hoursPerWeek=hoursPerDay*7;
        int minutesPerWeek=(int)(hoursPerWeek*60);
        boolean isQualified=hoursPerWeek>=10;
        System.out.print("请输入每周目标学习时长小时数为：");
        double aimHours=scanner.nextDouble();

        System.out.println();
        System.out.println("===学习报告===");
        System.out.println("姓名："+name);
        System.out.println("每天学习时间："+hoursPerDay+"小时");
        System.out.println("一周学习时长："+hoursPerWeek+"小时");
        System.out.println("总计一周学习时长"+minutesPerWeek+"分钟");
        System.out.println("每周学习时长是否超过10小时："+isQualified);
        System.out.println("距离目标时长还差"+(aimHours-hoursPerWeek)+"小时");
        scanner.close();
    }
}
