package com.sree.department.service.impl;

import com.sree.department.repository.DepartmentRepository;
import com.sree.department.service.DepartmentService;
import com.sree.dto.Department;
import com.sree.dto.DepartmentPreviewDto;
import com.sree.dto.Student;
import com.sree.dto.StudentPreviewDto;
import com.sree.exception.DepartmentNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    private Map<Integer, DepartmentPreviewDto> departmentPreviewDtoHashMap = new HashMap<>();

    @Override
    public Collection<DepartmentPreviewDto> getAllDepartments() {
        return Collections.unmodifiableMap(departmentPreviewDtoHashMap).values();
    }

    @Override
    public DepartmentPreviewDto getDepartmentById(int id) {
        Optional<Department> d = departmentRepository.findById(id);
        Department department = d.orElseThrow(() -> new DepartmentNotFoundException("department with id " + id + " is not found"));
        return departmentPreviewDtoHashMap.get(department.getId());
    }

    @Override
    public void addDepartment(Department department) {
        departmentRepository.save(department);
        DepartmentPreviewDto departmentPreviewDto = new DepartmentPreviewDto();
        departmentPreviewDto.setId(department.getId());
        departmentPreviewDto.setName(department.getName());
        departmentPreviewDtoHashMap.put(department.getId(), departmentPreviewDto);


    }

    @Override
    public void deleteDepartmentById(int id) {
        departmentRepository.deleteById(id);
    }

    /**
     * when this update method is invoked from department service do not pass the student list in the department object
     * but when this method is invoked from the student service pass the student list
     *
     * @param id
     * @param department
     */
    @Override
    public void updateDepartmentById(int id, Department department) {
        //find the existing department
        Optional<Department> d = departmentRepository.findById(id);
        Department oldDepartment = d.orElseThrow(() -> new DepartmentNotFoundException("department with id " + id + " is not found"));
        oldDepartment.setName(department.getName());
        departmentRepository.save(oldDepartment);

        DepartmentPreviewDto departmentPreviewDto = departmentPreviewDtoHashMap.get(id);
        departmentPreviewDto.setId(oldDepartment.getId());
        departmentPreviewDto.setName(oldDepartment.getName());
        Map<Integer, StudentPreviewDto> studentList = departmentPreviewDto.getStudentList();

        for (Student student : department.getStudents()) {
            StudentPreviewDto studentPreviewDto = new StudentPreviewDto();
            studentPreviewDto.setId(student.getId());
            studentPreviewDto.setName(student.getName());
            studentPreviewDto.setCourse(student.getCourse());
            studentPreviewDto.setDepartmentName(department.getName());
            studentList.put(student.getId(), studentPreviewDto);
        }

        departmentPreviewDto.setStudentList(studentList);
        departmentPreviewDtoHashMap.put(id, departmentPreviewDto);
    }

    /*@Override
    public DepartmentPreviewDto findStudentsByDepartment(int id) {
        return departmentRepository.findStudentsByDepartment(id);
    }*/

}
