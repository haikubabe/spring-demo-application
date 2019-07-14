package com.sree.department.service;

import com.sree.dto.Department;
import com.sree.dto.DepartmentPreviewDto;

import java.util.Collection;

public interface DepartmentService {

    Collection<DepartmentPreviewDto> getAllDepartments();

    DepartmentPreviewDto getDepartmentById(int id);

    void addDepartment(Department department);

    void deleteDepartmentById(int id);

    void updateDepartmentById(int id, Department department);

//    DepartmentPreviewDto findStudentsByDepartment(int id);
}
