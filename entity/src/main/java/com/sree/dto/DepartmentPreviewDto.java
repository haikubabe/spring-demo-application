package com.sree.dto;

import java.util.ArrayList;
import java.util.List;

public class DepartmentPreviewDto {

    private int id;
    private String name;
    private List<StudentPreviewDto> studentList;

    public DepartmentPreviewDto() {
        studentList = new ArrayList<>();
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

    public List<StudentPreviewDto> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<StudentPreviewDto> studentList) {
        this.studentList = studentList;
    }

}
