package com.sree.student.service.impl;

import com.sree.student.model.Student;
import com.sree.student.repository.StudentRepository;
import com.sree.student.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Override
    public Collection<Student> getAllStudents() {
        return studentRepository.getAllStudents();
    }

    @Override
    public Student getStudentById(int studentId) {
        return studentRepository.getStudentById(studentId);
    }

    @Override
    public Student addStudent(Student student) {
        return studentRepository.addStudent(student);
    }

    @Override
    public void deleteStudentById(int studentId) {
        studentRepository.deleteStudentById(studentId);
    }

    @Override
    public Student updateStudentById(int studentId, Student student) {
        return studentRepository.updateStudentById(studentId, student);
    }
}
