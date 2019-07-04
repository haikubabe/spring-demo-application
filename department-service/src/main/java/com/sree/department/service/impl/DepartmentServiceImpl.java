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
    public Collection<DepartmentPreview> getAllDepartments() {
        return departmentRepository.getAllDepartments();
    }

    @Override
    public DepartmentPreview getDepartmentById(int id) {
        return departmentRepository.getDepartmentById(id);
    }

    @Override
    public DepartmentPreview addDepartment(DepartmentPreview department) {
        return departmentRepository.addDepartment(department);
    }

    @Override
    public void deleteDepartmentById(int id) {
        departmentRepository.deleteDepartmentById(id);
    }

    @Override
    public DepartmentPreview updateDepartmentById(int id, DepartmentPreview department) {
        return departmentRepository.updateDepartmentById(id, department);
    }

    @Override
    public Department findStudentsByDepartment(int id) {
        return departmentRepository.findStudentsByDepartment(id);
    }
}
