// 浏览器内存中的学生数据，刷新页面后会恢复为初始值。
const students = [
    {
        id: 1,
        name: "张三",
        className: "一班",
        javaScore: 88.5,
        createdAt: "2026-10-01 09:00"
    },
    {
        id: 2,
        name: "李四",
        className: "二班",
        javaScore: 72,
        createdAt: "2026-10-02 10:15"
    },
    {
        id: 3,
        name: "王五",
        className: "一班",
        javaScore: 95,
        createdAt: "2026-10-03 14:30"
    },
    {
        id: 4,
        name: "赵六",
        className: "三班",
        javaScore: 61.5,
        createdAt: "2026-10-04 08:45"
    },
    {
        id: 5,
        name: "孙七",
        className: "二班",
        javaScore: 79,
        createdAt: "2026-10-05 16:20"
    }
];

const tableBody = document.querySelector("#student-table-body");
const studentForm = document.querySelector("#student-form");
const studentCount = document.querySelector("#student-count");
const averageScore = document.querySelector("#average-score");
const highestScore = document.querySelector("#highest-score");
const recordCount = document.querySelector("#record-count");
const formMessage = document.querySelector("#form-message");

// 根据成绩返回对应的 CSS 状态类名。
function getScoreClass(score) {
    if (score >= 90) {
        return "excellent";
    }

    if (score >= 80) {
        return "good";
    }

    if (score >= 60) {
        return "pass";
    }

    return "fail";
}

// 创建一个只包含纯文本的表格单元格。
function createTextCell(value) {
    const cell = document.createElement("td");
    cell.textContent = String(value);
    return cell;
}

// 把一个学生对象转换成表格中的一行。
function createStudentRow(student) {
    const row = document.createElement("tr");

    row.append(
        createTextCell(student.id),
        createTextCell(student.name),
        createTextCell(student.className)
    );

    const scoreCell = document.createElement("td");
    const scoreBadge = document.createElement("span");
    scoreBadge.className = `score ${getScoreClass(student.javaScore)}`;
    scoreBadge.textContent = student.javaScore.toFixed(2);
    scoreCell.append(scoreBadge);
    row.append(scoreCell);

    row.append(createTextCell(student.createdAt));

    const actionsCell = document.createElement("td");
    actionsCell.className = "actions";

    const deleteButton = document.createElement("button");
    deleteButton.type = "button";
    deleteButton.className = "button small danger delete-button";
    deleteButton.dataset.id = String(student.id);
    deleteButton.textContent = "删除";

    actionsCell.append(deleteButton);
    row.append(actionsCell);

    return row;
}

// 清空表格并根据 students 数组重新渲染全部学生。
function renderStudents() {
    tableBody.replaceChildren();

    if (students.length === 0) {
        const row = document.createElement("tr");
        const cell = document.createElement("td");
        cell.colSpan = 6;
        cell.className = "empty-state";
        cell.textContent = "暂无学生数据";
        row.append(cell);
        tableBody.append(row);
        updateSummary();
        return;
    }

    for (const student of students) {
        tableBody.append(createStudentRow(student));
    }

    updateSummary();
}

// 根据 students 数组更新人数、平均分和最高分。
function updateSummary() {
    const count = students.length;
    const total = students.reduce(
        (sum, student) => sum + student.javaScore,
        0
    );
    const average = count === 0 ? 0 : total / count;
    const highest = count === 0
        ? 0
        : Math.max(...students.map((student) => student.javaScore));

    studentCount.textContent = String(count);
    averageScore.textContent = average.toFixed(1);
    highestScore.textContent = highest.toFixed(1);
    recordCount.textContent = `共 ${count} 条`;
}

// 在新增表单下方显示错误或成功提示。
function setFormMessage(message, type = "error") {
    formMessage.textContent = message;
    formMessage.className = type === "success"
        ? "form-message success"
        : "form-message";
}

// 返回当前学生数据中可用的下一个 id。
function getNextId() {
    if (students.length === 0) {
        return 1;
    }

    return Math.max(...students.map((student) => student.id)) + 1;
}

// 处理新增学生表单的提交事件。
function handleFormSubmit(event) {
    event.preventDefault();

    const formData = new FormData(studentForm);
    const name = String(formData.get("name")).trim();
    const className = String(formData.get("className"));
    const score = Number(formData.get("javaScore"));

    // TODO 1:
    // 校验 name 是否为空、className 是否为空、score 是否为 0-100。
    // 如果校验失败，使用 setFormMessage("错误提示") 并 return。
    if(!name){
        setFormMessage("姓名不能为空");
        return;
    }
    if(!className){
        setFormMessage("班级不能为空");
        return;
    }
    if (Number.isNaN(score) || score < 0 || score > 100) {
        setFormMessage("Java成绩必须在0-100之间");
        return;
    }

    // TODO 2:
    // 创建 newStudent 对象并 push 到 students。
    // 调用 renderStudents()。
    // 调用 studentForm.reset()。
    // 使用 setFormMessage("新增成功", "success")。
    const newStudent = {
        id: getNextId(),
        name,
        className,
        javaScore: score,
        createdAt: new Date().toLocaleString("zh-CN")
    };
    students.push(newStudent);
    renderStudents();
    studentForm.reset();
    setFormMessage("新增成功", "success");  
}

// 处理表格中的删除按钮点击事件。
function handleTableClick(event) {
    const deleteButton = event.target.closest(".delete-button");

    if (!deleteButton) {
        return;
    }

    const id = Number(deleteButton.dataset.id);

    // TODO 3:
    // 使用 findIndex 找到 students 中 id 相同的元素。
    // 使用 splice 删除该元素。
    // 调用 renderStudents()。
    // 使用 setFormMessage("删除成功", "success")。
    const index = students.findIndex(student => student.id === id);
    if(index !== -1){
        students.splice(index, 1);
        renderStudents();
        setFormMessage("删除成功", "success");
    }
}

studentForm.addEventListener("submit", handleFormSubmit);
tableBody.addEventListener("click", handleTableClick);

renderStudents();

// defer 的作用是：
// 浏览器先并行下载 app.js，等 HTML 解析完成后再执行脚本。
// 事件委托是：
// 把点击事件绑定在父元素 tbody 上，通过事件冒泡统一处理所有删除按钮。
// preventDefault 阻止表单刷新页面
// 刷新后数据消失，是因为数组只在内存中