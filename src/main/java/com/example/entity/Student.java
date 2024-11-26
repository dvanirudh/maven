package com.example.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


import java.util.Map;

@Entity
public class Student {

    
    @Id
    private int id;

    @NotNull(message = "Student name must not be null")
    @Size(min = 1, message = "Student name must not be empty")
    private String name;

    @ElementCollection
    @CollectionTable(name = "entity_map", joinColumns = @JoinColumn(name = "entity_id"))
    @MapKeyColumn(name = "key_column")
    @Column(name = "value_column")
    private Map<String, Integer> subjects;

    Student(){}

    public Student(int id, String name, Map<String, Integer> subjects) {


        this.id = id;
        this.name = name;
        this.subjects = subjects;
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

    public Map<String, Integer> getSubjects() {
        return subjects;
    }

    public void setSubjects(Map<String, Integer> subjects) {
        this.subjects = subjects;
    }
}
