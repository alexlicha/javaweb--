package org.example.service;

import org.example.dao.StudentDAO;
import org.example.entity.Student;

import java.util.List;

public class StudentServiceImpl implements StudentService {
    private final StudentDAO studentDAO;

    // 通过构造函数注入DAO实现
    public StudentServiceImpl(StudentDAO studentDAO) {
        this.studentDAO = studentDAO;
    }

    @Override
    public Student getById(String studentNumber) {
        return studentDAO.findById(studentNumber);
    }

    @Override
    public List<Student> getByName(String name) {
        return studentDAO.findByName(name);
    }

    @Override
    public List<Student> getStudentsByCollege(String college) {
        return studentDAO.findByCollege(college);
    }

    @Override
    public List<Student> getStudentsByMajor(String major) {
        return studentDAO.findByMajor(major);
    }

    @Override
    public void add(Student student) {
        studentDAO.add(student);
    }

    @Override
    public void delete(String studentNumber) {
        studentDAO.delete(studentNumber);
    }

    @Override
    public void update(Student student) {
        studentDAO.update(student);
    }
}
