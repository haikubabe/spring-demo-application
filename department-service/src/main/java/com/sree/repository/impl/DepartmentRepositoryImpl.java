package com.sree.repository.impl;

import com.sree.exception.DepartmentNotFoundException;
import com.sree.model.Department;
import com.sree.repository.DepartmentRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Repository
public class DepartmentRepositoryImpl implements DepartmentRepository {

    private static Map<Integer, Department> departmentMap = new HashMap<>();

    @Override
    public Collection<Department> getAllDepartments() {
        return departmentMap.values();
    }

    @Override
    public Department getDepartmentById(int id) {
        if (departmentMap.get(id) == null) {
            throw new DepartmentNotFoundException("departmentId - " + id + " is not found");
        }
        return departmentMap.get(id);
    }

    @Override
    public Department addDepartment(Department department) {
        Department d = new Department();
        d.setId(department.getId());
        d.setName(department.getName());
        departmentMap.put(d.getId(), d);
        return d;
    }

    @Override
    public void deleteDepartmentById(int id) {
        if (departmentMap.get(id) == null) {
            throw new DepartmentNotFoundException("departmentId - " + id + " is not found, so it cannot be deleted");
        }
        departmentMap.remove(id);
    }

    @Override
    public Department updateDepartmentById(int id, Department department) {
        if (departmentMap.get(id) == null) {
            throw new DepartmentNotFoundException("departmentId - " + id + " is not found, so it cannot be updated");
        }
        Department d = departmentMap.get(id);
        d.setId(department.getId());
        d.setName(department.getName());
        return d;
    }
}
