package com.sree.student.service;

import com.sree.student.model.Student;

import java.util.Collection;

public interface StudentService {

    Collection<Student> getAllStudents();

    Student getStudentById(int studentId);

    Student addStudent(Student student);

    void deleteStudentById(int studentId);

    Student updateStudentById(int studentId, Student student);

}
