package com.sree.student.repository;

import com.sree.student.model.Student;

import java.util.Collection;

public interface StudentRepository {

    Collection<Student> getAllStudents();

    Student getStudentById(int rollNo);

    Student addStudent(Student student);

    void deleteStudentById(int rollNo);

    Student updateStudentById(int rollNo, Student student);
}
