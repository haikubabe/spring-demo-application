package com.sree.student.repository.impl;

import com.sree.student.exception.StudentNotFoundException;
import com.sree.student.model.Student;
import com.sree.student.repository.StudentRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Repository
public class StudentRepositoryImpl implements StudentRepository {

    private static Map<Integer,Student> studentMap = new HashMap<>();

    static {
        studentMap.put(1, new Student(1, "Sreemoyee", "Computer Science"));
        studentMap.put(2, new Student(2, "Sushmita", "Computer Science"));
        studentMap.put(3, new Student(3, "Sudip", "Biology"));
        studentMap.put(4, new Student(4, "Kumar", "Maths"));
    }


    @Override
    public Collection<Student> getAllStudents() {
        return studentMap.values();
    }

    @Override
    public Student getStudentById(int rollNo) {
        if (studentMap.get(rollNo) == null) {
            throw new StudentNotFoundException("studentId - " + rollNo + " is not found");
        }
        return studentMap.get(rollNo);
    }

    @Override
    public Student addStudent(Student student) {
        Student s = new Student();
        s.setRollNo(student.getRollNo());
        s.setName(student.getName());
        s.setCourse(student.getCourse());
        studentMap.put(s.getRollNo(), s);
        return s;
    }

    @Override
    public void deleteStudentById(int rollNo) {
        if (studentMap.get(rollNo) == null) {
            throw new StudentNotFoundException("studentId - " + rollNo + " is not found, so it cannot be deleted");
        }
        studentMap.remove(rollNo);
    }

    @Override
    public Student updateStudentById(int rollNo, Student student) {
        if (studentMap.get(rollNo) == null) {
            throw new StudentNotFoundException("studentId - " + rollNo + " is not found, so it cannot be updated");
        }
        Student s = studentMap.get(rollNo);
        s.setRollNo(student.getRollNo());
        s.setName(student.getName());
        s.setCourse(student.getCourse());
        return s;
    }
}
