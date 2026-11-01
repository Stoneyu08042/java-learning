package org.example.day13;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Day13 {
    public static void main(String[] args) {
        StudentDao studentDao = new StudentDao();

        try (Scanner scanner = new Scanner(System.in)) {
            boolean running = true;

            while (running) {
                printMenu();
                String choice = scanner.nextLine().trim();

                try {
                    switch (choice) {
                        case "1" -> showAllStudents(studentDao);
                        case "2" -> addStudent(studentDao, scanner);
                        case "3" -> findStudentById(studentDao, scanner);
                        case "4" -> updateScore(studentDao, scanner);
                        case "5" -> deleteStudent(studentDao, scanner);
                        case "0" -> {
                            running = false;
                            System.out.println("已退出学生管理系统");
                        }
                        default -> System.out.println(
                                "请输入 0 到 5 之间的数字"
                        );
                    }
                } catch (SQLException e) {
                    System.out.println("数据库操作失败：" + e.getMessage());
                } catch (NumberFormatException e) {
                    System.out.println("请输入合法数字");
                } catch (IllegalArgumentException e) {
                    System.out.println("输入不合法：" + e.getMessage());
                }
            }
        }
    }

    private static void printMenu() {
        System.out.println();
        System.out.println("===== 学生管理系统 =====");
        System.out.println("1. 查看全部学生");
        System.out.println("2. 新增学生");
        System.out.println("3. 按 id 查询学生");
        System.out.println("4. 修改学生成绩");
        System.out.println("5. 删除学生");
        System.out.println("0. 退出");
        System.out.print("请选择：");
    }

    private static void showAllStudents(StudentDao studentDao)
            throws SQLException {
        List<Student> students = studentDao.findAll();

        if (students.isEmpty()) {
            System.out.println("当前没有学生数据");
            return;
        }

        System.out.printf(
                "%-6s %-12s %-8s %-20s%n",
                "ID", "姓名", "成绩", "创建时间"
        );

        for (Student student : students) {
            System.out.printf(
                    "%-6d %-12s %-8.2f %-20s%n",
                    student.getId(),
                    student.getName(),
                    student.getJavaScore(),
                    student.getCreatedAt()
            );
        }
    }

    private static void addStudent(
            StudentDao studentDao,
            Scanner scanner
    ) throws SQLException {
        String name = readName(scanner);
        double score = readScore(scanner);

        Student student = new Student(0, name, score, null);
        long id = studentDao.insert(student);

        System.out.println("新增成功，学生 id：" + id);
    }

    private static void findStudentById(
            StudentDao studentDao,
            Scanner scanner
    ) throws SQLException {
        long id = readId(scanner);
        Student student = studentDao.findById(id);

        if (student == null) {
            System.out.println("没有找到 id 为 " + id + " 的学生");
            return;
        }

        System.out.println(student);
    }

    private static void updateScore(
            StudentDao studentDao,
            Scanner scanner
    ) throws SQLException {
        long id = readId(scanner);
        double score = readScore(scanner);

        boolean success = studentDao.updateScore(id, score);

        if (success) {
            System.out.println("成绩修改成功");
        } else {
            System.out.println("没有找到要修改的学生");
        }
    }

    private static void deleteStudent(
            StudentDao studentDao,
            Scanner scanner
    ) throws SQLException {
        long id = readId(scanner);
        boolean success = studentDao.delete(id);

        if (success) {
            System.out.println("学生删除成功");
        } else {
            System.out.println("没有找到要删除的学生");
        }
    }

    private static String readName(Scanner scanner) {
        System.out.print("请输入学生姓名：");
        String name = scanner.nextLine().trim();

        if (name.isEmpty()) {
            throw new IllegalArgumentException("姓名不能为空");
        }

        return name;
    }

    private static long readId(Scanner scanner) {
        System.out.print("请输入学生 id：");
        long id = Long.parseLong(scanner.nextLine().trim());

        if (id <= 0) {
            throw new IllegalArgumentException("id 必须大于 0");
        }

        return id;
    }

    private static double readScore(Scanner scanner) {
        System.out.print("请输入 Java 成绩（0-100）：");
        double score = Double.parseDouble(scanner.nextLine().trim());

        if (score < 0 || score > 100) {
            throw new IllegalArgumentException(
                    "成绩必须在 0 到 100 之间"
            );
        }

        return score;
    }
}
