package com.sree.student.repository;

import com.sree.dto.Student;
import org.springframework.data.jpa.repository.JpaRepository;


public interface StudentRepository extends JpaRepository<Student, Integer> {

  /*  @Query("SELECT s FROM Department d INNER JOIN d.students s WHERE d.id = :departmentId")
    List<Student> findStudentsByDepartmentId(int departmentId);*/


}
