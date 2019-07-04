package com.sree.student.repository;

import com.sree.student.model.Student;

import java.util.Collection;

public interface StudentRepository {

    Collection<Student> getAllStudents();

    Student getStudentById(int studentId);

    Student addStudent(Student student);

    void deleteStudentById(int studentId);

    Student updateStudentById(int studentId, Student student);

}
