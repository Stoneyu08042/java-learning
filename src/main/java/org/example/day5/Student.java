package org.example.day5;

public class Student {
    private String name;
    private  double[] scores;
    public Student(String name,double[] scores){
        this.name=name;
        this.scores=scores;
    }
    public double getAvgScores(){
        double sum=0;
        for (double score:scores){
            sum += score;
        }
        return sum/scores.length;
    }
    public double getMax(){
        double max=scores[0];
        for (int i=1;i<scores.length;i++){
            if (scores[i]>max){
                max=scores[i];
            }
        }
        return max;
    }
    public String getRating(){
        double average=getAvgScores();
        if(average>=90){
            return "优秀";
        } else if (average>=60) {
            return "及格";
        }else {
            return "不及格";
        }
    }
    public String getReport() {
        StringBuilder report = new StringBuilder();

        report.append("姓名：").append(name).append(System.lineSeparator());
        report.append("平均分：").append(getAvgScores()).append(System.lineSeparator());
        report.append("最高分：").append(getMax()).append(System.lineSeparator());
        report.append("等级：").append(getRating()).append(System.lineSeparator());

        return report.toString();
    }

    public void printReport() {
        System.out.print(getReport());
        System.out.println();
    }
}
