package org.example.dao;

import org.example.entity.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StudentDAOImpl implements StudentDAO {
    private static final String URL = "jdbc:mysql://localhost:3306/web?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "123456";

    private Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL Driver not found", e);
        }
    }

    @Override
    public Student findById(String studentNumber) {
        String sql = "SELECT * FROM students WHERE student_number = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, studentNumber);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapToStudent(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Student> findAll() {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM students";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                students.add(mapToStudent(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    @Override
    public List<Student> findByName(String name) {
        String sql = "SELECT * FROM students WHERE name LIKE ?";
        List<Student> students = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + name + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                students.add(mapToStudent(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    @Override
    public List<Student> findByCollege(String college) {
        String sql = "SELECT * FROM students WHERE college = ?";
        List<Student> students = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, college);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                students.add(mapToStudent(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    @Override
    public List<Student> findByMajor(String major) {
        String sql = "SELECT * FROM students WHERE major = ?";
        List<Student> students = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, major);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                students.add(mapToStudent(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    @Override
    public void add(Student student) {
        String sql = "INSERT INTO students (student_number, name, gender, hometown, id_card, college, major, class_name) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, student.getStudentNumber());
            ps.setString(2, student.getName());
            ps.setString(3, student.getGender());
            ps.setString(4, student.getHometown());
            ps.setString(5, student.getIdCard());
            ps.setString(6, student.getCollege());
            ps.setString(7, student.getMajor());
            ps.setString(8, student.getClassName());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(String studentNumber) {
        String sql = "DELETE FROM students WHERE student_number = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, studentNumber);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Student student) {
        String sql = "UPDATE students SET name = ?, gender = ?, hometown = ?, id_card = ?, college = ?, major = ?, class_name = ? WHERE student_number = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, student.getName());
            ps.setString(2, student.getGender());
            ps.setString(3, student.getHometown());
            ps.setString(4, student.getIdCard());
            ps.setString(5, student.getCollege());
            ps.setString(6, student.getMajor());
            ps.setString(7, student.getClassName());
            ps.setString(8, student.getStudentNumber());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Student mapToStudent(ResultSet rs) throws SQLException {
        Student student = new Student();
        student.setStudentNumber(rs.getString("student_number"));
        student.setName(rs.getString("name"));
        student.setGender(rs.getString("gender"));
        student.setHometown(rs.getString("hometown"));
        student.setIdCard(rs.getString("id_card"));
        student.setCollege(rs.getString("college"));
        student.setMajor(rs.getString("major"));
        student.setClassName(rs.getString("class_name"));
        return student;
    }
}
