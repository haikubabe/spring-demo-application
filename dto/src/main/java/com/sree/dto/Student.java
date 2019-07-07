package com.sree.dto;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Student implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    private String course;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "dept_id", referencedColumnName = "id"),
            @JoinColumn(name = "dept_name", referencedColumnName = "name")
    })
    private Department department;

    public Student() {

    }

    public Student(String name, String course) {
        this.name = name;
        this.course = course;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}
