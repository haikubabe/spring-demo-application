package com.sree.department.service.impl;

import com.sree.department.repository.DepartmentRepository;
import com.sree.department.service.DepartmentService;
import com.sree.dto.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    public List<Department> getAllDepartments() {
        /*List<DepartmentPreviewDto> department = new ArrayList<>();
        for (Department d : departmentRepository.findAll()) {
            DepartmentPreviewDto departmentPreviewDto = new DepartmentPreviewDto();
            departmentPreviewDto.setName(d.getName());
            List<Student> students = d.getStudents();
            departmentPreviewDto.setStudents(d.getStudents());
        }*/
        return departmentRepository.findAll();
    }

    @Override
    public void addDepartment(Department department) {
        departmentRepository.save(department);
    }

    /*@Override
    public DepartmentPreviewDto getDepartmentById(int id) {
        return departmentRepository.getDepartmentById(id);
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
    public DepartmentPreviewDto findStudentsByDepartment(int id) {
        return departmentRepository.findStudentsByDepartment(id);
    }*/
}
