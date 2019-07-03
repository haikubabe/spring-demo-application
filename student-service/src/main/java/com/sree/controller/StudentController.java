package com.sree.controller;

import com.sree.service.StudentService;
import com.sree.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping
    public Collection<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    @GetMapping("/{studentId}")
    public Student getStudentById(@PathVariable("studentId") int rollNo) {
        return studentService.getStudentById(rollNo);
    }

    @PostMapping
    public Student addStudent(@RequestBody Student student) {
        return studentService.addStudent(student);
    }

    @DeleteMapping("/{studentId}")
    public void deleteStudentById(@PathVariable("studentId") int rollNo) {
        studentService.deleteStudentById(rollNo);
    }

    @PutMapping("/{studentId}")
    public Student updateStudentById(@PathVariable("studentId") int rollNo, @RequestBody Student student) {
        return studentService.updateStudentById(rollNo, student);
    }

    @GetMapping("/departments/{departmentId}/students")
    public List<Student> findByDepartment(@PathVariable int departmentId) {
        return studentService.findByDepartment(departmentId);
    }
}
