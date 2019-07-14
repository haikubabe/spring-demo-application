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

        //create the student preview model
        StudentPreviewDto studentPreviewDto = new StudentPreviewDto();
        studentPreviewDto.setId(newStudent.getId());
        studentPreviewDto.setName(student.getName());
        studentPreviewDto.setCourse(student.getCourse());
        studentPreviewDto.setDepartmentName(department.getName());
        studentPreviewDtoMap.put(newStudent.getId(), studentPreviewDto);


        department.addStudent(existingStudent);
        //update the existing student with the department id
//        studentRepository.save(existingStudent);

        List<Student> students = department.getStudents();
        List<Student> newStudents = new ArrayList<>();
        for (Student stu:students) {
            stu.setDepartment(null);
            newStudents.add(stu);
        }

        //update the department with the new student
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Department> requestEntity = new HttpEntity<>(department, requestHeaders);

        ResponseEntity<Void> responseEntity = restTemplate.exchange("http://localhost:8082/departments/" + department.getId(),
                HttpMethod.PUT, requestEntity, Void.class);
        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            System.out.println("Department with id " + department.getId() + " is updated");
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
        oldStudent.setName(student.getName());
        oldStudent.setCourse(student.getCourse());

        StudentPreviewDto studentPreviewDto = new StudentPreviewDto();
        int departmentId;

        //you are updating the student details in the same department
        if (student.getDepartment() == null || student.getDepartment().getId() == oldStudent.getDepartment().getId()) {
            departmentId = oldStudent.getDepartment().getId();
        }
        //you are updating the student details in the new department
        else {
            departmentId = student.getDepartment().getId();
        }

        try {
            RestTemplate restTemplate = restTemplateBuilder.build();
            ResponseEntity<Department> responseEntity = restTemplate.exchange("http://localhost:8082/departments/" + departmentId,
                    HttpMethod.GET, null, new ParameterizedTypeReference<Department>() {
                    });
            Department department = responseEntity.getBody();
            department.addStudent(oldStudent);
            studentPreviewDto.setDepartmentName(department.getName());
        } catch (DepartmentNotFoundException e) {
            throw new DepartmentNotFoundException("department with id " + student.getDepartment().getId() + " is not found");
        }

        //save the existing student with updated details
        studentRepository.save(oldStudent);

        //create student preview model
        studentPreviewDto.setId(oldStudent.getId());
        studentPreviewDto.setName(oldStudent.getName());
        studentPreviewDto.setCourse(oldStudent.getCourse());

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
