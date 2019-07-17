package com.sree.dto;

import java.util.HashMap;
import java.util.Map;

public class DepartmentPreviewDto {

    private int id;
    private String name;
    private Map<Integer, StudentPreviewDto> studentList;

    public DepartmentPreviewDto() {
        studentList = new HashMap<>();
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

    public Map<Integer, StudentPreviewDto> getStudentList() {
        return studentList;
    }

    public void setStudentList(Map<Integer, StudentPreviewDto> studentList) {
        this.studentList = studentList;
    }

}
