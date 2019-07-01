package com.sree.student.service;

import com.sree.student.model.Student;

import java.util.Collection;

public interface StudentService {

    Collection<Student> getAllStudents();

    Student getStudentById(int rollNo);

    Student addStudent(Student student);

    void deleteStudentById(int rollNo);

    Student updateStudentById(int rollNo, Student student);
}
