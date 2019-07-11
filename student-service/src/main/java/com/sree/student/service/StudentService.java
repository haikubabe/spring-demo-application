package com.sree.student.service;

import com.sree.dto.Student;
import com.sree.dto.StudentPreviewDto;

import java.util.Collection;

public interface StudentService {

    Collection<StudentPreviewDto> getAllStudents();

    StudentPreviewDto getStudentById(int studentId);

    void addStudent(Student student);

    void deleteStudentById(int studentId);

    void updateStudentById(int studentId, Student student);

//    List<StudentPreviewDto> findStudentsByDepartment(int departmentId);
}
