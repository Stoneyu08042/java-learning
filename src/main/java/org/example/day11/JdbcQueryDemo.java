package org.example.day11;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcQueryDemo {
    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("未找到 MySQL JDBC 驱动，请刷新 Maven 依赖");
            e.printStackTrace();
            return;
        }

        String url = "jdbc:mysql://localhost:3306/java_learning"
                + "?useSSL=false"
                + "&allowPublicKeyRetrieval=true"
                + "&serverTimezone=Asia/Shanghai"
                + "&characterEncoding=utf8";

        String username = "root";
        String password = System.getenv("MYSQL_PASSWORD");

        if (password == null) {
            System.out.println("请先设置 MYSQL_PASSWORD 环境变量");
            return;
        }

        String sql = """
        SELECT id, name, java_score, created_at
        FROM students
        WHERE java_score >= ?  
        /*  ? 是参数占位符  */
        ORDER BY java_score DESC
        """;
//        try-with-resources 的作用:
//        括号中的资源会在代码执行完毕或发生异常后自动关闭。
//        但关闭顺序通常是与声明顺序相反
        try (
                Connection connection =
                        DriverManager.getConnection(url, username, password);
                PreparedStatement statement =
                        connection.prepareStatement(sql)
        ) {
            statement.setDouble(1, 80);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    long id = resultSet.getLong("id");
                    String name = resultSet.getString("name");
                    double score = resultSet.getDouble("java_score");

                    System.out.printf(
                            "id=%d, 姓名=%s, 成绩=%.2f%n",
                            id, name, score
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("数据库操作失败：" + e.getMessage());
            e.printStackTrace();
        }
    }
}
//JDBC：规定 Java 应该怎样连接数据库
//Connector/J：具体负责用 MySQL 协议完成连接和通信
//Connection：Java 程序和数据库之间的一次连接。
//PreparedStatement：准备并执行 SQL，可以携带 ? 参数。
//ResultSet：保存查询返回的结果，可以逐行读取。