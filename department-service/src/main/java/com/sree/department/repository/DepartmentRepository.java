package com.sree.department.repository;

import com.sree.department.model.Department;
import com.sree.preview.DepartmentPreview;

import java.util.Collection;

public interface DepartmentRepository {

    Collection<DepartmentPreview> getAllDepartments();

    DepartmentPreview getDepartmentById(int id);

    DepartmentPreview addDepartment(DepartmentPreview department);

    void deleteDepartmentById(int id);

    DepartmentPreview updateDepartmentById(int id, DepartmentPreview department);

    Department findStudentsByDepartment(int id);
}
