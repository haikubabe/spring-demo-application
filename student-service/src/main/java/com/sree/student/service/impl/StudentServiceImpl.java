package com.sree.student.service.impl;

import com.sree.department.repository.DepartmentRepository;
import com.sree.dto.Department;
import com.sree.dto.Student;
import com.sree.dto.StudentDto;
import com.sree.exception.StudentNotFoundException;
import com.sree.student.repository.StudentRepository;
import com.sree.student.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private EntityManager entityManager;

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
    public void addStudent(Student student, int departmentId) {
        Department department = entityManager.find(Department.class, departmentId);
//        Department department = d.orElseThrow(() -> new DepartmentNotFoundException("department with id " + departmentId + " is not found"));
        student.setDepartment(department);
        department.getStudents().add(student);
        studentRepository.save(student);
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
