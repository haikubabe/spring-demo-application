package com.sree.department.repository.impl;

import com.sree.exception.DepartmentNotFoundException;
import com.sree.department.repository.DepartmentRepository;
import com.sree.department.model.Department;
import com.sree.preview.StudentPreview;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class DepartmentRepositoryImpl implements DepartmentRepository {

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

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

    @Override
    public Department findStudentByDepartment(int id) {
        Department department = getDepartmentById(id);
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<List<StudentPreview>> responseEntity =
                restTemplate.exchange("http://localhost:8080/departments/" + id + "/students", HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<StudentPreview>>() {
                        });
        List<StudentPreview> studentPreviewList = responseEntity.getBody();
        department.setStudentList(studentPreviewList);
        return department;
    }
}
