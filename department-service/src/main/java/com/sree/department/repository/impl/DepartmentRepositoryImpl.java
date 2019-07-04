package com.sree.department.repository.impl;

import com.sree.exception.DepartmentNotFoundException;
import com.sree.department.repository.DepartmentRepository;
import com.sree.department.model.Department;
import com.sree.preview.DepartmentPreview;
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

    private static Map<Integer, DepartmentPreview> departmentMap = new HashMap<>();

    @Override
    public Collection<DepartmentPreview> getAllDepartments() {
        return departmentMap.values();
    }

    @Override
    public DepartmentPreview getDepartmentById(int id) {
        if (departmentMap.get(id) == null) {
            throw new DepartmentNotFoundException("departmentId - " + id + " is not found");
        }
        return departmentMap.get(id);
    }

    @Override
    public DepartmentPreview addDepartment(DepartmentPreview department) {
        DepartmentPreview d = new DepartmentPreview(department.getId(), department.getName());
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
    public DepartmentPreview updateDepartmentById(int id, DepartmentPreview department) {
        if (departmentMap.get(id) == null) {
            throw new DepartmentNotFoundException("departmentId - " + id + " is not found, so it cannot be updated");
        }
        DepartmentPreview d = departmentMap.get(id);
        d.setId(department.getId());
        d.setName(department.getName());
        return d;
    }

    @Override
    public Department findStudentsByDepartment(int id) {
        DepartmentPreview departmentPreview = getDepartmentById(id);
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<List<StudentPreview>> responseEntity =
                restTemplate.exchange("http://localhost:8080/departments/" + id + "/students", HttpMethod.GET, null,
                        new ParameterizedTypeReference<List<StudentPreview>>() {
                        });
        List<StudentPreview> studentPreviewList = responseEntity.getBody();
        Department department = new Department(departmentPreview.getId(), departmentPreview.getName());
        department.setStudentList(studentPreviewList);
        return department;
    }
}
