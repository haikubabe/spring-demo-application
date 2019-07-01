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
    public Student getStudentById(int rollNo) {
        return studentRepository.getStudentById(rollNo);
    }

    @Override
    public Student addStudent(Student student) {
        return studentRepository.addStudent(student);
    }

    @Override
    public void deleteStudentById(int rollNo) {
        studentRepository.deleteStudentById(rollNo);
    }

    @Override
    public Student updateStudentById(int rollNo, Student student) {
        return studentRepository.updateStudentById(rollNo, student);
    }

}
