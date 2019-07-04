package com.sree.student.model;

public class Student {

    private int id;
    private String name;
    private String course;
    private int departmentId;

    public Student() {

    }

    public Student(int id, String name, String course, int departmentId) {
        this.id = id;
        this.name = name;
        this.course = course;
        this.departmentId = departmentId;
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

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }
}
