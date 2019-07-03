package com.sree.service;

import com.sree.model.Department;

import java.util.Collection;

public interface DepartmentService {

    Collection<Department> getAllDepartments();

    Department getDepartmentById(int id);

    Department addDepartment(Department department);

    void deleteDepartmentById(int id);

    Department updateDepartmentById(int id, Department department);
}
