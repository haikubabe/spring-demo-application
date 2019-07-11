package com.sree.dto;

import java.util.List;

public class DepartmentPreviewDto {

    private String name;
    private List<StudentPreviewDto> studentList;

    public DepartmentPreviewDto() {

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
