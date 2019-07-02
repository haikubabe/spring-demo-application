package com.sree.department.repository.impl;

import com.sree.department.exception.DepartmentNotFoundException;
import com.sree.department.model.Department;
import com.sree.department.repository.DepartmentRepository;
import com.sree.student.model.Student;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Repository
public class DepartmentRepositoryImpl implements DepartmentRepository {

    private static Map<Integer,Department> departmentMap = new HashMap<>();

    static {
        departmentMap.put(1, new Department(1, "Computer Science And Technology"));
        departmentMap.put(2, new Department(2, "Information Technology"));
        departmentMap.put(3, new Department(3, "Electronics And Telecommunication Engineering"));
        departmentMap.put(4, new Department(4, "Electrical Engineering"));
    }

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

    }

    @Override
    public Collection<Student> getAllStudentsInDepartment(int id) {
        return null;
    }

    @Override
    public Student getStudentInDepartment(int id, int rollNo) {
        return null;
    }
}
