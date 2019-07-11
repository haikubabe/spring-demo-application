package com.sree.department.service;

import com.sree.dto.Department;

import java.util.List;

public interface DepartmentService {

    List<Department> getAllDepartments();

    Department getDepartmentById(int id);

    void addDepartment(Department department);

//    void deleteDepartmentById(int id);

    void updateDepartmentById(int id, Department department);

//    DepartmentPreviewDto findStudentsByDepartment(int id);
}
