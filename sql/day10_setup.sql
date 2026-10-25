-- 第 10 天 MySQL 练习初始化
-- 注意：会删除并重新创建 java_learning.students 表

CREATE DATABASE IF NOT EXISTS java_learning
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE java_learning;

DROP TABLE IF EXISTS students;

CREATE TABLE students (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    java_score DECIMAL(5, 2) NOT NULL,
    created_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO students (name, java_score, created_at) VALUES
('张三', 88.50, '2026-10-01 09:00:00'),
('李四', 72.00, '2026-10-02 10:15:00'),
('王五', 95.00, '2026-10-03 14:30:00'),
('赵六', 61.50, '2026-10-04 08:45:00'),
('孙七', 79.00, '2026-10-05 16:20:00'),
('周八', 100.00, '2026-10-06 11:10:00'),
('吴九', 45.00, '2026-10-07 13:40:00'),
('郑十', 83.00, '2026-10-08 15:05:00');

SELECT * FROM students;
