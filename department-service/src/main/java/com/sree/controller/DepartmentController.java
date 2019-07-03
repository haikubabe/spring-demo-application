package com.sree.controller;

import com.sree.service.DepartmentService;
import com.sree.model.Department;
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
}
