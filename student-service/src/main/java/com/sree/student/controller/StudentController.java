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

    @RequestMapping(method = RequestMethod.GET)
    public Collection<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    @RequestMapping(value = "/{studentId}", method = RequestMethod.GET)
    public Student getStudentById(@PathVariable("studentId") int rollNo) {
        return studentService.getStudentById(rollNo);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Student addStudent(@RequestBody Student student) {
        return studentService.addStudent(student);
    }

    @RequestMapping(value = "/{studentId}", method = RequestMethod.DELETE)
    public void deleteStudentById(@PathVariable("studentId") int rollNo) {
        studentService.deleteStudentById(rollNo);
    }

    @RequestMapping(value = "/{studentId}", method = RequestMethod.PUT)
    public Student updateStudentById(@PathVariable("studentId") int rollNo, @RequestBody Student student) {
        return studentService.updateStudentById(rollNo, student);
    }

}
