package com.sree.student.service;

import com.sree.dto.Student;
import com.sree.dto.StudentDto;

import java.util.List;

public interface StudentService {

    List<Student> getAllStudents();

    Student getStudentById(int studentId);

    void addStudent(Student student);

    void deleteStudentById(int studentId);

    void updateStudentById(int studentId, StudentDto studentDto);

//    List<StudentPreviewDto> findStudentsByDepartment(int departmentId);
}
