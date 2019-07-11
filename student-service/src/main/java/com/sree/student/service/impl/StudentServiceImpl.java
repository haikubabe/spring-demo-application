package com.sree.student.service.impl;

import com.sree.dto.Department;
import com.sree.dto.Student;
import com.sree.dto.StudentPreviewDto;
import com.sree.exception.DepartmentNotFoundException;
import com.sree.exception.DepartmentNullException;
import com.sree.exception.StudentNotFoundException;
import com.sree.student.repository.StudentRepository;
import com.sree.student.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    private Map<Integer, StudentPreviewDto> studentPreviewDtoMap = new HashMap<>();

    @Override
    public Collection<StudentPreviewDto> getAllStudents() {

        return Collections.unmodifiableMap(studentPreviewDtoMap).values();
    }

    @Override
    public StudentPreviewDto getStudentById(int studentId) {
        Optional<Student> s = studentRepository.findById(studentId);
        Student student = s.orElseThrow(() -> new StudentNotFoundException("student with id " + studentId + " is not found"));
        return studentPreviewDtoMap.get(student.getId());
    }

    @Override
    public void addStudent(Student student) {
        if (student.getDepartment() == null) {
            throw new DepartmentNullException("department cannot be null");
        }
        Department department;
        try {
            RestTemplate restTemplate = restTemplateBuilder.build();
            ResponseEntity<Department> responseEntity = restTemplate.exchange("http://localhost:8082/departments/" + student.getDepartment().getId(),
                    HttpMethod.GET, null, new ParameterizedTypeReference<Department>() {
                    });
            department = responseEntity.getBody();
        } catch (DepartmentNotFoundException e) {
            throw new DepartmentNotFoundException("department with id " + student.getDepartment().getId() + " is not found");
        }

        Student newStudent = new Student();
        newStudent.setName(student.getName());
        newStudent.setCourse(student.getCourse());
        //save the new student without department id
        studentRepository.save(newStudent);

        //create the student preview model
        StudentPreviewDto studentPreviewDto = new StudentPreviewDto();
        studentPreviewDto.setId(newStudent.getId());
        studentPreviewDto.setName(student.getName());
        studentPreviewDto.setCourse(student.getCourse());
        studentPreviewDto.setDepartmentName(department.getName());
        studentPreviewDtoMap.put(newStudent.getId(), studentPreviewDto);

        //find the newly added student
        Optional<Student> s = studentRepository.findById(newStudent.getId());
        Student existingStudent = s.get();
        department.addStudent(existingStudent);
        //update the existing student with the department id
        studentRepository.save(existingStudent);
    }

    @Override
    public void deleteStudentById(int studentId) {
        studentRepository.deleteById(studentId);
    }

    @Override
    public void updateStudentById(int studentId, Student student) {
        if (student.getDepartment() == null) {
            throw new DepartmentNullException("department cannot be null");
        }
        Optional<Student> s = studentRepository.findById(studentId);
        Student oldStudent = s.orElseThrow(() -> new StudentNotFoundException("student with id " + studentId + " is not found"));
        oldStudent.setName(student.getName());
        oldStudent.setCourse(student.getCourse());

        StudentPreviewDto studentPreviewDto = new StudentPreviewDto();
        studentPreviewDto.setId(studentId);
        studentPreviewDto.setName(oldStudent.getName());
        studentPreviewDto.setCourse(oldStudent.getCourse());

        try {
            RestTemplate restTemplate = restTemplateBuilder.build();
            ResponseEntity<Department> responseEntity = restTemplate.exchange("http://localhost:8082/departments/" + student.getDepartment().getId(),
                    HttpMethod.GET, null, new ParameterizedTypeReference<Department>() {
                    });
            Department department = responseEntity.getBody();
            department.addStudent(oldStudent);
            studentPreviewDto.setDepartmentName(department.getName());
        } catch (DepartmentNotFoundException e) {
            throw new DepartmentNotFoundException("department with id " + student.getDepartment().getId() + " is not found");
        }

        studentPreviewDtoMap.put(studentId, studentPreviewDto);
        studentRepository.save(oldStudent);
    }

    /*@Override
    public List<StudentPreviewDto> findStudentsByDepartment(int departmentId) {
        Department department = studentRepository.findDepartmentById(departmentId);
        if (department != null) {
            List<StudentPreviewDto> studentList = studentRepository.findStudentsByDepartmentId(departmentId);
            if (studentList.size() == 0) {
                throw new StudentNotFoundException("no students found in this department");
            } else {
                return studentList;
            }
        } else {
            throw new DepartmentNotFoundException("department with id " + departmentId + " doesn't exist");
        }
    }*/

}
