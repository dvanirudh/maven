package com.example.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.Map;


public class StudentsDto {

    private Long id;

    @NotBlank(message = "Please provide firstName while add the student Data.")
    private String firstName;

    @NotBlank(message = "Student name must be a non-empty string.")
    private String lastName;

    @NotNull(message = "Date of birth is mandatory.")
    private LocalDate birthDate;


    private Map<String, Integer> subjects;


    public StudentsDto(){}

    public StudentsDto(Long id, String firstName, String lastName, LocalDate birthDate, Map<String, Integer> subjects) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.subjects = subjects;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Map<String, Integer> getSubjects() {
        return subjects;
    }

    public void setSubjects(Map<String, Integer> subjects) {
        this.subjects = subjects;
    }
}

