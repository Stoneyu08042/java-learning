package org.example.studentapi.student;

public class Student {
    private final long id;
    private final String name;
    private final String className;
    private final double javaScore;
    private final String createdAt;

    public Student(
            long id,
            String name,
            String className,
            double javaScore,
            String createdAt
    ) {
        this.id = id;
        this.name = name;
        this.className = className;
        this.javaScore = javaScore;
        this.createdAt = createdAt;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getClassName() {
        return className;
    }

    public double getJavaScore() {
        return javaScore;
    }

    public String getCreatedAt() {
        return createdAt;
    }
}
