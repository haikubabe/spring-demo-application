package com.sree.student.service.impl;

import com.sree.dto.Department;
import com.sree.dto.Student;
import com.sree.dto.StudentDto;
import com.sree.exception.StudentNotFoundException;
import com.sree.student.repository.StudentRepository;
import com.sree.student.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

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
        return student.orElseThrow(() -> new StudentNotFoundException("student with id " + student + " is not found"));
        /*StudentPreviewDto studentPreviewDto = new StudentPreviewDto();
        studentPreviewDto.setName(s.getName());
        studentPreviewDto.setCourse(s.getCourse());
        studentPreviewDto.setDepartmentName(s.getDepartment().getName());*/
    }

    @Override
    public void addStudent(Student student) {
        Department department = studentRepository.findDepartmentById(student.getDepartment().getId());
        student.setDepartment(department);
        department.getStudents().add(student);
        studentRepository.save(student);
        /*if (department != null) {
            student.setDepartment(department);
            department.getStudents().add(student);
            studentRepository.save(student);
        } else {
            throw new DepartmentNotFoundException("department with id " + studentDto.getDepartmentId() + " doesn't exist");
        }*/

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
