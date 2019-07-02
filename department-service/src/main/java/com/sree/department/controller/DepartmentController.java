package com.sree.department.controller;

import com.sree.department.model.Department;
import com.sree.department.service.DepartmentService;
import com.sree.student.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/departments")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @GetMapping
    public Collection<Department> getAllDepartments() {
        return departmentService.getAllDepartments();
    }

    @GetMapping("/{departmentId}")
    public Department getDepartmentById(@PathVariable("departmentId") int id) {
        return departmentService.getDepartmentById(id);
    }

    @PostMapping
    public Department addDepartment(@RequestBody Department department) {
        return departmentService.addDepartment(department);
    }

    @DeleteMapping("/{departmentId}")
    public void deleteDepartmentById(@PathVariable("departmentId") int id) {
        departmentService.deleteDepartmentById(id);
    }

    @PutMapping("/{departmentId}")
    public Department updateDepartmentById(@PathVariable("departmentId") int id, @RequestBody Department department) {
        return departmentService.updateDepartmentById(id, department);
    }

    @GetMapping("/{departmentId}/students")
    public Collection<Student> getAllStudentsInDepartment(@PathVariable("departmentId") int id) {
        return departmentService.getAllStudentsInDepartment(id);
    }

    @GetMapping("/{departmentId}/students/{studentId}")
    public Student getStudentInDepartment(@PathVariable("departmentId") int id, @PathVariable("studentId") int rollNo) {
        return departmentService.getStudentInDepartment(id, rollNo);
    }

}
