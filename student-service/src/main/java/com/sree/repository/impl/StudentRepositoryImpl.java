package com.sree.repository.impl;

import com.sree.exception.StudentNotFoundException;
import com.sree.model.Student;
import com.sree.repository.StudentRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class StudentRepositoryImpl implements StudentRepository {

    private static Map<Integer, Student> studentMap = new HashMap<>();

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
        s.setDepartmentId(student.getDepartmentId());
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
        s.setDepartmentId(student.getDepartmentId());
        return s;
    }

    @Override
    public List<Student> findByDepartment(int departmentId) {
        List<Student> studentList = new ArrayList<>();
        for (Student s : studentMap.values()) {
            if (s.getDepartmentId() == departmentId) {
                studentList.add(s);
            }
        }
        return studentList;
    }
}
