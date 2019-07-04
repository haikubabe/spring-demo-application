package com.sree.student.controller;

import com.sree.preview.StudentPreview;
import com.sree.student.service.StudentService;
import com.sree.student.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/students")
    public Collection<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    @GetMapping("/students/{studentId}")
    public Student getStudentById(@PathVariable("studentId") int studentId) {
        return studentService.getStudentById(studentId);
    }

    @PostMapping("/students")
    public Student addStudent(@RequestBody Student student) {
        return studentService.addStudent(student);
    }

    @DeleteMapping("/students/{studentId}")
    public void deleteStudentById(@PathVariable("studentId") int studentId) {
        studentService.deleteStudentById(studentId);
    }

    @PutMapping("/students/{studentId}")
    public Student updateStudentById(@PathVariable("studentId") int studentId, @RequestBody Student student) {
        return studentService.updateStudentById(studentId, student);
    }

    @GetMapping("/departments/{departmentId}/students")
    public List<StudentPreview> findByDepartment(@PathVariable int departmentId) {
        return studentService.findByDepartment(departmentId);
    }

}
