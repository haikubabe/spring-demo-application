package com.sree.service;

import com.sree.model.Student;

import java.util.Collection;
import java.util.List;

public interface StudentService {

    Collection<Student> getAllStudents();

    Student getStudentById(int rollNo);

    Student addStudent(Student student);

    void deleteStudentById(int rollNo);

    Student updateStudentById(int rollNo, Student student);

    List<Student> findByDepartment(int departmentId);
}
