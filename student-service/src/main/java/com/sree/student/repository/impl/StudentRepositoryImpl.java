package com.sree.student.repository.impl;

import com.sree.exception.DepartmentNotFoundException;
import com.sree.exception.StudentNotFoundException;
import com.sree.preview.DepartmentPreview;
import com.sree.preview.StudentPreview;
import com.sree.student.model.Student;
import com.sree.student.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public List<StudentPreview> findStudentsByDepartment(int departmentId) {
        List<StudentPreview> studentList = new ArrayList<>();
        for (Student student : studentMap.values()) {
            if (student.getDepartmentId() == departmentId) {
                StudentPreview studentPreview = new StudentPreview();
                studentPreview.setId(student.getId());
                studentPreview.setName(student.getName());
                studentPreview.setCourse(student.getCourse());
                studentList.add(studentPreview);
            }
        }
        if (studentList.size() == 0) {
            throw new StudentNotFoundException("No students found in this department");
        }
        return studentList;
    }

}
