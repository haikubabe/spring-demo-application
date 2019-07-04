package com.sree.department.model;

import com.sree.preview.StudentPreview;

import java.util.ArrayList;
import java.util.List;

public class Department {

    private int id;
    private String name;
    private List<StudentPreview> studentList = new ArrayList<>();

    public Department() {

    }

    public Department(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<StudentPreview> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<StudentPreview> studentList) {
        this.studentList = studentList;
    }
}
