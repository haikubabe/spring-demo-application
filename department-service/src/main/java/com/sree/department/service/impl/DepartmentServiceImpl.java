package com.sree.department.service.impl;

import com.sree.department.repository.DepartmentRepository;
import com.sree.department.service.DepartmentService;
import com.sree.department.model.Department;
;
import com.sree.preview.DepartmentPreview;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    public Collection<Department> getAllDepartments() {
        return departmentRepository.getAllDepartments();
    }

    @Override
    public Department getDepartmentById(int id) {
        return departmentRepository.getDepartmentById(id);
    }

    @Override
    public Department addDepartment(Department department) {
        return departmentRepository.addDepartment(department);
    }

    @Override
    public void deleteDepartmentById(int id) {
        departmentRepository.deleteDepartmentById(id);
    }

    @Override
    public Department updateDepartmentById(int id, Department department) {
        return departmentRepository.updateDepartmentById(id, department);
    }

    @Override
    public DepartmentPreview findStudentsByDepartment(int id) {
        return departmentRepository.findStudentsByDepartment(id);
    }
}
