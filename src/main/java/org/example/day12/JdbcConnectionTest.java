package org.example.day12;

import java.sql.Connection;

public class JdbcConnectionTest {
    public static void main(String[] args) {
        try (Connection connection = JdbcUtils.getConnection()) {
            System.out.println("数据库连接成功：" + connection.getCatalog());
        } catch (Exception e) {
            System.out.println("数据库连接失败：" + e.getMessage());
            e.printStackTrace();
        }
    }
}
