package com.example.entity;


import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "STUDENT")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Subject> subjects = new ArrayList<>();

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

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }

    public Map<String, Integer> getSubjectsMap() {
        Map<String, Integer> subjectMap = new HashMap<>();
        for (Subject subject : subjects) {
            subjectMap.put(subject.getSubjectName(), subject.getMarks());
        }
        return subjectMap;
    }

    public void addOrUpdateSubject(String subjectName, int marks) {
        for (Subject subject : subjects) {
            if (subject.getSubjectName().equals(subjectName)) {
                subject.setMarks(marks);
                return;
            }
        }
        Subject newSubject = new Subject(subjectName, marks, this); // pass the student reference
        subjects.add(newSubject);
    }
}

