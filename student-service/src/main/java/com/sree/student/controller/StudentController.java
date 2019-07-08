package com.sree.student.controller;

import com.sree.dto.Student;
import com.sree.dto.StudentDto;
import com.sree.student.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/students")
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    @GetMapping("/students/{studentId}")
    public Student getStudentById(@PathVariable("studentId") int studentId) {
        return studentService.getStudentById(studentId);
    }

    @PostMapping("/departments/{departmentId}/student")
    public void addStudent(@RequestBody Student student, @PathVariable("departmentId") int departmentId) {
        studentService.addStudent(student, departmentId);
    }

    @DeleteMapping("/students/{studentId}")
    public void deleteStudentById(@PathVariable("studentId") int studentId) {
        studentService.deleteStudentById(studentId);
    }

    @PutMapping("/students/{studentId}")
    public void updateStudentById(@PathVariable("studentId") int studentId, @RequestBody StudentDto student) {
        studentService.updateStudentById(studentId, student);
    }

    /*@GetMapping("/departments")
    public List<StudentPreviewDto> findStudentsByDepartment(@RequestParam(value = "departmentId") int departmentId) {
        return studentService.findStudentsByDepartment(departmentId);
    }*/
}
