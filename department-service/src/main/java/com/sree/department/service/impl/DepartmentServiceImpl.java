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
        //while adding the department do not set the list of students in it
        department.setStudents(null);
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

        DepartmentPreviewDto departmentPreviewDto;
        List<StudentPreviewDto> students = convertToPreviewDto(department.getStudents(), oldDepartment.getName());

        if (departmentPreviewDtoHashMap.get(id) != null) {
            departmentPreviewDto = departmentPreviewDtoHashMap.get(id);
            departmentPreviewDto.setId(oldDepartment.getId());
            departmentPreviewDto.setName(oldDepartment.getName());
            List<StudentPreviewDto> oldStudents = departmentPreviewDto.getStudentList();
            for (StudentPreviewDto s : students) {
                oldStudents.add(s);
            }
            departmentPreviewDto.setStudentList(oldStudents);
        } else {
            departmentPreviewDto = new DepartmentPreviewDto();
            departmentPreviewDto.setId(oldDepartment.getId());
            departmentPreviewDto.setName(oldDepartment.getName());
            departmentPreviewDto.setStudentList(students);
        }

        departmentPreviewDtoHashMap.put(id, departmentPreviewDto);
    }

    /*@Override
    public DepartmentPreviewDto findStudentsByDepartment(int id) {
        return departmentRepository.findStudentsByDepartment(id);
    }*/

    private List<StudentPreviewDto> convertToPreviewDto(List<Student> students, String departmentName) {
        List<StudentPreviewDto> studentPreviewDtoList = new ArrayList<>();
        for (Student student : students) {
            StudentPreviewDto s = new StudentPreviewDto();
            s.setId(student.getId());
            s.setName(student.getName());
            s.setCourse(student.getCourse());
            s.setDepartmentName(departmentName);
            studentPreviewDtoList.add(s);
        }
        return studentPreviewDtoList;
    }
}
