package com.sree.student.repository;

import com.sree.dto.Department;
import com.sree.dto.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {

    @Query("SELECT d FROM Student s INNER JOIN s.department d WHERE d.id = :departmentId")
    Department findDepartmentById(int departmentId);

    @Query("SELECT s FROM Department d INNER JOIN d.students s WHERE d.id = :departmentId")
    List<Student> findStudentsByDepartmentId(int departmentId);
}
