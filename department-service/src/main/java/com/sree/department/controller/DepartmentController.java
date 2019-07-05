package com.sree.department.controller;

import com.sree.department.service.DepartmentService;
import com.sree.department.model.Department;
import com.sree.preview.DepartmentPreview;
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
    public DepartmentPreview findStudentsByDepartment(@PathVariable int departmentId) {
        return departmentService.findStudentsByDepartment(departmentId);
    }
}
