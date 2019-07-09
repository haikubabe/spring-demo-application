package com.sree.student.service.impl;

import com.sree.department.repository.DepartmentRepository;
import com.sree.dto.Department;
import com.sree.dto.Student;
import com.sree.dto.StudentDto;
import com.sree.exception.DepartmentNotFoundException;
import com.sree.exception.StudentNotFoundException;
import com.sree.student.repository.StudentRepository;
import com.sree.student.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @Override
    public List<Student> getAllStudents() {
        /*List<StudentPreviewDto> studentList = new ArrayList<>();
        for (Student s : studentRepository.findAll()) {
            StudentPreviewDto student = new StudentPreviewDto();
            student.setName(s.getName());
            student.setCourse(s.getCourse());
            student.setDepartmentName(s.getDepartment().getName());
            studentList.add(student);
        }
        return studentList;*/
        return studentRepository.findAll();
    }

    @Override
    public Student getStudentById(int studentId) {
        Optional<Student> student = studentRepository.findById(studentId);
        return student.orElseThrow(() -> new StudentNotFoundException("student with id " + studentId + " is not found"));
        /*StudentPreviewDto studentPreviewDto = new StudentPreviewDto();
        studentPreviewDto.setName(s.getName());
        studentPreviewDto.setCourse(s.getCourse());
        studentPreviewDto.setDepartmentName(s.getDepartment().getName());*/
    }

    @Override
    public void addStudent(Student student) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        ResponseEntity<Department> responseEntity = restTemplate.exchange("http://localhost:8082/departments/" + 1,
                HttpMethod.GET, null, new ParameterizedTypeReference<Department>(){});
        Department department = responseEntity.getBody();
        if (department == null) {
            throw new DepartmentNotFoundException("department with id " + 1 + " is not found");
        }
        student.setDepartment(department);
        department.getStudents().add(student);

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Department> requestEntity = new HttpEntity<>(department, requestHeaders);

        ResponseEntity<Void> responseEntity1 = restTemplate.exchange("http://localhost:8082/departments/" + 1,
                HttpMethod.PUT, requestEntity, Void.class);

        if (responseEntity1.getStatusCode() == HttpStatus.OK) {
            studentRepository.save(student);
        }
    }

    @Override
    public void deleteStudentById(int studentId) {
        studentRepository.deleteById(studentId);
    }

    @Override
    public void updateStudentById(int studentId, StudentDto studentDto) {
        Optional<Student> student = studentRepository.findById(studentId);
        if (!student.isPresent()) {
            throw new StudentNotFoundException("student with id " + student + " is not found");
        }
        studentRepository.save(convertFromDto(studentDto));
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

    private Student convertFromDto(StudentDto studentDto) {
        Student student = new Student(studentDto.getName(), studentDto.getCourse());
        return student;
    }

}
