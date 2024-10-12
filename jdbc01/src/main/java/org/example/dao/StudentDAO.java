package org.example.dao;

import org.example.entity.Student;

import java.util.List;

public interface StudentDAO extends GenericDAO<Student, String> {
    List<Student> findByName(String name);
    List<Student> findByCollege(String college);
    List<Student> findByMajor(String major);
}
