package com.example.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;


import java.util.Map;

public class StudentsDto {

    @NotNull(message = "Student ID is mandatory.")
    @Positive(message = "Student ID must be a positive number")
    private int id;

    @NotNull(message = "Student name is mandatory.")
    @NotBlank(message = "Student name must be a non-empty string.")
    private String name;


    @NotNull(message = "Subject and marks are mandatory.")
    private Map<String, Integer> subjects;

    public StudentsDto(){}

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

    public Map<String, Integer> getSubjects() {
        return subjects;
    }

    public void setSubjects(Map<String, Integer> subjects) {
        this.subjects = subjects;
    }
}
