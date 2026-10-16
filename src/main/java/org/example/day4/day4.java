package org.example.day4;

import java.util.Scanner;

public class day4 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        double[] scores = new double[5];
        for (int i = 0; i < scores.length; i++) {
            System.out.print("请输入第" + (i + 1) + "个成绩：");
            scores[i] = scanner.nextDouble();
        }
        double avgScore = calculateAvg(scores);
        double max=findMax(scores);
        String rating=getRating(avgScore);

        System.out.println("成绩的平均分为："+avgScore);
        System.out.println("成绩的最大值为："+max);
        System.out.println("平均成绩的等级为："+rating);

        scanner.close();
    }

    public static double calculateAvg(double[] scores) {
        double sum = 0;
        for (double score : scores) {
            sum += score;
        }
        return sum / scores.length;
    }
    public static double findMax(double[] scores){
        double max=scores[0];
        for (int i=1;i<scores.length;i++){
            if (scores[i]>max){
                max=scores[i];
            }
        }
        return max;
    }
    public static String getRating(double avgScore) {
        if (avgScore >= 90) {
            return "优秀";
        } else if (avgScore >= 60) {
            return "及格";
        } else {
            return "不及格";
        }
    }
}