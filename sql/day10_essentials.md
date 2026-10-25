# MySQL 第 10 天必会内容

## 不需要反复手敲

- 创建数据库
- 创建表
- 批量插入初始化数据
- 用户和权限配置

这些内容已经放在 `day10_setup.sql` 中，直接执行即可。

## 必须能手写

### 1. 查询指定列

```sql
SELECT name, java_score
FROM students;
```

### 2. 条件筛选

```sql
SELECT *
FROM students
WHERE java_score >= 80;
```

### 3. 排序和限制数量

```sql
SELECT *
FROM students
ORDER BY java_score DESC
LIMIT 3;
```

### 4. 聚合统计

```sql
SELECT
    COUNT(*) AS student_count,
    AVG(java_score) AS average_score,
    MAX(java_score) AS max_score,
    MIN(java_score) AS min_score
FROM students;
```

### 5. 单行插入

```sql
INSERT INTO students (name, java_score)
VALUES ('测试学生', 80.00);
```

### 6. 修改数据

先查询确认，并拿到主键 `id`：

```sql
SELECT id, name, java_score
FROM students
WHERE name = '吴九';
```

假设查询结果的 `id` 是 7，再按主键修改：

```sql
UPDATE students
SET java_score = 60.00
WHERE id = 7;
```

### 7. 删除数据

先查询确认，并拿到主键 `id`：

```sql
SELECT id, name, java_score
FROM students
WHERE java_score < 50;
```

假设查询结果的 `id` 是 7，再按主键删除：

```sql
DELETE FROM students
WHERE id = 7;
```

## 必须记住

```text
SELECT：查询
WHERE：筛选条件
ORDER BY：排序
DESC：从高到低
ASC：从低到高
LIMIT：限制返回数量
UPDATE：修改
DELETE：删除
COUNT：统计数量
AVG：平均值
MAX：最大值
MIN：最小值
```
