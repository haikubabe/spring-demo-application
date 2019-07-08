package com.sree.department.service.impl;

import com.sree.department.repository.DepartmentRepository;
import com.sree.department.service.DepartmentService;
import com.sree.dto.Department;
import com.sree.exception.DepartmentNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    @Override
    public Department getDepartmentById(int id) {
        Optional<Department> department = departmentRepository.findById(id);
        return department.orElseThrow(() -> new DepartmentNotFoundException("department with id " + id + " is not found"));
    }

    @Override
    public void addDepartment(Department department) {
        departmentRepository.save(department);
    }

    /*@Override
    public void deleteDepartmentById(int id) {
        departmentRepository.deleteDepartmentById(id);
    }

    @Override
    public Department updateDepartmentById(int id, Department department) {
        return departmentRepository.updateDepartmentById(id, department);
    }

    @Override
    public DepartmentPreviewDto findStudentsByDepartment(int id) {
        return departmentRepository.findStudentsByDepartment(id);
    }*/
}
