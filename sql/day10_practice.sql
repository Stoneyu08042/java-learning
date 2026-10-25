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
-- 第 10 天 SQL 练习
-- 先执行 day10_setup.sql，再在这里写答案。

USE java_learning;

-- 1. 查询所有学生的姓名和 Java 成绩
select name as 姓名,java_score as Java成绩 from students;

-- 2. 查询 Java 成绩大于等于 80 的学生，按成绩从高到低排序
select * from students where java_score>=80 order by java_score desc;

-- 3. 查询成绩最高的前 3 名学生
select * from students  order by java_score desc limit 3;

-- 4. 统计学生总数
select count(*) as 学生总数 from students;

-- 5. 统计平均分、最高分和最低分
select avg(java_score) as 平均分,max(java_score) as 最高分,min(java_score) as 最低分 from students;

-- 6. 查询张三的完整信息
select * from students where name="张三";

-- 7. 把吴九的 Java 成绩修改为 60 分
-- 先写 SELECT 确认数据，再写 UPDATE
select * from students where name="吴九";
update  students set java_score=60  where id=7;

-- 8. 插入一名 40 分的低分测试学生，查询其 id 后按 id 删除
insert into students(name,java_score) values('低分测试',40);
select id,name,java_score from students where name='低分测试';
delete from students where id=9;

-- 9. 插入一名成绩为 85 分的测试学生
insert into students(name,java_score) values('石头',85);

-- 10. 按姓名查询刚才插入的测试学生
select * from students where name='石头';
