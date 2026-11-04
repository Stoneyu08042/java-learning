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
const searchNameInput = document.querySelector("#search-name");
const classFilter = document.querySelector("#filter-class");
const sortScore = document.querySelector("#sort-score");
const formTitle = document.querySelector("#form-title");
const formDescription = document.querySelector("#form-description");
const submitButton = document.querySelector("#submit-button");
const cancelEditButton = document.querySelector("#cancel-edit-button");

let editingId = null;

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

    const editButton = document.createElement("button");
    editButton.type = "button";
    editButton.className = "button small edit-button";
    editButton.dataset.id = String(student.id);
    editButton.textContent = "编辑";

    const deleteButton = document.createElement("button");
    deleteButton.type = "button";
    deleteButton.className = "button small danger delete-button";
    deleteButton.dataset.id = String(student.id);
    deleteButton.textContent = "删除";

    actionsCell.append(editButton, deleteButton);
    row.append(actionsCell);

    return row;
}

// 返回当前搜索、班级筛选和排序后的学生列表。
function getVisibleStudents() {
    const keyword = searchNameInput.value.trim().toLowerCase();
    const selectedClass = classFilter.value;
    const sortOrder = sortScore.value;

    const filteredStudents = students.filter((student) => {
        const matchesName = student.name
            .toLowerCase()
            .includes(keyword);
        const matchesClass =
            selectedClass === "" ||
            student.className === selectedClass;

        return matchesName && matchesClass;
    });

    if (sortOrder === "") {
        return filteredStudents;
    }

    const sortedStudents = [...filteredStudents];

    // TODO 1:
    // 根据 sortOrder 对 sortedStudents 排序。
    // score-desc：b.javaScore - a.javaScore
    // score-asc：a.javaScore - b.javaScore
    if (sortOrder === "score-desc") {
        sortedStudents.sort((a, b) => b.javaScore - a.javaScore);
    } else if (sortOrder === "score-asc") {
        sortedStudents.sort((a, b) => a.javaScore - b.javaScore);
    }   

    return sortedStudents;
}

// 清空表格并根据当前可见学生列表重新渲染。
function renderStudents() {
    tableBody.replaceChildren();
    const visibleStudents = getVisibleStudents();

    if (visibleStudents.length === 0) {
        const row = document.createElement("tr");
        const cell = document.createElement("td");
        cell.colSpan = 6;
        cell.className = "empty-state";
        cell.textContent = "没有符合条件的学生";
        row.append(cell);
        tableBody.append(row);
        updateSummary();
        recordCount.textContent = `显示 0 / ${students.length} 条`;
        return;
    }

    for (const student of visibleStudents) {
        tableBody.append(createStudentRow(student));
    }

    updateSummary();
    recordCount.textContent =
        `显示 ${visibleStudents.length} / ${students.length} 条`;
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

// 把学生数据填入表单并进入编辑状态。
function startEditing(student) {
    editingId = student.id;
    studentForm.elements.name.value = student.name;
    studentForm.elements.className.value = student.className;
    studentForm.elements.javaScore.value = student.javaScore;

    formTitle.textContent = "编辑学生";
    formDescription.textContent = `正在修改 ${student.name} 的信息。`;
    submitButton.textContent = "保存修改";
    cancelEditButton.hidden = false;

    setFormMessage("已进入编辑状态", "success");
    studentForm.scrollIntoView({
        behavior: "smooth",
        block: "center"
    });
}

// 退出编辑状态并清空表单。
function exitEditMode() {
    editingId = null;
    studentForm.reset();
    formTitle.textContent = "新增学生";
    formDescription.textContent = "填写信息后将学生加入列表。";
    submitButton.textContent = "新增学生";
    cancelEditButton.hidden = true;
    setFormMessage("");
}

// 处理新增或编辑学生表单的提交事件。
function handleFormSubmit(event) {
    event.preventDefault();

    const formData = new FormData(studentForm);
    const name = String(formData.get("name")).trim();
    const className = String(formData.get("className"));
    const score = Number(formData.get("javaScore"));

    if (!name) {
        setFormMessage("姓名不能为空");
        return;
    }

    if (!className) {
        setFormMessage("班级不能为空");
        return;
    }

    if (Number.isNaN(score) || score < 0 || score > 100) {
        setFormMessage("Java成绩必须在0-100之间");
        return;
    }

    if (editingId === null) {
        const newStudent = {
            id: getNextId(),
            name,
            className,
            javaScore: score,
            createdAt: new Date().toLocaleString("zh-CN")
        };

        students.push(newStudent);
        renderStudents();
        exitEditMode();
        setFormMessage("新增成功", "success");
        return;
    }

    const index = students.findIndex(
        (student) => student.id === editingId
    );

    if (index === -1) {
        setFormMessage("没有找到要修改的学生");
        exitEditMode();
        return;
    }

    // TODO 2:
    // 把 students[index] 的 name、className、javaScore
    // 更新为表单中的新值。
    students[index].name = name;
    students[index].className = className;
    students[index].javaScore = score;

    renderStudents();
    exitEditMode();
    setFormMessage("修改成功", "success");
}

// 处理表格中的编辑和删除按钮点击事件。
function handleTableClick(event) {
    const editButton = event.target.closest(".edit-button");

    if (editButton) {
        const id = Number(editButton.dataset.id);

        // TODO 3:
        // 使用 students.find(...) 找到 id 相同的学生。
        // 找到后调用 startEditing(student)。
        const student = students.find((student) => student.id === id);

        if (!student) {
            setFormMessage("没有找到要编辑的学生");
            return;
        }

        startEditing(student);

        return;
    }

    const deleteButton = event.target.closest(".delete-button");

    if (!deleteButton) {
        return;
    }

    const id = Number(deleteButton.dataset.id);

    const index = students.findIndex((student) => student.id === id);

    if (index !== -1) {
        students.splice(index, 1);

        if (editingId === id) {
            exitEditMode();
        }

        renderStudents();
        setFormMessage("删除成功", "success");
    }
}

studentForm.addEventListener("submit", handleFormSubmit);
tableBody.addEventListener("click", handleTableClick);
searchNameInput.addEventListener("input", renderStudents);
classFilter.addEventListener("change", renderStudents);
sortScore.addEventListener("change", renderStudents);
cancelEditButton.addEventListener("click", exitEditMode);

renderStudents();

// filter：找所有，返回数组
// find：找第一个，返回对象
// findIndex：找第一个，返回下标
//sort()会改变原有数组的排序，所以用之前会复制一个新数组

// 原始数据和筛选后的显示数据要分开
// students：原始数据，新增、编辑、删除都修改它
// visibleStudents：筛选、排序后的显示数据
// students 保持不变
// visibleStudents 根据条件临时生成
// 表格渲染 visibleStudents
