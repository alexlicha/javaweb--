package org.example.service;

import org.example.entity.Student;
import java.util.List;

public interface StudentService extends GenericService<Student> {
    List<Student> getStudentsByCollege(String college);
    List<Student> getStudentsByMajor(String major);
}