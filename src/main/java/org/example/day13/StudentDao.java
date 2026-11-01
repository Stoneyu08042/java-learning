package org.example.day13;

import org.example.day12.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class StudentDao {
    public List<Student> findAll() throws SQLException {
        List<Student> students = new ArrayList<>();

        String sql = """
                SELECT id, name, java_score, created_at
                FROM students
                ORDER BY java_score DESC
                """;

        try (
                Connection connection = JdbcUtils.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()
        ) {
            while (resultSet.next()) {
                students.add(mapStudent(resultSet));
            }
        }

        return students;
    }

    public Student findById(long id) throws SQLException {
        String sql = """
                SELECT id, name, java_score, created_at
                FROM students
                WHERE id = ?
                """;

        try (
                Connection connection = JdbcUtils.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {
            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapStudent(resultSet);
                }
            }
        }

        return null;
    }

    public long insert(Student student) throws SQLException {
        String sql = """
                INSERT INTO students (name, java_score)
                VALUES (?, ?)
                """;

        try (
                Connection connection = JdbcUtils.getConnection();
                PreparedStatement statement = connection.prepareStatement(
                        sql,
                        Statement.RETURN_GENERATED_KEYS
                )
        ) {
            statement.setString(1, student.getName());
            statement.setDouble(2, student.getJavaScore());

            int rows = statement.executeUpdate();
            if (rows != 1) {
                throw new SQLException(
                        "新增学生失败，影响行数：" + rows
                );
            }

            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (keys.next()) {
                    return keys.getLong(1);
                }
            }
        }

        throw new SQLException("插入成功，但没有获取到 id");
    }

    public boolean updateScore(long id, double score)
            throws SQLException {
        String sql = """
                UPDATE students
                SET java_score = ?
                WHERE id = ?
                """;

        try (
                Connection connection = JdbcUtils.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {
            statement.setDouble(1, score);
            statement.setLong(2, id);

            return statement.executeUpdate() == 1;
        }
    }

    public boolean delete(long id) throws SQLException {
        String sql = "DELETE FROM students WHERE id = ?";

        try (
                Connection connection = JdbcUtils.getConnection();
                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {
            statement.setLong(1, id);
            return statement.executeUpdate() == 1;
        }
    }

    private Student mapStudent(ResultSet resultSet)
            throws SQLException {
        return new Student(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getDouble("java_score"),
                resultSet.getString("created_at")
        );
    }
}
