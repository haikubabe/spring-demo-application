package com.sree.student.controller;

import com.sree.dto.Student;
import com.sree.dto.StudentPreviewDto;
import com.sree.student.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping
    public Collection<StudentPreviewDto> getAllStudents() {
        return studentService.getAllStudents();
    }

    @GetMapping("/{studentId}")
    public StudentPreviewDto getStudentById(@PathVariable("studentId") int studentId) {
        return studentService.getStudentById(studentId);
    }

    @PostMapping
    public void addStudent(@RequestBody Student student) {
        studentService.addStudent(student);
    }

    @DeleteMapping("/{studentId}")
    public void deleteStudentById(@PathVariable("studentId") int studentId) {
        studentService.deleteStudentById(studentId);
    }

    @PutMapping("/{studentId}")
    public void updateStudentById(@PathVariable("studentId") int studentId, @RequestBody Student student) {
        studentService.updateStudentById(studentId, student);
    }

    /*@GetMapping("/departments")
    public List<StudentPreviewDto> findStudentsByDepartment(@RequestParam(value = "departmentId") int departmentId) {
        return studentService.findStudentsByDepartment(departmentId);
    }*/
}
