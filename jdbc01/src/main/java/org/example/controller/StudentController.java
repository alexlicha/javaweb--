package org.example.controller;

import com.google.gson.Gson;
import org.example.dao.StudentDAOImpl;
import org.example.entity.Student;
import org.example.service.StudentService;
import org.example.service.StudentServiceImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/student")
public class StudentController extends HttpServlet {
    private StudentService studentService;

    @Override
    public void init() throws ServletException {
        studentService = new StudentServiceImpl(new StudentDAOImpl());
    }

    // 处理GET请求
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json; charset=UTF-8");
        PrintWriter out = response.getWriter();
        String action = request.getParameter("action");
        List<Student> students = new ArrayList<>();

        // 处理不同的查询逻辑
        if ("findByStudentNumber".equals(action)) {
            String studentNumber = request.getParameter("studentNumber");
            Student student = studentService.getById(studentNumber);
            if (student != null) {
                students.add(student);
            }
        } else if ("findByName".equals(action)) {
            String name = request.getParameter("name");
            students = studentService.getByName(name);
        } else if ("findByCollege".equals(action)) {
            String college = request.getParameter("college");
            students = studentService.getStudentsByCollege(college);
        } else if ("findByMajor".equals(action)) {
            String major = request.getParameter("major");
            students = studentService.getStudentsByMajor(major);
        }

        // 将查询结果转换为JSON返回前端
        String jsonResponse = new Gson().toJson(students);
        out.print(jsonResponse);
        out.flush();
    }

    // 处理POST请求
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json; charset=UTF-8"); // 设置响应类型为 JSON
        PrintWriter out = response.getWriter();
        String action = request.getParameter("action");
        Gson gson = new Gson(); // 初始化 Gson

        // 用于存储返回的结果
        Map<String, String> result = new HashMap<>();

        // 添加学生
        if ("addStudent".equals(action)) {
            Student student = createStudentFromRequest(request);
            studentService.add(student);
            result.put("message", "学生添加成功");
        }
        // 删除学生
        else if ("deleteStudent".equals(action)) {
            String studentNumber = request.getParameter("studentNumber");
            studentService.delete(studentNumber);
            result.put("message", "学生删除成功");
        }
        // 更新学生
        else if ("updateStudent".equals(action)) {
            String studentNumber = request.getParameter("studentNumber");
            String field = request.getParameter("field");
            String newValue = request.getParameter("newValue");

            if (studentNumber != null && field != null && newValue != null) {
                Student student = studentService.getById(studentNumber);
                if (student != null) {
                    switch (field) {
                        case "name":
                            student.setName(newValue);
                            break;
                        case "gender":
                            student.setGender(newValue);
                            break;
                        case "hometown":
                            student.setHometown(newValue);
                            break;
                        case "idCard":
                            student.setIdCard(newValue);
                            break;
                        case "college":
                            student.setCollege(newValue);
                            break;
                        case "major":
                            student.setMajor(newValue);
                            break;
                        case "className":
                            student.setClassName(newValue);
                            break;
                        default:
                            result.put("message", "无效的字段");
                            out.print(gson.toJson(result));
                            return;
                    }
                    studentService.update(student);
                    result.put("message", "学生信息更新成功");
                } else {
                    result.put("message", "未找到学生");
                }
            } else {
                result.put("message", "学号或字段或新值不能为空");
            }
        }

        // 将结果转换为 JSON 返回给前端
        out.print(gson.toJson(result));
        out.flush();
    }


    // 从请求中构建学生对象
    private Student createStudentFromRequest(HttpServletRequest request) {
        Student student = new Student();
        student.setStudentNumber(request.getParameter("studentNumber"));
        student.setName(request.getParameter("name"));
        student.setGender(request.getParameter("gender"));
        student.setHometown(request.getParameter("hometown"));
        student.setIdCard(request.getParameter("idCard"));
        student.setCollege(request.getParameter("college"));
        student.setMajor(request.getParameter("major"));
        student.setClassName(request.getParameter("className"));
        return student;
    }
}
