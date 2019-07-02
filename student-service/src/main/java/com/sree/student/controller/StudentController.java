package com.sree.student.controller;

import com.sree.student.model.Student;
import com.sree.student.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

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

}
