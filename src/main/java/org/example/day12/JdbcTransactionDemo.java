package org.example.day12;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcTransactionDemo {
    public static void main(String[] args) {
        try (Connection connection = JdbcUtils.getConnection()) {
            runCommitDemo(connection);
            runRollbackDemo(connection);
        } catch (Exception e) {
            System.out.println("事务练习失败：" + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void runCommitDemo(Connection connection)
            throws SQLException {
        long testStudentId = 0;
        connection.setAutoCommit(false);

        try {
            testStudentId = insertStudent(connection, "事务提交测试", 80.00);
            updateScore(connection, testStudentId, 85.00);

            connection.commit();
            System.out.println(
                    "事务提交成功，测试学生 id=" + testStudentId
            );
        } catch (SQLException | RuntimeException e) {
            connection.rollback();
            System.out.println("提交失败，已回滚");
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }

        if (testStudentId > 0) {
            int deletedRows = deleteStudent(connection, testStudentId);
            System.out.println("清理测试数据，删除行数：" + deletedRows);
        }
    }

    private static void runRollbackDemo(Connection connection)
            throws SQLException {
        long studentId = 1;
        double beforeScore = getScore(connection, studentId);

        connection.setAutoCommit(false);

        try {
            updateScore(connection, studentId, 100.00);
            System.out.println("事务中临时把学生成绩改为 100");
            throw new SQLException("模拟事务执行失败");
        } catch (SQLException e) {
            connection.rollback();
            System.out.println("事务已回滚：" + e.getMessage());
        } finally {
            connection.setAutoCommit(true);
        }

        double afterScore = getScore(connection, studentId);
        System.out.printf(
                "回滚前成绩：%.2f，回滚后成绩：%.2f%n",
                beforeScore,
                afterScore
        );
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
            statement.executeUpdate();

            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (keys.next()) {
                    return keys.getLong(1);
                }
            }
        }

        throw new SQLException("插入成功，但没有获取到学生 id");
    }

    private static void updateScore(
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

            int rows = statement.executeUpdate();
            if (rows != 1) {
                throw new SQLException(
                        "修改学生失败，影响行数：" + rows
                );
            }
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

    private static double getScore(
            Connection connection,
            long id
    ) throws SQLException {
        String sql = """
                SELECT java_score
                FROM students
                WHERE id = ?
                """;

        try (PreparedStatement statement =
                     connection.prepareStatement(sql)) {
            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getDouble("java_score");
                }
            }
        }

        throw new SQLException("没有找到 id 为 " + id + " 的学生");
    }
}
