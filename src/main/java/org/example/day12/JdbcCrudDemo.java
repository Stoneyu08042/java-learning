package org.example.day12;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcCrudDemo {
    public static void main(String[] args) {
        try (Connection connection = JdbcUtils.getConnection()) {
            System.out.println("===== 初始学生列表 =====");
            printAllStudents(connection);

            long studentId = insertStudent(connection, "测试学生", 88.00);
            System.out.println("\n新增学生 id：" + studentId);

            System.out.println("\n===== 新增后的学生 =====");
            printStudentById(connection, studentId);

            int updatedRows = updateScore(connection, studentId, 90.00);
            System.out.println("\n修改影响行数：" + updatedRows);
            printStudentById(connection, studentId);

            int deletedRows = deleteStudent(connection, studentId);
            System.out.println("\n删除影响行数：" + deletedRows);

            System.out.println("\n===== 删除后的学生列表 =====");
            printAllStudents(connection);
        } catch (Exception e) {
            System.out.println("数据库操作失败：" + e.getMessage());
            e.printStackTrace();
        }
    }

    private static long insertStudent(
            Connection connection,
            String name,
            double score
    ) throws SQLException {
        String sql = """
                INSERT INTO students (name, java_score)
                VALUES (?, ?)
                """;

        try (PreparedStatement statement =
                     connection.prepareStatement(
                             sql,
                             Statement.RETURN_GENERATED_KEYS
                     )) {
            statement.setString(1, name);
            statement.setDouble(2, score);

            int rows = statement.executeUpdate();
            System.out.println("插入影响行数：" + rows);

            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (keys.next()) {
                    return keys.getLong(1);
                }
            }
        }

        throw new SQLException("插入成功，但没有获取到学生 id");
    }

    private static int updateScore(
            Connection connection,
            long id,
            double score
    ) throws SQLException {
        String sql = """
                UPDATE students
                SET java_score = ?
                WHERE id = ?
                """;

        try (PreparedStatement statement =
                     connection.prepareStatement(sql)) {
            statement.setDouble(1, score);
            statement.setLong(2, id);
            return statement.executeUpdate();
        }
    }

    private static int deleteStudent(
            Connection connection,
            long id
    ) throws SQLException {
        String sql = "DELETE FROM students WHERE id = ?";

        try (PreparedStatement statement =
                     connection.prepareStatement(sql)) {
            statement.setLong(1, id);
            return statement.executeUpdate();
        }
    }

    private static void printStudentById(
            Connection connection,
            long id
    ) throws SQLException {
        String sql = """
                SELECT id, name, java_score, created_at
                FROM students
                WHERE id = ?
                """;

        try (PreparedStatement statement =
                     connection.prepareStatement(sql)) {
            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    printStudentRow(resultSet);
                } else {
                    System.out.println("没有找到 id 为 " + id + " 的学生");
                }
            }
        }
    }

    private static void printAllStudents(
            Connection connection
    ) throws SQLException {
        String sql = """
                SELECT id, name, java_score, created_at
                FROM students
                ORDER BY java_score DESC
                """;

        try (PreparedStatement statement =
                     connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            System.out.printf(
                    "%-5s %-10s %-8s %-20s%n",
                    "ID", "姓名", "成绩", "创建时间"
            );

            while (resultSet.next()) {
                printStudentRow(resultSet);
            }
        }
    }

    private static void printStudentRow(ResultSet resultSet)
            throws SQLException {
        System.out.printf(
                "%-5d %-10s %-8.2f %-20s%n",
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getDouble("java_score"),
                resultSet.getString("created_at")
        );
    }
}
//executeQuery()：执行 SELECT，返回 ResultSet
//executeUpdate()：执行 INSERT、UPDATE、DELETE，返回受影响行数
//setAutoCommit(false)：关闭自动提交，开启手动事务
//commit()：确认全部修改
//rollback()：撤销全部修改
//先不要自动提交 所有操作先放进同一个事务
//最后统一 commit 或 rollback