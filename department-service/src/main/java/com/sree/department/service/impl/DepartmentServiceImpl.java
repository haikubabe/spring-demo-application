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
    }*/

    @Override
    public void updateDepartmentById(int id, Department department) {
        Optional<Department> d = departmentRepository.findById(id);
        Department department1 = d.orElseThrow(() -> new DepartmentNotFoundException("department with id " + id + " is not found"));
        department1.setName(department.getName());
        departmentRepository.save(department1);
    }

    /*@Override
    public DepartmentPreviewDto findStudentsByDepartment(int id) {
        return departmentRepository.findStudentsByDepartment(id);
    }*/
}
