package com.example.facultyofscience.Models;

public class SubjectGrade {
    private int hours;
    private String grade, name;

    public SubjectGrade(int hours, String grade, String name) {
        this.hours = hours;
        this.grade = grade;
        this.name = name;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getName() {
        return name.isEmpty() ? "مادة" : name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
