package org.example.day3;

import java.util.Scanner;

public class day3 {
    public static void main(String[] args){
        Scanner scanner=new Scanner(System.in);
        double totalHours=0;
        for(int day=1;day<=7;day++){
            System.out.print("请输入第"+day+"天的学习小时数：");
            double hours=scanner.nextDouble();
            totalHours+=hours;
        }
        double avg_hours=totalHours/7;
        String rating;
        if(avg_hours>=3){
            rating="非常棒";
        } else if (avg_hours>=2) {
            rating="还行";
        } else if (avg_hours>=1) {
            rating="再不学等死了";
        }else {
            rating="完蛋";
        }
        System.out.println();
        System.out.println("====学习统计=====");
        System.out.println("7天总学习时间"+totalHours+"小时");
        System.out.println("平均每天"+avg_hours+"小时");
        System.out.println("评价："+rating);

        for(int i=1;i<=10;i++){
            System.out.println(i);
        }
        int sum=0;
        int j=1;
        while (j<=100){
            sum+=j;
            j+=1;
        }
        System.out.print("1到100的总和是："+sum);
        System.out.println("请输入成绩：");
        double score=scanner.nextDouble();
        String rate;
        if(score>=90){
            rate="优秀";
        } else if (score>=60) {
            rate="及格";
        } else{
            rate="不及格";
        }
        System.out.println("成绩评价：" + rate);
        scanner.close();
    }
}
