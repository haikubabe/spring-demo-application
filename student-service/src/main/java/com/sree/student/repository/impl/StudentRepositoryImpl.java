package com.sree.student.repository.impl;

import com.sree.exception.DepartmentNotFoundException;
import com.sree.preview.StudentPreview;
import com.sree.exception.StudentNotFoundException;
import com.sree.student.repository.StudentRepository;
import com.sree.student.model.Student;
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
    public Student getStudentById(int studentId) {
        if (studentMap.get(studentId) == null) {
            throw new StudentNotFoundException("studentId - " + studentId + " is not found");
        }
        return studentMap.get(studentId);
    }

    @Override
    public Student addStudent(Student student) {
        Student s = new Student();
        s.setId(student.getId());
        s.setName(student.getName());
        s.setCourse(student.getCourse());
        s.setDepartmentId(student.getDepartmentId());
        studentMap.put(s.getId(), s);
        return s;
    }

    @Override
    public void deleteStudentById(int studentId) {
        if (studentMap.get(studentId) == null) {
            throw new StudentNotFoundException("studentId - " + studentId + " is not found, so it cannot be deleted");
        }
        studentMap.remove(studentId);
    }

    @Override
    public Student updateStudentById(int studentId, Student student) {
        if (studentMap.get(studentId) == null) {
            throw new StudentNotFoundException("studentId - " + studentId + " is not found, so it cannot be updated");
        }
        Student s = studentMap.get(studentId);
        s.setId(student.getId());
        s.setName(student.getName());
        s.setCourse(student.getCourse());
        s.setDepartmentId(student.getDepartmentId());
        return s;
    }

    @Override
    public List<StudentPreview> findByDepartment(int departmentId) {
        List<StudentPreview> studentList = new ArrayList<>();
        for (Student s : studentMap.values()) {
            if (s.getDepartmentId() == departmentId) {
                studentList.add(new StudentPreview(s.getId(), s.getName(), s.getCourse()));
            } else {
                throw new DepartmentNotFoundException("departmentId - " + departmentId + " is not found");
            }
        }
        return studentList;
    }
}
