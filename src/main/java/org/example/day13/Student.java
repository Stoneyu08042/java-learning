package org.example.day13;

public class Student {
    private long id;
    private String name;
    private double javaScore;
    private String createdAt;

    public Student(long id, String name, double javaScore, String createdAt) {
        this.id = id;
        this.name = name;
        this.javaScore = javaScore;
        this.createdAt = createdAt;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getJavaScore() {
        return javaScore;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setJavaScore(double javaScore) {
        this.javaScore = javaScore;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        StringBuilder report = new StringBuilder();
        report.append("学号：").append(id).append(System.lineSeparator());
        report.append("姓名：").append(name).append(System.lineSeparator());
        report.append("成绩：").append(javaScore).append(System.lineSeparator());
        report.append("时间：").append(createdAt).append(System.lineSeparator());

        return report.toString();
    }
}
