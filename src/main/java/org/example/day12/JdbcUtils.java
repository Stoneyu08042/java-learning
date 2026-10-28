package org.example.day12;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class JdbcUtils {
    private static final String URL = "jdbc:mysql://localhost:3306/java_learning"
            + "?useSSL=false"
            + "&allowPublicKeyRetrieval=true"
            + "&serverTimezone=Asia/Shanghai"
            + "&characterEncoding=utf8";

    private static final String USERNAME = "root";

    private JdbcUtils() {
    }

    public static Connection getConnection() throws SQLException {
        String password = System.getenv("MYSQL_PASSWORD");

        if (password == null || password.isBlank()) {
            throw new IllegalStateException("请先设置 MYSQL_PASSWORD 环境变量");
        }

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("未找到 MySQL JDBC 驱动", e);
        }

        return DriverManager.getConnection(URL, USERNAME, password);
    }
}
