function fetchStudentData(event, actionType) {
    event.preventDefault(); // 阻止表单的默认提交行为
    let queryValue;
    if (actionType === 'studentNumber') {
        queryValue = document.getElementById("studentNumber").value;
    } else if (actionType === 'name') {
        queryValue = document.getElementById("name").value;
    } else if (actionType === 'college') {
        queryValue = document.getElementById("college").value;
    } else if (actionType === 'major') {
        queryValue = document.getElementById("major").value;
    }

    fetch(`student?action=findBy${capitalizeFirstLetter(actionType)}&${actionType}=${queryValue}`)
        .then(response => response.json())
        .then(data => {
            displayResults(data);
        })
        .catch(error => console.error("获取学生数据失败:", error));
}

function displayResults(data) {
    const resultSection = document.getElementById("resultSection");
    const resultTableBody = document.getElementById("resultTableBody");
    resultTableBody.innerHTML = ""; // 清空现有结果

    if (data.length > 0) {
        data.forEach(student => {
            const row = document.createElement("tr");
            row.innerHTML = `
                <td>${student.studentNumber}</td>
                <td>${student.name}</td>
                <td>${student.gender}</td>
                <td>${student.hometown}</td>
                <td>${student.idCard}</td>
                <td>${student.college}</td>
                <td>${student.major}</td>
                <td>${student.className}</td>
            `;
            resultTableBody.appendChild(row);
        });
        resultSection.style.display = "block"; // 显示结果部分
    } else {
        alert("未找到相关学生");
        resultSection.style.display = "none"; // 隐藏结果部分
    }
}

function fetchStudentDataForUpdate() {
    const studentNumber = document.getElementById("updateStudentNumber").value;

    fetch(`student?action=findByStudentNumber&studentNumber=${studentNumber}`)
        .then(response => response.json())
        .then(data => {
            if (data.length > 0) {
                const student = data[0];
                document.getElementById("studentName").innerText = student.name;
                document.getElementById("studentGender").innerText = student.gender;
                document.getElementById("studentHometown").innerText = student.hometown;
                document.getElementById("studentIdCard").innerText = student.idCard;
                document.getElementById("studentCollege").innerText = student.college;
                document.getElementById("studentMajor").innerText = student.major;
                document.getElementById("studentClassName").innerText = student.className;

                // 显示学生信息
                document.getElementById("studentInfo").style.display = "block";
                document.getElementById("updateFields").style.display = "block"; // 显示更新字段选择
            } else {
                alert("未找到学生");
                clearUpdateFields();
            }
        })
        .catch(error => console.error("获取学生数据失败:", error));
}

function updateStudent() {
    const studentNumber = document.getElementById("updateStudentNumber").value;
    const field = document.getElementById("updateField").value;
    const newValue = document.getElementById("newValue").value;

    if (!field || !newValue) {
        alert("请先选择要更新的字段和新值");
        return;
    }

    fetch(`student?action=updateStudent&studentNumber=${studentNumber}&field=${field}&newValue=${newValue}`, {
        method: 'POST'
    })
        .then(response => response.json())
        .then(result => {
            alert(result.message || "更新成功");
            clearUpdateFields(); // 清空输入
        })
        .catch(error => console.error("更新学生失败:", error));
}

function displayFieldInput() {
    const fieldSelect = document.getElementById("updateField");
    const newValueInput = document.getElementById("newValueInput");

    if (fieldSelect.value) {
        newValueInput.style.display = "block";
    } else {
        newValueInput.style.display = "none";
    }
}

function showSection(sectionId) {
    const sections = document.querySelectorAll('.section');
    sections.forEach(section => {
        section.style.display = 'none';
    });
    document.getElementById(sectionId).style.display = 'block';
}

function clearUpdateFields() {
    document.getElementById("studentName").innerText = "";
    document.getElementById("studentGender").innerText = "";
    document.getElementById("studentHometown").innerText = "";
    document.getElementById("studentIdCard").innerText = "";
    document.getElementById("studentCollege").innerText = "";
    document.getElementById("studentMajor").innerText = "";
    document.getElementById("studentClassName").innerText = "";
    document.getElementById("newValue").value = "";
    document.getElementById("updateField").value = "";
    document.getElementById("studentInfo").style.display = "none";
    document.getElementById("updateFields").style.display = "none";
    document.getElementById("updateStudentNumber").value = '';
    document.getElementById("updateField").selectedIndex = 0;
    document.getElementById("newValue").value = '';
}

// 帮助函数：首字母大写
function capitalizeFirstLetter(string) {
    return string.charAt(0).toUpperCase() + string.slice(1);
}
