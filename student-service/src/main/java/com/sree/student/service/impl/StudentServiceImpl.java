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
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

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
        RestTemplate restTemplate = restTemplateBuilder.build();
        try {
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
        department.addStudent(newStudent);
        //update the existing student with the department id
        studentRepository.save(newStudent);

        List<Student> students = department.getStudents();
        for (Student stu : students) {
            stu.setDepartment(null);
        }

        //update the department with the new student
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Department> requestEntity = new HttpEntity<>(department, requestHeaders);
        ResponseEntity<Void> responseEntity = restTemplate.exchange("http://localhost:8082/departments/" + department.getId(),
                HttpMethod.PUT, requestEntity, Void.class);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            System.out.println("Department with id " + department.getId() + " is updated");
            //create the student preview model
            StudentPreviewDto studentPreviewDto = new StudentPreviewDto();
            studentPreviewDto.setId(newStudent.getId());
            studentPreviewDto.setName(student.getName());
            studentPreviewDto.setCourse(student.getCourse());
            studentPreviewDto.setDepartmentName(department.getName());
            studentPreviewDtoMap.put(newStudent.getId(), studentPreviewDto);
        }
    }

    @Override
    public void deleteStudentById(int studentId) {
        studentRepository.deleteById(studentId);
        //delete that particular student from the department it belongs to
    }

    @Override
    public void updateStudentById(int studentId, Student student) {
        //find the existing student
        Optional<Student> s = studentRepository.findById(studentId);
        Student oldStudent = s.orElseThrow(() -> new StudentNotFoundException("student with id " + studentId + " is not found"));

        StudentPreviewDto studentPreviewDto = new StudentPreviewDto();
        int departmentId;
        Department department;
        RestTemplate restTemplate = restTemplateBuilder.build();
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);

        if (student.getDepartment() == null) {
            departmentId = oldStudent.getDepartment().getId();
        } else {
            departmentId = student.getDepartment().getId();
        }

        try {
            ResponseEntity<Department> responseEntity = restTemplate.exchange("http://localhost:8082/departments/" + departmentId,
                    HttpMethod.GET, null, new ParameterizedTypeReference<Department>() {
                    });
            department = responseEntity.getBody();
        } catch (DepartmentNotFoundException e) {
            throw new DepartmentNotFoundException("department with id " + student.getDepartment().getId() + " is not found");
        }

        //you are updating the student details in the new department
        if (student.getDepartment().getId() != oldStudent.getDepartment().getId()) {
            Department oldDepartment;
            try {
                ResponseEntity<Department> responseEntity = restTemplate.exchange("http://localhost:8082/departments/" + oldStudent.getDepartment().getId(),
                        HttpMethod.GET, null, new ParameterizedTypeReference<Department>() {
                        });
                oldDepartment = responseEntity.getBody();
            } catch (DepartmentNotFoundException e) {
                throw new DepartmentNotFoundException("department with id " + student.getDepartment().getId() + " is not found");
            }

            oldDepartment.removeStudent(oldStudent);
            //delete the student from the previous department
            HttpEntity<Department> requestEntity = new HttpEntity<>(oldDepartment, requestHeaders);
            ResponseEntity<Void> responseEntity = restTemplate.exchange("http://localhost:8082/departments/" + oldDepartment.getId(),
                    HttpMethod.PUT, requestEntity, Void.class);
        }

        oldStudent.setName(student.getName());
        oldStudent.setCourse(student.getCourse());
        department.addStudent(oldStudent);
        studentRepository.save(oldStudent);

        List<Student> students = department.getStudents();
        for (Student stu : students) {
            stu.setDepartment(null);
        }

        //update the department with the new student
        HttpEntity<Department> requestEntity = new HttpEntity<>(department, requestHeaders);
        ResponseEntity<Void> responseEntity = restTemplate.exchange("http://localhost:8082/departments/" + department.getId(),
                HttpMethod.PUT, requestEntity, Void.class);

        //create student preview model
        studentPreviewDto.setId(oldStudent.getId());
        studentPreviewDto.setName(oldStudent.getName());
        studentPreviewDto.setCourse(oldStudent.getCourse());
        studentPreviewDto.setDepartmentName(department.getName());
        studentPreviewDtoMap.put(studentId, studentPreviewDto);
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
