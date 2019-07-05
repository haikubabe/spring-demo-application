package com.sree.preview;

import java.util.List;

public class DepartmentPreview {

    private String name;
    private List<StudentPreview> studentPreviewList;

    public DepartmentPreview(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<StudentPreview> getStudentPreviewList() {
        return studentPreviewList;
    }

    public void setStudentPreviewList(List<StudentPreview> studentPreviewList) {
        this.studentPreviewList = studentPreviewList;
    }
}
